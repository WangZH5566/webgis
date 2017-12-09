<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<table class="table table-bordered" id="table_list">
    <thead>
    <tr>
        <th style="width: 10%;text-align: center;"><input type="checkbox" id="check_all"></th>
        <th style="width: 18%;text-align: center;">序号</th>
        <th style="width: 78%;text-align: center;">名称</th>
    </tr>
    </thead>
    <tbody id="tbody_list">
    <c:forEach var="item" items="${dtos}" varStatus="st">
        <tr id="${item.id}" url="${item.url}" layer="${item.layer}" bound="${item.bound}" num="${item.layerNum}" resolution="${item.resolution}">
            <td style="text-align: center;"><input type="checkbox" value="${item.id}" name="id"></td>
            <td>${item.id}</td>
            <td>${item.seaChartName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>