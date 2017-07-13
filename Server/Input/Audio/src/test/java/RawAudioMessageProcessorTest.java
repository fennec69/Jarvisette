import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.google_speech.GoogleSpeechRecognizer;
import com.fhacktory.hotword_detector.SnowboyHotwordModule;
import com.fhacktory.input.audio.raw.RawAudioMessageProcessor;
import com.fhacktory.input.audio.raw.RawAudioSignalDto;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by farid on 19/06/2017.
 */
public class RawAudioMessageProcessorTest {

  //  @Test
    public void test() {
        TestRawAudioModule testModule = new TestRawAudioModule();
        Injector injector = Guice.createInjector(testModule);
        RawAudioMessageProcessor rawAudioMessageProcessor = injector.getInstance(RawAudioMessageProcessor.class);
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            byte[] data = Files.readAllBytes(Paths.get(classLoader.getResource("snowboy_sample.wav").toURI()));
            RawAudioSignalDto rawAudioSignalDto = new RawAudioSignalDto();
            rawAudioSignalDto.setRawSignal(data);
            rawAudioSignalDto.setResponseUUID("11111");
            rawAudioSignalDto.setResponseType("text");
            Gson gson = new Gson();
            rawAudioMessageProcessor.process(gson.toJson(rawAudioSignalDto), "2222");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class TestRawAudioModule extends AbstractModule {

        @Override
        protected void configure() {
            install(new SnowboyHotwordModule());
            bind(InputMessageProcessor.class).to(TestAudioMessageProcessor.class);
            bind(RawAudioMessageProcessor.class);
        }
    }

    static class TestAudioMessageProcessor implements InputMessageProcessor {

        @Override
        public synchronized void process(String message, String inputUuid) {

        }
    }
}
