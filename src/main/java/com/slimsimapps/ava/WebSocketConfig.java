package com.slimsimapps.ava;

import com.slimsimapps.ava.badlog.BadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    BadLogService log;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        log.a(config);
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        log.o(config);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.a(registry);
        //registry.addEndpoint("/gs-guide-websocket").withSockJS();

        registry.addEndpoint("/gs-guide-websocket")
                .setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy()))
                //.setAllowedOrigins( "slimsim.heliohost.org" )
                //.setAllowedOrigins( "*" ) // this one worked, but was buggy, slow and was interrupted after a while...
                .withSockJS();

        log.o(registry);
    }

}