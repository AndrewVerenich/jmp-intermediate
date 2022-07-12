package com.andver.service1;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.List;

/**
 * Sever side config
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        String endPoint = "/endpoint";
        registry.addEndpoint(endPoint).withSockJS().setHttpMessageCacheSize(1200);
        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry.addEndpoint(endPoint).setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        String webSocketEndPoint = "/endpoint";
        String webSocketTopic = "/topic";
        log.info("Configure websocket with endpoint:{} and topic:{}.", webSocketEndPoint, webSocketTopic);
        registry.setApplicationDestinationPrefixes(webSocketEndPoint);
        registry.enableSimpleBroker(webSocketTopic);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration)
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        registration.taskExecutor(executor);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration)
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        registration.taskExecutor(executor);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters)
    {
        return messageConverters.add(new MappingJackson2MessageConverter());
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration)
    {
        registration.setSendTimeLimit(30000).setSendBufferSizeLimit(2048 * 2048);
    }
}
