package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Morgan
 * @create 2022-11-03-22:43
 */
@Mapper
public interface FollowingGroupDao {
    FollowingGroup getById(Long groupId);
}
