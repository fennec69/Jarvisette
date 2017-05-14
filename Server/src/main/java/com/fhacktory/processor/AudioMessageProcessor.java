package com.fhacktory.processor;

import com.fhacktory.utils.SignalUtils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
public class AudioMessageProcessor {

    private static final int TIMER_DELAY_IN_MS = 500;

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
                    for(String uuid : mSignalBuffer.keySet()) {
                        System.out.println("Message received: " + uuid);
                    }
                    OutputActionProcessor outputActionFinder = new OutputActionProcessor();
                    outputActionFinder.processAudioMessages(new TreeMap<>(mSignalBuffer));
                    mSignalBuffer.clear();
                }
            }, TIMER_DELAY_IN_MS);
        }
        int rms = (int) SignalUtils.calculateRMS(signal);
        AudioMessage audioMessage = new AudioMessage(uuid, signal, rms);
        mSignalBuffer.put(uuid, audioMessage);
    }
}
