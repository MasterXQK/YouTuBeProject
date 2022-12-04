package com.Morgan.bilibili.domain.constant;

/**
 * @author Morgan
 * @create 2022-10-17-15:41
 */
public class UserConstant {
    public static final String gender_male = "0";

    public static final String gender_female = "1";

    public static final String gender_null = "0";

    public static final String default_birth = "1999-03-12";

    public static final String default_nick = "二次元萌新";

    public static final String[] phoneRepeatErr = {"手机号已经注册!", "Mobile number has been registered!"};

    public static final String[] phoneEmptyErr = {"手机号不能为空！", "Mobile number cannot be empty!"};


    // UserFollowing Constant
    public static final Long SPECIAL_FOLLOW = 0L; // 特别关注

    public static final Long HIDE_FOLLOW = 1L; // 悄悄关注

    public static final Long DEFAULT_FOLLOW = 2L; // 默认关注


    // UserFollowingGroup Constant
    public static final String CUSTOM_FOLLOW = "3"; // 自定义关注分组

}
