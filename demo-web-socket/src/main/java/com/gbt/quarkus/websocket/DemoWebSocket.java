package com.gbt.quarkus.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/demows/{clientId}")
@ApplicationScoped
public class DemoWebSocket {

    Logger log = LoggerFactory.getLogger(DemoWebSocket.class);
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId) {
        log.debug("Opening a session");
        sessions.put(clientId, session);
    }


    @OnClose
    public void onClose(Session session, @PathParam("clientId") String clientId) {
        log.debug("Closing a session");
        sessions.remove(clientId, session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("Message from {}", session.getId());
        sessions.forEach((clientId, session1) -> {
            session1.getAsyncRemote().sendText(clientId + ": " + message, sendResult -> {
                if (sendResult.getException() != null) {
                    log.error("Error sending a message {}", message, sendResult.getException());
                } else {
                    log.debug("Broadcast to everyone successfully!!!");
                }
            });
        });
    }
}
