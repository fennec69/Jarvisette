package com.fhacktory.plugins.inputs.text;

import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.action_detector.apiai.config.ApiAiModule;
import com.fhacktory.plugins.inputs.text.com.endpoints.TextInputRestEndpoint;
import com.fhacktory.plugins.inputs.text.com.endpoints.TextInputSocketEndpoint;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by fkilani on 21/05/2017.
 */
public class TextInputModule extends AbstractModule {

    private boolean mUseRestEndpoint;
    private boolean mUseWebsocketEnpoint;

    public TextInputModule(boolean rest, boolean websocket) {
        mUseRestEndpoint = rest;
        mUseWebsocketEnpoint = websocket;
    }

    @Override
    protected void configure() {
        install(new ApiAiModule());
        bind(TextMessageProcessor.class);
        if(mUseWebsocketEnpoint) {
            bind(TextInputSocketEndpoint.class);
            Multibinder<WebsocketEndpoint> websocketEndpointMultibinder = Multibinder.newSetBinder(binder(), WebsocketEndpoint.class);
            websocketEndpointMultibinder.addBinding().to(TextInputSocketEndpoint.class);
        }
        if(mUseRestEndpoint) bind(TextInputRestEndpoint.class);
    }
}
