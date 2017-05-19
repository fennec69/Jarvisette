package com.fhacktory.action_detector;

import com.fhacktory.data.entities.CommandAction;

/**
 * Created by farid on 14/05/2017.
 */
public interface CommandActionDetector {
    CommandAction getAction(String query);
}
