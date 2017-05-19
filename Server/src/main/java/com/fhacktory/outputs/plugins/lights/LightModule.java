package com.fhacktory.outputs.plugins.lights;

import com.fhacktory.outputs.common.MessageBuilder;
import com.fhacktory.data.entities.ActionType;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by farid on 19/05/2017.
 */
public class LightModule extends AbstractModule {
    @Override
    protected void configure() {
        MapBinder<String, MessageBuilder> messageBuilderBinder = MapBinder.newMapBinder(binder(), String.class, MessageBuilder.class);
        messageBuilderBinder.addBinding(ActionType.DIMLIGHT).to(DimlightMessageBuilder.class);
        messageBuilderBinder.addBinding(ActionType.LIGHT).to(LightMessageBuilder.class);
    }
}
