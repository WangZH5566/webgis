<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content" style="height:275px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 6%;">
                                <input type="checkbox" id="chk_add_user">
                            </th>
                            <th style="width: 16%;">所属单位</th>
                            <th style="width: 16%;">所属台位</th>
                            <th style="width: 16%;">登录名</th>
                            <th style="width: 16%;">用户名</th>
                            <th style="width: 10%;">密码</th>
                            <th style="width: 20%;">能否跨单位发送电文</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_add_user_list">
                        <c:forEach var="item" items="${dtos}">
                            <tr>
                                <td style="text-align: center;">
                                    <input type="checkbox" data-un="${item.unitId}" data-de="${item.departmentId}" data-cur="${item.curDate}" data-ser="${item.serialNo}">
                                </td>
                                <td>${item.unitName}</td>
                                <td>${item.departmentName}</td>
                                <td>
                                    <input type="text" value="${item.loginName}" style="width: 100%;">
                                </td>
                                <td>
                                    <input type="text" value="${item.userName}" style="width: 100%;">
                                </td>
                                <td>
                                    <input type="text" value="${item.oldPassword}" style="width: 100%;">
                                </td>
                                <td style="text-align: center;">
                                    <input type="checkbox" <c:if test="${item.isCrossUnit eq 1}">checked</c:if>>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>