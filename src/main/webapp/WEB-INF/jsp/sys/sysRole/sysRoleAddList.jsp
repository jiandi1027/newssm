<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<div class="add_btn">
    <a id="sysRoleAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysRoleAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">关闭</a>
</div>
<form method="post" id="sysRoleAddList_form">
    <table id="sysRoleAddList_table" class="add_table">
        <tr>
            <td>角色名称：</td>
            <td><input name="roleName"  class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysRole.roleName}" title=""/></td>
        </tr>
        <tr>
            <td>拥有权限：</td>
            <td><input name="permissionId" class="combotree-permissions" id="sysRoleAddList_permission"
                       value="${sysRole.permissionId}" title="" data-options="
                          onLoadSuccess: function (node, data) {
                          var t = $('.combotree-permissions').combotree('tree');//获取tree
                         for (var i = 0; i < data.length; i++) {
                         node = t.tree('find', data[i].id);
                         t.tree('expandAll', node.target);//展开所有节点
                      }
                 }
                "/>
            </td>
        </tr>
    </table>
</form>
<script>
    $(function () {
    });

    //保存
    $('#sysRoleAddList_save').click(function () {
        var sysRoleAddList_form = $("#sysRoleAddList_form");
        if (sysRoleAddList_form.form('validate')) {
            sysRoleAddList_form.submit();
        } else {
            $.messager.show({
                title: '提示',
                msg: '数据不能为空'
            });
        }
    });

    //关闭
    $('#sysRoleAddList_close').click(function () {
        window.history.go(-1);
    });


    $('#sysRoleAddList_form').form({
        url: 'sys/sysRole/save',
        queryParams: {
            id: '${sysRole.id}'
        },
        success: function (data) {
            data = JSON.parse(data);
            if (data.code === 200) {
                window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysRole/list";
                showMsg(data.data);
            } else {
                showMsg('编辑失败');
            }
        }, error: function (data) {
        }
    });
</script>
</body>
</html>
