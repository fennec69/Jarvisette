package com.fhacktory.google_speech.endpoints;


import com.fhacktory.google_speech.dtos.requests.GoogleSpeechRequestDto;
import com.fhacktory.google_speech.dtos.responses.GoogleSpeechResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by farid on 13/05/2017.
 */
public interface GoogleSpeechEndpoint {

    //TODO: Pass key as parameter
    @Headers("Content-Type: application/json")
    @POST("v1/speech:recognize?key=AIzaSyAtdIOzV-koKwReTsDz7SvsM6sV8MgbfoU")
    Call<GoogleSpeechResponseDto> getSpeechRecognitions(@Body GoogleSpeechRequestDto dto);
}
