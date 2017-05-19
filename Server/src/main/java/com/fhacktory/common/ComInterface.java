package com.fhacktory.common;

import com.fhacktory.data.entities.OutputDevice;

/**
 * Created by fkilani on 18/05/2017.
 */
public interface ComInterface {
    void sendMessage(String message, OutputDevice outputDevice);
}
