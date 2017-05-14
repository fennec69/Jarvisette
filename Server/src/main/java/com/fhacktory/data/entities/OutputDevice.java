package com.fhacktory.data.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputDevice {

    private List<String> mCapabilities;
    private String mUUID;
    private AudioLocation mAudioLocation;

    public OutputDevice(String UUID) {
        mUUID = UUID;
        mCapabilities = new ArrayList<String>();
    }

    public AudioLocation getAudioLocation() {
        return mAudioLocation;
    }

    public void setAudioLocation(AudioLocation audioLocation) {
        mAudioLocation = audioLocation;
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

    public boolean hasCapability(String capability) {
        for(String tmpCap : mCapabilities) {
            if(tmpCap.equals(capability)) return true;
        }
        return false;
    }
}
