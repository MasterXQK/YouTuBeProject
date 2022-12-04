package com.Morgan.bilibili.api;

import com.Morgan.bilibili.api.support.UserSupport;
import com.Morgan.bilibili.domain.*;
import com.Morgan.bilibili.service.UserFollowingService;
import com.Morgan.bilibili.service.UserService;
import com.Morgan.bilibili.service.util.RSAUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Morgan
 * @create 2022-10-17-14:39
 */
@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    // 注入UserFollowingService
    @Autowired
    private UserFollowingService userFollowingService;


    // 根据header内的token 获得用户userId 从而获得用户具体信息
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo() {
        Long curUserId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(curUserId);
        return new JsonResponse<>(user);
    }

    // 获得RSA公钥
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRSAPublicKey() {
        String publicKey = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKey);
    }

    // 用户注册
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }

    // 用户登录 鉴权
    // 产生token 并且返回
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        // 根据userID生成token 并且返回
        // 下次用户带着我们服务器生成的token来登录 就可以免密码
        return JsonResponse.success(token);
    }

    // 用户update 解析token拿到userID再去做更改逻辑
    // 通过 phone + password进行登录 并且产生token
    @PutMapping("/users")
    public JsonResponse<String> updateUser(@RequestBody User user) throws Exception {
        // 通过token 获得userID
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);

        System.out.println(user);
        System.out.println(user.getId());
        userService.updateUser(user);
        return JsonResponse.success();
    }

    // 修改用户信息
    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        Long userId = userSupport.getCurrentUserId();
        // TODO for test
//        long userId = 18;
        userInfo.setUserId(userId);
        userService.updateUserInfo(userInfo);
        return JsonResponse.success();
    }

    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> PageListUserInfos(@RequestParam Integer number, @RequestParam Integer size,
                                                                @RequestParam String nick) {
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("number", number);
        params.put("size", size);
        params.put("nick", nick);
        PageResult<UserInfo> result = userService.PageListUserInfos(params);
        // 只是得到了所有模糊查询nick的用户信息 还要判断是否为该uerId的关注者
        if (result.getTotal() > 0) {
            List<UserInfo> checkedUserInfo = userFollowingService.checkFollowingStatus(userId, result);
            result.setList(checkedUserInfo);
        }
        return new JsonResponse<>(result);
    }
}


