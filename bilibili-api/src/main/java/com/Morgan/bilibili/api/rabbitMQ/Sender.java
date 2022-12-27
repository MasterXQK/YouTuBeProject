package com.Morgan.bilibili.api.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Morgan
 * @create 2022-12-27-20:34
 */
@Component
public class Sender {

    public static final String QUEUENAME = "chatbot";

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender:" + context);
        this.rabbitTemplate.convertAndSend(QUEUENAME, context);
    }
}

