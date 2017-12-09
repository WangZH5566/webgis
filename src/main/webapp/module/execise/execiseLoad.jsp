<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width: 4%;text-align: center;"><input type="checkbox" id="check_all"></th>
            <th style="width: 8%;text-align: center;">推演序号</th>
            <th style="width: 18%;text-align: center;">推演名称</th>
            <th style="width: 12%;text-align: center;">创建时间</th>
            <th style="width: 12%;text-align: center;">开始时间</th>
            <th style="width: 12%;text-align: center;">结束时间</th>
            <th style="width: 10%;text-align: center;">海图类型</th>
            <th style="width: 10%;text-align: center;">推演状态</th>
            <%--<th style="width: 10%;text-align: center;">推演时长</th>--%>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}" data-sta="${item.execStatus}">
                <td style="text-align: center;"><input type="checkbox" data-id="${item.id}" data-sta="${item.execStatus}"></td>
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>${item.execiseName}</td>
                <td>${item.createTimeView}</td>
                <td>${item.beginTimeView}</td>
                <td>${item.endTimeView}</td>
                <td>${item.seaChartName}</td>
                <td style="text-align: center;">${item.execStatusView}</td>
                <%--<td style="text-align: center;">${item.execTime}</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>