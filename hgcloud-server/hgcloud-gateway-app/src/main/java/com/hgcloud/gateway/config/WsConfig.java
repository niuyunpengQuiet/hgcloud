package com.hgcloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

/**
 * ws配置（Spring Framework 7 / Boot 4 下 WebFlux 网关默认使用 Reactor Netty）
 *
 * @author yubaoshan
 * @date 2025-08-05 23:30
 */
@Configuration
public class WsConfig {

    @Bean
    @Primary
    WebSocketClient reactorNettyWebSocketClient(){
        return new ReactorNettyWebSocketClient();
    }

    @Bean
    @Primary
    public RequestUpgradeStrategy requestUpgradeStrategy() {
        return new ReactorNettyRequestUpgradeStrategy();
    }
}
