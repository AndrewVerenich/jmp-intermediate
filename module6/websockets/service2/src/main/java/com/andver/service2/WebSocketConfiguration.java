//package com.andver.service2;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.web.socket.WebSocketHttpHeaders;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//import org.springframework.web.socket.sockjs.client.SockJsClient;
//import org.springframework.web.socket.sockjs.client.Transport;
//import org.springframework.web.socket.sockjs.client.WebSocketTransport;
//import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;
//
//import javax.websocket.ContainerProvider;
//import javax.websocket.WebSocketContainer;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Client side config
// */
//@Configuration
//public class WebSocketConfiguration
//{
//    @Bean
//    public WebSocketStompClient webSocketStompClient(List<Transport> transports, ObjectMapper mapper,
//                                                     MyStompSessionHandler handler)
//    {
//        SockJsClient sockJsClient = new SockJsClient(transports);
//        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
////        sockJsClient.setInfoReceiver((infoUrl, headers) -> "");
//
//        WebSocketStompClient result = new WebSocketStompClient(sockJsClient);
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setObjectMapper(mapper);
//        result.setMessageConverter(converter);
//        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
//
//        headers.add("Authorization","Bearer-4619f420-1a40-11ed-c1d1-d8576dd68200");
//        headers.add("farmId","300000000000003");
//        result.connect("wss://app-atl.five9.com:443/supsvcs/sws", headers,handler);
//        return result;
//    }
//
//    @Bean
//    public List<Transport> transports()
//    {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        container.setDefaultMaxTextMessageBufferSize(8192);
//        container.setDefaultMaxBinaryMessageBufferSize(8192);
//        WebSocketClient wsClient = new StandardWebSocketClient(container);
//        Transport transport = new WebSocketTransport(wsClient);
//
//        return Collections.singletonList(transport);
//    }
//}
