import ai.kitt.snowboy.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class HotwordDetector {

    private final String LIB_NAME = "/libsnowboy-detect-java.dylib";
    private final String COMMON_RES_NAME = "/common.res";
    private final String UDML_FILE_PATH = "/snowboy.umdl";

    private SnowboyDetect mSnowboyDetect;

    public HotwordDetector() throws IOException {
        NativeUtils.loadLibraryFromJar(LIB_NAME);

        // Sets up Snowboy.
        File fileOut1;
        InputStream in1;
        File fileOut2;
        InputStream in2;
        in1 = HotwordDetector.class.getResourceAsStream(COMMON_RES_NAME);
        fileOut1 = File.createTempFile("commonres", ".res");
        OutputStream out1 = FileUtils.openOutputStream(fileOut1);
        IOUtils.copy(in1, out1);

        in2= HotwordDetector.class.getResourceAsStream(UDML_FILE_PATH);
        fileOut2 = File.createTempFile("snowboy", ".umdl");
        OutputStream out2 = FileUtils.openOutputStream(fileOut2);
        IOUtils.copy(in2, out2);

        in1.close();
        out1.close();
        in2.close();
        out2.close();

        mSnowboyDetect = new SnowboyDetect(fileOut1.toString(),fileOut2.toString());
        mSnowboyDetect.SetSensitivity("0.5");
        mSnowboyDetect.SetAudioGain(1);
    }

    public void startDetection() {
        try {
            // Sets up audio.
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetLine.open(format);
            targetLine.start();

            System.out.println("Hotword detection started");
            // Reads 0.1 second of audio in each call.
            byte[] targetData = new byte[3200];
            short[] snowboyData = new short[1600];
            int numBytesRead;

            while (true) {
                numBytesRead = targetLine.read(targetData, 0, targetData.length);

                if (numBytesRead == -1) {
                    System.out.print("Fails to read audio data.");
                    break;
                }

                ByteBuffer.wrap(targetData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(snowboyData);

                int result = mSnowboyDetect.RunDetection(snowboyData, snowboyData.length);
                if (result > 0) {
                    System.out.print("Hotword " + result + " detected!\n");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
