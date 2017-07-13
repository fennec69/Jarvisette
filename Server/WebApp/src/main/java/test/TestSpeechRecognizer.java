package test;

import com.fhacktory.common.SpeechRecognizer;

/**
 * Created by farid on 12/07/2017.
 */
public class TestSpeechRecognizer implements SpeechRecognizer {
    @Override
    public String speechToText(String audioData) {
        return "Allume la lampe";
    }
}
