package com.fhacktory.outputs.common;

import com.fhacktory.data.entities.OutputDevice;

import java.util.Collection;
import java.util.List;

/**
 * Created by farid on 19/05/2017.
 */
public interface OutputDeviceManager {
    void setCapabilities(String uuid, List<String> capabilities);
    Collection<OutputDevice> getAll();
}
