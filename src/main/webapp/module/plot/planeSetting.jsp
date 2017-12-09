<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<table class="table table-bordered">
    <thead>
    <tr>
        <th style="width: 25%;">
            <%--<input type="checkbox" id="chk_plane_all">--%>
        </th>
        <th style="width: 75%;">飞机编号</th>
    </tr>
    </thead>
    <tbody id="tbody_plane_list">
    <c:forEach var="item" items="${baseInfoList}" varStatus="st">
        <tr data-bi="${item.id}" data-mt="${item.mainType}" data-ms="${item.maxSpeed}">
            <td><input type="checkbox" data-bi="${item.id}"></td>
            <td>${item.infoCode}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>