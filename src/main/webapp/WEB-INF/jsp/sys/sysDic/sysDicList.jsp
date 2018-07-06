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
        <table id="sysDicList_list" class="easyui-datagrid"></table>
    </div>
    <form id="sysDicList_searchForm">
        <div data-options="region:'east',iconCls:'icon-reload',title:'搜索条件',split:true" class="searchForm-east">
            <div class="easyui-layout">
                <div data-options="region:'center'" class="center">
                    <div>
                        <span> 表名： </span>
                        <input class="easyui-textbox" name="tabName" title="">
                    </div>
                    <div>
                        <span> 列名： </span>
                        <input class="easyui-textbox" name="columnName" title="">
                    </div>
                </div>

                <div data-options="region:'south'" class="south">
                    <a class="easyui-linkbutton search_btn" data-options="iconCls:'icon-search'" id="sysDicList_search"
                       onClick="$('#sysDicList_list').datagrid('load',$.serializeObject($('#sysDicList_searchForm')));">搜索</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" id="sysAccountList_clear"
                       onClick="$('#sysDicList_searchForm').form('clear');$('#sysDicList_list').datagrid('load',$.serializeObject($('#sysDicList_searchForm')));">清空</a>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="sysDicList_toolbar">
    <shiro:hasPermission name="数据字典_新增">
        <a onclick="sysDicList_add(null);" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="数据字典_删除">
        <a onclick="sysDicList_del();" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-times '">删除</a>
    </shiro:hasPermission>
</div>
<script>
    $(function () {
        $('#sysDicList_list').datagrid({
            title: "数据字典",
            url: 'sys/sysDic/list',
            method: 'post',
            toolbar: '#sysDicList_toolbar',
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
                $('.sysDicList_change').linkbutton({text: '修改', plain: true, iconCls: 'fa fa-repeat'});
            },
            columns: [[
                {title: 'id', field: 'id', checkbox: true},
                {title: '表名', field: 'tabName', width: '14%', align: 'center'},
                {title: '列名', field: 'columnName', width: '14%', align: 'center'},
                {title: '基准键', field: 'parentKey', width: '14%', align: 'center'},
                {title: '键', field: 'key', width: '14%', align: 'center'},
                {title: '值', field: 'value', width: '14%', align: 'center'},
                {title: '备注', field: 'note', width: '14%', align: 'center'},
                {title: '操作列', field: 'a', width: '15%', align: 'center', formatter: operate}
            ]]
        });
    });

    //操作列
    function operate(val, row, index) {
        var operation = '';
        <shiro:hasPermission name="数据字典_修改">
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysDicList_change" '
            + 'onClick="sysDicList_add(\'' + row.id + '\')">修改</a>';
        </shiro:hasPermission>
        return operation;
    }

    //添加 修改
    function sysDicList_add(id) {
        window.location = document.getElementsByTagName("base")[0].getAttribute("href") + 'sys/sysDic/addList?id=' + id;
    }

    //删除
    function sysDicList_del() {
        var sysDicList_list = $('#sysDicList_list');
        var row = sysDicList_list.datagrid('getSelected');
        $.messager.confirm('删除', '确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        id: row.id
                    },
                    url: 'sys/sysDic/del',
                    success: function (data) {
                        if (data.code === 200) {
                            sysDicList_list.datagrid('reload');
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

