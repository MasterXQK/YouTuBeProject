package com.Morgan.bilibili.api;

import com.Morgan.BiliBiliApp;
import com.Morgan.bilibili.api.rabbitMQ.Sender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Morgan
 * @create 2022-12-27-20:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiliBiliApp.class)
public class SenderTest {

    @Autowired
    private Sender sender;

    @Before
    public void before() throws Exception {
        System.out.println("Start sending message...");
    }

    @After
    public void after() throws Exception {
        System.out.println("Finish sending message！");
    }

    /**
     * Method: send()
     */
    @Test
    public void testSend() throws Exception {
        sender.send();
    }


}
