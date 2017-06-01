package com.fhacktory.common;

import com.fhacktory.data.CommandAction;

/**
 * Created by farid on 14/05/2017.
 */
public interface CommandActionDetector {
    CommandAction getAction(String query);
}
