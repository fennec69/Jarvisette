package com.fhacktory.plugins.outputs.tts.google_translate;

import com.fhacktory.plugins.outputs.tts.google_translate.endpoints.GoogleTranslateEndpoint;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by fkilani on 22/05/2017.
 */
public class GoogleTTS {

    private static final String GOOGLE_API_URL = "https://translate.google.com/";
    private Retrofit mGoogleTranslateRetrofit;
    private GoogleTranslateEndpoint mGoogleTranslateEndpoint;

    public GoogleTTS() {
        mGoogleTranslateRetrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGoogleTranslateEndpoint = mGoogleTranslateRetrofit.create(GoogleTranslateEndpoint.class);
    }

    public byte[] getTTS(String text, String lang) {
        try {
            return mp3ToWav(mGoogleTranslateEndpoint.getSpeech(text, lang).execute().body().byteStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] mp3ToWav(InputStream mp3Data) throws Exception {
        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3Data);
        AudioFormat sourceFormat = mp3Stream.getFormat();
        AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                sourceFormat.getSampleRate(), 16,
                sourceFormat.getChannels(),
                sourceFormat.getChannels() * 2,
                sourceFormat.getSampleRate(),
                false);
        AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
        File file  = new File("tmp.wav");
        AudioSystem.write(converted, AudioFileFormat.Type.WAVE, file);
        Path path = Paths.get("tmp.wav");
        return Files.readAllBytes(path);
    }

}
