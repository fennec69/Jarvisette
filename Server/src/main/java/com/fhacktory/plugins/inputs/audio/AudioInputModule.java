package com.fhacktory.plugins.inputs.audio;

import com.fhacktory.action_detector.apiai.config.ApiAiModule;
import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.data.MessageType;
import com.fhacktory.plugins.inputs.audio.speech_recognition.google_speech.config.GoogleSpeechModule;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by farid on 19/05/2017.
 */
public class AudioInputModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new GoogleSpeechModule());
        install(new ApiAiModule());
        bind(AudioMessageProcessor.class);
        MapBinder<MessageType, InputMessageProcessor> inputMessageProcessorMultibinder = MapBinder.newMapBinder(binder(), MessageType.class, InputMessageProcessor.class);
        inputMessageProcessorMultibinder.addBinding(MessageType.AUDIO_COMMAND).to(AudioMessageProcessor.class);
    }
}
