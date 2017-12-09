<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/css/jquery_zTree/diybtn.css" rel="stylesheet">
<style type="text/css">
    .col-md-4{
        padding-right: 8px;
        padding-left: 8px;
    }
    #div_list>div.selected div.icon-inner{
        border-color: #99a3fd;
    }
    .icon-outer{
        margin: 6px;
        width: 90px;
        height: 115px;
        position: relative;
        float: left;
        overflow: hidden;
    }
    .icon-outer .icon-inner{
        border: solid 5px #fff;
        padding: 2px 2px;
    }
    .icon-outer .icon-inner img{
        width: 76px;
        height:76px;
    }
    .icon-outer label{
        margin-top: 5px;
    }
    .icon-outer a.btn-delete{
        position: absolute;
        top: 0px;
        right: 0px;
        border: 2px #a2a2a2 solid;
        border-radius: 8px;
        display: block;
        height: 16px;
        line-height: 11px;
        width: 16px;
    }
    .icon-outer a.btn-delete{
        display: none;
    }
    .icon-outer:hover a.btn-delete{
        display: inherit;
        border-color: #a2a2a2;
    }
    .icon-outer:hover a.btn-delete:hover{
        border-color: #424242;
    }
    .icon-outer:hover a.btn-delete i{
        bcolor: #a2a2a2;
    }
    .icon-outer:hover a.btn-delete:hover i{
        color: #424242;
    }

    #form_ext_info a.btn-delete{
        position: absolute;
        top: 0px;
        right: 0px;
        border: 2px #424242 solid;
        border-radius: 8px;
        display: block;
        height: 16px;
        line-height: 11px;
        width: 16px;
    }
    #form_ext_info a.btn-delete i{
        color: #424242;
    }
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>图标分组</h5><a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_root_node">新增根节点</a>
                    </div>
                    <div id="div_zTree" class="widget-content" style="min-height:536px;overflow: auto;">
                        <ul id="ztree" class="ztree" style="margin-left: 20px;"></ul>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>图标列表</h5>
                        <a href="javascript:;" id="btn_add_icon" class="btn btn-xs btn-primary buttons">添加图标</a>
                    </div>
                    <div class="widget-content clearfix" style="min-height: 536px;text-align: center;">
                        <div  style="text-align: center;" id="div_list">

                        </div>
                        <div id="div_page_btn" style="top: 530px;position: absolute;left: 35px;"></div>
                    </div>
                </div>
            </div>
            <%--<div class="col-md-4">--%>
                <%--<div id="div_icon_edit_info" class="widget-box">--%>
                    <%--<div class="widget-title">--%>
                        <%--<h5>基础属性</h5>--%>
                        <%--<a href="javascript:;" id="btn_save_icon" class="btn btn-xs btn-primary buttons">保存</a>--%>
                    <%--</div>--%>
                    <%--<div class="widget-content" style="min-height: 245px;text-align: center;">--%>

                    <%--</div>--%>
                    <%--<div class="widget-title">--%>
                        <%--<h5>扩展属性(双击编辑)</h5><a href="javascript:;" id="btn_add_icon_field" class="btn btn-xs btn-primary buttons">添加属性</a>--%>
                    <%--</div>--%>
                    <%--<div class="widget-content" style="min-height: 255px;text-align: center;">--%>

                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.exedit-3.5.min.js"></script>
    <script src="${ctx}/js/layer-v2.2/layer/extend/layer.ext.js" ></script>
    <script src="${ctx}/js/laypage-v1.3/laypage/laypage.js" ></script>
    <script src="${ctx}/js/jquery/jquery.form-3.51.0.js"></script>
    <script src="${ctx}/js/module/iconManage/iconManage.js?v=20161028004"></script>
    <script type="text/javascript">
        var setting={
            data: {
                simpleData: {
                    enable: true,
                            idKey: "id",
                            pIdKey: "pid",
                            rootPId: 0
                }
            },
            view: {
                showIcon: true,
                        selectedMulti: false,
                    /*nameIsHTML: true,*/
                        addHoverDom: WEBGIS.iconGroupManage.addHoverDom,
                        removeHoverDom: WEBGIS.iconGroupManage.removeHoverDom
            },
            edit:{
                enable: true,
                        editNameSelectAll: true,
                        showRemoveBtn: true,
                        showRenameBtn : true,
                        removeTitle : "删除节点",
                        renameTitle : "修改节点"
            },
            callback: {
                beforeRemove: WEBGIS.iconGroupManage.beforeRemove,
                beforeRename: WEBGIS.iconGroupManage.beforeRename,
                beforeDrag: WEBGIS.iconGroupManage.beforeDrag,
                beforeDrop: WEBGIS.iconGroupManage.beforeDrop,
                onClick: WEBGIS.iconGroupManage.zTreeOnClick
            }
        };
        $(document).ready(function(){
            WEBGIS.iconManage.init();
        });
    </script>
</body>
</html>