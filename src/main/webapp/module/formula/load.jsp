<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width: 4%;">序号</th>
            <th style="width: 30%;">公式名称</th>
            <th style="width: 66%;">公式描述</th>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>${item.name}</td>
                <td>${item.remark}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>