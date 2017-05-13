package com.fhacktory.speech_recognition.google_speech.endpoints;

import com.fhacktory.speech_recognition.google_speech.dtos.requests.GoogleSpeechRequestDto;
import com.fhacktory.speech_recognition.google_speech.dtos.responses.GoogleSpeechResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by farid on 13/05/2017.
 */
public interface GoogleSpeechEndpoint {

    @Headers({"Content-Type: application/json",
            "Authorization: Bearer ya29.ElpJBGVefyIhrQ7dLqMy9WHblp50H71UsW-cw_K_UvLa914819qhpGVv0CJaBowXYIjLHuAFWsAmVby0WFlAJp_NfBwuT20ZGfuObP6zch62uGLGJoJe-ULDXjQ"})
    @POST("v1/speech:recognize")
    Call<GoogleSpeechResponseDto> getSpeechRecognitions(@Body GoogleSpeechRequestDto dto);
}
