package com.fhacktory.outputs.lights;

import com.fhacktory.data.entities.ActionType;
import com.fhacktory.outputs.OutputMessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 13/05/2017.
 */
public class LightOutputMessageBuilder implements OutputMessageBuilder {

    public static final String POWER_PARAM = "power";

    @Override
    public String buildMessage(Map<String, Object> parameters) {
        Gson gson = new Gson();
        if(parameters.containsKey(POWER_PARAM)) {
            LightMessageDto lightMessageDto = new LightMessageDto((Boolean) parameters.get(POWER_PARAM));
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
