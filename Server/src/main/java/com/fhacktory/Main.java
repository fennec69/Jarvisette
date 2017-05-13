package com.fhacktory;

import com.XYSeriesDemo;
import com.fhacktory.config.InjectionModule;
import com.fhacktory.inputs.endpoints.InputSocketEndpoint;
import com.fhacktory.outputs.endpoints.OutputSocketEndpoint;
import com.fhacktory.utils.SignalUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static final int BUFFER_SIZE = 1000000;

    public static void main(String[] args) throws Exception {

        InjectionModule injectionModule = new InjectionModule();
        Injector injector = Guice.createInjector(injectionModule);

        Server server = new Server(80);

        ServletContextHandler servletHandler = new ServletContextHandler();

        servletHandler.addEventListener(injector.getInstance((GuiceResteasyBootstrapServletContextListener.class)));
        server.setHandler(servletHandler);

        ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(servletHandler);
        wscontainer.addEndpoint(OutputSocketEndpoint.class);
        wscontainer.addEndpoint(InputSocketEndpoint.class);
        wscontainer.setDefaultMaxTextMessageBufferSize(BUFFER_SIZE);

        server.start();
        server.join();

       /* SpeechRecognizer speechRecognizer = new GoogleSpeechRecognizer();
        String test = speechRecognizer.speechToText(data);
        if(test != null) System.out.println(test);*/
    }

    private void drawSignal() {
        final XYSeriesDemo demo = new XYSeriesDemo("XY Series Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    private void runSampleTest() throws IOException {
        String fileName = "./sample1.wav";
        Path path = Paths.get(fileName);
        System.out.println("Reading file");
        short[] data = SignalUtils.byteArrayToShortArray(Files.readAllBytes(path));
        System.out.println(SignalUtils.calculateRMS(data));

        fileName = "./sample2.wav";
        path = Paths.get(fileName);
        System.out.println("Reading file");
        data = SignalUtils.byteArrayToShortArray(Files.readAllBytes(path));
        System.out.println(SignalUtils.calculateRMS(data));

        fileName = "./sample3.wav";
        path = Paths.get(fileName);
        System.out.println("Reading file");
        data = SignalUtils.byteArrayToShortArray(Files.readAllBytes(path));
        System.out.println(SignalUtils.calculateRMS(data));
    }

    public class JettyWebSocketServlet extends WebSocketServlet {
        @Override
        public void configure(WebSocketServletFactory factory) {
            factory.register(OutputSocketEndpoint.class);
            factory.register(InputSocketEndpoint.class);
            factory.getPolicy().setMaxTextMessageSize(BUFFER_SIZE);
        }
    }
}
