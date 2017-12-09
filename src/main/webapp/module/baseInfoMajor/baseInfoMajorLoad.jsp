<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<!DOCTYPE html>
<html>
<body>
    <table class="table table-bordered" id="table_list">
        <thead>
            <tr>
                <th style="width: 8%;">序号</th>
                <th style="width: 72%;">专业名称</th>
                <th style="width: 20%;">操作</th>
            </tr>
        </thead>
        <tbody id="tbody_list">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>${item.majorName}</td>
                <td style="text-align: center;">
                    <a href="javascript:;" data-id="${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>