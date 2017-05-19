package com.fhacktory.outputs.data.conf_file.dtos;

import java.util.List;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputDeviceDto {
    
    private String uuid;
    private List<LocationDto> locations;

    public String getUuid() {
        return uuid;
    }

    public List<LocationDto> getLocations() {
        return locations;
    }
}
