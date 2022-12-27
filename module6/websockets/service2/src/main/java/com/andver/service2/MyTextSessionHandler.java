package com.andver.service2;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class MyTextSessionHandler extends TextWebSocketHandler
{
    @Getter
    private WebSocketSession webSocketSession;

    public MyTextSessionHandler() throws ExecutionException, InterruptedException
    {
        StandardWebSocketClient client = new StandardWebSocketClient();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer-51659c6b-1e2c-11ed-de26-4539bf365e4b");
        httpHeaders.add("farmId", "149");
        webSocketSession = client.doHandshake(this, new WebSocketHttpHeaders(httpHeaders),
                URI.create("wss://app-atl.five9.com:443/supsvcs/sws")).get();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
    {
        log.info("Received : " + message.getPayload());
    }
}
