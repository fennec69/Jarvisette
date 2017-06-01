package com.fhactory.input.text;


import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.CommandActionDetector;
import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.data.ActionType;
import com.fhacktory.data.CommandAction;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by fkilani on 21/05/2017.
 */
@Singleton
public class TextMessageProcessor implements InputMessageProcessor {

    @Inject
    private CommandActionDetector mCommandActionDetector;
    @Inject
    private ActionProcessor mActionProcessor;

    private Gson mGson;

    public TextMessageProcessor() {
        mGson = new Gson();
    }

    public void process(String message, String inputUuid) {
        TextCommandDto textCommandDto = mGson.fromJson(message, TextCommandDto.class);
        CommandAction commandAction = mCommandActionDetector.getAction(textCommandDto.getText());
        commandAction.setResponseType(textCommandDto.getResponseType());
        commandAction.setResponseUuid(textCommandDto.getResponseUUID());
        if(commandAction.getResponseType() == null) commandAction.setResponseType(ActionType.TEXT);
        mActionProcessor.processOutput(commandAction, null);
    }
}
