package com.fhacktory.communication.outputs.tts;

import com.fhacktory.communication.outputs.ActionDto;

/**
 * Created by farid on 14/05/2017.
 */
public class TtsActionDto extends ActionDto {

    private String message;

    public TtsActionDto(String message) {
        super("tts");
        this.message = message;
    }

}
