package com.fhacktory.speech_recognition.google_speech.dtos.responses;

import java.util.List;

/**
 * Created by farid on 13/05/2017.
 */
public class GoogleSpeechAlternativeListResponseDto {

    private List<GoogleSpeechAlternativeResponseDto> alternatives;

    public List<GoogleSpeechAlternativeResponseDto> getAlternatives() {
        return alternatives;
    }
}
