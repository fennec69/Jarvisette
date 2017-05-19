package com.fhacktory.outputs.plugins.tts;

import com.fhacktory.data.entities.ActionType;
import com.fhacktory.outputs.common.MessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 19/05/2017.
 */
public class TtsMessageBuilder implements MessageBuilder {

    public static final String MESSAGE_PARAM = "message";

    @Override
    public String buildMessage(Map<String, String> parameters) {
        //TODO: transform text message to speech
        Gson gson = new Gson();
        if(parameters.containsKey(MESSAGE_PARAM)) {
            TtsOutputActionDto messageDto = new TtsOutputActionDto(parameters.get(MESSAGE_PARAM));
            return gson.toJson(messageDto);
        }
        return null;
    }

     class TtsOutputActionDto {

        private String action;
        private String message;

        public TtsOutputActionDto(String message) {
            this.action = ActionType.TTS;
            this.message = message;
        }
    }
}
