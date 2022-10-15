package com.Morgan.bilibili.service;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author Morgan
 * @create 2022-10-14-23:52
 */
@Mapper
public interface DemoDao {

    public Map<String, Object> queryNameById(Long userID);

    public String queryCreateTimeById(Long userID);
}
