package com.Morgan.bilibili.service.Impl;

import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.service.UserFollowingService;
import com.Morgan.bilibili.service.UserFollowingServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-12-15-0:36
 */
@Service
@Slf4j
public class UserFollowingServiceImpl implements UserFollowingServiceI {

    @Autowired
    private UserFollowingService userFollowingService;

    @Override
    @Result(column = "user_id", property = "userId")
    public List<User> getFans(Long userId) {
        log.info("getFans, userId: {}", userId);
        return userFollowingService.getFans(userId);
    }

}
