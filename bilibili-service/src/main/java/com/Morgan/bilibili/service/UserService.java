package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.UserDao;
import com.Morgan.bilibili.domain.PageResult;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserInfo;
import com.Morgan.bilibili.domain.constant.UserConstant;
import com.Morgan.bilibili.domain.exception.ConditionException;
import com.Morgan.bilibili.service.util.MD5Util;
import com.Morgan.bilibili.service.util.RSAUtil;
import com.Morgan.bilibili.service.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Morgan
 * @create 2022-10-17-14:37
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void mqAddUser(User user) {
        userDao.addUser(user);
    }

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
//            originPwd = password;
            throw new ConditionException("500", "密码解密失败！", "Password decryption failed!");
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


    public String login(User user) throws Exception {
        // 检查手机号是否违规
        if (StringUtils.isNullOrEmpty(user.getPhone())) {
            throw new ConditionException("400", "手机号不能为空！", "Mobile number cannot be empty!");
        }
        // 先检查user是否存在(根据phone)
        User dbUser = getUserByPhone(user.getPhone());
        if  (dbUser == null) {
            throw new ConditionException("400", "用户不存在！", "user does not exist!");
        }

        //解密
        String originPwd;
        try {
            originPwd = RSAUtil.decrypt(user.getPassword());
        } catch (Exception e) {
            throw new ConditionException("500", "密码解密失败！", "Password decryption failed!");
//            originPwd = user.getPassword();
        }

        // md5加密 并与原始密码对比
        String salt = dbUser.getSalt();
        String md5Pwd = MD5Util.sign(originPwd, salt, "UTF-8");
        if (!md5Pwd.equals(dbUser.getPassword())) { // 比对逆向生成的密码和数据库内该用户密码
            throw new ConditionException("400", "密码错误！", "Password error!");
        }

        // 根据userId获得令牌
        return TokenUtil.generateToken(dbUser.getId());
    }

    public User getUserInfo(Long userId) {
        if (userId < 0) {
            return null;
        }

        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        // 把UserInfo包进User内 一起返回
        user.setUserInfo(userInfo);
        return user;
    }

    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    // --------------------------------------------------- utils -------------------------------------------------------
    /*
   TODO redis保存phone number加速查询
    */
    private User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }


    public void updateUser(User user) throws Exception {
        Long id = user.getId();
        User dbUser = userDao.getUserById(id);
        if (dbUser == null) {
            throw new ConditionException("400", "用户不存在！", "User does not exist!");
        }
        if (StringUtils.isNullOrEmpty(dbUser.getPassword())) {
            String originPwd = RSAUtil.decrypt(user.getPassword());
            String md5Pwd = MD5Util.sign(originPwd, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Pwd);
        }
        user.setUpdateTime(new Date());
        userDao.updateUser(user);
    }

    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfo(userInfo);
    }

    public List<User> getUserInfoByIdList(List<Long> userIdList) {
        List<User> users = new ArrayList<>();
        for (Long id : userIdList) {
            users.add(getUserInfo(id));
        }
        return users;
    }

    public PageResult<UserInfo> PageListUserInfos(JSONObject params) {
        Integer number = params.getInteger("number");
        Integer size = params.getInteger("size");
        String nick = params.getString("nick");
        if (nick == null || nick.equals("")) {
            throw new ConditionException("400", "昵称不能为空！", "Nick cannot be empty!");
        }

        params.put("start", (number - 1) * size);
        params.put("limit", size);
        params.put("nick", "%" + nick + "%"); // 加上模糊查询的%

        System.out.println(params);
        Integer total = userDao.PageCountUserInfos(params); // 总数
        List<UserInfo> userInfoList = new ArrayList<>(); // 初始化
        if (total > 0) {
            userInfoList = userDao.PageListUserInfos(params); // 分页查询
        }
        return new PageResult<>(total, userInfoList);
    }
}
