<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width:40%;text-align: center;">发送人</th>
            <th style="width:30%;text-align: center;">文电名称</th>
            <th style="width:30%;text-align: center;">发送时间</th>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}" data-thtmlpath="${item.thtmlpath}" data-msg="${item.msg}">
                <td>${item.createByName}</td>
                <td>${item.tname}</td>
                <td>${item.receiveTimeView}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>