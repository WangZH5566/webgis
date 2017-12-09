<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <form class="form-horizontal" action="#" novalidate="novalidate">
            <div class="form-group" style="margin-left: 0;margin-right: 0;margin-bottom: 0;">
                <label class="col-sm-3 control-label">集群名称:</label>
                <div class="col-sm-8">
                    <input type="text" value="" class="form-control" id="txt_crowd_name"/>
                </div>
            </div>
        </form>
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content" style="height:315px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width:10%;"></th>
                            <th style="width:25%;">图标名称</th>
                            <th style="width:25%;">所属集群</th>
                            <th style="width:20%;">旗舰/长机</th>
                            <th style="width:20%;">操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_crowd_detail_list">
                        <c:forEach items="${iconDtos}" var="item">
                            <tr>
                                <td style="text-align: center;">
                                    <input type="checkbox" data-trid="${item.iconId}" data-na="${item.iconName}" <c:if test="${not empty item.crowdName}">disabled="disabled"</c:if>>
                                </td>
                                <td style="text-align: center;">${item.iconName}</td>
                                <td style="text-align: center;">${item.crowdName}</td>
                                <td style="text-align: center;">
                                    <c:if test="${not empty item.crowdName}">
                                        <c:if test="${item.isMain eq 1}">是</c:if>
                                        <c:if test="${item.isMain ne 1}">否</c:if>
                                    </c:if>
                                    <c:if test="${empty item.crowdName}">
                                        <input type="checkbox" data-trid="${item.iconId}">
                                    </c:if>
                                </td>
                                <td style="text-align: center;">
                                    <c:if test="${empty item.belongAirport and item.isMain eq 1}">
                                        <a href="javascript:;" data-id="${item.crowdId}">删除集群</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>