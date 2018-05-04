<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<form method="post" id="codeCreateDataList_form">
    <table id="codeCreateDataList_table" class="add_table">
        <tr>
            <td>表名：</td>
            <td><input name="tableName" class="easyui-textbox easyui-validatebox"
                       data-options="editable:false,required:true,validType:['length[0,20]'],delay:'0'"
                       value="${tableName}" title=""/></td>
        </tr>
        <tr>
            <td>作者：</td>
            <td><input name="author" class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0'"
                       value="" title=""/></td>
        </tr>
        <tr>
            <td>pojo存放位置：</td>
            <td><input name="pojoURL" class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,50]'],delay:'0'"
                       value="com.cjdjyf.newssm.pojo" title=""/></td>
        </tr>
        <tr>
            <td>mapper存放位置：</td>
            <td><input name="mapperURL" class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,50]'],delay:'0'"
                       value="com.cjdjyf.newssm.mapper" title=""/></td>
        </tr>
    </table>
</form>
<script>
    $(function () {
    });

    $('#codeCreateDataList_form').form({
        url: 'tool/codeCreate/codeExport',
        success: function (data) {
            data = JSON.parse(data);
            if (data.code === 200) {
                //下载zip
                window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/file/download?path=" + data.data;
                $('#codeCreateList_dialog').dialog('destroy');
                showMsg(data.msg);
            } else {
                showMsg('生成代码失败');
            }
        }, error: function (data) {
        }
    });
</script>
</body>
</html>
