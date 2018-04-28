/*
* sysAccountRole.java
* Copyright(C) 2009-2016 池剑迪
* All right Reserved
* 2018-03-10 created
*/
package com.cjdjyf.newssm.pojo.sys;


import com.cjdjyf.newssm.base.DataEntity;

import java.io.Serializable;

/**
* @Author cjd
* @version 1.0 2018-03-10
 */
public class SysAccountRole extends DataEntity<SysAccountRole> implements Serializable {
    /**账号ID */
    private String accountId;
    /**账号拥有角色 */
    private String roleId;

    public SysAccountRole() {
    }

    public SysAccountRole(String accountId, String roleId) {
        this.accountId = accountId;
        this.roleId = roleId;
    }

    public SysAccountRole(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}