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
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>第二步:创建台位</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_next">下一步</a>
                        <a href="${ctx}/exec/addStepOne?id=${exec_id}" class="btn btn-xs btn-primary buttons">上一步</a>
                    </div>
                    <div class="widget-content" id="div_list">
                        <div id="div_zTree" style="height:550px;overflow: auto;">
                            <ul id="ztree" class="ztree" style="margin-left: 20px;"></ul>
                        </div>
                    </div>
                    <input type="hidden" id="txt_id" value="${exec_id}">
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>选择台位</h5>
                    </div>
                    <div class="widget-content no-padding" style="height:216px;overflow-y: auto;">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th style="width: 10%;">
                                    <input type="checkbox" id="chk_de">
                                </th>
                                <th style="width: 20%;">台位名称</th>
                                <th style="width: 20%;">台位代码</th>
                                <th style="width: 50%;">能否跨单位发送电文</th>
                            </tr>
                            </thead>
                            <tbody id="tbody_de_list">
                            <c:forEach var="item" items="${allDeDtos}">
                                <c:if test="${not empty edMap[item.id]}">
                                    <c:set var="de" value="${edMap[item.id]}"></c:set>
                                    <tr>
                                        <td style="text-align: center;">
                                            <input type="checkbox" data-id="${item.id}">
                                        </td>
                                        <td>${de.departmentName}</td>
                                        <td>${de.departmentCode}</td>
                                        <td style="text-align: center;">
                                            <c:if test="${de.isCrossUnit eq 1}">是</c:if>
                                            <c:if test="${de.isCrossUnit eq 0}">否</c:if>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${empty edMap[item.id]}">
                                    <tr>
                                        <td style="text-align: center;">
                                            <input type="checkbox" data-id="${item.id}">
                                        </td>
                                        <td>${item.departmentName}</td>
                                        <td>${item.departmentCode}</td>
                                        <td style="text-align: center;">
                                            <c:if test="${item.isCrossUnit eq 1}">是</c:if>
                                            <c:if test="${item.isCrossUnit eq 0}">否</c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028002"></script>
    <script type="text/javascript">
        var zTreeNodes = ${zTreeNodes};
        var unitDepartment = ${unitDepartment};
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
                selectedMulti: false
            },
            callback: {
                onClick: WEBGIS.execStepTwo.zTreeOnClick
            }
        };

        WEBGIS.execStepTwo.init();
    </script>
</body>
</html>