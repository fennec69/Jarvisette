package com.fhacktory.input.audio.raw;

/**
 * Created by farid on 06/06/2017.
 */
public class RawAudioSignalDto {

    private byte[] rawSignal;
    private String responseUUID;
    private String responseType;

    public byte[] getRawSignal() {
        return rawSignal;
    }

    public String getResponseUUID() {
        return responseUUID;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setRawSignal(byte[] rawSignal) {
        this.rawSignal = rawSignal;
    }

    public void setResponseUUID(String responseUUID) {
        this.responseUUID = responseUUID;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
