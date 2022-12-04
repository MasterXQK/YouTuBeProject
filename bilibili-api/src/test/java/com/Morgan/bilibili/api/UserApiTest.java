package com.Morgan.bilibili.api;

import com.Morgan.BiliBiliApp;
import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.PageResult;
import com.Morgan.bilibili.domain.UserInfo;
import com.Morgan.bilibili.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;

/**
 * @author Morgan
 * @create 2022-11-03-21:23
 */

@SpringBootTest(classes = BiliBiliApp.class) // 注入项目启动类
@RunWith(SpringRunner.class)
@Slf4j
class UserApiTest {

    // mock对象
    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserService userService;


    @BeforeAll
    public static void init() {
        System.out.println("mock init");
        mockMvc = MockMvcBuilders.standaloneSetup(new UserApi()).build();
    }

    // 用userService去测试getUserInfo()这个方法


    @Test
    public void getUserInfo() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number", 1);
        jsonObject.put("size", 10);
        jsonObject.put("nick", "x");

        JsonResponse<PageResult<UserInfo>> x = userApi.PageListUserInfos(1, 10, "x");
        for (UserInfo userInfo : x.getData().getList()) {
            System.out.println(userInfo.toString());
        }

//        PageResult<UserInfo> userInfoPageResult = userService.PageListUserInfos(jsonObject);
//        for (UserInfo userInfo : userInfoPageResult.getList()) {
//            log.debug("xqk UserInfo -> " + userInfo.toString());
//            System.out.println("xqk UserInfo -> " + userInfo);
//        }


    }




}