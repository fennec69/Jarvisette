package com.fhacktory.plugins.inputs.audio;

import com.fhacktory.data.CommandAction;
import com.fhacktory.data.ActionType;
import com.fhacktory.data.Location;
import com.fhacktory.common.CommandActionDetector;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.plugins.inputs.audio.speech_recognition.SpeechRecognizer;
import com.fhacktory.utils.SignalUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;

/**
 * Created by farid on 14/05/2017.
 */
@Singleton
public class AudioMessageProcessor {

    private static final int TIMER_DELAY_IN_MS = 500;

    @Inject
    private CommandActionDetector mCommandActionDetector;
    @Inject
    private SpeechRecognizer mSpeechRecognizer;
    @Inject
    private ActionProcessor mActionProcessor;

    private Map<String, AudioMessage> mSignalBuffer;
    private Timer mTimer;

    public AudioMessageProcessor() {
        mSignalBuffer = new TreeMap<>();
    }

    public synchronized void onAudioMessageReceived(String uuid, String signal) {
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
        int rms = (int) SignalUtils.calculateRMS(signal);
        AudioMessage audioMessage = new AudioMessage(uuid, signal, rms);
        mSignalBuffer.put(uuid, audioMessage);
    }

    private void processMessage(Map<String, AudioMessage> audioMessages) {
        AudioMessage mostPowerfulMessage = getMostPowerfulAudioMessage(audioMessages);
        //TODO: Try to start this on the first message for time saving
        String query = mSpeechRecognizer.speechToText(mostPowerfulMessage.getSignal());
        CommandAction commandAction = mCommandActionDetector.getAction(query);
        commandAction.setResponseType(ActionType.TTS);
        Location location = calculateAudioLocation(audioMessages);
        mActionProcessor.processOutput(commandAction, commandAction.getResponseSpeech(), location);
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
