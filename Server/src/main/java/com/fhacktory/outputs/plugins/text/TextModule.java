package com.fhacktory.outputs.plugins.text;

import com.fhacktory.common.MessageBuilder;
import com.fhacktory.data.entities.ActionType;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by farid on 19/05/2017.
 */
public class TextModule extends AbstractModule {
    @Override
    protected void configure() {
        MapBinder<String, MessageBuilder> messageBuilderBinder = MapBinder.newMapBinder(binder(), String.class, MessageBuilder.class);
        messageBuilderBinder.addBinding(ActionType.TEXT).to(TextMessageBuilder.class);
    }
}
