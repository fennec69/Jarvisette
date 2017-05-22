package com.fhacktory.input.text.com.endpoints;

import com.fhacktory.common.WebsocketConfigurator;
import com.fhacktory.common.ComInterface;
import com.fhacktory.common.ComInterfaceManager;
import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.input.text.TextMessageProcessor;
import com.fhacktory.input.text.com.dtos.TextCommandDto;
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
@ServerEndpoint(value = "/input/command/{uuid}", configurator = WebsocketConfigurator.class)
public class TextInputSocketEndpoint implements WebsocketEndpoint, ComInterface {

    @Inject
    private ComInterfaceManager mComInterfaceManager;

    @Inject
    private TextMessageProcessor mTextMessageProcessor;

    private static Map<String, Session> sessions = new TreeMap<String, Session>();
    private Gson mGson;

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Input module connected " + sess.getPathParameters().get("uuid"));
        sessions.put(sess.getPathParameters().get("uuid"), sess);
        mComInterfaceManager.register(sess.getPathParameters().get("uuid"), this);
    }

    @OnMessage
    public void onWebSocketText(Session session, String message) {
        System.out.println("Received TEXT message: " + message);
        String uuid = session.getPathParameters().get("uuid");
        if (mGson == null) mGson = new Gson();
        TextCommandDto textCommandDto = mGson.fromJson(message, TextCommandDto.class);
        mTextMessageProcessor.processMessage(textCommandDto, uuid);
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Input module disconnected " + uuid);
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
