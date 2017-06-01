import com.fhacktory.apiai.config.ApiAiModule;
import com.fhacktory.common.ActionProcessor;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.core.ComInterfaceManager;
import com.fhacktory.core.DeviceManager;
import com.fhacktory.core.OutputActionProcessor;
import com.fhacktory.core.com.SocketEndpoint;
import com.fhacktory.core.config.CoreModule;
import com.fhacktory.core.data.conf_file.ConfigFileDeviceManager;
import com.fhacktory.input.audio.AudioInputModule;
import com.fhacktory.outputs.text.TextOutputModule;
import com.fhactory.input.text.TextInputModule;
import com.fhactory.output.tts.TtsOutputModule;
import com.fhactory.outputs.lights.LightOutputModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by farid on 13/05/2017.
 */
public class InjectionModule extends AbstractModule {

    public InjectionModule() {}

    @Override
    protected void configure() {
        init();
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

        //Action Detector
        install(new ApiAiModule());
    }
}