<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!--对象-->
<%@ attribute name="sysFileList" type="java.util.List" required="false" description="文件列表" %>
<%--属性--%>
<%@ attribute name="upload_permission" type="java.lang.String" required="false" description="上传权限" %>
<%@ attribute name="download_permission" type="java.lang.String" required="false" description="下载权限" %>
<%@ attribute name="del_permission" type="java.lang.String" required="false" description="删除权限" %>
<%@ attribute name="file_show" type="java.lang.String" required="false" description="选择文件是否显示 为1不显示" %>
<%@ attribute name="file_del" type="java.lang.String" required="false" description="删除是否显示 为1不显示" %>
<link href="static/css/file.css" rel="stylesheet" type="text/css"/>
<shiro:hasPermission name="${upload_permission}">
    <c:if test="${file_show ne '1'}">
        <a href="javascript:void(0);" class="file">选择文件
            <input type="file" id="myFiles" name="myFiles" onchange="file_uploadFile()">
        </a>
    </c:if>
</shiro:hasPermission>

<shiro:hasPermission name="${download_permission}">
    <div id="fileDownload" style="display:none">1</div>
</shiro:hasPermission>

<shiro:hasPermission name="${del_permission}">
    <div id="fileDel" style="display:none">1</div>
</shiro:hasPermission>
<div style="width: 700px;height: 200px">
    <table id="file_fileList" class="easyui-datagrid" data-options="
            singleSelect: true,
            loadMsg: '数据正在加载,请耐心等待...',
            fit: true,
            striped: true,
            animate: true,
            fitColumns:true,
            onLoadSuccess: function () {
                $('.file_download').linkbutton({text: '下载', plain: true, iconCls: 'fa fa-download'});
                $('.file_remove').linkbutton({text: '删除', plain: true, iconCls: 'fa fa-times'});
            }">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">id</th>
            <th data-options="field:'fileName',width:'33%'">文件名</th>
            <th data-options="field:'fileSize',width:'33%'">文件大小</th>
            <th data-options="field:'filePath',hidden:true">文件路径</th>
            <th data-options="field:'createPeople',hidden:true">创建人</th>
            <th data-options="field:'a',width:'33%',formatter: file_operate">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sysFileList}" var="f">
            <tr>
                <td>
                        ${f.id}
                </td>
                <td>
                        ${f.fileName}
                </td>
                <td>
                        ${f.fileSize}
                </td>
                <td>
                        ${f.filePath}
                </td>
                <td>
                        ${f.createPeople}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    var saveFiles = "";
    var delFiles = "";

    //文件上传
    function file_uploadFile() {
        $.ajaxFileUpload({
            url: "sys/sysFile/upload",
            secureuri: false,// 一般设置为false
            type: 'post',
            dataType: 'text',
            async: true,   //是否是异步
            fileElementId: 'myFiles',// 文件上传表单的id
            success: function (data) // 服务器成功响应处理函数
            {
                var file = $.parseJSON(data);
                if (file.id == '' || file.id == 'null' || file.id.length == 0) {
                    showMsg('上传文件失败');
                    return;
                }

                $('#file_fileList').datagrid('appendRow', {
                    id: file.id,
                    fileName: file.fileName,
                    fileSize: file.fileSize,
                    filePath: file.filePath,
                    createPeople:file.createPeople
                });
                $('.file_download').linkbutton({text: '下载', plain: true, iconCls: 'fa fa-download'});
                $('.file_remove').linkbutton({text: '删除', plain: true, iconCls: 'fa fa-times'});
                showMsg("上传成功");
                //用来新增及修改的时候添加到待绑定文件
                if (saveFiles.length == 0) {
                    saveFiles = file.id;
                } else {
                    saveFiles = saveFiles + "," + file.id;
                }
            },
            error: function (data)// 服务器响应失败处理函数
            {
            }
        });
    }

    //文件显示操作列
    function file_operate(val, row, index) {
        var operation = '';
        var loginPeople='${user.userName}';
        operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="file_download" '
            + 'onClick="file_download(\'' + row.filePath.trim() + '\')">下载</a>';
        if (${file_del ne '1' }) {
            if (loginPeople == row.createPeople.trim()) {
                operation += '<a href="javascript:void(0);" href="javascript:void(0);" class="file_remove" '
                    + 'onClick="file_del(\'' + row.id.trim() + '\')">删除</a>';
            }
        }
        return operation;
    }

    //文件下载
    function file_download(path) {
        window.location = document.getElementsByTagName("base")[0].getAttribute("href") + "sys/sysFile/download?path=" + path;
    }

    //删除文件
    function file_del(id) {
        //待删除文件
        if (delFiles.length == 0) {
            delFiles = id;
        } else {
            delFiles = delFiles + "," + id;
        }
        var row = $('#file_fileList').datagrid('getSelected');
        var index = $('#file_fileList').datagrid('getRowIndex', row);

        $('#file_fileList').datagrid('deleteRow', index);
        showMsg('删除成功');

        /* $.ajax({
             type: 'POST',
             data: {
                 id: id
             },
             url: 'sys/sysFile/del',
             success: function (data) {
                 if (data.code === 200) {
                     var row = $('#file_fileList').datagrid('getSelected');
                     var index = $('#file_fileList').datagrid('getRowIndex', row);
                     $('#file_fileList').datagrid('deleteRow', index);
                     showMsg(data.data);
                 } else {
                     showMsg('删除失败');
                 }
             }
         })*/
    }
</script>