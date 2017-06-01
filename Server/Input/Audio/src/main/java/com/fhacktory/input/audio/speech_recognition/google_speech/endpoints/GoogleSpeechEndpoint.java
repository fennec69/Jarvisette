package com.fhacktory.input.audio.speech_recognition.google_speech.endpoints;


import com.fhacktory.input.audio.speech_recognition.google_speech.dtos.requests.GoogleSpeechRequestDto;
import com.fhacktory.input.audio.speech_recognition.google_speech.dtos.responses.GoogleSpeechResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by farid on 13/05/2017.
 */
public interface GoogleSpeechEndpoint {

    @Headers("Content-Type: application/json")
    @POST("v1/speech:recognize?key=AIzaSyCzdjkc0Zj_A9csp7nLbDKHvjiHKTA3t_M")
    Call<GoogleSpeechResponseDto> getSpeechRecognitions(@Body GoogleSpeechRequestDto dto);
}
