package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Morgan
 * @create 2022-11-03-22:43
 */
@Mapper
public interface FollowingGroupDao {
    FollowingGroup getById(Long groupId);

    FollowingGroup getByName(@Param("userId") Long userId, @Param("name") String name);

    FollowingGroup getByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
