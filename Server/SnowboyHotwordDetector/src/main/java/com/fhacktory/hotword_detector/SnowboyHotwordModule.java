package com.fhacktory.hotword_detector;

import com.fhacktory.common.HotwordDetector;
import com.google.inject.AbstractModule;

/**
 * Created by farid on 09/06/2017.
 */
public class SnowboyHotwordModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HotwordDetector.class).to(SnowboyHotwordDetector.class);
    }
}
