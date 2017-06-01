package com.fhacktory.common;

import java.util.Map;

/**
 * Created by fkilani on 18/05/2017.
 */
public interface MessageBuilder {
    String buildMessage(Map<String, String> parameters);
}
