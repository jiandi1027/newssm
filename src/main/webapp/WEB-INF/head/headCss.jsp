<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="static/other/easyui/themes/bootstrap/easyui.css"/>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="static/other/easyui/themes/icon.css"/>
<link href="static/other/awesome/css/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="static/css/view.css" rel="stylesheet" type="text/css" />
<link href="static/css/add.css" rel="stylesheet" type="text/css" />
<link href="static/css/list.css" rel="stylesheet" type="text/css" />

<%--兼容手机端--%>
<meta name="viewpor" content="width=device-width,initial-scale=1.0">