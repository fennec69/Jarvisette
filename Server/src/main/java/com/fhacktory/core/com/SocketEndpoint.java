package com.fhacktory.core.com;

import com.fhacktory.common.ComInterface;
import com.fhacktory.common.InputMessageProcessor;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.data.MessageType;
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
@ServerEndpoint(value = "/{uuid}")
public class SocketEndpoint implements WebsocketEndpoint, ComInterface {

    @Inject
    private Map<MessageType, InputMessageProcessor> mInputMessageProcessorMap;

    private static Map<String, Session> sessions = new TreeMap<String, Session>();
    private Gson mGson;

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Input module connected " + sess.getPathParameters().get("uuid"));
        sessions.put(sess.getPathParameters().get("uuid"), sess);
    }

    @OnMessage
    public void onWebSocketText(Session session, String message) {
        System.out.println("Received TEXT message: " + message);
        String uuid = session.getPathParameters().get("uuid");
        if (mGson == null) mGson = new Gson();
        InputMessageDto inputMessageDto = mGson.fromJson(message, InputMessageDto.class);
        if(inputMessageDto != null && inputMessageDto.getType() != null) {
            InputMessageProcessor inputMessageProcessor = mInputMessageProcessorMap.get(inputMessageDto.getType());
            inputMessageProcessor.process(message, uuid);
        }
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Input module disconnected " + uuid);
        sessions.remove(uuid);
    }

    @Override
    public void sendMessage(String message, String uuid) {
        if(sessions.containsKey(uuid)) {
            Session session = sessions.get(uuid);
            session.getAsyncRemote().sendText(message);
        }
    }
}
