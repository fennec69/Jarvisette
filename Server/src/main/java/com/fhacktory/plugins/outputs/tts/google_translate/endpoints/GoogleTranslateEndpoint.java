package com.fhacktory.plugins.outputs.tts.google_translate.endpoints;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by fkilani on 22/05/2017.
 */
public interface GoogleTranslateEndpoint {
    @Headers({"Referer: http://translate.google.com/"
            ,"Accept-Encoding: gzip"
            , "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36"})
    @GET("translate_tts?client=tw-ob&textlen=5&idx=0&ttsspeed=1&total=1&ie=UTF-8&tk=380668.235599")
    Call<ResponseBody> getSpeech(@Query("q") String text, @Query("tl") String lang);
}
