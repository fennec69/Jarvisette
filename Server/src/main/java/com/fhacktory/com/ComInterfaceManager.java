package com.fhacktory.com;

import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.common.ComInterfaceProcessor;
import com.fhacktory.common.ComInterface;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fkilani on 18/05/2017.
 */
@Singleton
public class ComInterfaceManager implements ComInterfaceProcessor {

    private volatile Map<String, ComInterface> mComInterfaceMap;

    public ComInterfaceManager() {
        mComInterfaceMap = new TreeMap<>();
    }

    public ComInterface getOutputComInterface(OutputDevice outputDevice) {
        return mComInterfaceMap.get(outputDevice.getUUID());
    }

    @Override
    public ComInterface getComInterface(String uuid) {
        return mComInterfaceMap.get(uuid);
    }

    @Override
    public synchronized void register(String uuid, ComInterface comInterface) {
        mComInterfaceMap.put(uuid, comInterface);
    }

    @Override
    public synchronized void unregister(String uuid) {
        mComInterfaceMap.remove(uuid);
    }
}
