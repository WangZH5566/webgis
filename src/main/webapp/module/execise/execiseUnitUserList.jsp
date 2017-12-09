<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
            <tr>
                <th style="width: 20%;">所属单位</th>
                <th style="width: 20%;">所属台位</th>
                <th style="width: 18%;">登录名</th>
                <th style="width: 18%;">用户名</th>
                <th style="width: 12%;">密码</th>
                <th style="width: 12%;">能否跨单位发送电文</th>
            </tr>
        </thead>
        <tbody id="tbody_list">
            <c:forEach var="item" items="${dtos}">
                <tr>
                    <td>${item.unitName}</td>
                    <td>${item.departmentName}</td>
                    <td>${item.loginName}</td>
                    <td data-id="${item.id}">${item.userName}</td>
                    <td>${item.oldPassword}</td>
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