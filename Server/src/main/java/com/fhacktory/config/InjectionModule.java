package com.fhacktory.config;

import com.fhacktory.input.audio.com.endpoints.InputRestEndpoint;
import com.fhacktory.input.audio.com.endpoints.InputSocketEndpoint;
import com.fhacktory.outputs.com.endpoints.OutputSocketEndpoint;
import com.google.inject.AbstractModule;

/**
 * Created by farid on 13/05/2017.
 */
public class InjectionModule extends AbstractModule {

    public InjectionModule() {}

    @Override
    protected void configure() {
        initEndpoints();
    }

    private void initEndpoints() {
        bind(OutputSocketEndpoint.class);
        bind(InputSocketEndpoint.class);
        bind(InputRestEndpoint.class);
    }
}