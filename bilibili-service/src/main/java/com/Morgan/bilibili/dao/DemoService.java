package com.Morgan.bilibili.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Morgan
 * @create 2022-10-15-0:16
 */
@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public String getPersonNameById(long id) {
        // service层调用dao层方法 走sql
        String name = demoDao.queryNameById(id);
        return (name == null || name.length() == 0) ? "NotExist" : name;
    }

}
