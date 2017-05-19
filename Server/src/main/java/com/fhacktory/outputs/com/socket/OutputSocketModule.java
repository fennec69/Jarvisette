package com.fhacktory.outputs.com.socket;

import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.outputs.com.socket.endpoints.OutputSocketEndpoint;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by farid on 19/05/2017.
 */
public class OutputSocketModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<WebsocketEndpoint> websocketEndpointMultibinder = Multibinder.newSetBinder(binder(), WebsocketEndpoint.class);
        websocketEndpointMultibinder.addBinding().to(OutputSocketEndpoint.class);
    }
}
