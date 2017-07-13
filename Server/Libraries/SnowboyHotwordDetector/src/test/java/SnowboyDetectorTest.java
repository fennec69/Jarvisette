import com.fhacktory.hotword_detector.SnowboyHotwordDetector;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by farid on 12/07/2017.
 */
public class SnowboyDetectorTest {

   // @Test
    public void testDetectHotwordFromFile() {
        try {
            SnowboyHotwordDetector hotwordDetector = new SnowboyHotwordDetector();
            ClassLoader classLoader = getClass().getClassLoader();
            byte[] data = Files.readAllBytes(Paths.get(classLoader.getResource("snowboy.wav").toURI()));
            byte[] data2 = Files.readAllBytes(Paths.get(classLoader.getResource("snowboy_sample.wav").toURI()));
            byte[] data3 = Files.readAllBytes(Paths.get(classLoader.getResource("ding.wav").toURI()));
            Assert.assertTrue(hotwordDetector.isHotwordDetected(data));
            Assert.assertTrue(hotwordDetector.isHotwordDetected(data2));
            Assert.assertFalse(hotwordDetector.isHotwordDetected(data3));
        } catch (Exception e) {
            Assert.fail();
            e.printStackTrace();
        }
    }
}
