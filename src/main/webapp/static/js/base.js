$(function () {
    //下拉框 是/否
    $('.combobox-choose').combobox({
        panelHeight: 'auto',
        editable: false,
        valueField: 'label',
        textField: 'value',
        data: [{
            label: '1',
            value: '是'
        }, {
            label: '0',
            value: '否'
        }]
    });

    //权限树形下拉树 单选
    $('.combotree-permission').combotree({
        method: 'post',
        url: 'sys/sysPermission/list',
        required: true,
        prompt: '请选择父节点...',
        panelHeight: 'auto',
        panelMaxWidth: 200,
        panelMaxHeight: 170,
        editable: true,
        loadFilter: function (data, parent) {
            return data.rows;
        }
    });

    //权限树形下拉树 多选
    $('.combotree-permissions').combotree({
        method: 'post',
        url: 'sys/sysPermission/list',
        required: true,
        prompt: '请选择父节点...',
        panelHeight: 'auto',
        panelMaxWidth: 200,
        panelMaxHeight: 170,
        checkbox: true,
        multiple: true,
        editable: true,
        //禁止联动选择
        cascadeCheck: false,
        loadFilter: function (data, parent) {
            return data.rows;
        },
        onCheck: function (node, checked) {
            if (checked) {
                //选中父节点
                checkNode($('.combotree-permissions').combotree('tree').tree('getParent', node.target));
            }
        }

        /*,
        onLoadSuccess: function (node, data) {
            var t = $('.combotree-permissions').combotree('tree');//获取tree
            for (var i = 0; i < data.length; i++) {
                node = t.tree("find", data[i].id);
                t.tree('expandAll', node.target);//展开所有节点
            }
        }*/
    });


    //部门树形下拉树 单选
    $('.combotree-group').combotree({
        method: 'post',
        url: 'sys/sysGroup/list',
        prompt: '请选择部门...',
        panelHeight: 'auto',
        panelMaxWidth: 200,
        panelMaxHeight: 170,
        editable: true,
        loadFilter: function (data, parent) {
            return data.rows;
        }
    });

    //角色下拉框 多选
    $('.combobox-role').combobox({
        panelHeight: 'auto',
        multiple: true,
        url: 'sys/sysRole/list',
        valueField: 'id',
        textField: 'roleName',
        loadFilter: function (data) {
            return data.rows;
        }
    });
});

//选中父类节点
function checkNode(node) {
    if (!node) {
        return;
    } else {
        //递归选中父节点
        checkNode($('.combotree-permissions').combotree('tree').tree('getParent', node.target));
        //展开当前节点
        $('.combotree-permissions').combotree('tree').tree('check', node.target);
    }
}
