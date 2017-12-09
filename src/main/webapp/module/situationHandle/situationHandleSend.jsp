<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box" style="margin-bottom: 0;">
                    <div class="widget-title">
                        <h5>发送文电</h5>
                        <a href="${ctx}/sh/main" class="btn btn-xs btn-primary buttons" id="btn_return">返回</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_send">发送</a>
                    </div>
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="form_search">
                            <c:if test="${not empty ttid}">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label form-label2" style="padding-left: 0;">选择文电:</label>
                                        <div class="col-sm-10 form-div2">
                                            <input type="text" placeholder="" class="form-control" id="txt_tt_name" value="${tele.tname}" disabled/>
                                            <input type="hidden" id="txt_tt_id" value="${ttid}">
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${empty ttid}">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label form-label2" style="padding-left: 0;">选择文电:</label>
                                    <div class="col-sm-10 form-div2">
                                        <select id="sel_tt" class="form-control">
                                            <option value=""></option>
                                            <c:forEach items="${teleList}" var="item">
                                                <option value="${item.id}">${item.tname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            </c:if>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label form-label2">主送人员:</label>
                                    <div class="col-sm-10 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_send_to_name" disabled />
                                        <input type="hidden" id="txt_send_to" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label form-label2" style="padding-left: 0;">抄送人员:</label>
                                    <div class="col-sm-10 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_copy_to_name" disabled/>
                                        <input type="hidden" id="txt_copy_to" disabled/>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>主送人员</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_left_clear_all">全清</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_left_chk_all">全选</a>
                    </div>
                    <div class="widget-content" id="div_tree_left">
                        <div id="div_zTree_left" style="height:450px;overflow-y: auto;">
                            <ul id="ztree_left" class="ztree" style="margin-left: 20px;"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>抄送人员</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_right_clear_all">全清</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_right_chk_all">全选</a>
                    </div>
                    <div class="widget-content" id="div_list_right">
                        <div id="div_zTree_right" style="height:450px;overflow-y: auto;">
                            <ul id="ztree_right" class="ztree" style="margin-left: 20px;"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.excheck-3.5.min.js"></script>
    <script src="${ctx}/js/module/situationHandle/situationHandle.js?v=20161028002"></script>
    <script type="text/javascript">
        var zTreeNodes = ${zTreeNodes};
        var setting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "ps", "N": "ps" },
                chkDisabledInherit: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onCheck: WEBGIS.shSend.zTreeOnCheck
            },
            view: {
                selectedMulti: false,
                nameIsHTML: true
            }
        };
        WEBGIS.shSend.init();
    </script>
</body>
</html>