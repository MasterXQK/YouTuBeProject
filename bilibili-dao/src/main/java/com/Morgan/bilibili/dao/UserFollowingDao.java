package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.FollowingGroup;
import com.Morgan.bilibili.domain.UserFollowing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-11-03-22:44
 */
@Mapper
public interface UserFollowingDao extends BaseMapper<UserFollowing> {

    void addUserFollowing(UserFollowing userFollowing);

    void deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    List<UserFollowing> getFollowingUserIdListByUserId(Long userId);

    List<UserFollowing> getUserFollowingByFollowingId(Long userId);

    Integer addFollowingGroup(FollowingGroup followingGroup);

    Integer updateFollowingGroup(FollowingGroup followingGroup);

    void deleteFollowingGroup(Long groupId);

    List<FollowingGroup> getUserFollowingGroup(Long userId);
}

