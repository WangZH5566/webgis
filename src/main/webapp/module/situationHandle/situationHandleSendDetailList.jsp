<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_detail_list">
        <thead>
        <tr>
            <th style="width:30%;text-align: center;">接收人</th>
            <th style="width:50%;text-align: center;">回执</th>
            <th style="width:20%;text-align: center;">已读/未读</th>
        </tr>
        </thead>
        <tbody id="tbody_detail_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td>${item.receiveByName}</td>
                <td style="text-align: center;"><c:if test="${item.isReceiptView=='否'}">否</c:if><c:if test="${item.isReceiptView!='否'}">${item.receiptMsg}</c:if></td>
                <td style="text-align: center;">${item.isReadView}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>