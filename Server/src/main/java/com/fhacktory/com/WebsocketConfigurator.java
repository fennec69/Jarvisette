package com.fhacktory.com;

import com.fhacktory.common.WebsocketEndpoint;
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
