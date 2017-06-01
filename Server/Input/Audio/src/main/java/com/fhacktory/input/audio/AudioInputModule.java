package com.fhacktory.input.audio;

import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.data.MessageType;
import com.fhacktory.input.audio.speech_recognition.google_speech.config.GoogleSpeechModule;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by farid on 19/05/2017.
 */
public class AudioInputModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new GoogleSpeechModule());
        bind(AudioMessageProcessor.class);
        MapBinder<MessageType, InputMessageProcessor> inputMessageProcessorMultibinder = MapBinder.newMapBinder(binder(), MessageType.class, InputMessageProcessor.class);
        inputMessageProcessorMultibinder.addBinding(MessageType.AUDIO_COMMAND).to(AudioMessageProcessor.class);
    }
}
