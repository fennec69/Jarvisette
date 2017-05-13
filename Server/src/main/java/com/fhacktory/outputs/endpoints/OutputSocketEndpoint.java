package com.fhacktory.outputs.endpoints;

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
public class OutputSocketEndpoint {

    private static Map<String, Session> sessions = new TreeMap<String, Session>();

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Output module connected " + sess.getPathParameters().get("uuid"));
        sessions.put(sess.getPathParameters().get("uuid"), sess);
    }

    @OnMessage
    public void onWebSocketText(Session session, String message) {
        System.out.println("Received TEXT message: " + message);
        session.getAsyncRemote().sendText("{\"action\":\"light\", \"power\":true}");
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Output module disconnected " + uuid);
        sessions.remove(uuid);
    }

    public static boolean sendMessage(String message, String uuid) {
        if(sessions.containsKey(uuid)) {
            Session session = sessions.get(uuid);
            session.getAsyncRemote().sendText(message);
            return true;
        }
        return false;
    }
}