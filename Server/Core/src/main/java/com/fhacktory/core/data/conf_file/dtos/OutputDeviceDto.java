package com.fhacktory.core.data.conf_file.dtos;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputDeviceDto {
    
    private String uuid;
    private Map<String, Float> locations;

    public String getUuid() {
        return uuid;
    }

    public Map<String, Float> getLocations() {
        return locations;
    }
}
