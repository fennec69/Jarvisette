package com.fhacktory.common;

import com.fhacktory.core.config.GuiceManager;
import com.google.inject.Inject;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

/**
 * Created by farid on 19/05/2017.
 */
public class WebsocketConfigurator extends ServerEndpointConfig.Configurator {

    @Inject
    private Set<WebsocketEndpoint> mWebsocketEndpoints;

    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return GuiceManager.injector.getInstance(endpointClass);
    }

    public void addEnpoints(ServerContainer serverContainer) {
        for(WebsocketEndpoint websocketEndpoint : mWebsocketEndpoints) {
            try {
                serverContainer.addEndpoint(websocketEndpoint.getClass());
            } catch (DeploymentException e) {
                e.printStackTrace();
            }
        }
    }

    
}
