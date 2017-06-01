package com.fhacktory.input.audio.speech_recognition.google_speech.dtos.requests;

/**
 * Created by farid on 13/05/2017.
 */
public class GoogleSpeechRequestDto {

    private GoogleSpeechConfigRequestDto config;
    private GoogleSpeechAudioRequestDto audio;

    public GoogleSpeechRequestDto(GoogleSpeechConfigRequestDto config, GoogleSpeechAudioRequestDto audio) {
        this.config = config;
        this.audio = audio;
    }
}
