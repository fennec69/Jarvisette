package com.fhacktory.core.com;

import com.fhacktory.data.MessageType;

/**
 * Created by farid on 24/05/2017.
 */
public class InputMessageDto {
    private MessageType type;
    private String message;

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
