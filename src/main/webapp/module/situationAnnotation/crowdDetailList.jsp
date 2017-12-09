<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>图标名称</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${crowdDetailDtos}" var="item">
        <tr id="${item.iconId}">
            <td style="text-align: center;">${item.iconName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>