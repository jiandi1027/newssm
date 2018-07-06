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
        <table id="toolFlowList_list" class="easyui-datagrid"></table>
    </div>
    <form id="toolFlowList_searchForm">
        <div data-options="region:'east',iconCls:'icon-reload',title:'搜索条件',split:true" class="searchForm-east">
            <div class="easyui-layout">
                <div data-options="region:'center'" class="center">
                </div>
                <div data-options="region:'south'" class="south">
                    <a class="easyui-linkbutton search_btn" data-options="iconCls:'icon-search'"
                       id="toolFlowList_search"
                       onClick="$('#toolFlowList_list').datagrid('load',$.serializeObject($('#toolFlowList_searchForm')));">搜索</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
                       onClick="$('#toolFlowList_searchForm').form('clear');$('#toolFlowList_list').datagrid('load',$.serializeObject($('#toolFlowList_searchForm')));">清空</a>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="toolFlowList_toolbar">
    <form id="toolFlow_file" enctype="multipart/form-data">
        <%--<input class="easyui-textbox" name="name" style="width:100px" prompt="流程名" >--%>
        <input class="easyui-filebox" name="files" style="width:150px" data-options="buttonText:'选择bpmn' ">
        <input class="easyui-filebox" name="files" style="width:150px"
               data-options="buttonText:'选择png',accept:'image/png'">
    </form>
    <a onclick="toolFlowList_deploy();" href="javascript:void(0);"
       class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">部署</a>
    <a onclick="toolFlowList_del();" href="javascript:void(0);"
       class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-times '">删除</a>
</div>
<div id="toolFlowList_dialog"></div>
<script>
    $(function () {
        $('#toolFlowList_list').datagrid({
            title: "流程定义部署",
            url: 'tool/toolFlow/list',
            method: 'post',
            toolbar: '#toolFlowList_toolbar',
            singleSelect: true,
            loadMsg: '数据正在加载,请耐心等待...',
            fit: true,
            fitColumns: true,
            striped: true,
            animate: true,
            onLoadSuccess: function () {
                /*$('.toolFlowList_deploy').linkbutton({text: '部署', plain: true, iconCls: 'fa fa-file'});*/
            }, loadFilter: function (data) {
                if (data.code == 200) {
                    return data.data;
                }
            },
            columns: [[
                {title: 't', field: 't', checkbox: true},
                {title: 'id', field: 'id', width: '16%', align: 'center'},
                {title: 'name', field: 'name', width: '16%', align: 'center'},
                {title: 'key', field: 'key', width: '16%', align: 'center'},
                {title: '部署ID', field: 'deploymentId', width: '16%', align: 'center'},
                {title: '版本', field: 'version', width: '16%', align: 'center'},
                {title: '操作列', field: 'a', width: '18%', align: 'center', formatter: operate}
            ]]
        });
    });

    //操作列
    function operate(val, row, index) {
        var operation = '';
        return operation;
    }

    //流程部署
    function toolFlowList_deploy() {
        var form = new FormData($("#toolFlow_file")[0]);
        $.ajax({
            type: 'POST',
            data: form,
            contentType: false,
            processData: false,
            url: 'tool/toolFlow/deploy',
            success: function (data) {
                if (data.code === 200) {
                    showMsg(data.data);
                    $('#toolFlowList_list').datagrid("reload");
                } else {
                    showMsg('部署流程定义失败');
                }
            }
        });
    }

    //删除
    function toolFlowList_del() {
        var toolFlowList_list = $('#toolFlowList_list');
        var row = toolFlowList_list.datagrid('getSelected');
        $.messager.confirm('删除', '确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        key: row.key
                    },
                    url: 'tool/toolFlow/del',
                    success: function (data) {
                        if (data.code === 200) {
                            toolFlowList_list.datagrid('reload');
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

