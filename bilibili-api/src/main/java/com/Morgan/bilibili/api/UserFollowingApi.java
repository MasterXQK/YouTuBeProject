package com.Morgan.bilibili.api;

import com.Morgan.bilibili.api.support.UserSupport;
import com.Morgan.bilibili.domain.FollowingGroup;
import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.User;
import com.Morgan.bilibili.domain.UserFollowing;
import com.Morgan.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * @author Morgan
 * @create 2022-11-13-20:00
 */
@RestController
public class UserFollowingApi {

    @Autowired
    private UserFollowingService userFollowingService;

    // 注入UserSupport
    @Autowired
    private UserSupport userSupport;


    @GetMapping("/test-mybatis-plus")
    public JsonResponse<List<UserFollowing>> testMybatisPlus(@RequestParam("ids") List<Long> ids) {
        return new JsonResponse<>(userFollowingService.getUserFollowingByIds(ids));
    }

    @PostMapping("/addUserFollowing")
    public JsonResponse<String> addUserFollowing(@RequestBody UserFollowing userFollowing) {
        userFollowingService.addUserFollowing(userFollowing);
        return JsonResponse.success();
    }

    // 按分组获得用户全部关注列表
    @GetMapping("/user-UserFollowings")
    public JsonResponse<HashMap<Long, List<User>>> getUserFollowing() {
        Long userId = userSupport.getCurrentUserId();
        // map , key->groupId, value->List<User>
        HashMap<Long, List<User>> followingMap = userFollowingService.getUserFollowing(userId);
        return new JsonResponse<>(followingMap);
    }

    @GetMapping("/user-fans")
    public JsonResponse<List<User>> getUserFans() {
        Long userId = userSupport.getCurrentUserId();
        List<User> fans = userFollowingService.getFans(userId);
        return new JsonResponse<>(fans);
    }

    // 新建用户分组
    @PostMapping("/user-following-group")
    public JsonResponse<Integer> addUserFollowingGroup(@RequestBody FollowingGroup followingGroup) {
        Long userId = userSupport.getCurrentUserId();
        Integer groupId = userFollowingService.addUserFollowingGroup(userId, followingGroup);
        return new JsonResponse<>(groupId);
    }

    // 删除用户分组
    @DeleteMapping("/user-following-group/{groupId}")
    public JsonResponse<String> deleteUserFollowingGroup(@PathVariable("groupId") Long groupId) {
        Long userId = userSupport.getCurrentUserId();
        userFollowingService.deleteUserFollowingGroup(userId, groupId);
        return JsonResponse.success();
    }


    // 修改用户分组
    @PutMapping("/user-following-group")
    public JsonResponse<String> updateUserFollowingGroup(@RequestBody FollowingGroup followingGroup) {
        Long userId = userSupport.getCurrentUserId();
        Integer groupId = userFollowingService.updateUserFollowingGroup(userId, followingGroup);
        if (groupId == null)
            return JsonResponse.fail("update failed", "修改失败");
        return JsonResponse.success(String.valueOf(groupId));
    }

    // 获得该用户全部分组
    @GetMapping("/user-following-group")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroup() {
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> followingGroups = userFollowingService.getUserFollowingGroup(userId);
        return new JsonResponse<>(followingGroups);
    }

}

