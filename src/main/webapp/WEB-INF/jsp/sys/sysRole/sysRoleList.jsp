<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <table id="sysRoleList_list" class="easyui-datagrid"></table>
    </div>
    <form id="sysRoleList_searchForm">
        <div data-options="region:'east',iconCls:'icon-reload',title:'搜索条件',split:true" class="searchForm-east">
            <div class="easyui-layout">
                <div data-options="region:'center'" class="center">
                    <span> 角色名称： </span>
                    <input class="easyui-textbox" name="roleName" data-options="width:100" title="">
                </div>
                <div data-options="region:'south'" class="south">
                    <a class="easyui-linkbutton search_btn" data-options="iconCls:'icon-search'" id="sysRoleList_search"
                       onClick="$('#sysRoleList_list').datagrid('load',$.serializeObject($('#sysRoleList_searchForm')));">搜索</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                       onClick="$('#sysRoleList_searchForm').form('clear');$('#sysRoleList_list').datagrid('load',$.serializeObject($('#sysRoleList_searchForm')));">清空</a>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="sysRoleList_toolbar">
    <shiro:hasPermission name="角色管理_新增">
        <a onclick="sysRoleList_add(null);" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="角色管理_删除">
        <a onclick="sysRoleList_del();" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-times '">删除</a>
    </shiro:hasPermission>
</div>
<script>
    $(function () {
        $('#sysRoleList_list').datagrid({
            title: "角色管理",
            url: 'sys/sysRole/list',
            method: 'post',
            toolbar: '#sysRoleList_toolbar',
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
                $('.sysRoleList_change').linkbutton({text: '修改', plain: true, iconCls: 'fa fa-repeat'});
            },
            columns: [[
                {title: 'id', field: 'id', checkbox: true},
                {title: '角色名称', field: 'roleName', width: '19%', align: 'center'},
                {title: '拥有权限', field: 'permissionName', width: '19%', align: 'center'},
                {title: '创建人', field: 'createPeople', width: '19%', align: 'center'},
                {title: '创建时间', field: 'createTime', width: '19%', align: 'center'},
                {title: '操作列', field: 'a', width: '23%', align: 'center', formatter: operate}
            ]]
        });
    });

    //操作列
    function operate(val, row, index) {
        var operation = '';
        <shiro:hasPermission name="角色管理_修改">
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysRoleList_change" '
            + 'onClick="sysRoleList_add(\'' + row.id + '\')">修改</a>';
        </shiro:hasPermission>
        return operation;
    }

    //添加 修改
    function sysRoleList_add(id) {
        window.location = document.getElementsByTagName("base")[0].getAttribute("href") + 'sys/sysRole/addList?id=' + id;
    }

    //删除
    function sysRoleList_del() {
        var sysRoleList_list = $('#sysRoleList_list');
        var row = sysRoleList_list.datagrid('getSelected');
        $.messager.confirm('删除', '确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        id: row.id
                    },
                    url: 'sys/sysRole/del',
                    success: function (data) {
                        if (data.code === 200) {
                            sysRoleList_list.datagrid('reload');
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

