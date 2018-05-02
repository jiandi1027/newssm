<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<div class="add_btn">
    <a id="sysDicAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysDicAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">关闭</a>
</div>
<form method="post" id="sysDicAddList_form">
    <table id="sysDicAddList_table" class="add_table">
        <tr>
            <td>表名：</td>
            <td><input name="tabName"  class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysDic.tabName}" title=""/></td>
        </tr>
        <tr>
            <td>列名：</td>
            <td><input name="columnName"  class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysDic.columnName}" title=""/></td>
        </tr>
        <tr>
            <td>基准键：</td>
            <td><input name="parentKey"  class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysDic.parentKey}" title=""/></td>
        </tr>
        <tr>
            <td>键：</td>
            <td><input name="key"  class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysDic.key}" title=""/></td>
        </tr>
        <tr>
            <td>值：</td>
            <td><input name="value"  class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="${sysDic.value}" title=""/></td>
        </tr>
        <tr>
            <td>备注：</td>
            <td><input name="note"  class="easyui-textbox easyui-validatebox"
                       data-options="validType:['length[0,50]'],delay:'0'"
                       value="${sysDic.note}" title=""/></td>
        </tr>



    </table>
</form>
<script>
    $(function () {
    });

    //保存
    $('#sysDicAddList_save').click(function () {
        var sysDicAddList_form = $("#sysDicAddList_form");
        if (sysDicAddList_form.form('validate')) {
            sysDicAddList_form.submit();
        } else {
            $.messager.show({
                title: '提示',
                msg: '数据不能为空'
            });
        }
    });

    //关闭
    $('#sysDicAddList_close').click(function () {
        window.history.go(-1);
    });


    $('#sysDicAddList_form').form({
        url: 'sys/sysDic/save',
        queryParams: {
            id: '${sysDic.id}'
        },
        success: function (data) {
            data = JSON.parse(data);
            if (data.code === 200) {
                window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysDic/list";
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
