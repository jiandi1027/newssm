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
        <table id="sysGroupList_list" class="easyui-treegrid">
        </table>
    </div>
</div>
<div id="sysGroupList_toolbar">
    <a onclick="sysGroupList_add(null);" href="javascript:void(0);"
       class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">新增</a> <a
        onclick="sysGroupList_del();" href="javascript:void(0);"
        class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-times '">删除</a>
</div>
<div id="sysGroupList_dialog"></div>
<script>
    var sysGroupList_dialog = $('#sysGroupList_dialog');
    $(function () {
        $('#sysGroupList_list').treegrid({
            title: "部门管理",
            url: 'sys/sysGroup/list',
            method: 'post',
            idField: 'id',
            treeField: 'text',
            toolbar: '#sysGroupList_toolbar',
            singleSelect: true,
            loadMsg: '数据正在加载,请耐心等待...',
            fit: true,
            fitColumns: true,
            striped: true,
            animate: true,
            onLoadSuccess: function (data) {
                $('.sysGroupList_change').linkbutton({text: '修改', plain: true, iconCls: 'fa fa-repeat'});
            },
            columns: [[
                {title: 'id', field: 'id', checkbox: true},
                {title: '部门名称', field: 'text', width: '16%', align: 'center'},
                {title: '部门图标', field: 'iconCls', width: '16%', align: 'center'},
                {title: '是否展开', field: 'state', width: '16%', align: 'center', formatter: is_unfold},
                {title: '创建人', field: 'createPeople', width: '16%', align: 'center'},
                {title: '创建时间', field: 'createTime', width: '16%', align: 'center'},
                {title: '操作列', field: 'a', width: '18%', align: 'center', formatter: operate}
            ]]
        });
    });

    //是否展开
    function is_unfold(val, row, index) {
        if (val === 'open') {
            return '是';
        } else if (val === 'closed') {
            return '否';
        }
        return "";
    }

    //操作列
    function operate(val, row, index) {
        var operation = '';
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysGroupList_change" '
            + 'onClick="sysGroupList_add(\'' + row.id + '\')">修改</a>';
        return operation;
    }

    //添加 修改
    function sysGroupList_add(id) {
        window.location = document.getElementsByTagName("base")[0].getAttribute("href") + 'sys/sysGroup/addList?id=' + id;
    }

    //删除
    function sysGroupList_del() {
        var sysGroupList_list = $('#sysGroupList_list');
        var row = sysGroupList_list.treegrid('getSelected');
        $.messager.confirm('删除', '确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        id: row.id
                    },
                    url: 'sys/sysGroup/del',
                    success: function (data) {
                        if (data.code === 200) {
                            sysGroupList_list.treegrid('reload');
                            $.messager.show({
                                title: '提示',
                                msg: data.data
                            });
                        }
                    }
                })
            }
        });
    }
</script>
</body>
</html>

