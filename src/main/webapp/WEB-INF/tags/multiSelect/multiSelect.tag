<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!--对象-->
<%@ attribute name="chooseList" type="java.util.List" required="false" description="文件列表" %>
<%--属性--%>
<%@ attribute name="name" type="java.lang.String" required="false" description="上传权限" %>
<%@ attribute name="messageOne" type="java.lang.String" required="false" description="搜索框提示消息" %>
<%@ attribute name="messageTwo" type="java.lang.String" required="false" description="搜索框提示消息" %>

<link href='static/other/multi-select-master/css/multi-select.css' rel='stylesheet'>
<style>
    .borderList {
        border: 1px solid #ccc;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
    }
</style>
<div style="width: 430px;height:280px;" class="borderList">
    <div style="width: 100%; height: 33px; padding: 10px 0 0 20px;">
        <div style="float: left;width: 166px">
            <div style="margin-right: 0px">
                ${name}
                <div style="float: right">
                    <a id="multiSelect_selectAll" href="javascript:void(0);" class="easyui-linkbutton" data-options="">全选</a>
                </div>
            </div>
        </div>
        <div style="float: left;width: 37px;height: 20px"></div>
        <div style="float: left;width: 167px">
            <a id="multiSelect_unSelectAll" href="javascript:void(0);" style="margin-left: 131px"
               class="easyui-linkbutton" data-options="">全选</a>
        </div>
    </div>
    <div style="margin-left: 20px">
        <select id='multiSelect_choose' multiple='multiple'>
            <c:forEach var="f" items="${chooseList}">
                <%--返回要以key value返回 --%>
                <option value='${f.key}'>${f.value}</option>
            </c:forEach>
        </select>
    </div>
</div>

<script type="text/javascript" src="static/other/multi-select-master/js/jquery.multi-select.js"
        charset="utf-8"></script>
<script type="text/javascript" src="static/other/multi-select-master/jquery.quicksearch.js"
        charset="utf-8"></script>
<script>
    //返回会签部门多选的数据
    function getChooseSelected() {
        var chooseResult = '';
        var options = $("#multiSelect_choose option:selected");
        for (var i = 0; i < options.length; i++) {
            if (chooseResult == '') {
                chooseResult = options[i].value
            } else {
                chooseResult = chooseResult + "," + options[i].value
            }
        }
        return chooseResult;
    }




    //全选
    $('#multiSelect_selectAll').click(function () {
        $('#multiSelect_choose').multiSelect('select_all');
        return false;
    });

    //反全选
    $('#multiSelect_unSelectAll').click(function () {
        $('#multiSelect_choose').multiSelect('deselect_all');
        return false;
    });

    //初始化
    $('#multiSelect_choose').multiSelect({
        selectableHeader: '<i class="fa fa-search pull-right p-r-sm" style="position:relative;top:6px;right: 6px;z-index:1;"></i><input class="search-input borderList" type="text" placeholder="${messageOne}" style="width:167px;border-bottom:0;border-radius:3px 3px 0 0;padding-top:3px;padding-bottom:3px;margin-top:-15px;">',
        selectionHeader: '<input class="borderList" type="text" disabled placeholder="${messageTwo}"style="width:166px;border-bottom:0;border-radius:3px 3px 0 0;padding-top:3px;padding-bottom:3px">',

        afterInit: function (ms) {
            var that = this,
                $selectableSearch = that.$selectableUl.prev(),
                $selectionSearch = that.$selectionUl.prev(),
                selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)',
                selectionSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selection.ms-selected';
            that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                .on('keydown', function (e) {
                    if (e.which === 40) {
                        that.$selectableUl.focus();
                        return false;
                    }
                });
            that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                .on('keydown', function (e) {
                    if (e.which == 40) {
                        that.$selectionUl.focus();
                        return false;
                    }
                });
        },
        afterSelect: function () {
            this.qs1.cache();
            this.qs2.cache();
        },
        afterDeselect: function () {
            this.qs1.cache();
            this.qs2.cache();
        }
    });
</script>