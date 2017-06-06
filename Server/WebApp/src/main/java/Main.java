/**
 * Created by farid on 13/05/2017.
 */
public class Main {

    public static final int BUFFER_SIZE = 1000000;

    public static void main(String[] args) throws Exception {

        HotwordDetector hotwordDetector = new HotwordDetector();
        hotwordDetector.startDetection();
      /*  InjectionModule injectionModule = new InjectionModule();
        Injector injector = Guice.createInjector(injectionModule);
        GuiceManager.injector = injector;

        String PORT = System.getenv("PORT");
        System.out.println(PORT);

        PORT = "8080";
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
        server.join();*/
    }

}
