package com.fhacktory.input.audio.command;

/**
 * Created by farid on 14/05/2017.
 */
public class AudioMessage {

    private String mSignal;
    private String mUuid;
    private String mResponseUuid;
    private String mResponseType;
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

    public String getResponseUuid() {
        return mResponseUuid;
    }

    public String getResponseType() {
        return mResponseType;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public void setResponseUuid(String responseUuid) {
        mResponseUuid = responseUuid;
    }

    public void setResponseType(String responseType) {
        mResponseType = responseType;
    }
}
