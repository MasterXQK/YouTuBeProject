package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Morgan
 * @create 2022-12-06-18:19
 */
@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoment userMoment);
}
