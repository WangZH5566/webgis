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
                        <div id="div_speed_attr_container">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">速度:</label>
                                    <div class="col-sm-7 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_move_speed" value="" />
                                    </div>
                                    <label class="col-sm-2 control-label form-label2" id="lab_speed_unit"></label>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">航向:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_move_angle" value="" />
                                    </div>
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
            <div class="widget-box" style="display: none;">
                <!-- 权限 -->
                <div class="widget-content" id="div_add_icon_right" style="height:180px;overflow: auto;"></div>
            </div>
        </div>
    </div>
</div>


<%--<div id="div_plot_icon">
    <i class="fa fa-close" style="font-size: 13px;position: absolute;right: 5px;top: 2px;color: gray;" onclick="WEBGIS.plotIcon.closePopup();"></i>
    <div class="widget-box" style="margin: 0;">
        <div class="widget-title">
            <h5>要图标绘</h5>
        </div>
        <div id="div_zTree" class="widget-content" style="padding: 0;">
            <ul id="ztree" class="ztree" style="height:206px;overflow: auto;"></ul>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_one_next">下一步</a>
        </div>
        <div class="widget-content" id="div_step_two" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;width: 500px;" id="div_step_two_cont"></div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_two_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_two_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_three" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;" id="div_step_three_cont"></div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_three_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_three_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_four" style="padding: 0;display: none;">
            <form class="form-horizontal" action="#" method="post" novalidate="novalidate" style="text-align:center;height: 206px;overflow-y: auto;" id="form_step_four">
                <canvas id="img_canvas" style="margin-top:30px;"></canvas>
            </form>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_four_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_four_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_five" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;" id="div_step_five_cont"></div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_five_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_five_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_six" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;" id="div_step_six_cont"></div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_six_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_six_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_seven" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;" id="div_step_seven_cont"></div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_seven_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_seven_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_eight" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;" id="div_step_eight_cont">
                <div class="form-group" style="margin-top: 20px;">
                    <label class="col-sm-3 control-label">名称:</label>
                    <div class="col-sm-9">
                        <input type="text" id="txt_icon_name" value="" class="form-control"/>
                    </div>
                    <div style="clear:both;"></div>
                </div>
                <div id="div_speed_attr_container">
                <div id="div_move_speed" class="form-group" style="margin-top: 20px;">
                    <label class="col-sm-3 control-label">速度:</label>
                    <div class="col-sm-7">
                        <input type="text" id="txt_move_speed" value="" class="form-control"/>
                    </div>
                    <label id="lab_speed_unit" class="col-sm-2 control-label" style="margin-left: -15px;padding: 0px;padding-top: 5px;">公里/时</label>
                    <div style="clear:both;"></div>
                </div>
                <div id="div_move_angle" class="form-group" style="margin-top: 20px;">
                    <label class="col-sm-3 control-label">航向:</label>
                    <div class="col-sm-9">
                        <input type="text" id="txt_move_angle" value="" class="form-control"/>
                    </div>
                    <div style="clear:both;"></div>
                </div>
                </div>
            </div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_eight_next">下一步</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_eight_pre">上一步</a>
        </div>
        <div class="widget-content" id="div_step_nine" style="padding: 0;display: none;">
            <div style="height: 206px;text-align: center;overflow-y: auto;" id="div_step_nine_cont"></div>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;" id="btn_step_final_save">保存</a>
            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-top: 5px;float: right;margin-right:5px;" id="btn_step_nine_pre">上一步</a>
        </div>
    </div>
</div>--%>
<script>
</script>
</body>
</html>