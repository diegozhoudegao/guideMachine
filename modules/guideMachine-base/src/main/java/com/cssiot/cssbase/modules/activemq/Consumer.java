package com.cssiot.cssbase.modules.activemq;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author 
 * 	2018-09-19 Diego.zhou 新建
 *
 */
@Component  
public class Consumer {  
        // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
    @JmsListener(destination = "mytest.queue")  
    public void receiveQueue(String text) {  
        System.out.println("Consumer收到的报文为:"+text);  
    }  
}
