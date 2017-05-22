package com.fhacktory.plugins.inputs.text;

import com.fhacktory.common.ActionProcessor;
import com.fhacktory.data.ActionType;
import com.fhacktory.data.CommandAction;
import com.fhacktory.common.CommandActionDetector;
import com.fhacktory.plugins.inputs.text.com.dtos.TextCommandDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by fkilani on 21/05/2017.
 */
@Singleton
public class TextMessageProcessor {

    @Inject
    private CommandActionDetector mCommandActionDetector;
    @Inject
    private ActionProcessor mActionProcessor;

    public void processMessage(TextCommandDto textCommandDto, String senderUuid) {
        CommandAction commandAction = mCommandActionDetector.getAction(textCommandDto.getText());
        commandAction.setResponseType(ActionType.TEXT);
        mActionProcessor.processOutput(commandAction, senderUuid, null);
    }
}
