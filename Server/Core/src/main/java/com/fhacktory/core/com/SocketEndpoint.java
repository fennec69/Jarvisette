package com.fhacktory.core.com;

import com.fhacktory.common.*;
import com.fhacktory.core.ComInterfaceManager;
import com.fhacktory.core.DeviceManager;
import com.fhacktory.data.MessageType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
@ServerEndpoint(value = "/{uuid}", configurator = WebsocketConfigurator.class)
public class SocketEndpoint implements ComInterface, WebsocketEndpoint {

    @Inject
    private Map<MessageType, InputMessageProcessor> mInputMessageProcessorMap;

    @Inject
    private ComInterfaceManager mComInterfaceManager;

    @Inject
    private DeviceManager mDeviceManager;

    private static Map<String, Session> sessions = new TreeMap<String, Session>();
    private Gson mGson;

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Input module connected " + sess.getPathParameters().get("uuid"));
        String uuid = sess.getPathParameters().get("uuid");
        sessions.put(uuid, sess);
        mComInterfaceManager.register(uuid, this);
    }

    @OnMessage
    public void onWebSocketText(Session session, String message) {
        System.out.println("Received TEXT message: " + message);
        String uuid = session.getPathParameters().get("uuid");
        if (mGson == null) mGson = new Gson();
        InputMessageDto inputMessageDto = mGson.fromJson(message, InputMessageDto.class);
        String contentMessage = mGson.toJson(inputMessageDto.getMessage());
        if (inputMessageDto != null && inputMessageDto.getType() != null) {
            switch (inputMessageDto.getType()) {
                case REGISTERING:
                    ServiceRegisterDto serviceRegisterDto = mGson.fromJson(contentMessage, ServiceRegisterDto.class);
                    mDeviceManager.setCapabilities(uuid, serviceRegisterDto.getServices());
                    break;
                case LOCATION:
                    LocationDto locationDto = mGson.fromJson(contentMessage, LocationDto.class);
                    mDeviceManager.setLocations(uuid, locationDto.getLocations());
                    break;
                default:
                    InputMessageProcessor inputMessageProcessor = mInputMessageProcessorMap.get(inputMessageDto.getType());
                    inputMessageProcessor.process(mGson.toJson(inputMessageDto.getMessage()), uuid);
                    break;

            }
        }
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Input module disconnected " + uuid);
        sessions.remove(uuid);
        mComInterfaceManager.unregister(uuid);
        mDeviceManager.removeDevice(uuid);
    }

    @Override
    public void sendMessage(String message, String uuid) {
        if (sessions.containsKey(uuid)) {
            Session session = sessions.get(uuid);
            session.getAsyncRemote().sendText(message);
        }
    }
}
