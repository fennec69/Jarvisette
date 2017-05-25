package com.fhacktory.core;

import com.fhacktory.data.OutputDevice;

import java.util.Collection;
import java.util.List;

/**
 * Created by farid on 19/05/2017.
 */
public interface DeviceManager {
    void setCapabilities(String uuid, List<String> capabilities);
    Collection<OutputDevice> getAll();
}
