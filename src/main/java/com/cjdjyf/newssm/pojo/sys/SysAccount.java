/*
* sysAccount.java
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
public class SysAccount extends DataEntity<SysAccount> implements Serializable {
    /**登录账号 */
    private String userName;
    /**登录密码 */
    private String password;
    /**团队ID */
    private String groupId;
    /**团队名称 */
    private String groupName;
    /**团队层级 */
    private String groupLevel;
    /**是否停用 0/1 否/是 */
    private String stopFlag;
    /**备注 */
    private String note;
    /**拥有角色ID */
    private String roleId;
    /**拥有角色名称 */
    private String roleName;
    /**模糊查询名称 */
    private String fuzzyName;
    /**修改密码 */
    private String replacePassword;
    /**父部门ID */
    private String groupParentId;

    public SysAccount(String userName) {
        this.userName = userName;
    }

    public SysAccount() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFuzzyName() {
        return fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getReplacePassword() {
        return replacePassword;
    }

    public void setReplacePassword(String replacePassword) {
        this.replacePassword = replacePassword;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getGroupParentId() {
        return groupParentId;
    }

    public void setGroupParentId(String groupParentId) {
        this.groupParentId = groupParentId;
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }
}