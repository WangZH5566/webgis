<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<table id="table_equipment_list" class="table table-bordered">
    <tr>
        <th style="width:50%;">装备名称</th>
        <th style="width:50%;text-align: center;">数量</th>
    </tr>
    <c:forEach items="${list}" var="item">
        <tr id="${item.euqipmentId}">
            <td title="${item.equipmentName}">${item.equipmentName}</td>
            <td style="text-align: center;padding:4px;"><input type="text" min="0" data-id="${item.euqipmentId}" data-name="${item.equipmentName}" class="spinnerInput" /></td>
        </tr>
    </c:forEach>
</table>