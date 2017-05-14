package com.fhacktory.communication.outputs;

import com.fhacktory.communication.outputs.lights.LightPowerActionDto;
import com.fhacktory.communication.outputs.music.MusicActionDto;
import com.fhacktory.communication.outputs.tts.TtsActionDto;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class ActionDtoFactory {

    public static ActionDto createActionDto(String action, Map<String, String> parameters) {
        switch (action.toLowerCase()) {
            case "light":
                if(parameters.containsKey("power")) {
                    boolean power = parameters.get("power").equals("on");
                    ActionDto actionDto = new LightPowerActionDto(power);
                    return actionDto;
                }
                break;
            case "tts":
                if(parameters.containsKey("speech")) {
                    ActionDto actionDto = new TtsActionDto(parameters.get("speech"));
                    return actionDto;
                }
                break;
            case "music":
                ActionDto actionDto = new MusicActionDto();
                return actionDto;
        }
        return null;
    }
}
