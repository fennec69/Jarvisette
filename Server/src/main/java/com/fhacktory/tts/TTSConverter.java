package com.fhacktory.tts;

import java.io.OutputStream;

/**
 * Created by farid on 13/05/2017.
 */
public interface TTSConverter {
    OutputStream convertTextToAudioStream(String textToSpeech);
    byte[] convertTextToAudioBytes(String textToSpeech);
}
