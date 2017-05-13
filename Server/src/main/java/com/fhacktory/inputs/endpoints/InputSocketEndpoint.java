package com.fhacktory.inputs.endpoints;

import com.fhacktory.inputs.endpoints.audio.AudioSignalDto;
import com.fhacktory.utils.SignalUtils;
import com.google.gson.Gson;

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
@ServerEndpoint(value = "/input/{uuid}")
public class InputSocketEndpoint {

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
        if(mGson == null) mGson = new Gson();
        AudioSignalDto audioSignalDto = mGson.fromJson(message, AudioSignalDto.class);
        double rms = SignalUtils.calculateRMS(audioSignalDto.getSignal());
        System.out.println("Signal rms: " + String.valueOf(rms));
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Input module disconnected " + uuid);
        sessions.remove(uuid);
    }
}
