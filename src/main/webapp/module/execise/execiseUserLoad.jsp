<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
            <tr>
                <th style="width: 18%;">所属单位</th>
                <th style="width: 18%;">所属台位</th>
                <th style="width: 18%;">登录名</th>
                <th style="width: 18%;">用户名</th>
                <th style="width: 10%;">密码</th>
                <th style="width: 10%;">能否跨单位发送电文</th>
                <th style="width: 8%;">操作</th>
            </tr>
        </thead>
        <tbody id="tbody_list">
            <c:forEach var="item" items="${dtos}">
                <tr>
                    <td>${item.unitName}</td>
                    <td>${item.departmentName}</td>
                    <td>${item.loginName}</td>
                    <td>${item.userName}</td>
                    <td>${item.oldPassword}</td>
                    <td style="text-align: center;">
                        <input type="checkbox" <c:if test="${item.isCrossUnit eq 1}">checked</c:if>>
                    </td>
                    <td style="text-align: center;">
                        <a href="javascript:;" data-id="${item.id}">修改</a><%--&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>--%>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>