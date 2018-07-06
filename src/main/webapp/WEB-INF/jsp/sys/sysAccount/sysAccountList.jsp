<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<loading:loading>
</loading:loading>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <table id="sysAccountList_list" class="easyui-datagrid"></table>
    </div>
    <form id="sysAccountList_searchForm">
        <div data-options="collapsed:true,region:'east',iconCls:'icon-reload',title:'搜索条件',split:true"
             class="searchForm-east">
            <div class="easyui-layout">
                <div data-options="region:'north'" style="height:218px" title="部门查询">
                    <input id="sysAccountList_searchGroup" name="groupId" title="" data-options="width:230">
                </div>
                <div data-options="region:'center'" class="center" title="条件查询">
                    <div>
                        <span> 账号： </span>
                        <input class="easyui-textbox" name="fuzzyName" title="">
                    </div>
                    <div>
                        <span> 角色： </span>
                        <input id="sysAccountList_role" name="roleId" title="">
                    </div>
                </div>
                <div data-options="region:'south'" class="south">
                    <a class="easyui-linkbutton search_btn" data-options="iconCls:'icon-search'"
                       id="sysAccountList_search"
                       onClick="$('#sysAccountList_list').datagrid('load',$.serializeObject($('#sysAccountList_searchForm')));">搜索</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="sysAccountList_clear"
                       onClick="$('#sysAccountList_searchForm').form('clear');$('#sysAccountList_list').datagrid('load',$.serializeObject($('#sysAccountList_searchForm')));">清空</a>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="sysAccountList_toolbar">
    <shiro:hasPermission name="账号管理_新增">
        <a onclick="sysAccountList_add(null);" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="账号管理_删除">
        <a onclick="sysAccountList_del();" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-times '">删除</a>
    </shiro:hasPermission>
</div>
<script>
    var sysAccountList_list = $('#sysAccountList_list');
    $(function () {
        $('#sysAccountList_list').datagrid({
            title: "账号管理",
            url: 'sys/sysAccount/list',
            method: 'post',
            toolbar: '#sysAccountList_toolbar',
            singleSelect: true,
            loadMsg: '数据正在加载,请耐心等待...',
            fit: true,
            fitColumns: true,
            striped: true,
            animate: true,
            pagination: true,
            pageSize: 10,
            pageList: [5, 10, 15, 20, 30, 50],
            onLoadSuccess: function () {
                $('.sysAccountList_change').linkbutton({text: '修改', plain: true, iconCls: 'fa fa-repeat'});
                $('.sysAccountList_reset').linkbutton({text: '重置密码', plain: true, iconCls: 'fa fa-repeat'});
                $('.sysAccountList_stop').linkbutton({text: '停用', plain: true, iconCls: 'fa fa-window-close'});
                $('.sysAccountList_start').linkbutton({text: '启用', plain: true, iconCls: 'fa fa-play'});

            },
            columns: [[
                {title: 'id', field: 'id', checkbox: true},
                {title: '账号', field: 'userName', width: '19%', align: 'center'},
                {title: '拥有角色', field: 'roleName', width: '19%', align: 'center'},
                {title: '所属单位', field: 'groupName', width: '19%', align: 'center'},
                {title: '备注', field: 'note', width: '21%', align: 'center'},
                {title: '操作列', field: 'a', width: '21%', align: 'center', formatter: operate}
            ]]
        });
        getGroup('sysAccountList_searchGroup');
        getRole('sysAccountList_role');
    });

    //操作列
    function operate(val, row, index) {
        var operation = '';
        <shiro:hasPermission name="账号管理_修改">
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysAccountList_change" '
            + 'onClick="sysAccountList_add(\'' + row.id + '\')">修改</a>';
        </shiro:hasPermission>
        <shiro:hasPermission name="账号管理_重置密码">
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysAccountList_reset" '
            + 'onClick="sysAccountList_reset(\'' + row.id + '\',\'' + row.userName + '\')">重置密码</a>';
        </shiro:hasPermission>
        <shiro:hasPermission name="账号管理_停用">
        </shiro:hasPermission>
        if (row.stopFlag == '1') {
            operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysAccountList_start" '
                + 'onClick="sysAccountList_stop(\'' + row.id + '\',0)">启用</a>';
        } else if (row.stopFlag == '0') {
            operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysAccountList_stop" '
                + 'onClick="sysAccountList_stop(\'' + row.id + '\',1)">停用</a>';
        }
        return operation;
    }

    //停用 启用
    function sysAccountList_stop(id, stopFlag) {
        if (stopFlag == '0') {
            var message = '启用账号';
        } else if (stopFlag == '1') {
            var message = '停用账号';
        }

        $.ajax({
            type: 'POST',
            data: {
                id: id,
                stopFlag: stopFlag
            },
            url: 'sys/sysAccount/save',
            success: function (data) {
                if (data.code === 200) {
                    showMsg(message + '成功');
                    sysAccountList_list.datagrid('reload');
                } else {
                    showMsg(message + '失败');
                }
            }
        });
    }

    //重置密码
    function sysAccountList_reset(id, userName) {
        $.messager.confirm('确认', '您确认想要重置密码吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        id: id,
                        userName: userName
                    },
                    url: 'sys/sysAccount/reset',
                    success: function (data) {
                        if (data.code === 200) {
                            showMsg('重置密码成功');
                        } else {
                            showMsg('重置密码失败');
                        }
                    }
                });
            }
        });
    }

    //跳转编辑页面
    function sysAccountList_add(id) {
        window.location = document.getElementsByTagName("base")[0].getAttribute("href") + 'sys/sysAccount/addList?id=' + id;
    }

    //删除
    function sysAccountList_del() {
        var row = sysAccountList_list.datagrid('getSelected');
        $.messager.confirm('删除', '确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        id: row.id
                    },
                    url: 'sys/sysAccount/del',
                    success: function (data) {
                        if (data.code === 200) {
                            sysAccountList_list.datagrid('reload');
                            showMsg(data.data);
                        } else {
                            showMsg('删除失败');
                        }
                    }
                })
            }
        });
    }
</script>
</body>
</html>

