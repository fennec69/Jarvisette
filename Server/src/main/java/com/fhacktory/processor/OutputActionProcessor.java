package com.fhacktory.processor;

import com.fhacktory.action_detector.GoogleActionDetector;
import com.fhacktory.action_detector.dtos.ApiAiResponseDto;
import com.fhacktory.action_detector.dtos.ApiAiResponseFulfilDto;
import com.fhacktory.communication.outputs.ActionDto;
import com.fhacktory.communication.outputs.ActionDtoFactory;
import com.fhacktory.communication.outputs.endpoints.OutputSocketEndpoint;
import com.fhacktory.communication.outputs.lights.LightPowerActionDto;
import com.fhacktory.communication.outputs.music.MusicActionDto;
import com.fhacktory.data.conf.ConfigLoader;
import com.fhacktory.data.entities.AudioLocation;
import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.processor.AudioMessage;
import com.fhacktory.speech_recognition.SpeechRecognizer;
import com.fhacktory.speech_recognition.google_speech.GoogleSpeechRecognizer;
import com.google.gson.Gson;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputActionProcessor {

    private SpeechRecognizer mSpeechRecognizer;
    private GoogleActionDetector mGoogleActionDetector;
    private Gson mGson;

    public OutputActionProcessor() {
        mSpeechRecognizer = new GoogleSpeechRecognizer();
        mGoogleActionDetector = new GoogleActionDetector();
        mGson = new Gson();
    }

    public void processAudioMessages(Map<String, AudioMessage> audioMessages) {
        AudioLocation myLocation = calculateAudioLocation(audioMessages);
        AudioMessage mostPowerfulMessage = getMostPowerfulAudioMessage(audioMessages);
        String command = mSpeechRecognizer.speechToText(mostPowerfulMessage.getSignal());
        System.out.println(mGson.toJson(myLocation));
        //String command = "allume la lampe";
        System.out.println("Command received:" + command);
       if(command != null) {
           ApiAiResponseDto apiAiResponseDto = mGoogleActionDetector.getAction(command);
           if(apiAiResponseDto != null && apiAiResponseDto.getResult() != null) {
               System.out.println("Action:" + apiAiResponseDto.getResult().getAction());
               OutputDevice closestDevice = findClosestOutputDevice(myLocation, apiAiResponseDto.getResult().getAction());
               OutputDevice closestTtsDevice = findClosestOutputDevice(myLocation, "tts");
               if(closestDevice != null) {
                   System.out.println("Closest device selected:" + closestDevice.getUUID());
                   String uuid = closestDevice.getUUID();
                   ActionDto actionDto = ActionDtoFactory.createActionDto(apiAiResponseDto.getResult().getAction(), apiAiResponseDto.getResult().getParameters());
                    if(actionDto instanceof LightPowerActionDto && !apiAiResponseDto.getResult().getParameters().get("number").isEmpty()) {
                        uuid = "RASP-JARB-" + apiAiResponseDto.getResult().getParameters().get("number");
                    }
                   boolean success = false;
                   if(actionDto != null) {
                       success = OutputSocketEndpoint.sendMessage(mGson.toJson(actionDto), uuid);
                   }
                   System.out.println(success);
               }
               else {
                   System.out.println("No matching devices found");
               }
               ApiAiResponseFulfilDto fulfilDto = apiAiResponseDto.getResult().getFulfillment();
               if(closestTtsDevice != null && fulfilDto != null && fulfilDto.getSpeech() != null) {
                   Map<String, String> tmpParam = new TreeMap<>();
                   tmpParam.put("speech", fulfilDto.getSpeech());
                   ActionDto ttsActionDto = ActionDtoFactory.createActionDto("tts", tmpParam);
                   OutputSocketEndpoint.sendMessage(mGson.toJson(ttsActionDto), closestTtsDevice.getUUID());
               }
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

        return audioLocation;
    }

    private OutputDevice findClosestOutputDevice(AudioLocation audioLocation, String capability) {
        OutputDevice result = null;
        double currentMatching = 0;
        for(OutputDevice outputDevice : ConfigLoader.getInstance().getOutputDeviceMap().values()) {
            if(outputDevice.hasCapability(capability)) {
                double matching = outputDevice.getAudioLocation().matching(audioLocation);
                if(matching >= currentMatching) {
                    result = outputDevice;
                    currentMatching = matching;
                }
            }
        }
        return result;
    }
}
