package com.fhacktory.input.text;

import com.fhacktory.common.ActionProcessor;
import com.fhacktory.data.entities.ActionType;
import com.fhacktory.data.entities.CommandAction;
import com.fhacktory.data.entities.Location;
import com.fhacktory.input.action_detector.CommandActionDetector;
import com.fhacktory.input.text.com.dtos.TextCommandDto;
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
        mActionProcessor.processOutput(commandAction, ActionType.TEXT, senderUuid, null);
    }
}
