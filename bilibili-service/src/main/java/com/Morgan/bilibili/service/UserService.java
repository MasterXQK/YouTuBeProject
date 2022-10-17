package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.UserDao;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserInfo;
import com.Morgan.bilibili.domain.constant.UserConstant;
import com.Morgan.bilibili.domain.exception.ConditionException;
import com.Morgan.bilibili.service.util.MD5Util;
import com.Morgan.bilibili.service.util.RSAUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Morgan
 * @create 2022-10-17-14:37
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        /*
         校验字段是否正确
         */
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("400", "手机号不能为空！", "Mobile number cannot be empty!");
        }
        /*
         检查是否存在
         TODO redis保存phone number加速查询
         */
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("400", "该手机号已经注册！", "This mobile number has been registered!");
        }
        System.out.println("手机号正确");
        /*
         注册
         */
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        // 解密前端传来的user.pwd
        String originPwd;
        try {
            originPwd = RSAUtil.decrypt(password);
        } catch (Exception e) {
            originPwd = password;
//            throw new ConditionException("500", "密码解密失败！", "Password decryption failed!");
        }

        String md5Pwd = MD5Util.sign(originPwd, salt, "UTF-8");
        // 处理一下加密的salt，加密后的的密码，创建时间
        user.setSalt(salt);
        user.setPassword(md5Pwd);
        user.setCreateTime(now);
        // 执行sql 并且拿到自增的主键id
        userDao.addUser(user);
        System.out.println("写入User成功");

        // init user's base info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId()); // user.id == userInfo.userId
        userInfo.setGender(UserConstant.gender_male);
        userInfo.setBirth(UserConstant.default_birth);
        userInfo.setNick(UserConstant.default_nick);
        // 级联创建userInfo
        userDao.addUserInfo(userInfo);
        System.out.println("级联写入UserInfo成功");

    }

    /*
    TODO redis保存phone number加速查询
     */
    private User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }
}