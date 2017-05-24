package com.fhacktory.plugins.outputs.com.socket.endpoints;

import com.fhacktory.common.ComInterface;
import com.fhacktory.common.ComInterfaceManager;
import com.fhacktory.common.WebsocketConfigurator;
import com.fhacktory.common.DeviceManager;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.plugins.outputs.com.socket.dtos.OutputRegisterDto;
import com.google.gson.Gson;
import com.google.inject.Inject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by farid on 13/05/2017.
 */
@ServerEndpoint(value = "/output/{uuid}", configurator = WebsocketConfigurator.class)
public class OutputSocketEndpoint implements ComInterface, WebsocketEndpoint {

    @Inject
    private ComInterfaceManager mComInterfaceManager;

    @Inject
    private DeviceManager mDeviceManager;

    private static Map<String, Session> sessions = new TreeMap<String, Session>();

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Output module connected " + sess.getPathParameters().get("uuid"));
        sessions.put(sess.getPathParameters().get("uuid"), sess);
    }

    @OnMessage
    public void onWebSocketText(Session session, String message) {
        System.out.println("Received TEXT message: " + message);
        Gson gson = new Gson();
        String uuid = session.getPathParameters().get("uuid");
        OutputRegisterDto outputRegisterDto = gson.fromJson(message, OutputRegisterDto.class);
        if(outputRegisterDto != null && outputRegisterDto.getServices() != null) {
            for(String service : outputRegisterDto.getServices()) {
                mComInterfaceManager.register(uuid, service, this);
            }
            mDeviceManager.setCapabilities(uuid, outputRegisterDto.getServices());
        }
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Output module disconnected " + uuid);
        sessions.remove(uuid);
        mComInterfaceManager.unregister(uuid);
    }

    @Override
    public void sendMessage(String message, String uuid) {
        if(sessions.containsKey(uuid)) {
            Session session = sessions.get(uuid);
            session.getAsyncRemote().sendText(message);
        }
    }
}