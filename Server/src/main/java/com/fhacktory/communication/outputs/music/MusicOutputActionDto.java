package com.fhacktory.communication.outputs.music;

import com.fhacktory.communication.outputs.OutputActionDto;
import com.fhacktory.data.entities.ActionType;

/**
 * Created by farid on 14/05/2017.
 */
public class MusicOutputActionDto extends OutputActionDto {

    public MusicOutputActionDto() {
        super(ActionType.MUSIC);
    }
}
