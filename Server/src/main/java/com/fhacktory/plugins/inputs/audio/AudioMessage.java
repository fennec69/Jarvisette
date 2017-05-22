package com.fhacktory.plugins.inputs.audio;

/**
 * Created by farid on 14/05/2017.
 */
public class AudioMessage {

    private String mSignal;
    private String mUuid;
    private int mRmsLevel;

    public AudioMessage(String uuid, String signal, int rmsLevel) {
        mSignal = signal;
        mUuid = uuid;
        mRmsLevel = rmsLevel;
    }

    public String getSignal() {
        return mSignal;
    }

    public String getUuid() {
        return mUuid;
    }

    public int getRmsLevel() {
        return mRmsLevel;
    }
}
