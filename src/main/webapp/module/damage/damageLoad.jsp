<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<!DOCTYPE html>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
        <tr>
            <th style="width: 12%;">序号</th>
            <th style="width: 38%;">受损程度</th>
            <%--<th style="width: 20%;">图标</th>--%>
            <th style="width: 30%;">操作</th>
        </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>${item.damageName}</td>
                <%--<td style="text-align: center;"><img src="${fiv}${item.imgPath}"> </td>--%>
                <td style="text-align: center;">
                    <a href="javascript:;" data-id="${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<%--<a href="javascript:;" data-id="${item.id}">修改图标</a>&nbsp;&nbsp;&nbsp;&nbsp;--%><a href="javascript:;" data-id="${item.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>