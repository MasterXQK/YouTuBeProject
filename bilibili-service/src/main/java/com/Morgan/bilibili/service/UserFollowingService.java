package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.UserFollowingDao;
import com.Morgan.bilibili.domain.*;
import com.Morgan.bilibili.domain.constant.UserConstant;
import com.Morgan.bilibili.domain.exception.ConditionException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Morgan
 * @create 2022-11-03-22:45
 */
@Service
@Slf4j
public class UserFollowingService {

    @Autowired
    UserFollowingDao userFollowingDao;

    @Autowired
    UserService userService;

    @Autowired
    FollowingGroupService followingGroupService;


    public List<UserFollowing> getUserFollowingByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return userFollowingDao.selectList(new LambdaQueryWrapper<UserFollowing>().in(UserFollowing::getId, ids));
    }

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
            userFollowing.setGroupId(UserConstant.DEFAULT_FOLLOW);
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

    public List<User> getFans(Long userId) {
        // 根据followingId == userId找到所有的userFollowings数据
        List<UserFollowing> userFollowings = userFollowingDao.getUserFollowingByFollowingId(userId);
        // 此时userId就是粉丝的User.id
        List<Long> userIdList = new ArrayList<>();
        for (UserFollowing uf : userFollowings) userIdList.add(uf.getUserId());
        List<User> fans = userService.getUserInfoByIdList(userIdList);
        return fans;
    }

    // 新建用户分组
    public Integer addUserFollowingGroup(Long userId, FollowingGroup followingGroup) {
        if (followingGroup == null) {
            throw new ConditionException("400", "数据不能为null", "Data cannot be null!");
        }
        if (userId == null) {
            throw new ConditionException("400", "当前用户ID缺失", "The current user ID is missing!");
        }
        if (followingGroup.getName() == null) {
            throw new ConditionException("400", "分组名不能为空", "Group name cannot be empty!");
        }

        // 检查一下分组是否存在
        // 主要检查 userId 及其 groupName
        FollowingGroup followingGroup1 = followingGroupService.getByName(userId, followingGroup.getName());
        if (followingGroup1 != null) {
            throw new ConditionException("400", "分组名已存在!", "Group name already exists!");
        }

        followingGroup.setUserId(userId);
        followingGroup.setType(UserConstant.CUSTOM_FOLLOW);
        followingGroup.setCreateTime(new Date());
        return userFollowingDao.addFollowingGroup(followingGroup);
    }

    // 修改用户分组
    public Integer updateUserFollowingGroup(Long userId, FollowingGroup followingGroup) {
        if (followingGroup == null) {
            throw new ConditionException("400", "数据不能为null", "Data cannot be null!");
        }
        if (userId == null) {
            throw new ConditionException("400", "当前用户ID缺失", "The current user ID is missing!");
        }
        if (followingGroup.getName() == null) {
            throw new ConditionException("400", "分组名不能为空", "Group name cannot be empty!");
        }
        if (followingGroup.getId() == null) {
            throw new ConditionException("400", "分组ID不能为空", "Group ID cannot be empty!");
        }
        

        // 检查一下该userId下面的分组是否存在 通过groupId去查
        FollowingGroup dbGroup = followingGroupService.getById(followingGroup.getId());
        if (dbGroup == null) {
            throw new ConditionException("400", "分组不存在!", "Group does not exist!");
        }
        if (!dbGroup.getType().equals(3)) {
            throw new ConditionException("400", "系统分组不能修改!", "System groups cannot be modified!");
        }
        if (!dbGroup.getUserId().equals(userId)) {
            throw new ConditionException("400", "该分组不属于当前用户!", "The group does not belong to the current user!");
        }


        // 该userId下面的该分组存在 进行修改
        followingGroup.setUserId(userId);
        followingGroup.setType(UserConstant.CUSTOM_FOLLOW);
        followingGroup.setCreateTime(new Date());
        return userFollowingDao.updateFollowingGroup(followingGroup);
    }

    // 删除用户分组
    public void deleteUserFollowingGroup(Long userId, Long groupId) {
        if (groupId == null) {
            throw new ConditionException("400", "分组ID不能为空", "Group ID cannot be empty!");
        }
        if (userId == null) {
            throw new ConditionException("400", "当前用户ID缺失", "The current user ID is missing!");
        }

        // 检查一下该userId下面的分组是否存在 通过groupId去查
        FollowingGroup dbGroup = followingGroupService.getById(groupId);
        if (dbGroup == null) {
            throw new ConditionException("400", "分组不存在!", "Group does not exist!");
        }
        if (!dbGroup.getType().equals("3")) {
            throw new ConditionException("400", "系统分组不能删除!", "System groups cannot be deleted!");
        }
        if (!dbGroup.getUserId().equals(userId)) {
            throw new ConditionException("400", "该分组不属于当前用户!", "The group does not belong to the current user!");
        }

        // 该userId下面的该分组存在 进行删除
        userFollowingDao.deleteFollowingGroup(groupId);
    }


    // 根据用户id获得该用户全部的自定义分组
    public List<FollowingGroup> getUserFollowingGroup(Long userId) {
        if (userId == null) {
            throw new ConditionException("400", "当前用户ID缺失", "The current user ID is missing!");
        }
        return userFollowingDao.getUserFollowingGroup(userId);
    }

    public List<UserInfo> checkFollowingStatus(Long userId, PageResult<UserInfo> result) {
        // 获取用户所有关注
        List<UserFollowing> userFollowings = userFollowingDao.getFollowingUserIdListByUserId(userId);
        // 用户关注Id的Set
        HashSet<Long> followingIdSet = new HashSet<>();
        for (UserFollowing userFollowing : userFollowings) {
            followingIdSet.add(userFollowing.getFollowingId());
        }
        System.out.println(followingIdSet.toString());

        // 遍历result.getList 看看哪些用户在userId的关注里
        for (UserInfo userInfo : result.getList()) {
            userInfo.setFollowed(false);

            if (followingIdSet.contains(userInfo.getUserId())) {
                // 如果在set里面 说明是userId的关注者 设置为true
                userInfo.setFollowed(true);
            }
        }
        return result.getList();
    }
}