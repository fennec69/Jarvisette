package manager;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Hex;

import java.net.URI;

import websocket.WebsocketClient;
import websocket.dto.AudioSignalDto;

/**
 * Created by Frederic on 13/05/2017.
 */

public class ConnectionManager {

    private static final String ENDPOINT = "ws://192.168.101.144:8080/input/";
    //TODO: TO BE CHANGED!
    private static final String UUID_TEST_SAMSUNG = "Samysamsung";
    private static final String UUID_TEST_MOTOROLA = "Motobruhbruhbruh";
    private static final String TAG = ConnectionManager.class.getSimpleName();
g
    private static ConnectionManager instance;
    private WebsocketClient mClient;

    private ConnectionManager() {
        mClient = new WebsocketClient(URI.create(ENDPOINT + UUID_TEST_SAMSUNG), new WebsocketClient.Listener() {
            @Override
            public void onConnect() {
                Log.d(TAG, "Connected!");
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, String.format("Got string message! %s", message));
            }

            @Override
            public void onMessage(byte[] data) {
                Log.d(TAG, String.format("Got binary message! %s", Hex.encodeHex(data)));
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG, "Error!", error);
            }
        }, null);
        mClient.connect();
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            return new ConnectionManager();
        }
        return instance;
    }

    public void sendAudioSignal(AudioSignalDto audioSignalDto) {
        mClient.send(new Gson().toJson(audioSignalDto));
    }

}
