package com.fhacktory.common;

/**
 * Created by farid on 19/05/2017.
 */
public interface ComInterfaceManager {
    void sendMessage(String message, String uuid);
    void register(String uuid, ComInterface comInterface);
    void unregister(String uuid);
}
