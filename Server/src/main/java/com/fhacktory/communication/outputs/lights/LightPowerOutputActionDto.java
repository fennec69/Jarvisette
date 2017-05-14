package com.fhacktory.communication.outputs.lights;

import com.fhacktory.communication.outputs.OutputActionDto;
import com.fhacktory.data.entities.ActionType;

/**
 * Created by farid on 13/05/2017.
 */
public class LightPowerOutputActionDto extends OutputActionDto {

    private boolean power;

    public LightPowerOutputActionDto(boolean power) {
        super(ActionType.LIGHT);
        setPower(power);
    }

    public boolean isOn() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }
}
