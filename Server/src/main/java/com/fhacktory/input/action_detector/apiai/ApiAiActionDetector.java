package com.fhacktory.input.action_detector.apiai;

import com.fhacktory.data.entities.CommandAction;
import com.fhacktory.input.action_detector.CommandActionDetector;
import com.fhacktory.input.action_detector.apiai.dtos.ApiAiRequestDto;
import com.fhacktory.input.action_detector.apiai.dtos.ApiAiResponseDto;
import com.fhacktory.input.action_detector.apiai.endpoints.ApiAiEndpoint;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by farid on 14/05/2017.
 */
public class ApiAiActionDetector implements CommandActionDetector {

    private static final String API_AI_URL = "https://api.api.ai/";

    private Retrofit mApiAiRetrofit;
    private ApiAiEndpoint mApiAiEndpoint;

    public ApiAiActionDetector() {
        mApiAiRetrofit = new Retrofit.Builder()
                .baseUrl(API_AI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiAiEndpoint = mApiAiRetrofit.create(ApiAiEndpoint.class);
    }

    public CommandAction getAction(String query) {
        if (query == null) return null;
        ApiAiRequestDto requestDto = new ApiAiRequestDto(query);
        CommandAction action = new CommandAction();
        Response<ApiAiResponseDto> response;
        try {
            response = mApiAiEndpoint.getAction(requestDto).execute();
            if(response.isSuccessful()) {
                ApiAiResponseDto apiAiResponseDto = response.body();
                if(apiAiResponseDto != null && apiAiResponseDto.getResult() != null) {
                    action.setAction(apiAiResponseDto.getResult().getAction());
                    action.setParameters(apiAiResponseDto.getResult().getParameters());
                    if(apiAiResponseDto.getResult().getFulfillment() != null) {
                        action.setResponseSpeech(apiAiResponseDto.getResult().getFulfillment().getSpeech());
                    }
                }
            }
            else {
                System.out.println(response.errorBody().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return action;
    }
}
