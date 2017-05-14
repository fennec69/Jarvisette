package ai.kitt.snowboy.audio;

import android.util.Base64;

import java.io.ByteArrayOutputStream;

import manager.ConnectionManager;
import webservice.dto.AudioSignalDto;

/**
 * Created by Frederic on 13/05/2017.
 */

public class AudioDataToServer implements AudioDataReceivedListener  {

    private ByteArrayOutputStream mByteArrayOutputStream;
    private ConnectionManager mConnectionManager;

    public AudioDataToServer() {
        mConnectionManager = ConnectionManager.getInstance();
    }

    @Override
    public void start() {
        mByteArrayOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public void onAudioDataReceived(byte[] data, int length) {
        if (null != mByteArrayOutputStream) {
            mByteArrayOutputStream.write(data, 0, length);
        }
    }

    @Override
    public void stop() {
//        String audioString = Base64.encodeToString(mByteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
        String audioString = "Lol";
        AudioSignalDto audioSignalDto = new AudioSignalDto(audioString);
        mConnectionManager.sendAudioSignal(audioSignalDto);
    }
}
