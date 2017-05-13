package com.fhacktory.speech_recognition;

/**
 * Created by farid on 13/05/2017.
 */
public interface SpeechRecognizer {
    String speechToText(byte[] audioData);
}
