package com.fhacktory.core;

import com.fhacktory.data.OutputDevice;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by farid on 19/05/2017.
 */
public interface DeviceManager {
    void setCapabilities(String uuid, List<String> capabilities);
    void setLocations(String uuid, Map<String, Float> locations);
    void addDevice(String uuid);
    Collection<OutputDevice> getAll();
    void removeDevice(String uuid);
}
