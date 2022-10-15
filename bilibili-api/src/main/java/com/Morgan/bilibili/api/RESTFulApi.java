package com.Morgan.bilibili.api;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Morgan
 * @create 2022-10-15-14:57
 */

// restful 模式开发demo
@RestController
public class RESTFulApi {
    private final String ID = "id";

    private final Map<Integer, Map<String, Object>> dataMap;

    RESTFulApi() {
        dataMap = new HashMap<>();
        // 初始化 插入十条数据
        for (int i = 1; i <= 10; i++) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", i);
            data.put("name", new StringBuffer().append("name").append(i).toString());
            data.put("create_time", System.currentTimeMillis());
            dataMap.put(i, data);
        }
    }

    // GET 具有幂等性
    @GetMapping("/data/{id}")
    public Map<String, Object> getDataById(@PathVariable Integer id) {
        return dataMap.getOrDefault(id, null);
    }

    // DELETE 具有幂等性
    @DeleteMapping("/data/{id}")
    public String deleteDataById(@PathVariable Integer id) {
        String deleteResult = dataMap.containsKey(id) ? "delete success" : "already delete";
        dataMap.remove(id);
        return deleteResult;
    }

    // POST 具有幂等性 (每次post都增加)
    @PostMapping("/data")
    public String postData(@RequestBody Map<String, Object> newData) {
        // 顺序增加
        Integer[] ids = dataMap.keySet().toArray(new Integer[0]);
        Arrays.sort(ids);
        int nextId = ids[ids.length - 1] + 1;
        dataMap.put(nextId, newData);
        String postResult = "success post";
        return postResult;
    }

    // PUT 不具有幂等性 (如果id存在则更新 id不存在则顺序添加)
    @PutMapping("/data")
    public String putData(@RequestBody Map<String, Object> newData) {
        Integer newId = Integer.valueOf(String.valueOf(newData.get(ID)));
        if (dataMap.containsKey(newId)) {
            dataMap.put(newId, newData);
        } else {
            Integer[] ids = dataMap.keySet().toArray(new Integer[0]);
            Arrays.sort(ids);
            Integer nextId = ids[ids.length - 1] + 1;
            dataMap.put(nextId, newData);
        }
        String putResult = "put success";
        return putResult;
    }

    // 测试用
    @GetMapping("/getAllData")
    public Map<Integer, Map<String, Object>> getAllData() {
        return dataMap;
    }
}
