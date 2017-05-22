package com.fhacktory.input.text;

import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.input.action_detector.apiai.config.ApiAiModule;
import com.fhacktory.input.text.com.endpoints.TextInputSocketEndpoint;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by fkilani on 21/05/2017.
 */
public class TextInputModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ApiAiModule());
        bind(TextMessageProcessor.class);
        bind(TextInputSocketEndpoint.class);
        Multibinder<WebsocketEndpoint> websocketEndpointMultibinder = Multibinder.newSetBinder(binder(), WebsocketEndpoint.class);
        websocketEndpointMultibinder.addBinding().to(TextInputSocketEndpoint.class);
    }
}
