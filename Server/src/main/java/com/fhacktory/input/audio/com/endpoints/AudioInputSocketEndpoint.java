package com.fhacktory.input.audio.com.endpoints;

import com.fhacktory.common.WebsocketEndpoint;
import com.fhacktory.input.audio.AudioMessageProcessor;
import com.fhacktory.input.audio.com.dtos.AudioSignalDto;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
@ServerEndpoint(value = "/input/audio/{uuid}")
public class AudioInputSocketEndpoint implements WebsocketEndpoint {

    @Inject
    private AudioMessageProcessor mAudioMessageProcessor;

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
        AudioSignalDto audioSignalDto = mGson.fromJson(message, AudioSignalDto.class);
        mAudioMessageProcessor.onAudioMessageReceived(uuid, audioSignalDto.getSignal());
    }

    @OnClose
    public void onWebSocketClose(Session session) {
        String uuid = session.getPathParameters().get("uuid");
        System.out.println("Input module disconnected " + uuid);
        sessions.remove(uuid);
    }
}
