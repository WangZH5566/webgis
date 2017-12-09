<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content" style="height:315px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th style="width: 20%;"></th>
                                <th style="width: 80%;">集群名称</th>
                            </tr>
                        </thead>
                        <tbody id="tbody_crowd_list">
                        <c:forEach items="${crowdDtos}" var="item">
                            <tr data-id="${item.id}">
                                <td style="text-align: center;">
                                    <input type="checkbox" data-id="${item.id}">
                                </td>
                                <td style="text-align: center;">${item.crowdName}</td>
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