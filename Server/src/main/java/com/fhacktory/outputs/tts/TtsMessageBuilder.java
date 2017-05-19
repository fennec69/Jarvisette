package com.fhacktory.outputs.tts;

import com.fhacktory.data.entities.ActionType;
import com.fhacktory.outputs.OutputMessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 19/05/2017.
 */
public class TtsMessageBuilder implements OutputMessageBuilder {

    public static final String MESSAGE_PARAM = "message";

    @Override
    public String buildMessage(Map<String, String> parameters) {
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
