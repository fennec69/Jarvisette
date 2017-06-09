package com.fhacktory.hotword_detector;

import ai.kitt.snowboy.SnowboyDetect;
import com.fhacktory.common.HotwordDetector;
import com.fhacktory.utils.NativeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SnowboyHotwordDetector implements HotwordDetector {

    private final String LIB_NAME = "/libsnowboy-detect-java.dylib";
    private final String COMMON_RES_NAME = "/common.res";
    private final String UDML_FILE_PATH = "/snowboy.umdl";

    private SnowboyDetect mSnowboyDetect;

    public SnowboyHotwordDetector() throws IOException {
        NativeUtils.loadLibraryFromJar(LIB_NAME);

        // Sets up Snowboy.
        File fileOut1;
        InputStream in1;
        File fileOut2;
        InputStream in2;
        in1 = SnowboyHotwordDetector.class.getResourceAsStream(COMMON_RES_NAME);
        fileOut1 = File.createTempFile("commonres", ".res");
        OutputStream out1 = FileUtils.openOutputStream(fileOut1);
        IOUtils.copy(in1, out1);

        in2 = SnowboyHotwordDetector.class.getResourceAsStream(UDML_FILE_PATH);
        fileOut2 = File.createTempFile("snowboy", ".umdl");
        OutputStream out2 = FileUtils.openOutputStream(fileOut2);
        IOUtils.copy(in2, out2);

        in1.close();
        out1.close();
        in2.close();
        out2.close();

        mSnowboyDetect = new SnowboyDetect(fileOut1.toString(), fileOut2.toString());
        mSnowboyDetect.SetSensitivity("0.5");
        mSnowboyDetect.SetAudioGain(1);
    }

    public boolean isHotwordDetected(byte[] rawSignal) {
        short[] snowboyData = new short[1600];
        ByteBuffer.wrap(rawSignal).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(snowboyData);
        int result = mSnowboyDetect.RunDetection(snowboyData, snowboyData.length);
        return result > 0;
    }
}
