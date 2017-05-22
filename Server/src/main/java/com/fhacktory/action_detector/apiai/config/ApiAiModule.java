package com.fhacktory.action_detector.apiai.config;

import com.fhacktory.action_detector.apiai.ApiAiActionDetector;
import com.fhacktory.common.CommandActionDetector;
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
