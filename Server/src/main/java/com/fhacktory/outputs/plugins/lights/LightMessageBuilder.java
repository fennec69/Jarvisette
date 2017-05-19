package com.fhacktory.outputs.plugins.lights;

import com.fhacktory.data.entities.ActionType;
import com.fhacktory.outputs.common.MessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 13/05/2017.
 */
public class LightMessageBuilder implements MessageBuilder {

    public static final String POWER_PARAM = "power";

    @Override
    public String buildMessage(Map<String, String> parameters) {
        Gson gson = new Gson();
        if(parameters.containsKey(POWER_PARAM)) {
            LightMessageDto lightMessageDto = new LightMessageDto(Boolean.valueOf(parameters.get(POWER_PARAM)));
            return gson.toJson(lightMessageDto);
        }
        return null;
    }

    class LightMessageDto {
        private String action;
        private boolean power;

        public LightMessageDto(boolean power) {
            this.action = ActionType.LIGHT;
            this.power = power;
        }
    }
}
