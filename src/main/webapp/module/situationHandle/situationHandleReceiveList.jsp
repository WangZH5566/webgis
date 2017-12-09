<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<table class="table table-bordered" id="table_list">
    <thead>
    <tr>
        <th style="width:16%;text-align: center;">文电名称</th>
        <th style="width:10%;text-align: center;">发送人</th>
        <th style="width:20%;text-align: center;">发送时间</th>
        <th style="width:10%;text-align: center;">回执</th>
        <th style="width:13%;text-align: center;">已读/未读</th>
        <th style="width:21%;text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody id="tbody_list">
    <c:forEach var="item" items="${dtos}" varStatus="st">
        <tr id="${item.id}" data-id="${item.telegramId}">
            <td title="${item.telegramName}" class="<c:if test='${item.isReadView==\'未读\'}'>blue</c:if>">${item.telegramName}</td>
            <td title="${item.sendByName}">${item.sendByName}</td>
            <td>${item.receiveTimeView}</td>
            <td style="text-align: center;">${item.isReceiptView}</td>
            <td style="text-align: center;">${item.isReadView}</td>
            <td>
                <a href="${ctx}/sh/teleSend?ttid=${item.telegramId}">转发</a>&nbsp;&nbsp;
                <a href="${tvisitPath}${item.docName}" data-id="${item.id}" target='_blank'>下载</a>&nbsp;&nbsp;
                <c:if test="${item.isReceipt ne 1}">
                    <a href="javascript:;" data-id="${item.id}" onclick="WEBGIS.sh.isReceiptClick(this);">回执</a>&nbsp;&nbsp;
                </c:if>
                <c:if test="${item.isRead ne 1}">
                    <a href="javascript:;" data-id="${item.id}" onclick="WEBGIS.sh.isReadClick(this);">已读
                </c:if>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>