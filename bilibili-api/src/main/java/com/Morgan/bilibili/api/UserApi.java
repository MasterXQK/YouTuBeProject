package com.Morgan.bilibili.api;

import com.Morgan.bilibili.api.support.UserSupport;
import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.service.UserService;
import com.Morgan.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        return JsonResponse.success(token);
    }
}
