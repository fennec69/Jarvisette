package com.fhacktory.outputs.lights;

import com.fhacktory.outputs.ActionDto;

/**
 * Created by farid on 13/05/2017.
 */
public class LightPowerActionDto extends ActionDto {

    private boolean power;

    public LightPowerActionDto(boolean power) {
        super("light");
        setPower(power);
    }

    public boolean isOn() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }
}
