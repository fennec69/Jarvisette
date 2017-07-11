package com.fhacktory.apiai.dtos;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class ApiAiResponseResultDto {

    private String action;
    private Map<String, String> parameters;
    private ApiAiResponseFulfilDto fulfillment;

    public String getAction() {
        return action;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public ApiAiResponseFulfilDto getFulfillment() {
        return fulfillment;
    }
}
