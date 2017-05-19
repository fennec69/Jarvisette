package com.fhacktory.outputs.lights;

import com.fhacktory.outputs.OutputMessageBuilder;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 13/05/2017.
 */
public class DimlightOutputMessageBuilder implements OutputMessageBuilder {

    public static final String DIMLIGHT_PARAM = "dimlight";

    @Override
    public String buildMessage(Map<String, Object> parameters) {
        Gson gson = new Gson();
        if(parameters.containsKey(DIMLIGHT_PARAM)) {
           DimlightMessageDto lightMessageDto = new DimlightMessageDto((Integer) parameters.get(DIMLIGHT_PARAM));
            return gson.toJson(lightMessageDto);
        }
        return null;
    }

    class DimlightMessageDto {

        private String action;
        private int dimpercent;

        public DimlightMessageDto(int dimPercent) {
            setDimpercent(dimPercent);
            this.action = DIMLIGHT_PARAM;
        }

        public void setDimpercent(int dimpercent) {
            if(dimpercent > 100) this.dimpercent = 100;
            else if(dimpercent < 0) this.dimpercent = 0;
            else this.dimpercent = dimpercent;
        }
    }
}
