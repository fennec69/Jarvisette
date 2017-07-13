import com.fhacktory.apiai.config.ApiAiModule;
import com.fhacktory.common.SpeechRecognizer;
import com.fhacktory.core.config.CoreModule;
import com.fhacktory.google_speech.config.GoogleSpeechModule;
import com.fhacktory.hotword_detector.SnowboyHotwordModule;
import com.fhacktory.input.audio.AudioInputModule;
import com.fhacktory.outputs.text.TextOutputModule;
import com.fhactory.input.text.TextInputModule;
import com.fhactory.output.tts.TtsOutputModule;
import com.fhactory.outputs.lights.LightOutputModule;
import com.google.inject.AbstractModule;
import test.TestSpeechRecognizer;

/**
 * Created by farid on 13/05/2017.
 */
public class InjectionModule extends AbstractModule {

    public InjectionModule() {}

    @Override
    protected void configure() {
        initForTest();
    }

    private void init() {
        //Input plugins
        install(new AudioInputModule());
        install(new TextInputModule());

        //Output plugins
        install(new LightOutputModule());
        install(new TextOutputModule());
        install(new TtsOutputModule());

        //Core
        install(new CoreModule());

        //Common plugins
        install(new ApiAiModule());
        install(new GoogleSpeechModule());
        install(new SnowboyHotwordModule());
    }

    private void initForTest() {
        //Input plugins
        install(new AudioInputModule());
        install(new TextInputModule());

        //Output plugins
        install(new LightOutputModule());
        install(new TextOutputModule());
        install(new TtsOutputModule());

        //Core
        install(new CoreModule());

        //Common plugins
        install(new ApiAiModule());
        bind(SpeechRecognizer.class).to(TestSpeechRecognizer.class);
        install(new SnowboyHotwordModule());
    }
}