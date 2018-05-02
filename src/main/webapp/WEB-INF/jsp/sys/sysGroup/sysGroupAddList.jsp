<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<div class="add_btn">
    <a id="sysGroupAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysGroupAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">关闭</a>
</div>
<form method="post" id="sysGroupAddList_form">
    <table id="sysGroupAddList_table" class="add_table">
        <tr>
            <td>部门名称：</td>
            <td><input name="groupName" class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysGroup.groupName}" title=""/></td>
        </tr>
        <tr>
            <td>部门图标：</td>
            <td><input name="groupIcon" class="easyui-textbox"
                       value="${sysGroup.groupIcon}" title=""/></td>
        </tr>
        <tr>
            <td>是否展开：</td>
            <td><input name="groupState" id="sysPermissionAdd_menuState" class="combobox-choose" title=""
                       value="${sysGroup.groupState}"/></td>
        </tr>
        <tr>
            <td>排序号：</td>
            <td><input name="orderNum" class="easyui-textbox easyui-validatebox"
                       data-options="validType:['length[0,50]'],delay:'0'"
                       value="${sysGroup.orderNum}" title=""/></td>
        </tr>
        <tr>
            <td>父部门：</td>
            <td><input name="parentId" value="${sysGroup.parentId}"
                       data-options="required:true,validType:['length[0,50]'],delay:'0'"
                       class="combotree-group" title=""/></td>
        </tr>
    </table>
</form>
<script>
    //初始化下拉框值
    $(function () {
    });

    //保存
    $('#sysGroupAddList_save').click(function () {
        var sysGroupAddList_form = $("#sysGroupAddList_form");
        if (sysGroupAddList_form.form('validate')) {
            sysGroupAddList_form.submit();
        } else {
            $.messager.show({
                title: '提示',
                msg: '数据不能为空'
            });
        }
    });

    //关闭
    $('#sysGroupAddList_close').click(function () {
        window.history.go(-1);
    });


    $('#sysGroupAddList_form').form({
        url: 'sys/sysGroup/save',
        queryParams: {
            id: '${sysGroup.id}'
        },
        success: function (data) {
            data = JSON.parse(data);
            if (data.code === 200) {
                window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysGroup/list";
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
