package com.fhacktory.outputs.com;

import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.outputs.com.OutputComInterface;

import java.util.Map;

/**
 * Created by fkilani on 18/05/2017.
 */
public class ComInterfaceManager {

    private Map<String, OutputComInterface> comInterfaceMap;

    public OutputComInterface getOutputComInterface(OutputDevice outputDevice) {
        return comInterfaceMap.get(outputDevice.getUUID());
    }
}
