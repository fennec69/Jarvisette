package com.fhacktory.input.audio.speech_recognition.google_speech.config;

import com.fhacktory.input.audio.speech_recognition.SpeechRecognizer;
import com.fhacktory.input.audio.speech_recognition.google_speech.GoogleSpeechRecognizer;
import com.google.inject.AbstractModule;

/**
 * Created by farid on 19/05/2017.
 */
public class GoogleSpeechModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SpeechRecognizer.class).to(GoogleSpeechRecognizer.class);
    }
}
