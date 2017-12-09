<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content" style="height:250px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 20%;"></th>
                            <th style="width: 80%;">集群名称</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_langding_crowd">
                        <c:forEach items="${crowdDtos}" var="item">
                            <tr>
                                <td style="text-align: center;">
                                    <input type="radio" name="rad_langing" data-crowdId="${item.crowdId}" data-iconId="${item.iconId}">
                                </td>
                                <td style="text-align: center;">${item.crowdName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="widget-content" style="height:250px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 20%;"></th>
                            <th style="width: 80%;">机场名称</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_airport_list">
                        <c:forEach items="${airportDtos}" var="item">
                            <tr>
                                <td style="text-align: center;">
                                    <input type="radio" name="rad_airport" data-id="${item.id}" data-co="${item.newestCoordinate}">
                                </td>
                                <td style="text-align: center;">${item.iconName}</td>
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