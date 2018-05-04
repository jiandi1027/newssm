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
        <table id="codeCreateList_list" class="easyui-datagrid"></table>
    </div>
    <form id="codeCreateList_searchForm">
        <div data-options="region:'east',iconCls:'icon-reload',title:'搜索条件',split:true" class="searchForm-east">
            <div class="easyui-layout">
                <div data-options="region:'center'" class="center">
                    <span> 表名： </span>
                    <input class="easyui-textbox" name="tableName" data-options="width:100" title="">
                </div>
                <div data-options="region:'south'" class="south">
                    <a class="easyui-linkbutton search_btn" data-options="iconCls:'icon-search'"
                       id="codeCreateList_search"
                       onClick="$('#codeCreateList_list').datagrid('load',$.serializeObject($('#codeCreateList_searchForm')));">搜索</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                       onClick="$('#codeCreateList_searchForm').form('clear');$('#codeCreateList_list').datagrid('load',$.serializeObject($('#codeCreateList_searchForm')));">清空</a>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="codeCreateList_toolbar">
    <a onclick="codeCreateList_connect();" href="javascript:void(0);"
       class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">连接数据库</a>
</div>
<div id="codeCreateList_dialog"></div>


<script>
    $(function () {
        $('#codeCreateList_list').datagrid({
            title: "开发者工具",
            url: 'tool/codeCreate/list',
            method: 'post',
            toolbar: '#codeCreateList_toolbar',
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
                $('.codeCreateList_create').linkbutton({text: '生成', plain: true, iconCls: 'fa fa-file'});
            },
            columns: [[
                {title: 'id', field: 'id', checkbox: true},
                {title: '表名', field: 'tableName', width: '19%', align: 'center'},
                {title: '数据库引擎', field: 'engine', width: '19%', align: 'center'},
                {title: '表描述', field: 'tableComment', width: '19%', align: 'center'},
                {title: '创建时间', field: 'createTime', width: '19%', align: 'center'},
                {title: '操作列', field: 'a', width: '23%', align: 'center', formatter: operate}
            ]]
        });
    });

    //操作列
    function operate(val, row, index) {
        var operation = '';
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="codeCreateList_create" '
            + 'onClick="codeCreateList_create(\'' + row.tableName + '\')">生成代码</a>';
        return operation;
    }

    //生成代码
    function codeCreateList_create(tableName) {
        $('#codeCreateList_dialog').dialog({
            title: '连接数据库',
            iconCls: 'fa fa-fire',
            width: 400,
            height: 300,
            resizable: true,
            closed: false,
            modal: true,
            cache: false,
            href: 'tool/codeCreate/dataList?tableName='+tableName,
            buttons: [{
                text: '连接测试',
                iconCls: 'icon-save',
                handler: function () {
                    if ($("#codeCreateDataList_form").form('validate')) {
                        $('#codeCreateDataList_form ').submit();
                    } else {
                        $.messager.show({
                            title: '提示',
                            msg: '信息不能为空'
                        });
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'fa fa-remove ',
                handler: function () {
                    $('#codeCreateList_dialog').dialog('close');
                }
            }]
        });
        $('#codeCreateList_dialog').dialog('center');
    }



</script>
</body>
</html>

