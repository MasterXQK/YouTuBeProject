package com.Morgan.bilibili.api;

import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Morgan
 * @create 2022-11-03-21:23
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserApiTest {


    @Autowired
    private MockMvc mockMvc;

    @Resource
    private UserApi userApi;


    @Test
    public void testUpdateUserInfo() {

        UserInfo ui = new UserInfo();
        ui.setUpdateTime(new Date());
        ui.setNick("test nick name");
        ui.setGender("1");

        JsonResponse<String> resp = userApi.updateUserInfo(ui);
        System.out.println(resp);

    }

}