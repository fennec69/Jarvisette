package com.fhacktory.common;

import com.fhacktory.data.entities.Location;

import java.util.Map;

/**
 * Created by farid on 19/05/2017.
 */
public interface ActionProcessor {
    void processOutput(String action, Map<String, String> parameters, String response, String responseType, Location location);
}
