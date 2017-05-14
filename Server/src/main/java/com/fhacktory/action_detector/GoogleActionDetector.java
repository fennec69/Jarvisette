package com.fhacktory.action_detector;

import com.fhacktory.action_detector.dtos.ApiAiRequestDto;
import com.fhacktory.action_detector.dtos.ApiAiResponseDto;
import com.fhacktory.action_detector.endpoints.ApiAiEndpoint;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by farid on 14/05/2017.
 */
public class GoogleActionDetector {

    private static final String API_AI_URL = "https://api.api.ai/";

    private Retrofit mApiAiRetrofit;
    private ApiAiEndpoint mApiAiEndpoint;

    public GoogleActionDetector() {
        mApiAiRetrofit = new Retrofit.Builder()
                .baseUrl(API_AI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiAiEndpoint = mApiAiRetrofit.create(ApiAiEndpoint.class);
    }

    public ApiAiResponseDto getAction(String query) {
        ApiAiRequestDto requestDto = new ApiAiRequestDto(query);
        Response<ApiAiResponseDto> response = null;
        try {
            response = mApiAiEndpoint.getAction(requestDto).execute();
            if(response.isSuccessful()) {
                ApiAiResponseDto apiAiResponseDto = response.body();
                return apiAiResponseDto;
            }
            else {
                System.out.println(response.errorBody().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
