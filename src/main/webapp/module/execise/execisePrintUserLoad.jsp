<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
            <tr>
                <th style="width: 200px;text-align: center;">所属单位</th>
                <th style="width: 200px;text-align: center;">所属台位</th>
                <th style="width: 130px;text-align: center;">登录名</th>
                <th style="width: 130px;text-align: center;">用户名</th>
                <th style="width: 100px;text-align: center;">密码</th>
                <th style="width: 90px;text-align: center;">能否跨单位发送电文</th>
            </tr>
        </thead>
        <tbody id="tbody_list">
            <c:forEach var="item" items="${dtos}">
                <tr style="height: 30px;">
                    <td style="text-align: center;">${item.unitName}</td>
                    <td style="text-align: center;">${item.departmentName}</td>
                    <td style="text-align: center;">${item.loginName}</td>
                    <td style="text-align: center;">${item.userName}</td>
                    <td style="text-align: center;">${item.oldPassword}</td>
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