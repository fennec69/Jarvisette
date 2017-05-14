package com.fhacktory.communication.outputs.lights;

import com.fhacktory.communication.outputs.OutputActionDto;
import com.fhacktory.data.entities.ActionType;

/**
 * Created by farid on 13/05/2017.
 */
public class DimlightOutputActionDto extends OutputActionDto {

    private int dimpercent;

    public DimlightOutputActionDto(int dimPercent) {
        super(ActionType.DIMLIGHT);
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
