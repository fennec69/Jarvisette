package com.fhacktory.speech_recognition.google_speech.dtos.responses;

import java.util.List;

/**
 * Created by farid on 13/05/2017.
 */
public class GoogleSpeechResponseDto {

    private List<GoogleSpeechAlternativeListResponseDto> results;

    public List<GoogleSpeechAlternativeListResponseDto> getResults() {
        return results;
    }
}
