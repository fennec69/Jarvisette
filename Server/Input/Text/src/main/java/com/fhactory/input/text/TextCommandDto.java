package com.fhactory.input.text;

/**
 * Created by farid on 13/05/2017.
 */
public class TextCommandDto {

    private String responseUUID;
    private String responseType;
    private String text;

    public String getText() {
        return text;
    }

    public String getResponseUUID() {
        return responseUUID;
    }

    public String getResponseType() {
        return responseType;
    }
}
