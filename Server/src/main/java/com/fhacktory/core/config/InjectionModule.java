package com.fhacktory.core.config;

import com.fhacktory.core.ComInterfaceManagerImpl;
import com.fhacktory.common.WebsocketConfigurator;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.ComInterfaceManager;
import com.fhacktory.plugins.inputs.text.TextInputModule;
import com.fhacktory.common.DeviceManager;
import com.fhacktory.plugins.inputs.audio.AudioInputModule;
import com.fhacktory.core.OutputActionProcessor;
import com.fhacktory.plugins.outputs.com.socket.OutputSocketModule;
import com.fhacktory.core.data.conf_file.ConfigFileDeviceManager;
import com.fhacktory.plugins.outputs.lights.LightOutputModule;
import com.fhacktory.plugins.outputs.text.TextOutputModule;
import com.fhacktory.plugins.outputs.tts.TtsOutputModule;
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
        //Input plugins
        install(new AudioInputModule(true, true));
        install(new TextInputModule(true, true));

        //Output plugins
        install(new OutputSocketModule());
        install(new LightOutputModule());
        install(new TextOutputModule());
        install(new TtsOutputModule());

        //Core
        bind(ActionProcessor.class).to(OutputActionProcessor.class);
        bind(ComInterfaceManager.class).to(ComInterfaceManagerImpl.class);
        bind(DeviceManager.class).to(ConfigFileDeviceManager.class);
        bind(WebsocketConfigurator.class);
    }
}