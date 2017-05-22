package com.fhacktory.outputs.plugins.text;

import com.fhacktory.data.entities.ActionType;
import com.fhacktory.common.MessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 19/05/2017.
 */
public class TextMessageBuilder implements MessageBuilder {

    public static final String MESSAGE_PARAM = "message";

    @Override
    public String buildMessage(Map<String, String> parameters) {
        Gson gson = new Gson();
        if(parameters.containsKey(MESSAGE_PARAM)) {
            TextOutputActionDto messageDto = new TextOutputActionDto(parameters.get(MESSAGE_PARAM));
            return gson.toJson(messageDto);
        }
        return null;
    }

    class TextOutputActionDto {

        private String action;
        private String message;

        public TextOutputActionDto(String message) {
            this.action = ActionType.TEXT;
            this.message = message;
        }
    }
}
