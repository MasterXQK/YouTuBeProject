package com.Morgan.bilibili.api;

import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.service.UserService;
import com.Morgan.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Morgan
 * @create 2022-10-17-14:39
 */
@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    // 获得RSA公钥
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRSAPublicKey() {
        String publicKey = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKey);
    }

    // 用户注册
    @PostMapping("/user")
    public JsonResponse<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }
}
