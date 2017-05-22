package com.fhacktory.outputs;

import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.ComInterfaceManager;
import com.fhacktory.data.entities.CommandAction;
import com.fhacktory.data.entities.Location;
import com.fhacktory.data.entities.OutputDevice;
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
    public void processOutput(CommandAction commandAction, String responseType, Location location) {
        OutputDevice closestDevice = findClosestOutputDevice(location, commandAction.getAction());
        sendOutputResponse(location, commandAction.getResponseSpeech(), responseType);
        sendOutputMessage(commandAction, closestDevice.getUUID());
    }

    @Override
    public void processOutput(CommandAction commandAction, String responseType, String responseUUID, Location location) {
        OutputDevice closestDevice = findClosestOutputDevice(location, commandAction.getAction());
        sendOutputResponse(commandAction.getResponseSpeech(), responseUUID, responseType);
        sendOutputMessage(commandAction, closestDevice.getUUID());
    }

    private void sendOutputMessage(CommandAction commandAction, String uuid) {
        MessageBuilder actionMessageBuilder = getMessageBuilderForAction(commandAction.getAction());
        String message = actionMessageBuilder.buildMessage(commandAction.getParameters());
        mComInterfaceManager.sendMessage(message, uuid);
    }

    private void sendOutputResponse(Location location, String response, String responseType) {
        OutputDevice responseDevice = findClosestOutputDevice(location, responseType);
        sendOutputResponse(response, responseDevice.getUUID(), responseType);
    }

    private void sendOutputResponse(String response, String responseUUID, String responseType) {
        Map<String, String> responseParameters = new TreeMap<>();
        responseParameters.put("response", response);
        MessageBuilder responseMessageBuilder = getMessageBuilderForAction(responseType);
        String responseMessage = responseMessageBuilder.buildMessage(responseParameters);
        mComInterfaceManager.sendMessage(responseMessage, responseUUID);
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
