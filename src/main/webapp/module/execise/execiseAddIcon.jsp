<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
</style>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content" style="height:180px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 10%;"></th>
                            <th style="width: 45%;">名称</th>
                            <th style="width: 45%;">舷号/代号/编号</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_bi_list">
                        <c:forEach var="item" items="${baseInfoList}" varStatus="st">
                            <tr data-bi="${item.id}" data-bin="<c:if test="${not empty item.infoName}">${item.infoName}</c:if><c:if test="${empty item.infoName}">${item.infoCode}</c:if>" data-mt="${item.mainType}" data-ms="${item.maxSpeed}">
                                <td><input type="radio" name="ria_bi" data-bi="${item.id}" data-bin="<c:if test="${not empty item.infoName}">${item.infoName}</c:if><c:if test="${empty item.infoName}">${item.infoCode}</c:if>" data-mt="${item.mainType}" data-ms="${item.maxSpeed}"></td>
                                <td>${item.infoName}</td>
                                <td>${item.infoCode}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="widget-box" id="div_add_icon_attr" style="display: none;">
                <div class="widget-content">
                    <form class="form-horizontal" action="javascript:;" method="post" novalidate="novalidate">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label class="col-sm-2 control-label form-label2">图标名称:</label>
                                <div class="col-sm-9 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_icon_name" value="" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label class="col-sm-2 control-label form-label2">图标:</label>
                                <div class="col-sm-9 form-div2">
                                    <canvas id="img_canvas"></canvas>
                                </div>
                            </div>
                        </div>
                        <div style="clear: both;"></div>
                    </form>
                </div>
            </div>
            <div class="widget-box" style="display: none;">
                <!-- 雷弹 -->
                <div class="widget-content" id="div_add_icon_ammu" style="height:180px;overflow: auto;"></div>
            </div>
            <div class="widget-box" style="display: none;">
                <!-- 器材 -->
                <div class="widget-content" id="div_add_icon_equip" style="height:180px;overflow: auto;"></div>
            </div>
            <div class="widget-box" style="display: none;" id="div_add_icon_plane">
                <!-- 飞机 -->
                <div class="widget-content">
                    <div class="col-sm-4" style="height:180px;overflow: auto;padding:0 5px 0 0" id="div_plane"></div>
                    <div class="col-sm-8" style="height:180px;overflow: auto;padding:0 0 0 5px" id="div_plane_ammu"></div>
                    <div style="clear:both;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
</script>
</body>
</html>