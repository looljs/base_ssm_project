<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台管理主页</title>
    <link rel="stylesheet" type="text/css" href="/static/easyui/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/icon.css" />
    <script type="text/javascript" src="/static/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
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
</head>
<body class="easyui-layout">
    <div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#FFFFFF;text-align :center;">
        <img src="/static/easyui/images/loading.jpg" width="50%">
    </div>
    <!-- begin of header -->
    <div class="wu-header" data-options="region:'north',border:false,split:true">
        <div class="wu-header-left">
            <h1>【looli.club】后台管理系统</h1>
        </div>
        <div class="wu-header-right">
            <p><strong class="easyui-tooltip" title="0条未读消息"></strong>${username}，欢迎您！</p>
            <p><a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a href="/system/logout">安全退出</a></p>
        </div>
    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
    <div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'">
        <div class="easyui-accordion" data-options="border:false,fit:true">
<#--            <div title="用户管理" data-options="iconCls:'icon-wrench'" style="padding:5px;">-->
<#--                <ul class="easyui-tree wu-side-tree">-->
<#--                    <li iconCls="icon-chart-organisation">-->
<#--                        <a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="/user/user_list.ftl" iframe="1">用户管理</a></li>-->
<#--                </ul>-->
<#--            </div>-->
<#--            <div title="系统设置" data-options="iconCls:'icon-wrench'" style="padding:5px;">-->
<#--                <ul class="easyui-tree wu-side-tree">-->
<#--                    <li iconCls="icon-chart-organisation">-->
<#--                        <a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="/menu/menu_list.ftl" iframe="0">菜单管理</a></li>-->
<#--                    <li iconCls="icon-chart-organisation">-->
<#--                        <a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="/role/role_list.ftl" iframe="1">角色管理</a></li>-->
<#--                </ul>-->
<#--            </div>-->
            <#list menuListTop as menu>
                <div title="${menu.name}" data-options="iconCls:'${menu.icon}'" style="padding:5px;">
                    <ul class="easyui-tree wu-side-tree">
                        <#list childList as child>
                            <#if child.parentId==menu.id>
                                <li iconCls="${child.icon}">
                                    <a href="javascript:void(0)" data-icon="${child.icon}" data-link="${child.url}" iframe="${item_index?if_exists+1}">
                                        ${child.name}
                                    </a>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </#list>
        </div>
    </div>


    <!-- end of sidebar -->
    <!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">
            <div title="首页" data-options="href:'welcome',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
        </div>
    </div>
    <!-- end of main -->
    <!-- begin of footer -->
    <div class="wu-footer" data-options="region:'south',border:true,split:true">
        &copy; 2019 【looli.club】 All Rights Reserved
    </div>
    <!-- end of footer -->
    <script type="text/javascript">
        $(function(){
            $('.wu-side-tree a').bind("click",function(){
                var title = $(this).text();
                var url = $(this).attr('data-link');
                var iconCls = $(this).attr('data-icon');
                var iframe = $(this).attr('iframe')==1?true:false;
                addTab(title,url,iconCls,iframe);
            });
        });


        /**
         * Name 添加菜单选项
         * Param title 名称
         * Param href 链接
         * Param iconCls 图标样式
         * Param iframe 链接跳转方式（true为iframe，false为href）
         */
        function addTab(title, href, iconCls, iframe){
            var tabPanel = $('#wu-tabs');
            if(!tabPanel.tabs('exists',title)){
                var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
                if(iframe){
                    tabPanel.tabs('add',{
                        title:title,
                        content:content,
                        iconCls:iconCls,
                        fit:true,
                        cls:'pd3',
                        closable:true
                    });
                }
                else{
                    tabPanel.tabs('add',{
                        title:title,
                        href:href,
                        iconCls:iconCls,
                        fit:true,
                        cls:'pd3',
                        closable:true
                    });
                }
            }
            else
            {
                tabPanel.tabs('select',title);
            }
        }
        /**
         * Name 移除菜单选项
         */
        function removeTab(){
            var tabPanel = $('#wu-tabs');
            var tab = tabPanel.tabs('getSelected');
            if (tab){
                var index = tabPanel.tabs('getTabIndex', tab);
                tabPanel.tabs('close', index);
            }
        }
    </script>
</body>
</html>