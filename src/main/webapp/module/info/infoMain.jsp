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
    <input id="txt_fileVisitPath" type="hidden" value="${fileVisitPath}"/>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>资料列表</h5><a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_root_node">新增根节点</a>
                    </div>
                    <div class="widget-content" id="div_zTree" style="height: 550px;overflow-y: scroll;">
                        <ul id="ztree" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>资料内容</h5><a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_upload_file">上传文件</a>
                    </div>
                    <div class="widget-content" style="height: 550px;overflow-y: scroll;">
                        <div id="div_file_detail">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="div_upload_form" class="container-fluid" style="display: none;">
        <div class="row">
            <div class="col-lg-12" style="float: left;">
                <form id="form_upload_file" class="form-horizontal" action="#" method="post" novalidate="novalidate">
                    <input type="hidden" id="txt_upload_directory" name="pid" value="0" />
                    <input type="hidden" name="isDirectory" value="false" />
                    <div class="form-group">
                        <label class="col-sm-3 control-label">名称:</label>
                        <div class="col-sm-7">
                            <input type="text" id="txt_name" name="name" placeholder="请输入文件名称" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">文件:</label>
                        <div class="col-sm-7">
                            <input type="file" id="f_file" name="file" placeholder="请选择文档" style="border:none;box-shadow:none;" class="form-control"/>
                        </div>
                    </div>
                    <div style="text-align: center;margin-top:50px;">
                        <input type="button" id="btn_upload_save" value="保存" class="btn btn-sm btn-success" />
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/layer-v2.2/layer/extend/layer.ext.js" ></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.exedit-3.5.min.js"></script>
    <script src="${ctx}/js/jquery/jquery.form-3.51.0.js"></script>
    <script src="${ctx}/js/module/info/info.js?v=20161117001"></script>
    <script type="text/javascript">
//        var zTreeNodes = [{id:1,pId:0,name:"资料目录1",open:true},{id:2,pId:0,name:"资料目录2",open:true},{id:3,pId:0,name:"资料目录3",open:true},
//            {id:11,pId:1,name:"资料1-1",open:true},{id:12,pId:1,name:"资料1-2",open:true},{id:13,pId:1,name:"资料1-3",open:true},
//            {id:21,pId:2,name:"资料2-1",open:true},{id:22,pId:2,name:"资料2-2",open:true},{id:23,pId:2,name:"资料2-3",open:true},
//            {id:31,pId:3,name:"资料3-1",open:true},{id:32,pId:3,name:"资料3-2",open:true},{id:33,pId:3,name:"资料3-3",open:true}];

        var setting={
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: 0
                },
                key:{
                    name:"name",
                    title:"name"
                },
                keep:{
                    parent:true
                }
            },
            view: {
                showIcon: true,
                selectedMulti: false,
//                nameIsHTML: true,
                addHoverDom: WEBGIS.info.addHoverDom,
                removeHoverDom: WEBGIS.info.removeHoverDom
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
                beforeRemove: WEBGIS.info.beforeRemove,
                beforeRename: WEBGIS.info.beforeRename,
                beforeDrag: WEBGIS.info.beforeDrag,
                beforeDrop: WEBGIS.info.beforeDrop,
                onClick: WEBGIS.info.zTreeOnClick
            }
        };
        $(document).ready(function(){
            WEBGIS.info.init();
        });
    </script>
</body>
</html>