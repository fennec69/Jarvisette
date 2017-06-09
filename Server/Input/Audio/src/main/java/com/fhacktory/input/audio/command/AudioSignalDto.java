package com.fhacktory.input.audio.command;

/**
 * Created by farid on 13/05/2017.
 */
public class AudioSignalDto {

    private String signal;
    private String responseUUID;
    private String responseType;

    public String getSignal() {
        return signal;
    }

    public String getResponseUUID() {
        return responseUUID;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public void setResponseUUID(String responseUUID) {
        this.responseUUID = responseUUID;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
