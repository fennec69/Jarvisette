package com.fhacktory.core.config;

import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.core.ComInterfaceManager;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.core.com.SocketEndpoint;
import com.fhacktory.plugins.inputs.text.TextInputModule;
import com.fhacktory.core.DeviceManager;
import com.fhacktory.plugins.inputs.audio.AudioInputModule;
import com.fhacktory.core.OutputActionProcessor;
import com.fhacktory.core.data.conf_file.ConfigFileDeviceManager;
import com.fhacktory.plugins.outputs.lights.LightOutputModule;
import com.fhacktory.plugins.outputs.text.TextOutputModule;
import com.fhacktory.plugins.outputs.tts.TtsOutputModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by farid on 13/05/2017.
 */
public class InjectionModule extends AbstractModule {

    public InjectionModule() {}

    @Override
    protected void configure() {
        init();
    }

    private void init() {
        //Input plugins
        install(new AudioInputModule());
        install(new TextInputModule());

        //Output plugins
        install(new LightOutputModule());
        install(new TextOutputModule());
        install(new TtsOutputModule());

        //Core
        bind(ActionProcessor.class).to(OutputActionProcessor.class);
        bind(ComInterfaceManager.class).in(Singleton.class);
        bind(DeviceManager.class).to(ConfigFileDeviceManager.class);

        Multibinder<WebsocketEndpoint> websocketEndpointMultibinder = Multibinder.newSetBinder(binder(), WebsocketEndpoint.class);
        websocketEndpointMultibinder.addBinding().to(SocketEndpoint.class);
    }
}