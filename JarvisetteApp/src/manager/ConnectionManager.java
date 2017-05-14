package manager;

import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webservice.dto.AudioSignalDto;
import webservice.endpoint.WebServerEndpoint;

/**
 * Created by Frederic on 13/05/2017.
 */

public class ConnectionManager {

    private static final String ENDPOINT = "http://192.168.101.144:8080/";
    //TODO: TO BE CHANGED!
    private static final String UUID_TEST_SAMSUNG = "Samysamsung";
    private static final String UUID_TEST_MOTOROLA = "Motobruhbruhbruh";
    private static final String TAG = ConnectionManager.class.getSimpleName();

    private static ConnectionManager instance;
    private Retrofit mClient;
    private WebServerEndpoint mEndpoint;

    private ConnectionManager() {

        mClient = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mEndpoint = mClient.create(WebServerEndpoint.class);
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            return new ConnectionManager();
        }
        return instance;
    }

    public void sendAudioSignal(AudioSignalDto audioSignalDto) {
        mEndpoint.sendAudioSignal(UUID_TEST_SAMSUNG, audioSignalDto)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

}
