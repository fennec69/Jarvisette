package com.fhacktory.google_speech;

import com.fhacktory.common.SpeechRecognizer;
import com.fhacktory.google_speech.dtos.responses.GoogleSpeechResponseDto;
import com.fhacktory.google_speech.dtos.requests.GoogleSpeechAudioRequestDto;
import com.fhacktory.google_speech.dtos.requests.GoogleSpeechConfigRequestDto;
import com.fhacktory.google_speech.dtos.requests.GoogleSpeechRequestDto;
import com.fhacktory.google_speech.dtos.responses.GoogleSpeechAlternativeResponseDto;
import com.fhacktory.google_speech.endpoints.GoogleSpeechEndpoint;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by farid on 13/05/2017.
 */
public class GoogleSpeechRecognizer implements SpeechRecognizer {

    private static final String GOOGLE_API_URL = "https://speech.googleapis.com/";
    private static final long SAMPLE_RATE = 16000;
    private static final String LANGUAGE = "fr-FR";
    private static final String ENCODING = "LINEAR16";

    private Retrofit mGoogleSpeechRetrofit;
    private GoogleSpeechEndpoint mGoogleSpeechEndpoint;

    public GoogleSpeechRecognizer() {
        mGoogleSpeechRetrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGoogleSpeechEndpoint = mGoogleSpeechRetrofit.create(GoogleSpeechEndpoint.class);
    }

    public String speechToText(String audioData) {
        GoogleSpeechConfigRequestDto googleSpeechConfigRequestDto = new GoogleSpeechConfigRequestDto(ENCODING, SAMPLE_RATE, LANGUAGE);
        GoogleSpeechAudioRequestDto googleSpeechAudioRequestDto = new GoogleSpeechAudioRequestDto(audioData);
        GoogleSpeechRequestDto googleSpeechRequestDto = new GoogleSpeechRequestDto(googleSpeechConfigRequestDto, googleSpeechAudioRequestDto);
        try {
            Response<GoogleSpeechResponseDto> response = mGoogleSpeechEndpoint.getSpeechRecognitions(googleSpeechRequestDto).execute();
            if(response.isSuccessful() && response.body() != null) {
                GoogleSpeechResponseDto googleSpeechResponseDto = response.body();
                if(googleSpeechResponseDto != null
                        && googleSpeechResponseDto != null
                        && googleSpeechResponseDto.getResults() != null
                        && !googleSpeechResponseDto.getResults().isEmpty()
                        && googleSpeechResponseDto.getResults().get(0).getAlternatives() != null
                        && !googleSpeechResponseDto.getResults().get(0).getAlternatives().isEmpty()) {
                    GoogleSpeechAlternativeResponseDto alternative = googleSpeechResponseDto.getResults().get(0).getAlternatives().get(0);
                    return alternative.getTranscript();
                }
            }
            else {
                System.out.println(response.errorBody().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
