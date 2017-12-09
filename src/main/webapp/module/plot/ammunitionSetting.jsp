<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<table <c:if test="${empty isPl}">id="table_ammunition_list"</c:if> class="table table-bordered">
    <thead>
        <tr>
            <th style="width:40%;">雷弹型号</th>
            <th style="width:20%;">库存</th>
            <th style="width:40%;text-align: center;">数量</th>
        </tr>
    </thead>
    <tbody <c:if test="${not empty isPl}">id="tbody_plane_ammu_list"</c:if> data-biid="${biId}">
    <c:forEach items="${list}" var="item">
        <tr id="tr_ammu_${item.ammunitionId}">
            <td title="${item.ammunitionName}">${item.ammunitionName}</td>
            <td title="${item.ammunitionCount}">${item.ammunitionCount}</td>
            <td style="text-align: center;padding:4px;"><input type="text" min="0" max="${item.ammunitionCount}" data-id="${item.ammunitionId}" data-name="${item.ammunitionName}" maxlength="9" class="spinnerInput" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
