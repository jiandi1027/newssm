/*
* SysGroupMapper.java
* All right Reserved
* 2018-04-24 created
*/
package com.cjdjyf.newssm.mapper.sys;

import com.cjdjyf.newssm.pojo.sys.SysGroup;

public interface SysGroupMapper extends com.cjdjyf.newssm.base.BaseDao<com.cjdjyf.newssm.pojo.sys.SysGroup> {
    Integer getChildCount(SysGroup sysGroup);
}