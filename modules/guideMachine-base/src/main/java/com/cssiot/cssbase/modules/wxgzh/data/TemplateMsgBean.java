package com.cssiot.cssbase.modules.wxgzh.data;

import java.util.Map;

import lombok.Data;

@Data
public class TemplateMsgBean {

	private String openId;
	private String templateId;
	private String actionUrl;
	private Map data;
}
