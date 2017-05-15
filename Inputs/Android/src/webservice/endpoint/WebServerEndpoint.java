package webservice.endpoint;

import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import webservice.dto.AudioSignalDto;

/**
 * Created by Frederic on 14/05/2017.
 */

public interface WebServerEndpoint {

    @POST("input/{UUID}")
    Call<ResponseBody> sendAudioSignal(
            @Path("UUID") String uuid,
            @Body AudioSignalDto audioSignalString
    );
}
