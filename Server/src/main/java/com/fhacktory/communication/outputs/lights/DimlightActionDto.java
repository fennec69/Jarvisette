package com.fhacktory.communication.outputs.lights;

import com.fhacktory.communication.outputs.ActionDto;

/**
 * Created by farid on 13/05/2017.
 */
public class DimlightActionDto extends ActionDto {

    private int dimpercent;

    public DimlightActionDto(int dimPercent) {
        super("dimlight");
        setDimpercent(dimPercent);
    }

    public int getDimpercent() {
        return dimpercent;
    }

    public void setDimpercent(int dimpercent) {
        if(dimpercent > 100) this.dimpercent = 100;
        else if(dimpercent < 0) this.dimpercent = 0;
        else this.dimpercent = dimpercent;
    }
}
