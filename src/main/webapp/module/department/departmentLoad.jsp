<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width: 4%;">序号</th>
            <th style="width: 50%;">台位名称</th>
            <th style="width: 26%;">台位代码</th>
            <th style="width: 20%;">能否跨单位发送电文</th>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>${item.departmentName}</td>
                <td>${item.departmentCode}</td>
                <td style="text-align: center;">
                    <c:if test="${item.isCrossUnit eq 1}">是</c:if>
                    <c:if test="${item.isCrossUnit eq 0}">否</c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>