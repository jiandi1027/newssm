<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSS -->
    <link rel="stylesheet" href="static/css/assets/css/reset.css">
    <link rel="stylesheet" href="static/css/assets/css/supersized.css">
    <link rel="stylesheet" href="static/css/assets/css/style.css">
</head>
<body style="background-image: url('static/css/assets/img/backgrounds/background.jpg');">
<div class="page-container">
    <form id="login_form">
        <input type="text" id="login_userName" name="userName" placeholder="Username">
        <input type="password" id="login_password" name="password" class="password" placeholder="Password">
        <a id=login_btn href="javascript:void(0);" style="margin-top: 20px" class="easyui-linkbutton"
           data-options="width:'300px',height:'50px'">SignMe in</a>
    </form>
</div>
<%@include file="/WEB-INF/head/headJs.jsp" %>
<script type="text/javascript">
    $(function () {
    });

    $('#login_userName').keydown(function (e) {
        if (e.keyCode == 13) {
            $('#login_password').focus();
        }
    });
    $('#login_password').keydown(function (e) {
        if (e.keyCode == 13) {
            $('#login_btn').click();
        }
    });
    $('#login_btn').click(function () {
        var userName = $('#login_userName').val();
        var password = $('#login_password').val();
        if (userName.length === 0 || password.length == 0) {
            $.messager.show(
                {
                    title: '提示',
                    msg: "账号或密码不能为空!"
                });
            return;
        }
        $.ajax({
            url: 'sys/login',
            dataType: 'json',
            type: 'POST',
            data: $('#login_form').serialize(),
            success: function (data) {
                if (data.code === 200) {
                    //ie下location的base属性无效
                    window.location = document.getElementsByTagName("base")[0].getAttribute("href")+ "sys/index";
                } else {
                    $.messager.show(
                        {
                            title: '提示',
                            msg: data.data
                        });
                }
            },
            error: function (data) {
            }
        });
    });
</script>
</body>

</html>

