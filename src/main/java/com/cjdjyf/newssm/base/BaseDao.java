package com.cjdjyf.newssm.base;

import java.util.List;

/**
 * @author : cjd
 * @description : 基准Dao接口
 * @date : 2018/4/24 11:18
 */
public interface BaseDao<T> {
    int deleteByPrimaryKey(String id);
    int insert(T record);
    int insertSelective(T record);
    T selectByPrimaryKey(String id);
    List<T> selectAll(T record);
    int updateByPrimaryKeySelective(T record);
    int updateByPrimaryKey(T record);
    int batchDelete(T record);
}