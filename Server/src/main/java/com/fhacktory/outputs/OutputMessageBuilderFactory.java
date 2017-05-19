package com.fhacktory.outputs;

import java.util.Map;

/**
 * Created by fkilani on 18/05/2017.
 */
public class OutputMessageBuilderFactory {
    private static Map<String, OutputMessageBuilder> messageBuilderMap;

    public static OutputMessageBuilder getMessageBuilderForAction(String action) {
        return messageBuilderMap.get(action);
    }
}
