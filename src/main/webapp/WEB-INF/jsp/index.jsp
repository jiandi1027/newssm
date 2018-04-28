<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
    <title>首页</title>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height: 100px;">
    <div style="width: 100%; height: 100%; background-size: 100% 100%; background-image: url('static/images/top.jpg');">
        <div style="float: right; margin: 50px 60px 0 0; color: red; font-size: 20px">
            <span style="font-size: 15px">欢迎</span>
            <span style="margin: 0 40px 0 0; font-size: 25px">${user.userName}</span>
            <a style="cursor: pointer; font-size: 10px" id="change_pwd">[修改密码]</a>
            <a style="cursor: pointer; font-size: 10px" id="index_logout"> [点击退出]</a>
        </div>
    </div>
</div>
<div data-options="region:'west',title:'菜单',split:true" style="width: 250px;">
    <div class="easyui-accordion" data-options="fit:true,border:false">
        <c:forEach var="f" items="${menus}">
            <shiro:hasPermission name="${f.permissionName}">
                <div title="${f.permissionName}" iconCls="${f.menuIcon}">
                    <ul class="easyui-tree tree menu-tree" id="index_${f.id}"
                        data-options="fit:true,border:false,onClick:index_addTab">
                    </ul>
                </div>
            </shiro:hasPermission>
        </c:forEach>
    </div>
</div>
<%--标签页--%>
<div data-options="region:'center'" style="overflow: hidden;">
    <div id="index_tabs" style="overflow: hidden; fit: true">
        <div title="首页" data-options="iconCls:'fa fa-home',border:false,fit:true" style="overflow: hidden;"></div>
    </div>
</div>
<%--底部版权声明--%>
<div data-options="region:'south'" style="height: 30px;">
    <div style="line-height: 30px; overflow: hidden; text-align: center; background-color: rgb(238, 238, 238); width: 100%; height: 28px;">
        XXX 联系方式:xxxxxxxxxxxx
        <div id="index_html"></div>
    </div>
</div>
<div id="index_dialog"></div>
</body>
<script type="text/javascript">
    var index_tabs = $('#index_tabs');
    $(function () {
        showMsg('登录成功');
        //获取class为easyui-tree的 添加动态菜单
        var $elements = $('.menu-tree');
        for (var i = 0; i < $elements.length; i++) {
            var id = $elements.get(i).id;
            $('#' + id).tree({
                url: 'sys/sysPermission/getMenu?parentId=' + id.split('_')[1] + '&menuFlag=1',
                loadFilter: function (data) {
                    data = data.data;
                    return data;
                }
            });
        }
    });

    //点击菜单项添加选项卡
    function index_addTab(node) {
        //获取选项卡，如果已经存在就选择并刷新，如果没存在就添加
        if (index_tabs.tabs('exists', node.text)) {
            index_tabs.tabs('select', node.text);
            refreshTab();
        } else {
            var content = '<iframe scrolling="auto" frameborder="0"  src="' + node.url + '" style="width:100%;height:99.3%;"></iframe>';
            index_tabs.tabs('add', {
                title: node.text,
                border: false,
                closable: true,
                fit: true,
                content: content,
                iconCls: node.iconCls
            });
        }
    }

    index_tabs.tabs(
        {
            fit: true,
            border: false,
            tools: [
                {
                    //工具条-主页
                    iconCls: 'fa fa-home',
                    handler: function () {
                        index_tabs.tabs('select', 0);
                    }
                },
                {
                    //获取工具条选择的对象，刷新
                    iconCls: 'fa fa-refresh ',
                    handler: function () {
                        refreshTab();
                    }
                },
                {
                    //选项卡-关闭
                    iconCls: 'fa fa-remove',
                    handler: function () {
                        var index = index_tabs.tabs('getTabIndex',
                            index_tabs.tabs('getSelected'));
                        var tab = index_tabs.tabs('getTab', index);
                        if (tab.panel('options').closable) {
                            index_tabs.tabs('close', index);
                        }
                    }
                }
            ],
            onSelect: function (title) {
            }
        });

    //首页初始化
    var tab = index_tabs.tabs('getTab', 0);
    index_tabs.tabs('update',
        {
            tab: tab,
            options: {
                content: '<iframe name="indexTab" scrolling="auto" src="sys/home" frameborder="0" style="width:100%;height:100%;"></iframe>',
                closable: false,
                selected: true
            }
        });

    //刷新选项卡
    function refreshTab() {
        var index = index_tabs.tabs('getTabIndex', index_tabs
            .tabs('getSelected'));
        var tab = index_tabs.tabs('getTab', index);
        var options = tab.panel('options');
        if (options.content) {
            index_tabs.tabs('update', {
                tab: tab,
                options: {
                    content: options.content
                }
            });
        } else {
            tab.panel('refresh', options.href);
        }
    }

    //退出账号
    $('#index_logout').click(function () {
        $.messager.confirm('确认', '是否退出账号？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    url: 'sys/logout',
                    success: function () {
                        //ie下location的base属性无效
                        window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/login";
                    }
                })
            }
        });
    });

    //修改密码
    $("#change_pwd").click(function () {
        var index_dialog = $('#index_dialog');
        index_dialog.dialog(
            {
                title: "修改密码",
                iconCls: 'fa fa-plus',
                width: 400,
                height: 250,
                resizable: true,
                closed: false,
                cache: false,
                href: 'sys/sysAccount/changePwdList',
                buttons: [
                    {
                        text: '提交',
                        iconCls: 'fa fa-floppy-o',
                        handler: function () {
                            var sysAccountChangePwd_form = $("#sysAccountChangePwdList_form");
                            if (sysAccountChangePwd_form.form('validate')) {
                                var pwd1 = $('#sysAccountChangePwdList_pwd1').passwordbox('getValue');
                                var pwd2 = $('#sysAccountChangePwdList_pwd2').passwordbox('getValue');
                                if (pwd1 !== pwd2) {
                                    showMsg("两次输入的密码不一致");
                                } else {
                                    sysAccountChangePwd_form.form('submit',
                                        {
                                            url: 'sys/sysAccount/changePwd',
                                            onSubmit: function () {
                                            },
                                            success: function (data) {
                                                index_dialog.dialog('close');
                                                showMsg(JSON.parse(data).data);
                                            }
                                        })
                                }
                            } else {
                                showMsg('数据不能为空');
                            }
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'fa fa-remove ',
                        handler: function () {
                            index_dialog.dialog('close');
                        }
                    }]
            });
        index_dialog.dialog('center');
    })
</script>
</html>
