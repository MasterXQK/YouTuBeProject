package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-12-06-18:19
 */
@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoment userMoment);

    UserMoment getUserMomentById(@Param("userId") Long userId, @Param("id")Long id);

    void deleteUserMoments(@Param("userId") Long userId, @Param("id") Long id);

    List<UserMoment> batchGetUserMoments(List<Long> followingIdList);
}
