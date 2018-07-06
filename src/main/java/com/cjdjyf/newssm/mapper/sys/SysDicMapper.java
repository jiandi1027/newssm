/*
* SysDicMapper.java
* Copyright(C) 2009-2016 池剑迪
* All right Reserved
* 2018-05-02 created
*/
package com.cjdjyf.newssm.mapper.sys;

public interface SysDicMapper extends com.cjdjyf.newssm.base.BaseDao<com.cjdjyf.newssm.pojo.sys.SysDic> {
    String getValueByParentKeyAndKey(String parentKey, String key);
}