<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width:40%;text-align: center;">文电名称</th>
            <th style="width:30%;text-align: center;">创建时间</th>
            <th style="width:20%;text-align: center;">状态</th>
            <th style="width:10%;text-align: center;">操作</th>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}" data-thtmlpath="${item.thtmlpath}" data-sts="${item.tstatus}">
                <td>${item.tname}</td>
                <td>${item.createTimeView}</td>
                <td>${item.tstatusView}</td>
                <td style="text-align: center;">
                    <a href="javascript:;" data-id="${item.id}">发送</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>