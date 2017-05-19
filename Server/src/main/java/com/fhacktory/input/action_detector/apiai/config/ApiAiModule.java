package com.fhacktory.input.action_detector.apiai.config;

import com.fhacktory.input.action_detector.apiai.ApiAiActionDetector;
import com.fhacktory.input.action_detector.CommandActionDetector;
import com.google.inject.AbstractModule;

/**
 * Created by farid on 19/05/2017.
 */
public class ApiAiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandActionDetector.class).to(ApiAiActionDetector.class);
    }
}
