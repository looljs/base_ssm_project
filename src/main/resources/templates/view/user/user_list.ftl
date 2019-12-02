<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, looli.club, Ltd." />
    <link rel="stylesheet" type="text/css" href="/static/easyui/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/icon.css" />
    <script type="text/javascript" src="/static/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <title>角色管理</title>
</head>
<body class="easyui-layout">
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
<#--            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>-->
<#--            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>-->
<#--            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>-->
            <#list user as m>
                <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
            </#list>
        </div>
        <div class="wu-toolbar-search">
            <label>用户名:</label><input id="search-name" class="wu-text" style="width:100px">
            <label>所属角色:</label>
            <input class="easyui-combobox" id="search-role" style="width:200px;" name="roleId">
            <label>性别:</label>
            <select id="search-sex" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <option value="0">未知</option>
                <option value="1">男</option>
                <option value="2">女</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">头像预览:</td>
                <td valign="middle">
                    <img id="preview-photo" style="float:left;" src="/images/user_photo.jpg" width="100px">
                    <a style="float:left;margin-top:40px;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()" plain="true">上传图片</a>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">头像:</td>
                <td><input type="text" id="add-photo" name="photo" value="/images/user_photo.jpg" readonly="readonly" class="wu-text " /></td>
            </tr>
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" name="username" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写用户名'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">密码:</td>
                <td><input type="password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写密码'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">所属角色:</td>
                <td>
                    <input id="add-roleId" class="easyui-combobox" name="roleId" style="width:268px;" data-options="
                            url:'/role/list2',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required: true,
                            missingMessage: '请选择角色',
                            ">
                </td>
            </tr>
            <tr>
                <td width="60" align="right">性别:</td>
                <td>
                    <select name="sex" class="easyui-combobox" panelHeight="auto" style="width:268px">
                        <option value="0">未知</option>
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">年龄:</td>
                <td><input type="text" name="age" class="wu-text easyui-numberbox easyui-validatebox" data-options="required:true,min:1,precision:0, missingMessage:'请填写年龄'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">地址:</td>
                <td><input type="text" name="address" class="wu-text easyui-validatebox" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td width="60" align="right">头像预览:</td>
                <td valign="middle">
                    <img id="edit-preview-photo" style="float:left;" src="/images/user_photo.jpg" width="100px">
                    <a style="float:left;margin-top:40px;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()" plain="true">上传图片</a>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">头像:</td>
                <td><input type="text" id="edit-photo" name="photo" value="/images/user_photo.jpg" readonly="readonly" class="wu-text " /></td>
            </tr>
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" id="edit-username" name="username" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写用户名'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">所属角色:</td>
                <td>
                    <input id="edit-roleId" class="easyui-combobox" name="roleId" style="width:268px;" data-options="
                            url:'/role/list2',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required: true,
                            missingMessage: '请选择角色',
                            ">
                </td>
            </tr>
            <tr>
                <td width="60" align="right">性别:</td>
                <td>
                    <select id="edit-sex" name="sex" class="easyui-combobox" panelHeight="auto" style="width:268px">
                        <option value="0">未知</option>
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">年龄:</td>
                <td><input type="text" id="edit-age" name="age" value="1" class="wu-text easyui-numberbox easyui-validatebox" data-options="required:true,min:1,precision:0, missingMessage:'请填写年龄'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">地址:</td>
                <td><input type="text" id="edit-address" name="address" class="wu-text easyui-validatebox" /></td>
            </tr>
        </table>
    </form>
</div>
<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-upload',title:'正在上传图片'" style="width:450px; padding:10px;">
    <div id="p" class="easyui-progressbar" style="width:400px;" data-options="text:'正在上传中...'"></div>
</div>
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
<!-- End of easyui-dialog -->
    <!--加载动画-->
    <div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
        <img src="/static/easyui/images/load-page.gif" width="50%">
        <h1><font color="#15428B">加载中....</font></h1>
    </div>
