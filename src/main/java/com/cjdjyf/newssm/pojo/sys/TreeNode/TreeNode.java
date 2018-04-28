package com.cjdjyf.newssm.pojo.sys.TreeNode;

import com.cjdjyf.newssm.base.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : cjd
 * @description : easyui树节点
 * @date : 2018/4/25 09:01
 */
public class TreeNode extends DataEntity {
    private String text;
    //是否展开
    private String state;
    //地址
    private String url;
    //图标
    private String iconCls;
    //子节点
    public List<TreeNode> children;

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public int getChildrenSize() {
        return children.size();
    }

    public TreeNode() {
        children = new ArrayList<>();
    }

    public void addChildren(TreeNode treeNode) {
        children.add(treeNode);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state.equals("0") ? "closed" : "open";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
