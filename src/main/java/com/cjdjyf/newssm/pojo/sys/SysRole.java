/*
* sysRole.java
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
public class SysRole extends DataEntity<SysRole> implements Serializable {
    /**权限名称 */
    private String roleName;
    /**拥有权限ID */
    private String permissionId;
    /**拥有权限名称 */
    private String permissionName;

    public SysRole(String roleName) {
        this.roleName = roleName;
    }

    public SysRole() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}