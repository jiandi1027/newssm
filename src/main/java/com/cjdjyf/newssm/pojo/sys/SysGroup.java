/*
* SysGroup.java
* Copyright(C) 2009-2016 池剑迪
* All right Reserved
* 2018-04-24 created
*/
package com.cjdjyf.newssm.pojo.sys;


import com.cjdjyf.newssm.base.DataEntity;
import com.cjdjyf.newssm.pojo.sys.TreeNode.GroupNode;

import java.io.Serializable;

/**
* @Author cjd
* @version 1.0 2018-04-24
 */
public class SysGroup extends DataEntity<SysGroup> implements Serializable {
    /**团队名称 */
    private String groupName;
    /**部门图标 */
    private String groupIcon;
    /**部门状态 0/1 关闭/展开 */
    private String groupState;
    /**部门阶级 */
    private String groupLevel;
    /**排序号 */
    private String orderNum;
    /**父团队ID */
    private String parentId;


    public SysGroup(String parentId) {
        this.parentId = parentId;
    }

    public SysGroup() {
    }

    public SysGroup(String groupName, String parentId) {
        this.groupName = groupName;
        this.parentId = parentId;
    }

    public GroupNode getGroupNode() {
        GroupNode groupNode = new GroupNode();
        groupNode.setId(super.getId());
        groupNode.setText(groupName);
        groupNode.setState(groupState);
        groupNode.setIconCls(groupIcon);
        groupNode.setCreatePeople(super.getCreatePeople());
        groupNode.setCreateTime(super.getCreateTime());
        return groupNode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupState() {
        return groupState;
    }

    public void setGroupState(String groupState) {
        this.groupState = groupState;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }
}