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
        <table id="sysPermissionList_list" class="easyui-treegrid">
        </table>
    </div>
</div>
<div id="sysPermissionList_toolbar">
    <a onclick="sysPermissionList_add(null);" href="javascript:void(0);"
       class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-plus'">新增</a> <a
        onclick="sysPermissionList_del();" href="javascript:void(0);"
        class="easyui-linkbutton" data-options="plain:true,iconCls:'fa fa-times '">删除</a>
</div>
<div id="sysPermissionList_dialog"></div>
<script>
    var sysPermissionList_dialog = $('#sysPermissionList_dialog');
    $(function () {
        $('#sysPermissionList_list').treegrid({
            title: "权限管理",
            url: 'sys/sysPermission/list',
            method: 'post',
            idField: 'id',
            treeField: 'text',
            toolbar: '#sysPermissionList_toolbar',
            singleSelect: true,
            loadMsg: '数据正在加载,请耐心等待...',
            fit: true,
            fitColumns: true,
            striped: true,
            animate: true,
            onLoadSuccess: function (data) {
                $('.sysPermissionList_change').linkbutton({text: '修改', plain: true, iconCls: 'fa fa-repeat'});
            },
            columns: [[
                {title: 'id', field: 'id', checkbox: true},
                {title: '权限名称', field: 'text', width: '16%', align: 'center'},
                {title: '权限URL', field: 'url', width: '16%', align: 'center'},
                {title: '是否菜单', field: 'flag', width: '16%', align: 'center', formatter: is_menu},
                {title: '菜单图标', field: 'iconCls', width: '16%', align: 'center'},
                {title: '是否展开', field: 'state', width: '16%', align: 'center', formatter: is_unfold},
                {title: '操作列', field: 'a', width: '18%', align: 'center', formatter: operate}
            ]]
        });
    });

    //是否菜单
    function is_menu(val, row, index) {
        if (val === '1') {
            return '是';
        } else if (val === '0') {
            return '否';
        }
        return "";
    }

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
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="sysPermissionList_change" '
            + 'onClick="sysPermissionList_add(\'' + row.id + '\')">修改</a>';
        return operation;
    }

    //添加 修改
    function sysPermissionList_add(id) {
        window.location = document.getElementsByTagName("base")[0].getAttribute("href")+ 'sys/sysPermission/addList?id=' + id;
    }

    //删除
    function sysPermissionList_del() {
        var sysPermissionList_list = $('#sysPermissionList_list');
        var row = sysPermissionList_list.treegrid('getSelected');
        $.messager.confirm('删除', '确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    data: {
                        id: row.id
                    },
                    url: 'sys/sysPermission/del',
                    success: function (data) {
                        if (data.code === 200) {
                            sysPermissionList_list.treegrid('reload');
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
