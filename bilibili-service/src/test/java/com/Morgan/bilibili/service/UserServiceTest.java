package com.Morgan.bilibili.service;

import com.Morgan.bilibili.dao.UserDao;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
/**
@author Morgan
@create 2022-12-04-23:20
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
class UserServiceTest {

    @Autowired
    private static MockMvc mockMvc;

    @BeforeAll
    public static void init() {
        System.out.println("mock init");
        mockMvc = MockMvcBuilders.standaloneSetup(new UserService()).build();
    }

}
