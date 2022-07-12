package com.andver.service1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class {@link WebSocketWriter} use to send message to client using
 * websocket.
 */
@Slf4j
@Component
public class WebSocketWriter
{
    private static final String CUSTOM_HEADER = "agentStatus";


    /**
     * The messaging template.
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Scheduled(fixedRate = 5000)
    public void sendMessage()
    {
        log.info("Sending message");
        sendObject("agentStateEvents...", CUSTOM_HEADER);
    }


    /**
     * Send object.
     *
     * @param <T>    the generic type
     * @param object the object
     * @param type   the type
     */
    private <T> void sendObject(T object, String type)
    {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", type);
        messagingTemplate.convertAndSend("/topic", object, headers);
    }

}
