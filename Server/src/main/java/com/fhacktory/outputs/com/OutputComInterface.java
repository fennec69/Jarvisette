package com.fhacktory.outputs.com;

import com.fhacktory.data.entities.OutputDevice;

/**
 * Created by fkilani on 18/05/2017.
 */
public interface OutputComInterface {
    void sendMessage(String message, OutputDevice outputDevice);
}
