package com.fhacktory.outputs;

import com.fhacktory.data.conf.ConfigLoader;
import com.fhacktory.data.entities.Location;
import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.outputs.OutputMessageBuilderManager;
import com.fhacktory.outputs.com.OutputComInterface;
import com.google.inject.Inject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputActionProcessor {

    @Inject
    private OutputComInterface outputComInterface;

    private Map<String, OutputMessageBuilder> messageBuilderMap;

    public void processOutput(String action, Map<String, String> parameters, String response, String responseType, Location location) {
        OutputDevice closestDevice = findClosestOutputDevice(location, action);
        sendOutputResponse(location, response, responseType);
        String message = OutputMessageBuilderManager.createAction(action, parameters);
        outputComInterface.sendMessage(message, closestDevice);
    }

    private void sendOutputResponse(Location location, String response, String responseType) {
        OutputDevice responseDevice = findClosestOutputDevice(location, responseType);
        Map<String, String> responseParameters = new TreeMap<>();
        responseParameters.put("response", response);
        String responseMessage = OutputMessageBuilderManager.createAction(responseType, responseParameters);
        outputComInterface.sendMessage(responseMessage, responseDevice);
    }

    private OutputDevice findClosestOutputDevice(Location location, String capability) {
        OutputDevice result = null;
        double currentMatching = 0;
        for (OutputDevice outputDevice : ConfigLoader.getInstance().getOutputDeviceMap().values()) {
            if (outputDevice.hasCapability(capability)) {
                double matching = outputDevice.getAudioLocation().matching(location);
                if (matching >= currentMatching) {
                    result = outputDevice;
                    currentMatching = matching;
                }
            }
        }
        return result;
    }
}
