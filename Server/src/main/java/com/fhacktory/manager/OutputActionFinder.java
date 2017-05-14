package com.fhacktory.manager;

import com.fhacktory.communication.outputs.ActionDto;
import com.fhacktory.communication.outputs.endpoints.OutputSocketEndpoint;
import com.fhacktory.communication.outputs.lights.LightPowerActionDto;
import com.fhacktory.data.conf.ConfigLoader;
import com.fhacktory.data.entities.AudioLocation;
import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.processor.AudioMessage;
import com.fhacktory.speech_recognition.SpeechRecognizer;
import com.fhacktory.speech_recognition.google_speech.GoogleSpeechRecognizer;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputActionFinder {

    private SpeechRecognizer mSpeechRecognizer;
    private Gson mGson;

    public OutputActionFinder() {
        mSpeechRecognizer = new GoogleSpeechRecognizer();
        mGson = new Gson();
    }

    public void processAudioMessages(Map<String, AudioMessage> audioMessages) {
        AudioLocation myLocation = calculateAudioLocation(audioMessages);
        AudioMessage mostPowerfulMessage = getMostPowerfulAudioMessage(audioMessages);
        //String command = mSpeechRecognizer.speechToText(mostPowerfulMessage.getSignal());
        String command = "allume la lampe";
        System.out.println("Command received:" + command);

        //REMOVE THIS! FOR TEST ONLY
        if(command != null && command.contains("lampe")) {
            OutputDevice closestDevice = findClosestOutputDevice(myLocation, "light");
            if(closestDevice != null) {
                System.out.println("Closest device selected:" + closestDevice.getUUID());
                boolean power = command.contains("allume");
                ActionDto lightPowerActionDto = new LightPowerActionDto(power);
                boolean success = OutputSocketEndpoint.sendMessage(mGson.toJson(lightPowerActionDto), closestDevice.getUUID());
                System.out.println(success);
            }
        }
        else System.out.println("Command null");
    }

    private AudioMessage getMostPowerfulAudioMessage(Map<String, AudioMessage> audioMessages) {
        AudioMessage result = null;
        for(AudioMessage audioMessage : audioMessages.values()) {
            if(result == null) result = audioMessage;
            else if(audioMessage.getRmsLevel() > result.getRmsLevel()) {
                result = audioMessage;
            }
        }

        return result;
    }

    private AudioLocation calculateAudioLocation(Map<String, AudioMessage> audioMessages) {
        double sum = 0;
        AudioLocation audioLocation = new AudioLocation();
        for(AudioMessage audioMessage : audioMessages.values()) {
            sum += audioMessage.getRmsLevel();
        }
        for(String uuid : audioMessages.keySet()) {
            float ratio = (float) (100 * (audioMessages.get(uuid).getRmsLevel() / sum));
            audioLocation.addLocation(uuid, ratio);
        }
        Gson gson = new Gson();
        System.out.println(gson.toJson(audioLocation));
        return audioLocation;
    }

    private OutputDevice findClosestOutputDevice(AudioLocation audioLocation, String capability) {
        OutputDevice result = null;
        double currentMatching = 0;
        for(OutputDevice outputDevice : ConfigLoader.getInstance().getOutputDeviceMap().values()) {
            if(outputDevice.hasCapability(capability)) {
                double matching = outputDevice.getAudioLocation().matching(audioLocation);
                if(matching > currentMatching) {
                    result = outputDevice;
                    currentMatching = matching;
                }
            }
        }
        return result;
    }
}
