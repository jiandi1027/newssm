/*
* SysAccountMapper.java
* Copyright(C) 2009-2016 池剑迪
* All right Reserved
* 2018-03-10 created
*/
package com.cjdjyf.newssm.mapper.sys;

import com.cjdjyf.newssm.pojo.sys.SysAccount;

public interface SysAccountMapper extends com.cjdjyf.newssm.base.BaseDao<com.cjdjyf.newssm.pojo.sys.SysAccount> {
    SysAccount findByName(String userName);
}