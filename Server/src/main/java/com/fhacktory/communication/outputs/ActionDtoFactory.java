package com.fhacktory.communication.outputs;

import com.fhacktory.communication.outputs.lights.LightPowerOutputActionDto;
import com.fhacktory.communication.outputs.music.MusicOutputActionDto;
import com.fhacktory.communication.outputs.tts.TtsOutputActionDto;
import com.fhacktory.data.entities.ActionType;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class ActionDtoFactory {

    public static OutputActionDto createActionDto(String action, Map<String, String> parameters) {
        switch (action.toLowerCase()) {
            case ActionType.LIGHT:
                if(parameters.containsKey("power")) {
                    boolean power = parameters.get("power").equals("on");
                    OutputActionDto outputActionDto = new LightPowerOutputActionDto(power);
                    return outputActionDto;
                }
                break;
            case ActionType.TTS:
                if(parameters.containsKey("speech")) {
                    OutputActionDto outputActionDto = new TtsOutputActionDto(parameters.get("speech"));
                    return outputActionDto;
                }
                break;
            case ActionType.MUSIC:
                OutputActionDto outputActionDto = new MusicOutputActionDto();
                return outputActionDto;
        }
        return null;
    }
}
