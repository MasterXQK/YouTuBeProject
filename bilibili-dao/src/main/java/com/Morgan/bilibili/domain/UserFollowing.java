package com.Morgan.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Morgan
 * @create 2022-11-03-22:39
 */
@Data
@TableName("t_user_following")
// refer t_user_following
public class UserFollowing {
    private Long id;

    private Long userId;

    private Long followingId;

    private Long groupId;

    private Date createTime;
}
