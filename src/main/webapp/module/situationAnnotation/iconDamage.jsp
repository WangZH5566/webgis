<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <form class="form-horizontal" action="#" novalidate="novalidate">
            <div class="form-group" style="margin-left: 0;margin-right: 0;margin-bottom: 0;">
                <label class="col-sm-3 control-label">维修总时长(人.小时):</label>
                <div class="col-sm-8">
                    <input type="text" value="${dto.damageTime}" class="form-control" id="txt_dt"/>
                </div>
            </div>
        </form>
        <div class="col-md-6">
            <div class="widget-box">
                <div class="widget-content" style="height:315px;overflow: auto;">
                    <table id="table_damage_list" class="table table-bordered">
                        <tr>
                            <th style="width:20%;"></th>
                            <th style="width:80%;">受损程度</th>
                        </tr>
                        <c:forEach items="${damageDtos}" var="item">
                            <tr id="${item.id}">
                                <td style="text-align: center;">
                                    <input type="radio" name="radio_damage" data-trid="${item.id}" <c:if test="${item.id eq dto.damage}">checked</c:if>>
                                </td>
                                <td style="text-align: center;">${item.damageName}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="widget-box">
                <div class="widget-content" style="height:315px;overflow: auto;">
                    <c:forEach items="${damageDetailMap}" var="item">
                    <table id="table_dd_${item.key}" class="table table-bordered" <c:if test="${item.key ne dto.damage}">style="display: none;"</c:if>>
                        <tr>
                            <th style="width:20%;"></th>
                            <th style="width:80%;">受损内容</th>
                        </tr>
                        <c:forEach items="${item.value}" var="sub">
                            <tr id="${sub.id}">
                                <td style="text-align: center;">
                                    <input type="checkbox" data-trid="${sub.id}" <c:if test="${fn:contains(','.concat(dto.damageDetail).concat(','),','.concat(sub.id).concat(','))}">checked</c:if>>
                                </td>
                                <td>${sub.damageContent}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>