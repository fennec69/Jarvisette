package com.fhacktory.outputs.com.socket.endpoints;

import com.fhacktory.common.ComInterface;
import com.fhacktory.common.ComInterfaceProcessor;
import com.fhacktory.outputs.common.OutputDeviceManager;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.data.entities.OutputDevice;
import com.fhacktory.outputs.com.socket.dtos.OutputRegisterDto;
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
@ServerEndpoint(value = "/output/{uuid}")
public class OutputSocketEndpoint implements ComInterface, WebsocketEndpoint {

    @Inject
    private ComInterfaceProcessor mComInterfaceProcessor;

    @Inject
    private OutputDeviceManager mOutputDeviceManager;

    private static Map<String, Session> sessions = new TreeMap<String, Session>();

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Output module connected " + sess.getPathParameters().get("uuid"));
        sessions.put(sess.getPathParameters().get("uuid"), sess);
        mComInterfaceProcessor.register(sess.getPathParameters().get("uuid"), this);
    }

    @OnMessage
    public void onWebSocketText(Session session, String message) {
        System.out.println("Received TEXT message: " + message);
        Gson gson = new Gson();
        String uuid = session.getPathParameters().get("uuid");
        OutputRegisterDto outputRegisterDto = gson.fromJson(message, OutputRegisterDto.class);
        if(outputRegisterDto.getServices() != null) {
            mOutputDeviceManager.setCapabilities(uuid, outputRegisterDto.getServices());
        }
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Output module disconnected " + uuid);
        sessions.remove(uuid);
        mComInterfaceProcessor.unregister(uuid);
    }

    @Override
    public void sendMessage(String message, OutputDevice outputDevice) {
        if(sessions.containsKey(outputDevice.getUUID())) {
            Session session = sessions.get(outputDevice.getUUID());
            session.getAsyncRemote().sendText(message);
        }
    }
}