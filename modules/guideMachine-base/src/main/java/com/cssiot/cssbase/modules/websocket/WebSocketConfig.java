package com.cssiot.cssbase.modules.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * websocket endpoint创建
 * @author 
 * 	2018-09-20 Diego.zhou 新建
 *
 */
@Configuration
public class WebSocketConfig {
	
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}