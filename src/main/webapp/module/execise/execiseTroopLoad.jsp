<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width: 4%;text-align: center;">序号</th>
            <th style="width: 10%;text-align: center;">图标</th>
            <th style="width: 10%;text-align: center;">图标名称</th>
            <th style="width: 10%;text-align: center;">图标类型</th>
            <th style="width: 10%;text-align: center;">速度</th>
            <th style="width: 10%;text-align: center;">最大速度</th>
            <th style="width: 10%;text-align: center;">速度单位</th>
            <th style="width: 10%;text-align: center;">所属机场</th>
            <th style="width: 10%;text-align: center;">所属单位</th>
            <th style="width: 6%;text-align: center;">操作</th>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>
                    <img src="${visitpath}${item.iconData}" width="32px" height="32px">
                </td>
                <td>${item.iconName}</td>
                <td>${item.typeName}</td>
                <td>${item.speed}</td>
                <td>${item.maxSpeed}</td>
                <td style="text-align: center;">${item.speedUnit}</td>
                <td style="text-align: center;">${item.belongAirportName}</td>
                <td style="text-align: center;">${item.unitName}</td>
                <td style="text-align: center;">
                    <a href="javascript:;" data-id="${item.id}">删除</a>
                    <c:if test="${item.belongAirport == null}">
                        <a href="javascript:;" data-id="${item.id}">信息维护</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>