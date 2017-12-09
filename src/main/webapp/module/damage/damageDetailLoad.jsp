<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<!DOCTYPE html>
<html>
<body>
    <table class="table table-bordered" id="table_list_detail">
        <thead>
        <tr>
            <th style="width: 12%;">序号</th>
            <th style="width: 68%;">受损程度详情</th>
            <th style="width: 20%;">操作</th>
        </tr>
        </thead>
        <tbody id="tbody_list_detail">
        <c:forEach var="item" items="${dtos}" varStatus="st">
            <tr id="${item.id}">
                <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                <td>${item.damageContent}</td>
                <td style="text-align: center;">
                    <a href="javascript:;" data-id="${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>