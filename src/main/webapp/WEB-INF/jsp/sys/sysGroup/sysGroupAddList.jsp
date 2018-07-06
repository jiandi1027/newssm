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
    <a id="sysGroupAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysGroupAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">返回</a>
</div>
<form method="post" id="sysGroupAddList_form">
    <table id="sysGroupAddList_table" class="add_table">
        <tr>
            <td>部门名称：</td>
            <td><input name="groupName" class=" easyui-textbox easyui-validatebox "
                       data-options="width:350,required:true,validType:['length[0,50]'],delay:'0',prompt:'填写如（${user.groupName}-房管部）'"
                       value="${sysGroup.groupName}" title=""/></td>
        </tr>

        <tr>
            <td>部门级别：</td>
            <td><input name="groupLevel" id="sysGroupAddList_groupLevel" class="easyui-textbox easyui-validatebox"
                       data-options="width:350,required:true,validType:['length[0,50]'],delay:'0'"
                       value="${sysGroup.groupLevel}" title=""/></td>
        </tr>

        <tr>
            <td>部门图标：</td>
            <td><input name="groupIcon" class="easyui-textbox"
                       value="${sysGroup.groupIcon}"
                       data-options="width:350,validType:['length[0,50]'],delay:'0'"
                       title=""/></td>
        </tr>
        <tr>
            <td>是否展开：</td>
            <td><input name="groupState" id="sysGroupAddList_groupState" title=""
                       data-options="width:350,validType:['length[0,50]'],delay:'0'"
                       value="${sysGroup.groupState}"/></td>
        </tr>
        <tr>
            <td>排序号：</td>
            <td><input name="orderNum" class="easyui-textbox easyui-validatebox"
                       data-options="width:350,validType:['length[0,50]'],delay:'0'"
                       value="${sysGroup.orderNum}" title=""/></td>
        </tr>
        <%--    <tr>
                <td>部门阶级：</td>
                <td><input name="groupLevel" id="sysPermissionAdd_groupLevel" class="easyui-combobox" title=""
                           value="${sysGroup.groupLevel}"/></td>
            </tr>--%>
        <tr>
            <td>父部门：</td>
            <td><input name="parentId" value="${sysGroup.parentId}" id="sysPermissionAdd_parentGroup"
                       data-options="required:true,validType:['length[0,50]'],delay:'0',width:350"
                       class="combotree-group" title=""/></td>
        </tr>
    </table>
</form>
<script>
    $(function () {
        getGroup('sysPermissionAdd_parentGroup');
        getChoose('sysGroupAddList_groupState');
       /* getDicCombobox('sysPermissionAdd_groupLevel', 7);*/
        getDicCombobox('sysGroupAddList_groupLevel', 20,350);
        if (${not empty parentId }) {
            $('#sysPermissionAdd_parentGroup').combotree('setValue', '${parentId}');
            $('#sysPermissionAdd_parentGroup').combobox('readonly', true);
        }
    });

    //保存
    $('#sysGroupAddList_save').click(function () {
        var sysGroupAddList_form = $("#sysGroupAddList_form");
        if (sysGroupAddList_form.form('validate')) {
            sysGroupAddList_save();
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

    //保存
    function sysGroupAddList_save() {
        $.ajax({
            type: 'POST',
            data: $.param({'id': '${sysGroup.id}'}) + '&' + $('#sysGroupAddList_form').serialize(),
            url: 'sys/sysGroup/save',
            success: function (data) {
                if (data.code === 200) {
                    window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysGroup/list";
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
