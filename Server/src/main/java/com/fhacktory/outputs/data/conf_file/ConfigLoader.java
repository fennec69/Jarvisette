package com.fhacktory.outputs.data.conf_file;

import com.fhacktory.outputs.common.OutputDeviceManager;
import com.fhacktory.outputs.data.conf_file.dtos.ConfFileDto;
import com.fhacktory.outputs.data.conf_file.dtos.LocationDto;
import com.fhacktory.outputs.data.conf_file.dtos.OutputDeviceDto;
import com.fhacktory.data.entities.Location;
import com.fhacktory.data.entities.OutputDevice;
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
public class ConfigLoader implements OutputDeviceManager {

    private Map<String, OutputDevice> mOutputDeviceMap;
    public ConfigLoader() {
        mOutputDeviceMap = new TreeMap<>();
        loadData();
    }

    public void loadData() {
        mOutputDeviceMap = new TreeMap<>();
        Gson gson = new Gson();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream fileInputStream = classLoader.getResourceAsStream("devices.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        ConfFileDto confFileDto = gson.fromJson(reader, ConfFileDto.class);
        for(OutputDeviceDto outputDeviceDto : confFileDto.getOutputs()) {
            OutputDevice outputDevice = new OutputDevice(outputDeviceDto.getUuid());
            Location location = new Location();
            for(LocationDto locationDto : outputDeviceDto.getLocations()) {
                location.addLocation(locationDto.getUuid(), locationDto.getIntensity());
            }
            outputDevice.setAudioLocation(location);
            mOutputDeviceMap.put(outputDevice.getUUID(), outputDevice);
        }
    }

    @Override
    public void setCapabilities(String uuid, List<String> capabilities) {
       if(mOutputDeviceMap.containsKey(uuid)) {
           mOutputDeviceMap.get(uuid).setCapabilities(capabilities);
       }
    }

    @Override
    public Collection<OutputDevice> getAll() {
        return mOutputDeviceMap.values();
    }
}
