package com.fhacktory.config;

import com.fhacktory.com.ComInterfaceManagerImpl;
import com.fhacktory.common.WebsocketConfigurator;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.ComInterfaceManager;
import com.fhacktory.input.text.TextInputModule;
import com.fhacktory.outputs.common.OutputDeviceManager;
import com.fhacktory.input.audio.AudioInputModule;
import com.fhacktory.outputs.OutputActionProcessor;
import com.fhacktory.outputs.com.socket.OutputSocketModule;
import com.fhacktory.outputs.data.conf_file.ConfigLoader;
import com.fhacktory.outputs.plugins.lights.LightModule;
import com.fhacktory.outputs.plugins.text.TextModule;
import com.fhacktory.outputs.plugins.tts.TtsModule;
import com.google.inject.AbstractModule;

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
        install(new AudioInputModule());
        install(new TextInputModule());

        install(new LightModule());
        install(new TextModule());
        install(new TtsModule());

        install(new OutputSocketModule());
        bind(OutputActionProcessor.class);
        bind(ComInterfaceManager.class).to(ComInterfaceManagerImpl.class);
        bind(ActionProcessor.class).to(OutputActionProcessor.class);
        bind(OutputDeviceManager.class).to(ConfigLoader.class);

        bind(WebsocketConfigurator.class);
    }
}