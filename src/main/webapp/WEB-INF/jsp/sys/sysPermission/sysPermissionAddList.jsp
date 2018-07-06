<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<loading:loading>
</loading:loading>
<div class="add_btn">
    <a id="sysPermissionAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysPermissionAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">返回</a>
</div>
<form method="post" id="sysPermissionAddList_form">
    <table id="sysPermissionAddList_table" class="add_table">
        <tr>
            <td>权限名称：</td>
            <td><input name="permissionName" class="easyui-textbox easyui-validatebox"
                       data-options="width:350,required:true,validType:['length[0,100]'],delay:'0'"
                       value="${sysPermission.permissionName}" title=""/></td>
        </tr>
        <tr>
            <td>权限地址：</td>
            <td><input name="url" class="easyui-textbox easyui-validatebox"
                       data-options="width:350,validType:['length[0,100]'],delay:'0'"
                       value="${sysPermission.url}" title=""/></td>
        </tr>
        <tr>
            <td>是否菜单：</td>
            <td><input name="menuFlag" id="sysPermissionAddList_menuFlag"
                       data-options="width:350,validType:['length[0,100]'],delay:'0'"
                       value="${sysPermission.menuFlag}"
                       title=""/></td>
        </tr>
        <tr>
            <td>菜单图标：</td>
            <td><input name="menuIcon" class="easyui-textbox"
                       data-options="width:350,validType:['length[0,100]'],delay:'0'"
                       value="${sysPermission.menuIcon}" title=""/></td>
        </tr>
        <tr>
            <td>是否展开：</td>
            <td><input name="menuState" id="sysPermissionAddList_menuState" title=""
                       data-options="width:350,validType:['length[0,100]'],delay:'0'"
                       value="${sysPermission.menuState}"/></td>
        </tr>
        <tr>
            <td>排序号：</td>
            <td><input name="orderNum" class="easyui-textbox easyui-validatebox"
                       data-options="width:350,validType:['length[0,100]'],delay:'0'"
                       value="${sysPermission.orderNum}" title=""/></td>
        </tr>
        <tr>
            <td>父菜单：</td>
            <td><input name="parentId" id="sysPermissionAddList_parentPermission" value="${sysPermission.parentId}"
                       data-options="required:true,validType:['length[0,50]'],delay:'0',width:350,
                          onLoadSuccess: function (node, data) {
                          var t = $('#sysPermissionAddList_parentPermission').combotree('tree');//获取tree
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
        getChoose('sysPermissionAddList_menuState');
        getChoose('sysPermissionAddList_menuFlag');
        getPermission('sysPermissionAddList_parentPermission', 350, 170);
        if (${not empty parentId }) {
            $('#sysPermissionAddList_parentPermission').combotree('setValue', '${parentId}');
            $('#sysPermissionAddList_parentPermission').combobox('readonly', true);
        }
    });

    //保存
    $('#sysPermissionAddList_save').click(function () {
        var sysPermissionAddList_form = $("#sysPermissionAddList_form");
        if (sysPermissionAddList_form.form('validate')) {
            sysPermissionAddList_save();
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


    //保存
    function sysPermissionAddList_save() {
        $.ajax({
            type: 'POST',
            data: $.param({'id': '${sysPermission.id}'}) + '&' + $('#sysPermissionAddList_form').serialize(),
            url: 'sys/sysPermission/save',
            success: function (data) {
                if (data.code === 200) {
                    window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysPermission/list";
                    showMsg(data.data);
                } else {
                    showMsg('编辑失败');
                }
            }
        });
    }

</script>
</body>
</html>

