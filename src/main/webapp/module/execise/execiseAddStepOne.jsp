<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/css/jquery_zTree/diybtn.css" rel="stylesheet">
<link href="${ctx}/js/jquery.spinner/jquery.spinner.css" rel="stylesheet">
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>新增推演(第一步)</h5>
                        <a href="${ctx}/exec/main" class="btn btn-xs btn-primary buttons">返回</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_next">下一步</a>
                    </div>
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="javascript:void(0);" method="post" novalidate="novalidate" id="form_search" style="height:300px;">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">推演名称:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_exec_name" value="${dto.execiseName}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">作战时间:</label>
                                    <div class="col-sm-5 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_fight_time" value="${dto.fightTimeView}" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
                                    </div>
                                    <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">年份隐藏位数:</label>
                                    <div class="col-sm-2 form-div2" style="margin-top: 4px;" >
                                        <input type="text" min="0" max="4" maxlength="1" class="spinnerInput" value="${dto.ftHideDigit}" id="txt_fthd"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">导演登录名:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_login_name" value="${dto.directorLoginName}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">导演用户名:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_user_name" value="${dto.directorUserName}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">导演密码:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_password" value="${dto.directorPassword}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">备注:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_comment" value="${dto.comment}" />
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                            <input type="hidden" id="txt_id" value="${dto.id}">
                            <input type="hidden" id="txt_director_id" value="${dto.directorId}">
                            <input type="hidden" id="txt_cur_date" value="${dto.curDate}">
                            <input type="hidden" id="txt_serial_no" value="${dto.serialNo}">
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>新增单位</h5>
                    </div>
                    <div class="widget-content" id="div_list">
                        <div id="div_zTree" style="height:550px;overflow: auto;">
                            <ul id="ztree" class="ztree" style="margin-left: 20px;"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.exedit-3.5.min.js"></script>
    <script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
    <script src="${ctx}/js/jquery.spinner/jquery.spinner.js?v=2016102813"></script>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028002"></script>
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
                beforeRemove: beforeRemove,
                beforeRename: beforeRename,
                beforeDrag: beforeDrag,
                beforeDrop: beforeDrop
            }
        };

        function showRemoveBtn(treeId, treeNode) {
            //return !treeNode.isFirstNode;
            var treeNodes = WEBGIS.execStepOne.treeObj.getNodes();
            var treeNodes_array = WEBGIS.execStepOne.treeObj.transformToArray(treeNodes);
            return treeNode.id != treeNodes_array[0].id;
        }

        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#diyBtnAdd_"+treeNode.id).length == 0){
                var addStr = "<span class=\"button diyBtnAdd\" id=\"diyBtnAdd_"+treeNode.id+"\" title=\"新增节点\" onfocus=\"this.blur();\"></span>";
                aObj.append(addStr);
                var btnAdd = $("#diyBtnAdd_"+treeNode.id);
                if (btnAdd) btnAdd.bind("click", function(){
                    var zTree = $.fn.zTree.getZTreeObj("ztree");
                    zTree.addNodes(treeNode, {id:"new_"+(100 + newCount), pId:treeNode.id, name:"单位" + (newCount++)});
                    return false;
                });
            }
            /*if ($("#diyBtnEdit_"+treeNode.id).length == 0){
                var editStr = "<span class=\"button diyBtnEdit\" id=\"diyBtnEdit_"+treeNode.id+"\" title=\"修改节点\" onfocus=\"this.blur();\"></span>";
                aObj.append(editStr);
                var btnEdit = $("#diyBtnEdit_"+treeNode.id);
                if (btnEdit) btnEdit.bind("click", function(){

                });
            }
            if ($("#diyBtnDelete_"+treeNode.id).length == 0){
                var deleteStr = "<span class=\"button diyBtnDelete\" id=\"diyBtnDelete_"+treeNode.id+"\" title=\"删除节点\" onfocus=\"this.blur();\"></span>";
                aObj.append(deleteStr);
                var btnDelete = $("#diyBtnDelete_"+treeNode.id);
                if (btnDelete) btnDelete.bind("click", function(){

                });
            }*/
        }
        function removeHoverDom(treeId, treeNode) {
            $("#diyBtnAdd_"+treeNode.id).unbind().remove();
        }

        function beforeRemove(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            zTree.selectNode(treeNode);
            return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
        }

        function beforeRename(treeId, treeNode, newName, isCancel) {
            if (newName.length == 0) {
                setTimeout(function() {
                    var zTree = $.fn.zTree.getZTreeObj("ztree");
                    zTree.cancelEditName();
                    alert("节点名称不能为空.");
                }, 0);
                return false;
            }
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

        WEBGIS.execStepOne.init();
    </script>
</body>
</html>