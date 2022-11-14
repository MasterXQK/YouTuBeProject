package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-11-03-22:44
 */
@Mapper
public interface UserFollowingDao {

    void addUserFollowing(UserFollowing userFollowing);

    void deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    List<UserFollowing> getFollowingUserIdListByUserId(Long userId);

    List<UserFollowing> getUserFollowingByFollowingId(Long userId);
}

