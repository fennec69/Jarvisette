package com.fhacktory.common;


import com.fhacktory.data.CommandAction;
import com.fhacktory.data.Location;

/**
 * Created by farid on 19/05/2017.
 */
public interface ActionProcessor {
    void processOutput(CommandAction commandAction, Location location);
}
