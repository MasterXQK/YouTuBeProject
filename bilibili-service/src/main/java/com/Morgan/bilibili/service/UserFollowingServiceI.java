package com.Morgan.bilibili.service;

import com.Morgan.bilibili.domain.User;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-12-15-0:36
 */
public interface UserFollowingServiceI {
    public List<User> getFans(Long userId);
}
