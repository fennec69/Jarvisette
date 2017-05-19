package com.fhacktory.data.conf;

import com.fhacktory.data.conf.dtos.ConfFileDto;
import com.fhacktory.data.conf.dtos.LocationDto;
import com.fhacktory.data.conf.dtos.OutputDeviceDto;
import com.fhacktory.data.entities.Location;
import com.fhacktory.data.entities.OutputDevice;
import com.google.gson.Gson;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
public class ConfigLoader {

    private static final ConfigLoader INSTANCE = new ConfigLoader();

    private Map<String, OutputDevice> mOutputDeviceMap;

    private ConfigLoader() {
        mOutputDeviceMap = new TreeMap<String, OutputDevice>();
    }

    public static ConfigLoader getInstance() {
        return INSTANCE;
    }

    public void loadFromConfFile() throws FileNotFoundException {
        mOutputDeviceMap = new TreeMap<String, OutputDevice>();
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

    public Map<String, OutputDevice> getOutputDeviceMap() {
        return mOutputDeviceMap;
    }
}
