package com.fhacktory.action_detector.apiai.dtos;

/**
 * Created by farid on 14/05/2017.
 */
public class ApiAiRequestDto {

    private String query;
    private String lang;
    private String sessionId;

    public ApiAiRequestDto(String query) {
        this.query = query;
        this.lang = "fr";
        this.sessionId = "1234567";
    }
}
