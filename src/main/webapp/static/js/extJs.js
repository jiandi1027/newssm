/*  全局对象 */
var sy = $.extend({}, sy);

/**
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function () {
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for (var i = 0; i < frame.length; i++) {
                frame[i].src = '';
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
                try {
                    CollectGarbage();
                } catch (e) {
                }
            }
        }
    } catch (e) {
    }
};

/*防止panel/window/dialog组件超出浏览器边界*/
var easyuiPanelOnMove = function (left, top) {
    var l = left;
    var t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    var width = parseInt($(this).parent().css('width')) + 14;
    var height = parseInt($(this).parent().css('height')) + 14;
    var right = l + width;
    var buttom = t + height;
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    if (right > browserWidth) {
        l = browserWidth - width;
    }
    if (buttom > browserHeight) {
        t = browserHeight - height;
    }
    $(this).parent().css({
        /* 修正面板位置 */
        left: l,
        top: t
    });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 *
 *
 * @requires jQuery,EasyUI
 *
 * 通用错误提示
 *
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function (XMLHttpRequest) {
    parent.$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

//为datagrid、treegrid增加表头菜单，用于显示或隐藏列
var createGridHeaderContextMenu = function (e, field) {
    e.preventDefault();
    var grid = $(this);
    /* grid本身 */
    var headerContextMenu = this.headerContextMenu;
    /* grid上的列头菜单对象 */
    if (!headerContextMenu) {
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
        var fields = grid.datagrid('getColumnFields');
        for (var i = 0; i < fields.length; i++) {
            var fildOption = grid.datagrid('getColumnOption', fields[i]);
            if (!fildOption.hidden) {
                $('<div iconCls="fa fa-times" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            } else {
                $('<div iconCls="bullet_blue" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            }
        }
        headerContextMenu = this.headerContextMenu = tmenu.menu({
            onClick: function (item) {
                var field = $(item.target).attr('field');
                if (item.iconCls == 'fa fa-times') {
                    grid.datagrid('hideColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'fa fa-plus'
                    });
                } else {
                    grid.datagrid('showColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'fa fa-times'
                    });
                }
            }
        });
    }
    headerContextMenu.menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

//扩展validatebox，添加验证两次密码功能
$.extend($.fn.validatebox.defaults.rules, {
    eqPwd: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '密码不一致！'
    },
    telno: {
        validator: function (value, param) {
            return (/^1[3|4|5|8][0-9]\d{8}$/.test(value.trim()));
        },
        message: '请输入正确的电话号码'
    },
    positivenumber: {
        validator: function (value, param) {
            return (/^\d+$/.test(value));
        },
        message: '请输入正整数'
    },
    passportNum: {   //G28233515
        validator: function (value, param) {
            return (/^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$/.test(value.trim()));
        },
        message: '请输入正确的护照号码'
    },
    chinaName: {   //姓名验证
        validator: function (value, param) {
            return (/^[\u4e00-\u9fa5 ]{2,20}$/.test(value.trim()));
        },
        message: '请输入中文'
    },
    idcard: {
        validator: function (value, param) {
            return isCardID(value) == true ? true : false;
        },
        message: '不是有效的身份证号码'
    },
    telNum: { //既验证手机号，又验证座机号
        validator: function (value, param) {
            return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d{3})|(\d{3}\-))?(1[358]\d{9})$)/.test(value);
        },
        message: '请输入正确的电话号码。'
    },
    tbcard: { //台胞证
        validator: function (value, param) {
            return ( /^[a-zA-Z0-9]{5,21}$/.test(value.trim()));
        },
        message: '不是有效的台胞证'
    },
    gatcard: {//港澳台通行证
        validator: function (value, param) {
            return ( /^[a-zA-Z0-9]{5,21}$/.test(value.trim()));
        },
        message: '不是有效的港澳台通行证 '
    }
});

