package com.fhacktory.action_detector;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class CommandAction {

    private String action;
    private Map<String, String> parameters;
    private String responseSpeech;

    public  String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getResponseSpeech() {
        return responseSpeech;
    }

    public void setResponseSpeech(String responseSpeech) {
        this.responseSpeech = responseSpeech;
    }
}
