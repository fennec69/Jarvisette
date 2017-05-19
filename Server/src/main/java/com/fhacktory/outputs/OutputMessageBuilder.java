package com.fhacktory.outputs;

import java.util.Map;

/**
 * Created by fkilani on 18/05/2017.
 */
public interface OutputMessageBuilder {
    String buildMessage(Map<String, String> parameters);
}
