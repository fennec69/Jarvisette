package com.fhacktory.core;

import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.ComInterfaceManager;
import com.fhacktory.common.DeviceManager;
import com.fhacktory.data.CommandAction;
import com.fhacktory.data.Location;
import com.fhacktory.data.OutputDevice;
import com.fhacktory.common.MessageBuilder;
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
    private DeviceManager mDeviceManager;


    @Override
    public void processOutput(CommandAction commandAction, Location location) {
        OutputDevice closestDevice = findClosestOutputDevice(location, commandAction.getAction());
        String responseUuid = commandAction.getResponseUuid();
        if(responseUuid == null) {
            OutputDevice closestResponseDevice = findClosestOutputDevice(location, commandAction.getResponseType());
           if(closestResponseDevice != null) responseUuid = closestResponseDevice.getUUID();
        }
        sendOutputResponse(commandAction.getResponseSpeech(), responseUuid, commandAction.getResponseType());
        if(closestDevice != null) sendOutputMessage(commandAction, closestDevice.getUUID());
    }

    private void sendOutputMessage(CommandAction commandAction, String uuid) {
        MessageBuilder actionMessageBuilder = getMessageBuilderForAction(commandAction.getAction());
        String message = actionMessageBuilder.buildMessage(commandAction.getParameters());
        mComInterfaceManager.sendMessage(message, uuid, commandAction.getAction());
    }

    private void sendOutputResponse(String response, String responseUUID, String responseType) {
        Map<String, String> responseParameters = new TreeMap<>();
        responseParameters.put("message", response);
        MessageBuilder responseMessageBuilder = getMessageBuilderForAction(responseType);
        String responseMessage = responseMessageBuilder.buildMessage(responseParameters);
        mComInterfaceManager.sendMessage(responseMessage, responseUUID, responseType);
    }

    private MessageBuilder getMessageBuilderForAction(String action) {
        return mMessageBuilderMap.get(action);
    }

    private OutputDevice findClosestOutputDevice(Location location, String capability) {
        OutputDevice result = null;
        double currentMatching = 0;
        for (OutputDevice outputDevice : mDeviceManager.getAll()) {
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