</body>
</html>
<script type="text/javascript">
    //上传图片
    function start(){
        var value = $('#p').progressbar('getValue');
        if (value < 100){
            value += Math.floor(Math.random() * 10);
            $('#p').progressbar('setValue', value);
        }else{
            $('#p').progressbar('setValue',0)
        }
    };
    function upload(){
        if($("#photo-file").val() === '')return;
        var formData = new FormData();
        formData.append('photo',document.getElementById('photo-file').files[0]);
        $("#process-dialog").dialog('open');
        var interval = setInterval(start,200);
        $.ajax({
            url:'upload_photo',
            type:'post',
            data:formData,
            contentType:false,
            processData:false,
            success:function(data){
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                if(data.type === 'success'){
                    $("#preview-photo").attr('src',data.filepath);
                    $("#add-photo").val(data.filepath);
                    $("#edit-preview-photo").attr('src',data.filepath);
                    $("#edit-photo").val(data.filepath);
                }else{
                    $.messager.alert("消息提醒",data.msg,"warning");
                }
            },
            error:function(data){
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                $.messager.alert("消息提醒","上传失败!","warning");
            }
        });
    }

    function uploadPhoto(){
        $("#photo-file").click();
    }


    /**
     *  添加记录
     */
    function add(){
        var validate = $("#add-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'add',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','添加成功！','info');
                    $('#add-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    /**
     * Name 修改记录
     */
    function edit(){
        var validate = $("#edit-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#edit-form").serialize();
        $.ajax({
            url:'edit',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','修改成功！','info');
                    $('#edit-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    /**
     * 删除记录
     */
    function remove(){
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                var item = $('#data-datagrid').datagrid('getSelections');
                if(item == null || item.length == 0){
                    $.messager.alert('信息提示','请选择要删除的数据！','info');
                    return;
                }
                // console.log(item);
                var ids = [];
                for (var i = 0; i < item.length;i++){
                    ids[i] = item[i].id
                }
                // console.log(ids);
                $.ajax({
                    url:'delete',
                    dataType:'json',
                    type:'post',
                    data:{"ids[]":ids},
                    success:function(data){
                        if(data.type === 'success'){
                            $.messager.alert('信息提示','删除成功！','info');
                            $('#data-datagrid').datagrid('reload');
                        }else{
                            $.messager.alert('信息提示',data.msg,'warning');
                        }
                    }
                });
            }
        });
    }

    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        //$('#add-form').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加用户信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                // $("#add-form input").val('');
            }
        });
    }

    /**
     * 打开修改窗口
     */
    function openEdit(){
        //$('#edit-form').form('clear');
        var item = $('#data-datagrid').datagrid('getSelections');
        if(item == null || item.length === 0){
            $.messager.alert('信息提示','请选择要修改的数据！','info');
            return;
        }
        if(item.length > 1){
            $.messager.alert('信息提示','请选择一条数据进行修改！','info');
            return;
        }
        item = item[0];
        $('#edit-dialog').dialog({
            closed: false,
            modal:true,
            title: "修改用户信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $("#edit-id").val(item.id);
                $("#edit-preview-photo").attr('src',item.photo);
                $("#edit-photo").val(item.photo);
                $("#edit-username").val(item.username);
                $("#edit-roleId").combobox('setValue',item.roleId);
                $("#edit-sex").combobox('setValue',item.sex);
                $("#edit-age").val(item.age);
                $("#edit-address").val(item.address);
            }
        });
    }


    //搜索按钮监听
    $("#search-btn").click(function(){
        var roleId = $("#search-role").combobox('getValue');
        var sex = $("#search-sex").combobox('getValue')
        var option = {username:$("#search-name").val()};
        if(roleId != -1){
            option.roleId = roleId;
        }
        if(sex != -1){
            option.sex = sex;
        }
        $('#data-datagrid').datagrid('reload',option);
    });

    var roleList;
    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({
        url:'list',
        rownumbers:true,
        singleSelect:false,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'photo',title:'用户头像',width:100,align:'center',formatter:function(value,row,index){
                    var img = '<img src="'+value+'" width="50px" />';
                    return img;
                }},
            { field:'username',title:'用户名',width:100,sortable:true},
            { field:'password',title:'密码',width:100},
            { field:'roleId',title:'所属角色',width:100,formatter:function(value,row,index){
                    console.log(roleList);
                    for(var i=0;i<roleList.length;i++){
                        if(value === roleList[i].id)return roleList[i].name;
                    }
                    return value;
                }},
            { field:'sex',title:'性别',width:100,formatter:function(value,row,index){
                    switch(value){
                        case 0:{
                            return '未知';
                        }
                        case 1:{
                            return '男';
                        }
                        case 2:{
                            return '女';
                        }
                    }
                    return value;
                }},
            { field:'age',title:'年龄',width:100},
            { field:'address',title:'地址',width:200}
        ]],
        onLoadSuccess:function(data){
            $('.authority-edit').linkbutton({text:'编辑权限',plain:true,iconCls:'icon-edit'});
        }
    });

    //显示角色下拉列表
    $(function() {
        $.ajax({
            type: "post",
            url: "/role/list",//请求后台数据
            dataType: "json",
            success: function (json) {
                // console.log(json);
                roleList = json;
                $("#search-role").combobox({//往下拉框塞值
                    data: json,
                    valueField: "id",//value值
                    textField: "name",//文本值
                    panelHeight: "auto"
                })
            }
        });
    });



</script>
<script>
    var pc;
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1000);
    };
    function closes() {
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>
