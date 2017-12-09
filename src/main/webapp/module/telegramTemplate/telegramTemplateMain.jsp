<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/css/jquery_zTree/diybtn.css" rel="stylesheet">
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}
    .a-upload {
        position: relative;
        overflow: hidden;
        *display: inline;
        *zoom: 1
    }
    .a-upload  input {
        position: absolute;
        line-height: 26px;
        font-size: 10px;
        right: 0;
        top: 0;
        filter:alpha(opacity=0);
        -moz-opacity:0;
        -khtml-opacity:0;
        opacity:0;
        cursor: pointer
    }
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>模板列表</h5>
                    </div>
                    <div class="widget-content" id="div_zTree" style="height: 550px;overflow-y: scroll;">
                        <ul id="ztree" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>模板内容</h5>
                        <%--<a href="javascript:;" id="btn_save" class="btn btn-xs btn-primary buttons">保存模板</a>--%>
                        <form style="float:right;margin: 0;" action="${ctx}/tt/upload.do" id="uploadForm" enctype="multipart/form-data" method="post" target="target_upload">
                            <a href="javascript:;" class="btn btn-xs btn-primary buttons a-upload">
                                <input type="file" name="btn_upLoad" id="btn_upLoad" value="" onchange="WEBGIS.tt.btnUpload(this)" >上传模板
                            </a>
                            <input type="hidden" name="ttId" id="ttId" value="" />
                        </form>
                        <iframe id="target_upload" name="target_upload" src="" style="display:none"></iframe>
                    </div>
                    <div class="widget-content">
                        <iframe id="f_cont" src="" width="100%" height="510px" seamless></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.exedit-3.5.min.js"></script>
    <script src="${ctx}/js/global/progressBar.js" type="text/javascript"></script>
    <script src="${ctx}/js/module/telegramTemplate/telegramTemplate.js?v=20161028001"></script>
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
                nameIsHTML: true,
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom
            },
            edit:{
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: showRemoveBtn,
                showRenameBtn : true,
                removeTitle : "删除节点",
                renameTitle : "修改节点"
            },
            callback: {
                onClick: WEBGIS.tt.zTreeOnClick,
                beforeRemove: beforeRemove,
                beforeRename: beforeRename,
                beforeDrag: beforeDrag,
                beforeDrop: beforeDrop,
                onDrop: zTreeOnDrop,
                onRemove: zTreeOnRemove
            }
        };

        function showRemoveBtn(treeId, treeNode) {
            return treeNode.id != 1;
        }

        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#diyBtnAdd_"+treeNode.id).length == 0){
                var addStr = "<span class=\"button diyBtnAdd\" id=\"diyBtnAdd_"+treeNode.id+"\" title=\"新增节点\" onfocus=\"this.blur();\"></span>";
                aObj.append(addStr);
                var btnAdd = $("#diyBtnAdd_"+treeNode.id);
                if (btnAdd) btnAdd.bind("click",function(){
                    addZtreeNode(treeNode);
                });
            }
        }

        function addZtreeNode(treeNode){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            $.ajax({
                type:"POST",
                url:ctx+"/tt/saveTt",
                data:{"pid":treeNode.id,"name":"新增模板"},
                datatype:"json",
                cache:false,
                success:function(r){
                    layer.close(index);
                    if(r != null && r.msg == "success"){
                        zTree.addNodes(treeNode,{id: r.id,pId: r.pid,name: r.tname});
                    }else{
                        layer.msg(r.msg,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.close(index);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
            return false;
        }

        function removeHoverDom(treeId, treeNode) {
            $("#diyBtnAdd_"+treeNode.id).unbind().remove();
        }

        function beforeRemove(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            zTree.selectNode(treeNode);
            var flag = false;
            return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
        }

        function zTreeOnRemove(event, treeId, treeNode) {
            var idArray = new Array();
            idArray.push(treeNode.id);
            var children = treeNode.children;
            buildChildrenID(idArray,children);

            $.post(ctx + "/tt/deleteTt",{"ids":idArray==null?"":idArray.join(",")},function(r){
                if(r == "success"){
                    layer.msg("操作成功",{icon: 1,time:1000});
                }else{
                    layer.msg("success",{icon: 2,time:1000});
                }
            });
        }

        function buildChildrenID(idArray,children){
            if(children==null||children.length<=0){
                return;
            }
            for(var i=0;i<children.length;i++){
                var node=children[i];
                idArray.push(node.id);
                var tmpChildren = node.children;
                buildChildrenID(idArray,tmpChildren);
            }
        }

        function beforeRename(treeId, treeNode, newName, isCancel) {
            if (newName.length == 0) {
                setTimeout(function() {
                    var zTree = $.fn.zTree.getZTreeObj("ztree");
                    zTree.cancelEditName();
                    layer.msg("节点名称不能为空！",{icon: 2,time: 1000});
                }, 0);
                return false;
            }
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"POST",
                url:ctx+"/tt/modifyTt",
                data:{"id":treeNode.id,"name":newName},
                cache:false,
                success:function(r){
                    layer.close(index);
                    if(r != null && r == "success"){
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.close(index);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
            return true;
        }

        function beforeDrag(treeId, treeNodes) {
            for (var i=0,l=treeNodes.length; i<l; i++) {
                if (treeNodes[i].drag === false) {
                    return false;
                }
            }
            return true;
        }
        function beforeDrop(treeId, treeNodes, targetNode, moveType) {
            return targetNode ? targetNode.drop !== false : true;
        }

        function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"POST",
                url:ctx+"/tt/modifyTt",
                data:{"id":treeNodes[0].id,"pid":treeNodes[0].pId},
                cache:false,
                success:function(r){
                    layer.close(index);
                    if(r != null && r == "success"){
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.close(index);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }

        WEBGIS.tt.init();
    </script>
</body>
</html>