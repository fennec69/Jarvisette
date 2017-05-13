package com.fhacktory;

import com.fhacktory.speech_recognition.SpeechRecognizer;
import com.fhacktory.speech_recognition.google_speech.GoogleSpeechRecognizer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./recording.pcm";
        Path path = Paths.get(fileName);
        System.out.println("Reading file");
        byte[] data = Files.readAllBytes(path);
        SpeechRecognizer speechRecognizer = new GoogleSpeechRecognizer();
        String test = speechRecognizer.speechToText(data);
        if(test != null) System.out.println(test);
    }

}
