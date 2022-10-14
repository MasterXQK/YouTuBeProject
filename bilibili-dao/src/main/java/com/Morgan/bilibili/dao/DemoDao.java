package com.Morgan.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author Morgan
 * @create 2022-10-14-23:52
 */
@Mapper
public interface DemoDao {

    public String queryNameById(Long userID);
}
