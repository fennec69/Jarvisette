package com.fhactory.output.tts;


import com.fhacktory.common.MessageBuilder;
import com.fhacktory.data.ActionType;
import com.fhactory.output.tts.google_translate.GoogleTTS;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by farid on 19/05/2017.
 */
public class TtsMessageBuilder implements MessageBuilder {

    public static final String MESSAGE_PARAM = "message";

    @Inject
    private GoogleTTS mGoogleTTS;

    public String buildMessage(Map<String, String> parameters) {
        Gson gson = new Gson();
        if(parameters.containsKey(MESSAGE_PARAM)) {
            String base64TTS = Base64.encodeBase64String(mGoogleTTS.getTTS(parameters.get(MESSAGE_PARAM), "fr"));
            TtsOutputActionDto messageDto = new TtsOutputActionDto(base64TTS);

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
