package com.cjdjyf.newssm.pojo.sys;


import com.cjdjyf.newssm.base.DataEntity;
import com.cjdjyf.newssm.pojo.sys.TreeNode.MenuNode;

import java.io.Serializable;

/**
 * @Author cjd
 * @version 1.0 2018-03-10
 */
public class SysPermission extends DataEntity<SysPermission> implements Serializable {
    /**权限名称 */
    private String permissionName;
    /**权限地址 */
    private String url;
    /**是否是菜单 0/1 否/是 */
    private String menuFlag;
    /**菜单图标 */
    private String menuIcon;
    /**菜单状态 0/1 关闭/展开 */
    private String menuState;
    /**父菜单 */
    private String parentId;
    /**排序号 */
    private String orderNum;

    public SysPermission(String parentId) {
        this.parentId = parentId;
    }

    public SysPermission() {
    }

    public MenuNode getMenuNode() {
        MenuNode menuNode = new MenuNode();
        menuNode.setId(super.getId());
        menuNode.setText(permissionName);
        menuNode.setState(menuState);
        menuNode.setIconCls(menuIcon);
        menuNode.setFlag(menuFlag);
        menuNode.setUrl(url);
        menuNode.setCreatePeople(super.getCreatePeople());
        menuNode.setCreateTime(super.getCreateTime());
        return menuNode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag == null ? null : menuFlag.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState == null ? null : menuState.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}