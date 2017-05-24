package com.fhacktory.common;

/**
 * Created by farid on 19/05/2017.
 */
public interface ComInterfaceManager {
    void sendMessage(String message, String uuid, String actionType);
    void register(String uuid, String actionType, ComInterface comInterface);
    void unregister(String uuid);
}
