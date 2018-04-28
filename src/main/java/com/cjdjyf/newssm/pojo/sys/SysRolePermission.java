/*
* sysRolePermission.java
* Copyright(C) 2009-2016 池剑迪
* All right Reserved
* 2018-03-10 created
*/
package com.cjdjyf.newssm.pojo.sys;


import com.cjdjyf.newssm.base.DataEntity;

import java.io.Serializable;

/**
 * @version 1.0 2018-03-10
 * @Author cjd
 */
public class SysRolePermission extends DataEntity<SysRolePermission> implements Serializable {
    /**角色ID */
    private String roleId;
    /**角色拥有权限 */
    private String permissionId;

    public SysRolePermission(String roleId) {
        this.roleId = roleId;
    }

    public SysRolePermission() {
    }

    public SysRolePermission(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }
}