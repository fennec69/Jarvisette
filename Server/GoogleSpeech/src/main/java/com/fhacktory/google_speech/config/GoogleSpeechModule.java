package com.fhacktory.google_speech.config;

import com.fhacktory.common.SpeechRecognizer;
import com.fhacktory.google_speech.GoogleSpeechRecognizer;
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
