//package com.andver.service2;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class MyStompSessionHandler extends StompSessionHandlerAdapter
//{
//    @Override
//    public void afterConnected(StompSession session, StompHeaders connectedHeaders)
//    {
//        session.subscribe("/topic", this);
//    }
//
//    @Override
//    public void handleFrame(StompHeaders headers, Object payload)
//    {
//        log.info("Received : " + payload);
//    }
//}
