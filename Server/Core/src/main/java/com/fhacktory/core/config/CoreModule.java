package com.fhacktory.core.config;

import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.core.ComInterfaceManager;
import com.fhacktory.core.DeviceManager;
import com.fhacktory.core.OutputActionProcessor;
import com.fhacktory.core.com.SocketEndpoint;
import com.fhacktory.core.data.conf_file.ConfigFileDeviceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by farid on 31/05/2017.
 */
public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ActionProcessor.class).to(OutputActionProcessor.class);
        bind(ComInterfaceManager.class).in(Singleton.class);
        bind(DeviceManager.class).to(ConfigFileDeviceManager.class);

        Multibinder<WebsocketEndpoint> websocketEndpointMultibinder = Multibinder.newSetBinder(binder(), WebsocketEndpoint.class);
        websocketEndpointMultibinder.addBinding().to(SocketEndpoint.class);
    }
}
