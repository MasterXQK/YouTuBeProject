package com.Morgan.bilibili.api.rabbitMQ;

import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.service.UserService;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Morgan
 * @create 2022-12-27-20:34
 */
@Component
@RabbitListener(queues = "chatbot")
public class Receiver {

    @Autowired
    private UserService userService;

    @RabbitHandler
    public void process(String s) {
        User user = new User();
        user.setPassword("123456");
        user.setUpdateTime(new Date());
        user.setPhone("15986776923");
        user.setEmail(s);
        userService.mqAddUser(user);
        System.out.println(JSON.toJSONString(user));
//        System.out.println("Receiver:" + s);
    }
}

