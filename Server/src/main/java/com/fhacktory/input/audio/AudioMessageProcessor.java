package com.fhacktory.input.audio;

import com.fhacktory.data.entities.CommandAction;
import com.fhacktory.action_detector.CommandActionDetector;
import com.fhacktory.data.entities.ActionType;
import com.fhacktory.data.entities.Location;
import com.fhacktory.outputs.OutputActionProcessor;
import com.fhacktory.speech_recognition.SpeechRecognizer;
import com.fhacktory.utils.SignalUtils;
import com.google.inject.Inject;

import java.util.*;

/**
 * Created by farid on 14/05/2017.
 */
public class AudioMessageProcessor {

    private static final int TIMER_DELAY_IN_MS = 500;

    @Inject
    private CommandActionDetector mCommandActionDetector;
    @Inject
    private SpeechRecognizer mSpeechRecognizer;
    @Inject
    private OutputActionProcessor outputActionProcessor;

    private static final AudioMessageProcessor INSTANCE = new AudioMessageProcessor();

    private Map<String, AudioMessage> mSignalBuffer;
    private Timer mTimer;

    private AudioMessageProcessor() {
        mSignalBuffer = new TreeMap<>();
    }

    public static AudioMessageProcessor getInstance() {
        return INSTANCE;
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
        Location location = calculateAudioLocation(audioMessages);
        outputActionProcessor.processOutput(commandAction.getAction(), commandAction.getParameters(), commandAction.getResponseSpeech(), ActionType.TTS, location);
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
