<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <div class="row" style="width:95%;margin:20px auto;">
        <div class="col-md-12">
            <div id="div_list" style="height: 315px;overflow-x: auto;">
                <table class="table table-bordered" id="table_list">
                    <thead>
                    <tr>
                        <th style="width: 25%;text-align: center;">备份时间</th>
                        <th style="width: 50%;text-align: center;">备注</th>
                        <th style="width: 25%;text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody_list">
                    <c:forEach var="item" items="${list}" varStatus="st">
                        <tr id="list_tr_${item.id}" backup-id="${item.id}">
                            <td>${item.createTimeView}</td>
                            <td>${item.comment}</td>
                            <td style="text-align: center;"><a href="${ctx}/userIcon/main?id=${item.id}" class="btn btn-xs btn-primary buttons">查看</a>
                                <a href="javascript:void(0);" onclick="WEBGIS.execUserMap.deleteBackup(${item.id})" class="btn btn-xs btn-primary buttons">删除</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="div_page_btn"></div>
        </div>
    </div>

</body>
</html>