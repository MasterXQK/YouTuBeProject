package com.Morgan.bilibili.api.rabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Morgan
 * @create 2022-12-27-20:35
 */
@Configuration
public class RabbitConfig {

    public static final String QUEUENAME = "chatbot";

    @Bean
    public Queue helloQueue() {
        return new Queue(QUEUENAME);
    }
}
