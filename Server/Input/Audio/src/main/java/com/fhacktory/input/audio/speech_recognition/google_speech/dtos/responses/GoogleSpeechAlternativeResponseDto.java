package com.fhacktory.input.audio.speech_recognition.google_speech.dtos.responses;

/**
 * Created by farid on 13/05/2017.
 */
public class GoogleSpeechAlternativeResponseDto {

    private String transcript;
    private float confidence;

    public String getTranscript() {
        return transcript;
    }

    public float getConfidence() {
        return confidence;
    }
}
