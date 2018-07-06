//下拉框 是/否
function getChoose(id) {
    $('#' + id).combobox({
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

}

function getRole(id) {
//角色下拉框
    $('#' + id).combobox({
        panelHeight: 'auto',
        url: 'sys/sysRole/list',
        valueField: 'id',
        textField: 'roleName',
        loadFilter: function (data) {
            return data.rows;
        }
    });
}


//部门树形下拉树 单选
function getGroup(id) {
    $('#' + id).combotree({
        method: 'post',
        url: 'sys/sysGroup/list',
        prompt: '请选择部门...',
        panelHeight: 'auto',
        panelMaxWidth: 302,
        panelMaxHeight: 170,
        editable: true,
        loadFilter: function (data, parent) {
            return data.rows;
        }
    });
}

//部门树形下拉树 单选
function getGroups(id,width,height) {
    $('#' + id).combotree({
        method: 'post',
        url: 'sys/sysGroup/list',
        prompt: '请选择部门...',
        panelHeight: 'auto',
        panelMaxWidth: width,
        panelMaxHeight: height,
        editable: true,
        //禁止联动选择
        cascadeCheck: false,
        loadFilter: function (data) {
            return data.rows;
        }, onClick: function (node) {
         /*   checkNode($('#' + id).combotree('tree').tree('getParent', node.target), id)
            checkChildNode(node, id);*/
        }
    });
}

//权限树形下拉树 单选
function getPermission(id, width, height) {
    $('#' + id).combotree({
        method: 'post',
        url: 'sys/sysPermission/list',
        required: true,
        prompt: '请选择父节点...',
        panelHeight: 'auto',
        panelMaxWidth: width,
        panelMaxHeight: height,
        editable: true,
        loadFilter: function (data, parent) {
            return data.rows;
        }
    });
}

//权限树形下拉树 多选 展开所有节点
function getPermissions(id, width, height) {
    $('#' + id).combotree({
        method: 'post',
        url: 'sys/sysPermission/list',
        prompt: '请选择父节点...',
        panelHeight: 'auto',
        checkbox: true,
        multiple: true,
        editable: true,
        panelMaxWidth: width,
        panelMaxHeight: height,
        //禁止联动选择
        cascadeCheck: false,
        loadFilter: function (data) {
            return data.rows;
        }, onClick: function (node) {
            checkNode($('#' + id).combotree('tree').tree('getParent', node.target), id)
            checkChildNode(node, id);
        }
    });
}

//选中父类节点
function checkNode(node, id) {
    if (!node) {
        return;
    } else {
        //递归选中父节点
        checkNode($('#' + id).combotree('tree').tree('getParent', node.target), id);
        //展开当前节点
        $('#' + id).combotree('tree').tree('check', node.target);
    }
}

//选中子类节点
function checkChildNode(node, id) {
    if (!node) {
        return;
    } else {
        var children = node.children;
        var checked = node.checked;

        for (var i in children) {
            //如果有子节点 选中子节点
            if (checked) {
                $('#' + id).combotree('tree').tree('check', children[i].target);
                checkChildNode(children[i], id);
            } else {
                $('#' + id).combotree('tree').tree('uncheck', children[i].target);
                checkChildNode(children[i], id);

            }
        }
    }
}

/**
 * @author : cjd
 * @description : 获取数据字典下拉框
 * @date : 12:27 2018/5/14
 */
function getDicCombobox(id, parentKey, width) {
    if (width == null) {
        width = 170;
    }
    $('#' + id).combobox({
        width: width,
        valueField: 'key',
        textField: 'value',
        url: 'sys/sysDic/getValues?parentKey=' + parentKey,
        panelHeight: 'auto',
        loadFilter: (function (data) {
            return data.data;
        })
    });
}

//获取数据字典的值
function getDicVal(parentKey, key) {
    var t = '';
    $.ajax({
        url: 'sys/sysDic/getValue?parentKey=' + parentKey + '&key=' + key,
        dataType: 'json',
        type: 'POST',
        async: false,
        success: function (data) {
            if (data.code == 200) {
                t = data.data;
            }
        }
    });
    return t;

}


/*遮盖层*/

//监听加载状态改变
document.onreadystatechange = completeLoading;

//加载状态为complete时移除loading效果
function completeLoading() {
    if (document.readyState == "complete") {
        $("#loading").hide();

    }
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

}