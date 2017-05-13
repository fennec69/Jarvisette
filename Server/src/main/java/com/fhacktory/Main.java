package com.fhacktory;

import com.fhacktory.config.InjectionModule;
import com.fhacktory.outputs.endpoints.OutputSocketEndpoint;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        InjectionModule injectionModule = new InjectionModule();
        Injector injector = Guice.createInjector(injectionModule);

        Server server = new Server(80);

        ServletContextHandler servletHandler = new ServletContextHandler();

        servletHandler.addEventListener(injector.getInstance((GuiceResteasyBootstrapServletContextListener.class)));
        server.setHandler(servletHandler);

        ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(servletHandler);
        wscontainer.addEndpoint(OutputSocketEndpoint.class);

        server.start();
        server.join();
        /*String fileName = "./recording.pcm";
        Path path = Paths.get(fileName);
        System.out.println("Reading file");
        byte[] data = Files.readAllBytes(path);
        SpeechRecognizer speechRecognizer = new GoogleSpeechRecognizer();
        String test = speechRecognizer.speechToText(data);
        if(test != null) System.out.println(test);*/
    }

    public class JettyWebSocketServlet extends WebSocketServlet {
        @Override
        public void configure(WebSocketServletFactory factory) {
            factory.register(OutputSocketEndpoint.class);
        }
    }
}
