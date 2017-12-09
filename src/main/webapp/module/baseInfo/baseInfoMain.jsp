<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/css/jquery_zTree/diybtn.css" rel="stylesheet">
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>基础资料分类</h5>
                    </div>
                    <div id="div_ztree" class="widget-content" style="min-height:536px;overflow: auto;">
                        <ul id="ul_ztree" class="ztree" style="margin-left: 20px;"></ul>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="widget-box">
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="form_search">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <div class="col-sm-2 form-div2" style="line-height: 30px;">
                                        <button type="button" class="btn btn-xs btn-primary buttons" id="btn_icon">选择图标</button>
                                    </div>
                                    <div class="col-sm-8 form-div2" id="div_img_base_info_type"></div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </form>
                    </div>
                </div>
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>资料列表</h5>
                        <a href="javascript:;" id="btn_add_info" class="btn btn-xs btn-primary buttons">添加资料</a>
                    </div>
                    <div class="widget-content" style="min-height: 452px;">
                        <div id="div_list" style="overflow-x:auto;"></div>
                        <div id="div_page_btn"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.exedit-3.5.min.js"></script>
    <script src="${ctx}/js/module/baseInfo/baseInfo.js?v=20161028002"></script>
    <script type="text/javascript">
        var zTreeNodes = ${zTreeNodes};
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                }
            },
            view: {
                showIcon: true,
                selectedMulti: false,
                /*nameIsHTML: true,*/
                addHoverDom: WEBGIS.baseInfo.addHoverDom,
                removeHoverDom: WEBGIS.baseInfo.removeHoverDom
            },
            edit:{
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: WEBGIS.baseInfo.showRemoveBtn,
                showRenameBtn : WEBGIS.baseInfo.showRenameBtn,
                removeTitle : "删除节点",
                renameTitle : "修改节点",
                drag: {
                    isCopy: false,
                    isMove: false
                }
            },
            callback: {
                onClick: WEBGIS.baseInfo.zTreeOnClick,
                beforeRemove: WEBGIS.baseInfo.beforeRemove,
                beforeRename: WEBGIS.baseInfo.beforeRename,
                onRemove: WEBGIS.baseInfo.onRemove,
                onRename: WEBGIS.baseInfo.onRename
            }
        };
        WEBGIS.baseInfo.init();
    </script>
</body>
</html>