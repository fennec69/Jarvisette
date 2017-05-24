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

    private volatile Map<String, Map<String, ComInterface>> mComInterfaceMap;

    public ComInterfaceManagerImpl() {
        mComInterfaceMap = new TreeMap<>();
    }

    public ComInterface getComInterface(String uuid, String actionType) {
        if(mComInterfaceMap.containsKey(uuid)) {
            Map<String,ComInterface> comInterfaces = mComInterfaceMap.get(uuid);
            return comInterfaces.get(actionType);
        }
        return null;
    }

    @Override
    public void sendMessage(String message, String uuid, String actionType) {
        ComInterface comInterface = getComInterface(uuid, actionType);
        if(comInterface != null) comInterface.sendMessage(message, uuid);
    }

    @Override
    public synchronized void register(String uuid, String actionType, ComInterface comInterface) {
        if(!mComInterfaceMap.containsKey(uuid)) {
            Map<String, ComInterface> comInterfaceMap = new TreeMap<>();
            mComInterfaceMap.put(uuid, comInterfaceMap);
        }
        mComInterfaceMap.get(uuid).put(actionType, comInterface);
    }

    @Override
    public synchronized void unregister(String uuid) {
        mComInterfaceMap.remove(uuid);
    }
}
