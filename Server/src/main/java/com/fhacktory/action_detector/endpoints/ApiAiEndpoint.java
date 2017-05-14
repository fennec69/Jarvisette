package com.fhacktory.action_detector.endpoints;

import com.fhacktory.action_detector.dtos.ApiAiRequestDto;
import com.fhacktory.action_detector.dtos.ApiAiResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by farid on 14/05/2017.
 */
public interface ApiAiEndpoint {

    @Headers({"Content-Type: application/json; charset=utf-8", "Authorization: Bearer 9a7ab761c87f4c939c6a74469fc0edc0"})
    @POST("v1/query?v=20150910")
    Call<ApiAiResponseDto> getAction(@Body ApiAiRequestDto dto);
}
