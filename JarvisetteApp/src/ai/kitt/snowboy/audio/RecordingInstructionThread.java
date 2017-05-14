package ai.kitt.snowboy.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ai.kitt.snowboy.Constants;
import ai.kitt.snowboy.MsgEnum;

/**
 * Created by Frederic on 13/05/2017.
 */

public class RecordingInstructionThread {

    private static final String TAG = RecordingInstructionThread.class.getSimpleName();
    private static final long LISTENING_TIME = 3 * 1000;

    private AudioDataReceivedListener listener = null;
    private Handler handler = null;
    private Thread thread;

    private static String strEnvWorkSpace = Constants.DEFAULT_WORK_SPACE;

    private MediaPlayer player = new MediaPlayer();

    public RecordingInstructionThread(Handler handler, AudioDataReceivedListener listener) {
        this.handler = handler;
        this.listener = listener;
        try {
            player.setDataSource(strEnvWorkSpace+"roger.wav");
            player.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Playing ding sound error", e);
        }
    }

    private void sendMessage(MsgEnum what, Object obj){
        if (null != handler) {
            Message msg = handler.obtainMessage(what.ordinal(), obj);
            handler.sendMessage(msg);
        }
    }

    public void startRecording() {
        if (thread != null)
            return;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                record();
            }
        });
        thread.start();
    }

    public void stopRecording() {
        player.start();
        if (thread == null)
            return;
        thread = null;
    }

    private void record() {
        Log.v(TAG, "Start");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        // Buffer size in bytes: for 0.1 second of audio
        int bufferSize = (int)(Constants.SAMPLE_RATE * 0.1 * 2);
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = Constants.SAMPLE_RATE * 2;
        }

        byte[] audioBuffer = new byte[bufferSize];
        AudioRecord record = new AudioRecord(
                MediaRecorder.AudioSource.DEFAULT,
                Constants.SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "Audio Record can't initialize!");
            return;
        }
        record.startRecording();
        if (null != listener) {
            listener.start();
        }
        Log.v(TAG, "Start recording instruction");

        long shortsRead = 0;

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < (LISTENING_TIME + startTime)) {
            record.read(audioBuffer, 0, audioBuffer.length);

            if (null != listener) {
                listener.onAudioDataReceived(audioBuffer, audioBuffer.length);
            }

            // Converts to short array.
            short[] audioData = new short[audioBuffer.length / 2];
            ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioData);

            shortsRead += audioData.length;
        }

        record.stop();
        record.release();

        sendMessage(MsgEnum.MSG_INSTRUCTION_OVER, "Instruction Over");

        if (null != listener) {
            listener.stop();
        }
        Log.v(TAG, String.format("Recording stopped. Samples read: %d", shortsRead));
    }
}
