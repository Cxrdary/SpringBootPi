package edu.redwoods.cis18.tomcatpi;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        var clientMessage = message.getPayload();
        System.out.println(clientMessage);
        session.sendMessage(new TextMessage("Received message! "+clientMessage.hashCode()));
    }
}