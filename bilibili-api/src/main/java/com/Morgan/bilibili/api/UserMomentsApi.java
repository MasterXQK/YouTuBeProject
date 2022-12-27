package com.Morgan.bilibili.api;

import com.Morgan.bilibili.api.support.UserSupport;
import com.Morgan.bilibili.dao.UserMomentsDao;
import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserMoment;
import com.Morgan.bilibili.domain.constant.UserMomentsConstant;
import com.Morgan.bilibili.service.UserMomentsService;
import com.Morgan.bilibili.service.util.RocketMQUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Morgan
 * @create 2022-12-06-18:18
 */
@RestController
public class UserMomentsApi {

    @Autowired
    private UserMomentsService userMomentsService;

    @Autowired
    private UserSupport userSupport;

    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    // 获取该用户订阅的所有关注者的相关动态 redis + rocketmq
    @GetMapping("/user-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribeMoments() throws Exception {
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> userMoments = userMomentsService.getUserSubscribeMoments(userId);
        return new JsonResponse<>(userMoments);
    }

    // 删除用户动态
    @DeleteMapping("/user-moments/{id}")
    public JsonResponse<String> deleteUserMoments(@PathVariable("id") Long id) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMomentsService.deleteUserMoments(userId, id);
        return JsonResponse.success();
    }

    // 获取用户自己的动态
    @GetMapping("/user-moments/{id}")
    public JsonResponse<UserMoment> getUserMoments(@PathVariable("id") Long id) throws Exception {
        Long userId = userSupport.getCurrentUserId();
//        UserMoment userMoment = userMomentsService.getUserMoments(userId, id);
//        return new JsonResponse<>(userMoment);
        return null;
    }


}
