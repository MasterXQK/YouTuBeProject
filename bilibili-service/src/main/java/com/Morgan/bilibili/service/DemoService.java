package com.Morgan.bilibili.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Morgan
 * @create 2022-10-15-0:16
 */
@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public Map<String, Object> getPersonNameById(long id) {
        // service层调用dao层方法 走sql
        Map<String, Object> map = demoDao.queryNameById(id);
        return (map == null || map.size() == 0) ? null : map;
    }

    public String getPersonCreateTimeById(Long id) {
        return demoDao.queryCreateTimeById(id);
    }

}
