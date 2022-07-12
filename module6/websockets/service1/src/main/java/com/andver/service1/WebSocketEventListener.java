package com.andver.service1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * The listener interface for receiving webSocketEvent events. The class that is
 * interested in processing a webSocketEvent event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addWebSocketEventListener<code> method. When the
 * webSocketEvent event occurs, that object's appropriate method is invoked.
 *
 */
@Component
@Slf4j
public class WebSocketEventListener
{

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event)
    {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("Received a new web socket connection IP:{}", headerAccessor.getDestination());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("Disconnnect web socket connection from IP:{}", headerAccessor.getDestination());
    }
}