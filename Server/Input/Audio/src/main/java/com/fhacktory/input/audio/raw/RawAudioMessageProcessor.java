package com.fhacktory.input.audio.raw;

import com.fhacktory.common.HotwordDetector;
import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.input.audio.command.AudioMessageProcessor;
import com.fhacktory.input.audio.command.AudioSignalDto;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by farid on 06/06/2017.
 */
@Singleton
public class RawAudioMessageProcessor implements InputMessageProcessor {

    private final int BYTE_BUFFER_SIZE = 1000000;
    private final int INPUT_COMMAND_DELAY_IN_MS = 5000;

    @Inject
    private HotwordDetector mHotwordDetector;

    @Inject
    private AudioMessageProcessor mAudioMessageProcessor;

    private Gson mGson;
    private Timer mTimer;
    private volatile Map<String, ExipirableBuffer> mInputMap;

    public RawAudioMessageProcessor() {
        mTimer = new Timer();
        mInputMap = new TreeMap<>();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                for(String uuid : mInputMap.keySet()) {
                    ExipirableBuffer exipirableBuffer = mInputMap.get(uuid);
                    if(exipirableBuffer.getLastUpdateTime() < currentTime - INPUT_COMMAND_DELAY_IN_MS) {
                        AudioSignalDto audioSignalDto = new AudioSignalDto();
                        audioSignalDto.setResponseType(exipirableBuffer.getResponseType());
                        audioSignalDto.setResponseUUID(exipirableBuffer.getResponseUUID());
                        mAudioMessageProcessor.process(mGson.toJson(audioSignalDto), uuid);
                        mInputMap.remove(uuid);
                    }
                }
            }
        }, 0, 1000);
    }

    @Override
    public synchronized void process(String message, String inputUuid) {
        RawAudioSignalDto dto = mGson.fromJson(message, RawAudioSignalDto.class);
        if(!mInputMap.containsKey(inputUuid) && mHotwordDetector.isHotwordDetected(dto.getRawSignal())) {
            mInputMap.put(inputUuid, new ExipirableBuffer(dto.getResponseType(), dto.getResponseUUID()));
        }
        else {
            ExipirableBuffer exipirableBuffer = mInputMap.get(inputUuid);
            exipirableBuffer.addBytes(dto.getRawSignal());
        }
    }

    private class ExipirableBuffer {
        private volatile long mLastUpdateTime;
        private volatile ByteBuffer mByteBuffer;
        private String mResponseType;
        private String mResponseUUID;

        public ExipirableBuffer(String responseType, String responseUUID) {
            this.mResponseType = responseType;
            this.mResponseUUID = responseUUID;
            mLastUpdateTime = Calendar.getInstance().getTimeInMillis();
            mByteBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);
        }

        public long getLastUpdateTime() {
            return mLastUpdateTime;
        }

        public void setLastUpdateTime(long lastUpdateTime) {
            mLastUpdateTime = lastUpdateTime;
        }

        public ByteBuffer getByteBuffer() {
            return mByteBuffer;
        }

        public void addBytes(byte[] bytes) {
            mLastUpdateTime = Calendar.getInstance().getTimeInMillis();
            mByteBuffer.put(bytes);
        }

        public String getResponseType() {
            return mResponseType;
        }

        public String getResponseUUID() {
            return mResponseUUID;
        }
    }
}
