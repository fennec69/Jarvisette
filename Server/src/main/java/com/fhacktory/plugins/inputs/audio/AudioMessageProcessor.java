package com.fhacktory.plugins.inputs.audio;

import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.data.CommandAction;
import com.fhacktory.data.ActionType;
import com.fhacktory.data.Location;
import com.fhacktory.common.CommandActionDetector;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.plugins.inputs.audio.speech_recognition.SpeechRecognizer;
import com.fhacktory.utils.SignalUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;

/**
 * Created by farid on 14/05/2017.
 */
@Singleton
public class AudioMessageProcessor implements InputMessageProcessor {

    private static final int TIMER_DELAY_IN_MS = 500;

    @Inject
    private CommandActionDetector mCommandActionDetector;
    @Inject
    private SpeechRecognizer mSpeechRecognizer;
    @Inject
    private ActionProcessor mActionProcessor;

    private Map<String, AudioMessage> mSignalBuffer;
    private Timer mTimer;
    private Gson mGson;

    public AudioMessageProcessor() {
        mSignalBuffer = new TreeMap<>();
        mGson = new Gson();
    }

    @Override
    public synchronized void process(String message, String inputUuid) {
        AudioSignalDto audioSignalDto = mGson.fromJson(message, AudioSignalDto.class);
        if(mSignalBuffer.isEmpty()) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    processMessage(new TreeMap<>(mSignalBuffer));
                    mSignalBuffer.clear();
                }
            }, TIMER_DELAY_IN_MS);
        }
        int rms = (int) SignalUtils.calculateRMS(audioSignalDto.getSignal());
        AudioMessage audioMessage = new AudioMessage(inputUuid, audioSignalDto.getSignal(), rms);
        audioMessage.setResponseType(audioSignalDto.getResponseType());
        audioMessage.setResponseUuid(audioSignalDto.getResponseUUID());
        mSignalBuffer.put(inputUuid, audioMessage);
    }

    private void processMessage(Map<String, AudioMessage> audioMessages) {
        //TODO: Try to start this on the first message for time saving
        AudioMessage mostPowerfulMessage = getMostPowerfulAudioMessage(audioMessages);
        String query = mSpeechRecognizer.speechToText(mostPowerfulMessage.getSignal());
        CommandAction commandAction = mCommandActionDetector.getAction(query);
        commandAction.setResponseType(mostPowerfulMessage.getResponseType());
        commandAction.setResponseUuid(mostPowerfulMessage.getResponseUuid());
        if(commandAction.getResponseType() == null) commandAction.setResponseType(ActionType.TTS);
        Location location = calculateAudioLocation(audioMessages);
        mActionProcessor.processOutput(commandAction, location);
    }

    private Location calculateAudioLocation(Map<String, AudioMessage> audioMessages) {
        double sum = 0;
        Location location = new Location();
        for (AudioMessage audioMessage : audioMessages.values()) {
            sum += audioMessage.getRmsLevel();
        }
        for (String uuid : audioMessages.keySet()) {
            float ratio = (float) (100 * (audioMessages.get(uuid).getRmsLevel() / sum));
            location.addLocation(uuid, ratio);
        }

        return location;
    }

    private AudioMessage getMostPowerfulAudioMessage(Map<String, AudioMessage> audioMessages) {
        AudioMessage result = null;
        for (AudioMessage audioMessage : audioMessages.values()) {
            if (result == null) result = audioMessage;
            else if (audioMessage.getRmsLevel() > result.getRmsLevel()) {
                result = audioMessage;
            }
        }
        return result;
    }
}
