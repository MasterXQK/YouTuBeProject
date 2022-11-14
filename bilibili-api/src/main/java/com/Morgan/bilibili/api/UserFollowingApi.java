package com.Morgan.bilibili.api;

import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserFollowing;
import com.Morgan.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * @author Morgan
 * @create 2022-11-13-20:00
 */
@RestController
public class UserFollowingApi {
    @Autowired
    private UserFollowingService userFollowingService;



    @PostMapping("/addUserFollowing")
    public JsonResponse<String> addUserFollowing(@RequestBody UserFollowing userFollowing) {
        userFollowingService.addUserFollowing(userFollowing);
        return JsonResponse.success();
    }

    // 按分组获得用户全部关注列表
    @GetMapping("/UserFollowings/{userId}")
    public JsonResponse<HashMap<Long, User>> getUserFollowing(@PathVariable Long userId) {
        HashMap<Long, User> followingMap = userFollowingService.getUserFollowing(userId);
        return new JsonResponse<>(followingMap);
    }
}

