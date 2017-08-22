package com.fhacktory.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputDevice {

    private List<String> mCapabilities;
    private String mUUID;
    private Location mLocation;

    public OutputDevice(String UUID) {
        mUUID = UUID;
        mCapabilities = new ArrayList<>();
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public void setCapabilities(List<String> capabilities) {
        mCapabilities = capabilities;
    }

    public List<String> getCapabilities() {
        return mCapabilities;
    }

    public String getUUID() {
        return mUUID;
    }

    public double matchingLocation(Location location) {
        if(location == null || mLocation == null) return 0;
        else return mLocation.matching(location);
    }

    public boolean hasCapability(String capability) {
        for(String tmpCap : mCapabilities) {
            if(tmpCap.equals(capability)) return true;
        }
        return false;
    }
}
