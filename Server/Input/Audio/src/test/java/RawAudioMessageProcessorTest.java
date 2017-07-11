import com.fhacktory.common.HotwordDetector;
import com.fhacktory.input.audio.command.AudioMessageProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.junit.BeforeClass;

/**
 * Created by farid on 19/06/2017.
 */
public class RawAudioMessageProcessorTest {

    @BeforeClass
    public void guiceConfig() {

    }

    @Inject
    private HotwordDetector mHotwordDetector;

    @Inject
    private AudioMessageProcessor mAudioMessageProcessor;

    class TestModule extends AbstractModule {

        @Override
        protected void configure() {

        }
    }
}
