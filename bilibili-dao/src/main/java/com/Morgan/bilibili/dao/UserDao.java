package com.Morgan.bilibili.dao;

import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Morgan
 * @create 2022-10-17-14:41
 */
@Mapper
public interface UserDao {

    User getUserByPhone(String phone);

    Integer addUser(User user);

    Integer addUserInfo(UserInfo userInfo);
}
