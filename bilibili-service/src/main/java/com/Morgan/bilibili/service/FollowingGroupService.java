package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.FollowingGroupDao;
import com.Morgan.bilibili.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Morgan
 * @create 2022-11-03-22:46
 */
@Service
public class FollowingGroupService {

    @Autowired
    FollowingGroupDao followingGroupDao;

    public FollowingGroup getById(Long groupId) {
        return followingGroupDao.getById(groupId);
    }

    public FollowingGroup getByName(Long userId, String name) {
        return followingGroupDao.getByName(userId, name);
    }

}
