package com.fhacktory.action_detector;

/**
 * Created by farid on 14/05/2017.
 */
public interface CommandActionDetector {
    CommandAction getAction(String query);
}
