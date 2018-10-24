package com.cssiot.cssbase.modules.wxgzh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx.gzh")
public class WxgzhConfiguration {
	private String appId;
	private String appSecret;
	private String messageTemplateId;
}
