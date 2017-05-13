package com.fhacktory.config;

import com.fhacktory.outputs.endpoints.OutputSocketEndpoint;
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
    }
}