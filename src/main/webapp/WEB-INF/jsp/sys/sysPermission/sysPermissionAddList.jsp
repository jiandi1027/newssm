<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<div class="add_btn">
    <a id="sysPermissionAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysPermissionAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">关闭</a>
</div>
<form method="post" id="sysPermissionAddList_form">
    <table id="sysPermissionAddList_table" class="add_table">
        <tr>
            <td>权限名称：</td>
            <td><input name="permissionName" class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,50]'],delay:'0'"
                       value="${sysPermission.permissionName}" title=""/></td>
        </tr>
        <tr>
            <td>权限地址：</td>
            <td><input name="url" class="easyui-textbox easyui-validatebox"
                       data-options="validType:['length[0,50]'],delay:'0'"
                       value="${sysPermission.url}" title=""/></td>
        </tr>
        <tr>
            <td>是否菜单：</td>
            <td><input name="menuFlag" id="sysPermissionAddList_menuFlag" class="combobox-choose"
                       value="${sysPermission.menuFlag}"
                       title=""/></td>
        </tr>
        <tr>
            <td>菜单图标：</td>
            <td><input name="menuIcon" class="easyui-textbox"
                       value="${sysPermission.menuIcon}" title=""/></td>
        </tr>
        <tr>
            <td>是否展开：</td>
            <td><input name="menuState" id="sysPermissionAddList_menuState" class="combobox-choose" title=""
                       value="${sysPermission.menuState}"/></td>
        </tr>
        <tr>
            <td>排序号：</td>
            <td><input name="orderNum" class="easyui-textbox easyui-validatebox"
                       data-options="validType:['length[0,50]'],delay:'0'"
                       value="${sysPermission.orderNum}" title=""/></td>
        </tr>
        <tr>
            <td>父菜单：</td>
            <td><input name="parentId" class="combotree-permission" value="${sysPermission.parentId}"
                       data-options="required:true,validType:['length[0,50]'],delay:'0',
                          onLoadSuccess: function (node, data) {
                          var t = $('.combotree-permission').combotree('tree');//获取tree
                         for (var i = 0; i < data.length; i++) {
                         node = t.tree('find', data[i].id);
                         t.tree('expandAll', node.target);//展开所有节点
                      }
                 }
                "
                       title=""/></td>
        </tr>
    </table>
</form>
<script>
    $(function () {
    });

    //保存
    $('#sysPermissionAddList_save').click(function () {
        var sysPermissionAddList_form = $("#sysPermissionAddList_form");
        if (sysPermissionAddList_form.form('validate')) {
            sysPermissionAddList_form.submit();
        } else {
            $.messager.show({
                title: '提示',
                msg: '数据不能为空'
            });
        }
    });

    //关闭
    $('#sysPermissionAddList_close').click(function () {
        window.history.go(-1);
    });

    //表单提交
    $('#sysPermissionAddList_form').form({
        url: 'sys/sysPermission/save',
        queryParams: {
            id: '${sysPermission.id}'
        },
        success: function (data) {
            console.log(data);
            data = JSON.parse(data);
            if (data.code === 200) {
                window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysPermission/list";
                showMsg(data.data);
            }
        }, error: function (data) {
        }
    });
</script>
</body>
</html>

