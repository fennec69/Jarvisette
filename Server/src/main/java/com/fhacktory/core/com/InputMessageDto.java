package com.fhacktory.core.com;

import com.fhacktory.data.MessageType;
import com.google.gson.JsonObject;

/**
 * Created by farid on 24/05/2017.
 */
public class InputMessageDto {
    private MessageType type;
    private JsonObject message;

    public MessageType getType() {
        return type;
    }

    public JsonObject getMessage() {
        return message;
    }
}
