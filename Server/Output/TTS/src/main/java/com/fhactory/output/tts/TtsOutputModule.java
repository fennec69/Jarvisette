package com.fhactory.output.tts;

import com.fhacktory.common.MessageBuilder;
import com.fhacktory.data.ActionType;
import com.fhactory.output.tts.google_translate.GoogleTTS;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by farid on 19/05/2017.
 */
public class TtsOutputModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GoogleTTS.class);
        MapBinder<String, MessageBuilder> messageBuilderBinder = MapBinder.newMapBinder(binder(), String.class, MessageBuilder.class);
        messageBuilderBinder.addBinding(ActionType.TTS).to(TtsMessageBuilder.class);
    }
}
