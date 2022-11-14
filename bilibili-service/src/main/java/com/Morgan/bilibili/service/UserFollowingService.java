package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.UserFollowingDao;
import com.Morgan.bilibili.domain.FollowingGroup;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserFollowing;
import com.Morgan.bilibili.domain.constant.UserFollowingConstant;
import com.Morgan.bilibili.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Morgan
 * @create 2022-11-03-22:45
 */
@Service
public class UserFollowingService {

    @Autowired
    UserFollowingDao userFollowingDao;

    @Autowired
    UserService userService;

    @Autowired
    FollowingGroupService followingGroupService;


    @Transactional
    public void addUserFollowing(UserFollowing userFollowing) {
        // 添加一行数据
        if (userFollowing == null) {
            throw new ConditionException("400", "数据不能为null", "Data cannot be null!");
        }
        if (userFollowing.getUserId() == null) {
            throw new ConditionException("400", "当前用户ID缺失", "The current user ID is missing!");
        }
        if (userFollowing.getFollowingId() == null) {
            throw new ConditionException("400", "被关注者ID缺失", "Followed ID is missing!");
        }
        // 被关注者必须在user数据库内
        if (userService.getUserById(userFollowing.getFollowingId()) == null) {
            throw new ConditionException("400", "该用户已注销!", "New Glory!");
        }

        // 检查一下是哪个关注分组
        Long groupId = userFollowing.getGroupId();
        if (groupId == null) {
            userFollowing.setGroupId(UserFollowingConstant.DEFAULT_FOLLOW);
        } else {
            // 检查一下分组是否存在
            FollowingGroup followingGroup = followingGroupService.getById(userFollowing.getGroupId());
            if (followingGroup == null) {
                throw new ConditionException("400", "关注分组不存在!", "Follow group does not exist!");
            }
        }

        // 插入之前先删除
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), userFollowing.getFollowingId());
        userFollowing.setCreateTime(new Date()); // 把创建时间补上
        userFollowingDao.addUserFollowing(userFollowing);
    }

    // key -> groupId
    // value -> User
    public HashMap<Long, List<User>> getUserFollowing(Long userId) {
        // 所有该userId相关的t_user_following数据
        List<UserFollowing> userFollowings = userFollowingDao.getFollowingUserIdListByUserId(userId);
        HashMap<Long, List<User>> groupMap = new HashMap<>();
        // 根据不同的groupId分组返回
        for (UserFollowing uf : userFollowings) {
            long groupId = uf.getGroupId();
            long followingId = uf.getFollowingId();
            // 用关注者的id 获取该User及其相关附属信息
            User followingUserInfo = userService.getUserInfo(followingId);
            if (!groupMap.containsKey(groupId))
                groupMap.put(groupId, new ArrayList<>());
            groupMap.get(groupId).add(followingUserInfo);
        }
        return groupMap;
    }
}