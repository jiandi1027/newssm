<%--@elvariable id="sysAccount" type="com.cjdjyf.newssm.pojo.sys.SysAccount"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <%@include file="/WEB-INF/head/headCss.jsp" %>
    <%@include file="/WEB-INF/head/headTag.jsp" %>
    <%@include file="/WEB-INF/head/headJs.jsp" %>
</head>
<body>
<loading:loading>
</loading:loading>
<div class="add_btn">
    <a id="sysAccountAddList_save" class="easyui-linkbutton" data-options="iconCls: 'fa fa-floppy-o'"
       href="javascript:void(0);">保存</a>
    <a id="sysAccountAddList_close" class="easyui-linkbutton" data-options="iconCls: 'fa fa-remove'"
       href="javascript:void(0);">返回</a>
</div>
<form method="post" id="sysAccountAddList_form">
    <table id="sysAccountAddList_table" class="add_table">
        <tr id="sysAccountAddList_userName_tr">
            <td>账号：</td>
            <td><input name="userName" id="sysAccountAddList_userName" class="easyui-textbox easyui-validatebox"
                       data-options="required:true,validType:['length[0,20]'],delay:'0',width:350"
                       value="${sysAccount.userName}" title=""/></td>
        </tr>
        <%--  <c:if test="${empty sysAccount.id}">
              <tr>
                  <td>密码：</td>
                  <td><input name="password" class="easyui-validatebox easyui-passwordbox"
                             ondragenter="return false"
                             data-options="required:true,delay:'0',validType:['length[0,20]']"
                             value="000000" title=""/></td>
              </tr>
          </c:if>--%>
        <tr>
            <td>角色：</td>
            <td><input name="roleId" class="combobox-role" id="sysAccountAddList_roles"
                       data-options="multiple: true,required:true,value:'${sysAccount.roleId}',width:350
                       ,onSelect:function(record){}"
                       title="">
            </td>
        </tr>
        <tr>
            <td>部门：</td>
            <td><input name="groupId" class="combotree-group" id="sysAccountAddList_group"
                       data-options="required:true,multiple: true,value:'${sysAccount.groupId}',width:'350',
                          onLoadSuccess: function (node, data) {
                          var t = $('.combotree-group').combotree('tree');//获取tree
                         for (var i = 0; i < data.length; i++) {
                         node = t.tree('find', data[i].id);
                         t.tree('expandAll', node.target);//展开所有节点
                      }
                 } "
            ></td>
        </tr>
        <tr>
            <td>备注：</td>
            <td><input name="note" id="sysAccountAddList_note"
                       class="easyui-textbox easyui-validatebox"
                       data-options="validType:['length[0,20]'],delay:'0',width:350"
                       value="${sysAccount.note}  " title=""/>
            </td>
        </tr>
    </table>
</form>
<script>
    $(function () {
        getGroups('sysAccountAddList_group',350,170);
        getRole('sysAccountAddList_roles');
        if (${not empty sysAccount.id}) {
            $('#sysAccountAddList_userName_tr').hide();
        }
    });

    //保存
    $('#sysAccountAddList_save').click(function () {
        var sysAccountAddList_form = $("#sysAccountAddList_form");
        if (sysAccountAddList_form.form('validate')) {
            sysAccountAddList_save();
        } else {
            $.messager.show({
                title: '提示',
                msg: '数据不能为空'
            });
        }
    });

    //关闭
    $('#sysAccountAddList_close').click(function () {
        window.history.go(-1);
    });

    //表单提交
    function sysAccountAddList_save() {
        $.ajax({
            type: 'POST',
            data: $.param({'id': '${sysAccount.id}'}) + '&' + $('#sysAccountAddList_form').serialize(),
            url: 'sys/sysAccount/save',
            success: function (data) {
                if (data.code === 200) {
                    window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysAccount/list";
                    showMsg(data.data);
                } else {
                    showMsg('编辑失败');
                }
            }
        });
    }
</script>
</body>
</html>

