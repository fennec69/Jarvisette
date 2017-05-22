package com.fhacktory.plugins.outputs.tts.google_translate;

import com.fhacktory.plugins.outputs.tts.google_translate.endpoints.GoogleTranslateEndpoint;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

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
            return mGoogleTranslateEndpoint.getSpeech(text, lang).execute().body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
