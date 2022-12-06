package com.Morgan.bilibili.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.Morgan.bilibili.dao.UserMomentsDao;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserMoment;
import com.Morgan.bilibili.domain.constant.UserMomentsConstant;
import com.Morgan.bilibili.domain.exception.ConditionException;
import com.Morgan.bilibili.service.util.RocketMQUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Morgan
 * @create 2022-12-06-18:19
 */
@Service
public class UserMomentsService {

    @Autowired
    private UserMomentsDao userMomentsDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void addUserMoments(UserMoment userMoment) throws Exception {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);

//        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentsProducer");
//        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
//        RocketMQUtil.syncSendMsg(producer, msg);
    }

    public void deleteUserMoments(Long userId, Long id) {
        // 检查该userId 下面是否有该动态的ID
        UserMoment userMoment = userMomentsDao.getUserMomentById(userId, id);
        if (userMoment == null) {
            throw new ConditionException("400", "您无权限删除该动态！", "You have no permission to delete this dynamic！");
        }
        // 如果存在 则删除这一条
        userMomentsDao.deleteUserMoments(userId, id);
    }

    public List<UserMoment> getUserSubscribeMoments(Long userId) {
        // 找到他的所有关注
        // 遍历每个关注者发布的动态
        // 汇聚 按时间排序
        HashMap<Long, List<User>> userFollowing = userFollowingService.getUserFollowing(userId);
        List<Long> followingIdList = new ArrayList<>();
        for (Long key : userFollowing.keySet()) {
            List<User> users = userFollowing.get(key);
            for (User user : users) {
                followingIdList.add(user.getId());
            }
        }

        List<UserMoment> userMomentList = userMomentsDao.batchGetUserMoments(followingIdList);
        return userMomentList;

        /*
        // 从redis里面取
        String redisKey = "subscribed-" + userId;
        String listStr = redisTemplate.opsForValue().get(redisKey);
        if (StringUtil.isNullOrEmpty(listStr))
            return new ArrayList<>();
        return JSONArray.parseArray(listStr, UserMoment.class);
         */
    }
}
