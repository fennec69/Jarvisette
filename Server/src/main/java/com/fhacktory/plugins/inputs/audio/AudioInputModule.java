package com.fhacktory.plugins.inputs.audio;

import com.fhacktory.action_detector.apiai.config.ApiAiModule;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.plugins.inputs.audio.com.endpoints.AudioInputRestEndpoint;
import com.fhacktory.plugins.inputs.audio.com.endpoints.AudioInputSocketEndpoint;
import com.fhacktory.plugins.inputs.audio.speech_recognition.google_speech.config.GoogleSpeechModule;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by farid on 19/05/2017.
 */
public class AudioInputModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GoogleSpeechModule());
        install(new ApiAiModule());
        bind(AudioMessageProcessor.class);
        bind(AudioInputRestEndpoint.class);
        Multibinder<WebsocketEndpoint> websocketEndpointMultibinder = Multibinder.newSetBinder(binder(), WebsocketEndpoint.class);
        websocketEndpointMultibinder.addBinding().to(AudioInputSocketEndpoint.class);
    }
}
