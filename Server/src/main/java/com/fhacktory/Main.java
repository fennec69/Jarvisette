package com.fhacktory;

import com.fhacktory.communication.inputs.endpoints.InputSocketEndpoint;
import com.fhacktory.communication.outputs.endpoints.OutputSocketEndpoint;
import com.fhacktory.config.InjectionModule;
import com.fhacktory.data.conf.ConfigLoader;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static final int BUFFER_SIZE = 1000000;

    public static void main(String[] args) throws Exception {

        ConfigLoader.getInstance().loadFromConfFile();
        InjectionModule injectionModule = new InjectionModule();
        Injector injector = Guice.createInjector(injectionModule);

        String PORT = System.getenv("PORT");
        System.out.println(PORT);
        
        Server server = new Server(Integer.parseInt(PORT));

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addEventListener(injector.getInstance((GuiceResteasyBootstrapServletContextListener.class)));
        servletHandler.addServlet(HttpServletDispatcher.class, "/*");
        servletHandler.addEventListener(injector.getInstance((GuiceResteasyBootstrapServletContextListener.class)));
        server.setHandler(servletHandler);

        ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(servletHandler);
        wscontainer.addEndpoint(OutputSocketEndpoint.class);
        wscontainer.addEndpoint(InputSocketEndpoint.class);
        wscontainer.setDefaultMaxTextMessageBufferSize(BUFFER_SIZE);

        server.start();
        server.join();
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
