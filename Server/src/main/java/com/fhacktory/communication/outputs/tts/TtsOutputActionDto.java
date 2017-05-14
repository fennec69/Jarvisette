package com.fhacktory.communication.outputs.tts;

import com.fhacktory.communication.outputs.OutputActionDto;
import com.fhacktory.data.entities.ActionType;

/**
 * Created by farid on 14/05/2017.
 */
public class TtsOutputActionDto extends OutputActionDto {

    private String message;

    public TtsOutputActionDto(String message) {
        super(ActionType.TTS);
        this.message = message;
    }

}
