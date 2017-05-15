package com.fhacktory.processor;

import com.fhacktory.action_detector.CommandAction;
import com.fhacktory.action_detector.CommandActionDetector;
import com.fhacktory.action_detector.apiai.ApiAiActionDetector;
import com.fhacktory.communication.outputs.OutputActionDto;
import com.fhacktory.communication.outputs.ActionDtoFactory;
import com.fhacktory.communication.outputs.endpoints.OutputSocketEndpoint;
import com.fhacktory.communication.outputs.tts.TtsOutputActionDto;
import com.fhacktory.data.conf.ConfigLoader;
import com.fhacktory.data.entities.ActionType;
import com.fhacktory.data.entities.AudioLocation;
import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.speech_recognition.SpeechRecognizer;
import com.fhacktory.speech_recognition.google_speech.GoogleSpeechRecognizer;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by farid on 14/05/2017.
 */
public class OutputActionProcessor {

    private final String DEFAULT_ERRORE_RESPONSE_MESSAGE = "Désolé, je n'ai pas compris votre commande";

    private SpeechRecognizer mSpeechRecognizer;
    private CommandActionDetector mActionDetector;
    private Gson mGson;

    public OutputActionProcessor() {
        mSpeechRecognizer = new GoogleSpeechRecognizer();
        mActionDetector = new ApiAiActionDetector();
        mGson = new Gson();
    }

    public void processAudioMessages(Map<String, AudioMessage> audioMessages) {
        AudioLocation myLocation = calculateAudioLocation(audioMessages);
        AudioMessage mostPowerfulMessage = getMostPowerfulAudioMessage(audioMessages);
        String command = mSpeechRecognizer.speechToText(mostPowerfulMessage.getSignal());

        System.out.println(mGson.toJson(myLocation));
        System.out.println("Command received:" + command);

        CommandAction action = mActionDetector.getAction(command);
        OutputDevice closestDevice = findOutputDevice(myLocation, action);

        if (action != null && closestDevice != null) {
            System.out.println("CommandAction:" + action.getAction());
            System.out.println("Closest device selected:" + closestDevice.getUUID());
            OutputActionDto outputActionDto = ActionDtoFactory.createActionDto(action.getAction(), action.getParameters());
            if (outputActionDto != null) {
                OutputSocketEndpoint.sendMessage(mGson.toJson(outputActionDto), closestDevice.getUUID());
            }

        }
        else {
            System.out.println("Output device found: " + closestDevice != null);
            System.out.println("Command action found: " + action != null);
        }
        if(action != null) computeOutputSpeechResponseIfPossibe(myLocation, action.getResponseSpeech());
    }

    //Shitty code
    private OutputDevice findOutputDevice(AudioLocation audioLocation, CommandAction action) {
        if(audioLocation == null || action == null) return null;
        String uuid;
        if (action.getParameters() != null
                && !action.getParameters().isEmpty()
                && !action.getParameters().get("number").isEmpty()) {
            uuid = "RASP-JARB-" + action.getParameters().get("number");
            return ConfigLoader.getInstance().getOutputDeviceMap().get(uuid);
        } else return findClosestOutputDevice(audioLocation, action.getAction());
    }

    private void computeOutputSpeechResponseIfPossibe(AudioLocation myLocation, String speech) {
        OutputDevice closestTtsDevice = findClosestOutputDevice(myLocation, ActionType.TTS);
        if (speech == null) speech = DEFAULT_ERRORE_RESPONSE_MESSAGE;
        if (closestTtsDevice != null) {
            OutputActionDto ttsOutputActionDto = new TtsOutputActionDto(speech);
            OutputSocketEndpoint.sendMessage(mGson.toJson(ttsOutputActionDto), closestTtsDevice.getUUID());
        }
    }

    private AudioMessage getMostPowerfulAudioMessage(Map<String, AudioMessage> audioMessages) {
        AudioMessage result = null;
        for (AudioMessage audioMessage : audioMessages.values()) {
            if (result == null) result = audioMessage;
            else if (audioMessage.getRmsLevel() > result.getRmsLevel()) {
                result = audioMessage;
            }
        }
        return result;
    }

    private AudioLocation calculateAudioLocation(Map<String, AudioMessage> audioMessages) {
        double sum = 0;
        AudioLocation audioLocation = new AudioLocation();
        for (AudioMessage audioMessage : audioMessages.values()) {
            sum += audioMessage.getRmsLevel();
        }
        for (String uuid : audioMessages.keySet()) {
            float ratio = (float) (100 * (audioMessages.get(uuid).getRmsLevel() / sum));
            audioLocation.addLocation(uuid, ratio);
        }

        return audioLocation;
    }

    private OutputDevice findClosestOutputDevice(AudioLocation audioLocation, String capability) {
        OutputDevice result = null;
        double currentMatching = 0;
        for (OutputDevice outputDevice : ConfigLoader.getInstance().getOutputDeviceMap().values()) {
            if (outputDevice.hasCapability(capability)) {
                double matching = outputDevice.getAudioLocation().matching(audioLocation);
                if (matching >= currentMatching) {
                    result = outputDevice;
                    currentMatching = matching;
                }
            }
        }
        return result;
    }
}
