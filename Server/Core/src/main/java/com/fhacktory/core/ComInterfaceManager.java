package com.fhacktory.core;


import com.google.inject.Singleton;
import com.fhacktory.common.ComInterface;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fkilani on 18/05/2017.
 */
@Singleton
public class ComInterfaceManager {

    private volatile Map<String, ComInterface> mComInterfaceMap;

    public ComInterfaceManager() {
        mComInterfaceMap = new TreeMap<>();
    }

    public ComInterface getComInterface(String uuid) {
        return mComInterfaceMap.get(uuid);
    }

    public void sendMessage(String message, String uuid) {
        ComInterface comInterface = getComInterface(uuid);
        if(comInterface != null) comInterface.sendMessage(message, uuid);
    }

    public synchronized void register(String uuid, ComInterface comInterface) {
        mComInterfaceMap.put(uuid, comInterface);
    }

    public synchronized void unregister(String uuid) {
        mComInterfaceMap.remove(uuid);
    }
}
