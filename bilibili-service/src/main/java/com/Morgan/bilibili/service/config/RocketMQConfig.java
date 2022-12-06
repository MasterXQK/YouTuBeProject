package com.Morgan.bilibili.service.config;

import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserMoment;
import com.Morgan.bilibili.domain.constant.UserMomentsConstant;
import com.Morgan.bilibili.service.UserFollowingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.lettuce.core.event.command.CommandSucceededEvent;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan
 * @create 2022-12-06-17:43
 */
@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;


//    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;

//    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

//    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = msgs.get(0);
                if (msg == null)
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                String bodyStr = new String(msg.getBody());
                UserMoment userMoment = JSON.parseObject(bodyStr, UserMoment.class);
                Long userId = userMoment.getUserId();
                List<User> fans = userFollowingService.getFans(userId);
                for (User fan : fans) {
                    String redisKey = "subscribed-" + fan.getId();
                    String subscribedStr = redisTemplate.opsForValue().get(redisKey);
                    List<UserMoment> subscribedList;
                    if (StringUtil.isNullOrEmpty(subscribedStr)) {
                        subscribedList = new ArrayList<>();
                    } else {
                        subscribedList = JSONArray.parseArray(subscribedStr, UserMoment.class);
                    }
                    subscribedList.add(userMoment);
                    redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(subscribedList));
                }
                System.out.println("打印消息：" + msg.toString());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }
}
