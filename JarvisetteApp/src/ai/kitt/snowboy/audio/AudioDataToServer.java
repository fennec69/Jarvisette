package ai.kitt.snowboy.audio;

import java.io.ByteArrayOutputStream;

import manager.ConnectionManager;
import websocket.dto.AudioSignalDto;

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
//        String audioString = Base64.encodeToString(mByteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        String audioString = "LooooL";
        AudioSignalDto audioSignalDto = new AudioSignalDto(audioString);
        mConnectionManager.sendAudioSignal(audioSignalDto);
    }
}
