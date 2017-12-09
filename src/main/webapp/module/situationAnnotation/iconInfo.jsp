<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <c:choose>
        <c:when test="${mt eq 0}">
            <form class="form-horizontal" action="#" novalidate="novalidate">
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">名称:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.infoName}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">弦号:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.infoCode}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">隶属单位:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.belongUnit}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">最大航速（节）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.maxSpeed}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">续航能力（海里）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.endurance}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">满载排水量（吨）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.maxDisplacement}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">标准排水量（吨）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.standardDisplacement}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">生产研制单位:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.developmentUnit}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">服役日期:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.serviceDate}" class="form-control"/>
                    </div>
                </div>
            </form>
        </c:when>
        <c:when test="${mt eq 1}">
            <form class="form-horizontal" action="#" novalidate="novalidate">
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">编号:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.infoCode}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">隶属单位:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.belongUnit}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">最大航速（公里/时）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.maxSpeed}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">作战半径（公里）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.fightRadius}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">最大起飞重量（吨）:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.maxTakeOffWeight}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">生产研制单位:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.developmentUnit}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">服役日期:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.serviceDate}" class="form-control"/>
                    </div>
                </div>
            </form>
        </c:when>
        <c:when test="${mt eq 2 or mt eq 3 or mt eq 4}">
            <form class="form-horizontal" action="#" novalidate="novalidate">
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">名称:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.infoName}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">地址:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.address}" class="form-control"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 0;margin-right: 0;">
                    <label class="col-sm-3 control-label">经纬度:</label>
                    <div class="col-sm-8">
                        <input type="text" value="${dto.longitudeAndLatitude}" class="form-control"/>
                    </div>
                </div>
            </form>
        </c:when>
        <c:otherwise></c:otherwise>
    </c:choose>
    <%--<div class="col-sm-3 pull-right">
        <input type="button" id="btn_repair_order_cancel" value="关闭" class="btn btn-sm btn-primary buttons pull-right" />
    </div>--%>
</body>
</html>