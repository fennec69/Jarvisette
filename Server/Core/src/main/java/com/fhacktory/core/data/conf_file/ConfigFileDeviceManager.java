package com.fhacktory.core.data.conf_file;

import com.fhacktory.core.DeviceManager;
import com.fhacktory.core.data.conf_file.dtos.ConfFileDto;
import com.fhacktory.core.com.LocationDto;
import com.fhacktory.core.data.conf_file.dtos.OutputDeviceDto;
import com.fhacktory.data.Location;
import com.fhacktory.data.OutputDevice;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
@Singleton
public class ConfigFileDeviceManager implements DeviceManager {

    private volatile Map<String, OutputDevice> mOutputDeviceMap;

    public ConfigFileDeviceManager() {
        mOutputDeviceMap = new TreeMap<>();
        loadData();
    }

    public synchronized void loadData() {
        mOutputDeviceMap = new TreeMap<>();
        Gson gson = new Gson();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream fileInputStream = classLoader.getResourceAsStream("devices.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        ConfFileDto confFileDto = gson.fromJson(reader, ConfFileDto.class);
        for (OutputDeviceDto outputDeviceDto : confFileDto.getOutputs()) {
            OutputDevice outputDevice = new OutputDevice(outputDeviceDto.getUuid());
            Location location = new Location();
            location.setLocations(outputDeviceDto.getLocations());
            outputDevice.setLocation(location);
            mOutputDeviceMap.put(outputDevice.getUUID(), outputDevice);
        }
    }

    @Override
    public synchronized void setCapabilities(String uuid, List<String> capabilities) {
        if (!mOutputDeviceMap.containsKey(uuid)) addDevice(uuid);
        mOutputDeviceMap.get(uuid).setCapabilities(capabilities);
        System.out.println(String.format("Capabilities set for %s: %s", uuid, capabilities));
    }

    @Override
    public synchronized void setLocations(String uuid, Map<String, Float> locations) {
        if (!mOutputDeviceMap.containsKey(uuid)) addDevice(uuid);
        Location location = new Location();
        location.setLocations(locations);
        mOutputDeviceMap.get(uuid).setLocation(location);
        System.out.println(String.format("Location set for %s: %s", uuid, locations));
    }

    @Override
    public void addDevice(String uuid) {
        mOutputDeviceMap.put(uuid, new OutputDevice(uuid));
    }

    @Override
    public Collection<OutputDevice> getAll() {
        return mOutputDeviceMap.values();
    }

    @Override
    public void removeDevice(String uuid) {
        mOutputDeviceMap.remove(uuid);
    }
}
