package com.cssiot.cssutil.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件内容
 * @author
 * 	2017-09-18 athena 创建
 */
@Component
@ConfigurationProperties(prefix="moduleOperate")
@Data
public class BaseConfig {
	
}