package com.Morgan.bilibili.api;

import com.Morgan.bilibili.dao.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Morgan
 * @create 2022-10-15-0:19
 */
@RestController
public class DemoApi {

    // api层调用service层
    // service层调用dao层
    @Autowired
    private DemoService demoService;

    // get方法
    @GetMapping("/queryNameById")
    public Map<String, Object> getPersonNameById(Long id) {
        return demoService.getPersonNameById(id);
    }

    @GetMapping("/queryCreateTimeById")
    public String queryCreateTimeById(Long id) {
        return demoService.getPersonCreateTimeById(id);
    }


}
