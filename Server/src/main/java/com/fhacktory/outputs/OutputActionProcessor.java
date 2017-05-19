package com.fhacktory.outputs;

import com.fhacktory.data.entities.Location;
import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.com.ComInterfaceManager;
import com.fhacktory.common.ComInterface;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.outputs.common.MessageBuilder;
import com.fhacktory.outputs.common.OutputDeviceManager;
import com.google.inject.Inject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputActionProcessor implements ActionProcessor {

    @Inject
    private ComInterfaceManager mComInterfaceManager;

    @Inject
    private  Map<String, MessageBuilder> mMessageBuilderMap;

    @Inject
    private OutputDeviceManager mOutputDeviceManager;

    @Override
    public void processOutput(String action, Map<String, String> parameters, String response, String responseType, Location location) {
        OutputDevice closestDevice = findClosestOutputDevice(location, action);
        sendOutputResponse(location, response, responseType);
        MessageBuilder actionMessageBuilder = getMessageBuilderForAction(action);
        String message = actionMessageBuilder.buildMessage(parameters);
        ComInterface comInterface = mComInterfaceManager.getOutputComInterface(closestDevice);
        comInterface.sendMessage(message, closestDevice);
    }

    private void sendOutputResponse(Location location, String response, String responseType) {
        OutputDevice responseDevice = findClosestOutputDevice(location, responseType);
        Map<String, String> responseParameters = new TreeMap<>();
        responseParameters.put("response", response);
        ComInterface comInterface = mComInterfaceManager.getOutputComInterface(responseDevice);
        MessageBuilder responseMessageBuilder = getMessageBuilderForAction(responseType);
        String responseMessage = responseMessageBuilder.buildMessage(responseParameters);
        comInterface.sendMessage(responseMessage, responseDevice);
    }

    private MessageBuilder getMessageBuilderForAction(String action) {
        return mMessageBuilderMap.get(action);
    }

    private OutputDevice findClosestOutputDevice(Location location, String capability) {
        OutputDevice result = null;
        double currentMatching = 0;
        for (OutputDevice outputDevice : mOutputDeviceManager.getAll()) {
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
