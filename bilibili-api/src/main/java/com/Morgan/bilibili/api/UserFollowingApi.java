package com.Morgan.bilibili.api;

import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.UserFollowing;
import com.Morgan.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
