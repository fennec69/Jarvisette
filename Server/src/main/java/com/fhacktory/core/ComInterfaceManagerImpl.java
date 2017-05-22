package com.fhacktory.core;

import com.fhacktory.common.ComInterface;
import com.fhacktory.common.ComInterfaceManager;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fkilani on 18/05/2017.
 */
@Singleton
public class ComInterfaceManagerImpl implements ComInterfaceManager {

    private volatile Map<String, ComInterface> mComInterfaceMap;

    public ComInterfaceManagerImpl() {
        mComInterfaceMap = new TreeMap<>();
    }

    public ComInterface getComInterface(String uuid) {
        return mComInterfaceMap.get(uuid);
    }

    @Override
    public void sendMessage(String message, String uuid) {
        if(mComInterfaceMap.containsKey(uuid)) getComInterface(uuid).sendMessage(message, uuid);
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
