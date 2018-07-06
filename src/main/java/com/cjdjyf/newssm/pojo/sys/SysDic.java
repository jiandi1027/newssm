/*
* SysDic.java
* Copyright(C) 2009-2016 池剑迪
* All right Reserved
* 2018-05-02 created
*/
package com.cjdjyf.newssm.pojo.sys;


import com.cjdjyf.newssm.base.DataEntity;

import java.io.Serializable;

/**
* @Author cjd
* @version 1.0 2018-05-02
 */
public class SysDic extends DataEntity<SysDic> implements Serializable {
    /**表名 */
    private String tabName;

    /**列名 */
    private String columnName;

    /**依赖键 */
    private String parentKey;

    /**键 */
    private String key;

    /**值 */
    private String value;

    /**备注 */
    private String note;

    /**排序号 */
    private String orderNum;

    public SysDic(String parentKey, String key) {
        this.parentKey = parentKey;
        this.key = key;
    }

    public SysDic() {
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName == null ? null : tabName.trim();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey == null ? null : parentKey.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }
}