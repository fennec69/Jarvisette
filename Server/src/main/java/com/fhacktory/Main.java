package com.fhacktory;

import com.fhacktory.common.WebsocketConfigurator;
import com.fhacktory.core.config.GuiceManager;
import com.fhacktory.core.config.InjectionModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static final int BUFFER_SIZE = 1000000;

    public static void main(String[] args) throws Exception {

        InjectionModule injectionModule = new InjectionModule();
        Injector injector = Guice.createInjector(injectionModule);
        GuiceManager.injector = injector;

        String PORT = System.getenv("PORT");
        System.out.println(PORT);

        PORT = "5000";
        Server server = new Server(Integer.parseInt(PORT));

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(HttpServletDispatcher.class, "/*");
        servletHandler.addEventListener(injector.getInstance((GuiceResteasyBootstrapServletContextListener.class)));
        server.setHandler(servletHandler);

        ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(servletHandler);
        WebsocketConfigurator websocketConfigurator = injector.getInstance(WebsocketConfigurator.class);
        wscontainer.setDefaultMaxTextMessageBufferSize(BUFFER_SIZE);
        websocketConfigurator.addEnpoints(wscontainer);

        server.start();
        server.join();
    }

}