//扩展tree，使其支持平滑数据格式
$.fn.tree.defaults.loadFilter = function (data, parent) {
    var opt = $(this).data().tree.options;
    var idFiled, textFiled, parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

// 扩展treegrid，使其支持平滑数据格式
$.fn.treegrid.defaults.loadFilter = function (data, parentId) {
    var opt = $(this).data().treegrid.options;
    var idFiled, textFiled, parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

// 扩展combotree，使其支持平滑数据格式
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 *
 * @requires jQuery,EasyUI
 *
 * 创建一个模式化的dialog
 *
 * @returns $.modalDialog.handler 这个handler代表弹出的dialog句柄
 *
 * @returns $.modalDialog.xxx 这个xxx是可以自己定义名称，主要用在弹窗关闭时，刷新某些对象的操作，可以将xxx这个对象预定义好
 */
$.modalDialog = function (options) {
    if ($.modalDialog.handler == undefined) {// 避免重复弹出
        var opts = $.extend({
            title: '',
            width: 840,
            height: 680,
            modal: true,
            onClose: function () {
                $.modalDialog.handler = undefined;
                $(this).dialog('destroy');
            },
            onOpen: function () {
            }
        }, options);
        opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
        return $.modalDialog.handler = $('<div/>').dialog(opts);
    }
};

//将form表单元素的值序列化成对象 用作datagrid参数传递
$.serializeObject = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

/**
 *
 * 增加formatString功能
 *
 * 使用方法：$.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 *
 * @returns 格式化后的字符串
 */
$.formatString = function (str) {
    for (var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};

/**
 *
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 *
 * @returns list
 */
$.stringToList = function (value) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(',');
        for (var i = 0; i < t.length; i++) {
            values.push('' + t[i]);
            /* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};

/**
 *
 * @requires jQuery
 *
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
    type: 'POST',
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        try {
            parent.$.messager.progress('close');
            parent.$.messager.alert('错误', XMLHttpRequest.responseText);
        } catch (e) {
            alert(XMLHttpRequest.responseText);
        }
    }
});

/**
 *
 * @requires jQuery
 *
 * 去掉空格
 * **/
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};
String.prototype.ltrim = function () {
    return this.replace(/(^\s*)/g, '');
};
String.prototype.rtrim = function () {
    return this.replace(/(\s*$)/g, '');
};

//防止退格键导致页面回退
$(document).keydown(function (e) {
    var doPrevent;
    if (e.keyCode == 8) {
        var d = e.srcElement || e.target;
        if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        } else {
            doPrevent = true;
        }
    } else {
        doPrevent = false;
    }
    if (doPrevent)
        e.preventDefault();
});

//显示消息简写
function showMsg(msg) {
    top.window.$.messager.show({
        title: '提示',
        msg: '<div class="light-info"><div class="light-tip icon-tip"></div><div>' + msg || "消息内容！" + '</div></div>',
        timeout: 3000,
        showType: 'slide'
    });
}


//接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
sy.getList = function (value) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(',');
        for (var i = 0; i < t.length; i++) {
            values.push('' + t[i]);
            /* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};

//将form表单元素的值序列化成对象
sy.serializeObject = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};
//将JSON对象转换成字符串
sy.jsonToUrlParam = function (form) {
    var querystring = "";
    $.each(form.serializeArray(), function (index) {
        if (querystring != "")
            querystring = querystring + "&" + this['name'] + "=" + this['value'];
        else
            querystring = this['name'] + "=" + this['value'];
    });
    return querystring;
};

sy.getMonthEndDate = function () {
    var current = new Date();
    var currentMonth = current.getMonth();
    var nextMonth = ++currentMonth;
    var nextMonthDayOne = new Date(current.getFullYear(), nextMonth, 1);
    var minusDate = 1000 * 60 * 60 * 24;
    var endDate = new Date(nextMonthDayOne.getTime() - minusDate);
    return endDate;
}
sy.getMonthBeginDate = function () {
    var current = new Date();
    var currentMonth = current.getMonth();
    var begindate = new Date(current.getFullYear(), currentMonth, 1);
    return begindate;
}

/***
 * 获取数据字典key值
 */
sy.getDicKey = function (tabName, columnName, value, defaultValue) {
    var url = sy.pn() + '/sys/sysDic/getDicKey?tabName=' + tabName + '&columnName=' + columnName + '&defaultValue=' + defaultValue;
    var str = '';
    $.ajax({
        type: 'post',
        url: url,
        cache: false,
        async: false, //同步请求
        data: {
            value: value
        },
        dataType: 'json',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        },
        success: function (result) {
            if (result != null && $.trim(result) != '' && typeof(result.key) != 'undefined') {
                str = result.key;
            }
        }
    });
    return str;
};

//格式化时间
Date.prototype.format = function (format) {
    if (isNaN(this.getMonth())) {
        return '';
    }
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }
    var o = {
        /* month */
        "M+": this.getMonth() + 1,
        /* day */
        "d+": this.getDate(),
        /* hour */
        "h+": this.getHours(),
        /* minute */
        "m+": this.getMinutes(),
        /* second */
        "s+": this.getSeconds(),
        /* quarter */
        "q+": Math.floor((this.getMonth() + 3) / 3),
        /* millisecond */
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/**
 * @author
 *
 * @requires jQuery
 *
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
    type: 'POST',
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        $.messager.progress('close');
        $.messager.alert('错误', XMLHttpRequest.responseText);
    }
});

/*textarea 字数限制*/
function textarealength(obj, maxlength) {
    var v = $(obj).val();
    var l = v.length;
    if (l > maxlength) {
        v = v.substring(0, maxlength);
    }
    $(obj).parent().find(".textarea-length").text(v.length);
}


//timestamp转换date
function formatDate(value, str) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }
    return dt.format(str); //扩展的Date的format方法(上述插件实现)
}