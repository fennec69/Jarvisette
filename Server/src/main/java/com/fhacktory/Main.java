package com.fhacktory;

import com.fhacktory.plugins.outputs.tts.google_translate.GoogleTTS;
import com.fhacktory.plugins.outputs.tts.google_translate.endpoints.GoogleTranslateEndpoint;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static final int BUFFER_SIZE = 1000000;

    public static void main(String[] args) throws Exception {

        GoogleTTS googleTTS = new GoogleTTS();
        byte[] data = googleTTS.getTTS("Salut salle con", "fr");

        File targetFile = new File("test.mp3");
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        fileOutputStream.write(data);

        /*InjectionModule injectionModule = new InjectionModule();
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
        websocketConfigurator.addEnpoints(wscontainer);

        server.start();
        server.join();*/
    }

}
