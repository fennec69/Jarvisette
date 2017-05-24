package com.fhacktory.plugins.inputs.text;

import com.fhacktory.action_detector.apiai.config.ApiAiModule;
import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.data.MessageType;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by fkilani on 21/05/2017.
 */
public class TextInputModule extends AbstractModule {


    @Override
    protected void configure() {
        install(new ApiAiModule());
        bind(TextMessageProcessor.class);
        MapBinder<MessageType, InputMessageProcessor> inputMessageProcessorMultibinder = MapBinder.newMapBinder(binder(), MessageType.class, InputMessageProcessor.class);
        inputMessageProcessorMultibinder.addBinding(MessageType.TEXT_COMMAND).to(TextMessageProcessor.class);
    }
}
