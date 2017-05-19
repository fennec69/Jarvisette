package com.fhacktory.outputs.plugins.lights;

import com.fhacktory.data.entities.ActionType;
import com.fhacktory.outputs.common.MessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 13/05/2017.
 */
public class DimlightMessageBuilder implements MessageBuilder {

    public static final String DIMLIGHT_PARAM = "dimlight";

    @Override
    public String buildMessage(Map<String, String> parameters) {
        Gson gson = new Gson();
        if(parameters.containsKey(DIMLIGHT_PARAM)) {
           DimlightMessageDto lightMessageDto = new DimlightMessageDto(Integer.valueOf(parameters.get(DIMLIGHT_PARAM)));
            return gson.toJson(lightMessageDto);
        }
        return null;
    }

    class DimlightMessageDto {

        private String action;
        private int dimpercent;

        public DimlightMessageDto(int dimPercent) {
            setDimpercent(dimPercent);
            this.action = ActionType.DIMLIGHT;
        }

        public void setDimpercent(int dimpercent) {
            if(dimpercent > 100) this.dimpercent = 100;
            else if(dimpercent < 0) this.dimpercent = 0;
            else this.dimpercent = dimpercent;
        }
    }
}
