package com.fhacktory.common;

/**
 * Created by farid on 19/05/2017.
 */
public interface ComInterfaceProcessor {
    ComInterface getComInterface(String uuid);
    void register(String uuid, ComInterface comInterface);
    void unregister(String uuid);
}
