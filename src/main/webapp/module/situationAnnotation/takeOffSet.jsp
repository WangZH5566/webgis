<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <form class="form-horizontal" action="#" novalidate="novalidate">
            <div class="form-group" style="margin-left: 0;margin-right: 0;margin-bottom: 5px;">
                <label class="col-sm-2 control-label">批号:</label>
                <div class="col-sm-9">
                    <input type="text" value="" class="form-control" id="txt_crowd_name"/>
                </div>
            </div>
            <div class="form-group" style="margin-left: 0;margin-right: 0;margin-bottom: 5px;">
                <label class="col-sm-2 control-label">航速:</label>
                <div class="col-sm-7">
                    <input type="text" value="" class="form-control" id="txt_crowd_speed"/>
                </div>
                <label class="col-sm-2 control-label form-label2" style="text-align: left;">公里/时</label>
            </div>
            <div class="form-group" style="margin-left: 0;margin-right: 0;margin-bottom: 5px;">
                <label class="col-sm-2 control-label">航向:</label>
                <div class="col-sm-9">
                    <input type="text" value="" class="form-control" id="txt_crowd_angle"/>
                </div>
            </div>
        </form>
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-title">
                    <ul class="nav nav-tabs" id="ul_tab">
                        <li class="active" data-type="0"><a href="javascript:;">未起飞列表</a></li>
                        <li class="" data-type="1"><a href="javascript:;">已起飞列表</a></li>
                    </ul>
                </div>
                <div class="widget-content" style="height:245px;overflow: auto;">
                    <div id="div_0" name="div_show_for_tab">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th style="width:10%;"></th>
                                <th style="width:30%;">飞机编号</th>
                                <th style="width:30%;">所属批号</th>
                                <th style="width:30%;">长机</th>
                            </tr>
                            </thead>
                            <tbody id="tbody_plane_crowd">
                            <c:forEach items="${unTakeOffDtos}" var="item">
                                <tr>
                                    <td style="text-align: center;">
                                        <input type="checkbox" data-trid="${item.iconId}">
                                    </td>
                                    <td style="text-align: center;">${item.iconName}</td>
                                    <td style="text-align: center;">${item.crowdName}</td>
                                    <td style="text-align: center;">
                                        <input type="checkbox" data-trid="${item.iconId}">
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div id="div_1" name="div_show_for_tab" style="display: none;">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th style="width:35%;">飞机编号</th>
                                <th style="width:35%;">所属批号</th>
                                <th style="width:30%;">长机</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${takeOffDtos}" var="item">
                                <tr>
                                    <td style="text-align: center;">${item.iconName}</td>
                                    <td style="text-align: center;">${item.crowdName}</td>
                                    <td style="text-align: center;">
                                        <c:if test="${item.isMain eq 1}">是</c:if>
                                        <c:if test="${item.isMain ne 1}">否</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>