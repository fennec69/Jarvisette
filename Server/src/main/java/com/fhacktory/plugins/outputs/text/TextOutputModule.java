package com.fhacktory.plugins.outputs.text;

import com.fhacktory.common.MessageBuilder;
import com.fhacktory.data.ActionType;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by farid on 19/05/2017.
 */
public class TextOutputModule extends AbstractModule {
    @Override
    protected void configure() {
        MapBinder<String, MessageBuilder> messageBuilderBinder = MapBinder.newMapBinder(binder(), String.class, MessageBuilder.class);
        messageBuilderBinder.addBinding(ActionType.TEXT).to(TextMessageBuilder.class);
    }
}
