package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.UserMomentsDao;
import com.Morgan.bilibili.domain.UserMoment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Morgan
 * @create 2022-12-06-18:19
 */
@Service
public class UserMomentsService {

    @Autowired
    private UserMomentsDao userMomentsDao;

    public void addUserMoments(UserMoment userMoment) {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
    }
}
