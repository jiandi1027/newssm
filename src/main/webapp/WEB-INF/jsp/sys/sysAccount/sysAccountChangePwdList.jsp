<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<form method="post" id="sysAccountChangePwdList_form">
    <table id="sysAccountChangePwdList_table" class="add_table">
        <tr>
            <td>当前密码：</td>
            <td><input name="password" class="easyui-validatebox easyui-passwordbox"
                       ondragenter="return false"
                       data-options="required:true,delay:'0',validType:['length[0,20]']"
                       value="" title=""/></td>
        </tr>
        <tr>
            <td>修改密码：</td>
            <td><input id="sysAccountChangePwdList_pwd1" name="replacePassword"
                       class="easyui-validatebox easyui-passwordbox"
                       ondragenter="return false"
                       data-options="required:true,delay:'0',validType:['length[0,20]']"
                       value="" title=""/></td>
        </tr>
        <tr>
            <td>再次输入：</td>
            <td><input id="sysAccountChangePwdList_pwd2" class="easyui-validatebox easyui-passwordbox"
                       ondragenter="return false"
                       data-options="required:true,delay:'0',validType:['length[0,20]']"
                       value="" title=""/></td>
        </tr>
    </table>
</form>
<script>
    $(function () {
    });
</script>
</body>
</html>

