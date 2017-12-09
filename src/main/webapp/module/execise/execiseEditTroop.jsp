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
                <div class="widget-content">
                    <form class="form-horizontal" action="javascript:;" method="post" novalidate="novalidate">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label class="col-sm-2 control-label form-label2">经度:</label>
                                <div class="col-sm-9 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_longitude" value="" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label form-label2">纬度:</label>
                                <div class="col-sm-9 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_latitude" value="" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12" id="div_speed" style="display: none;">
                            <div class="form-group">
                                <label class="col-sm-2 control-label form-label2">速度:</label>
                                <div class="col-sm-7 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_move_speed" value="" />
                                </div>
                                <label class="col-sm-2 control-label form-label2" id="lab_speed_unit"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label form-label2">航向:</label>
                                <div class="col-sm-9 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_move_angle" value="" />
                                </div>
                            </div>
                        </div>
                        <div style="clear: both;"></div>
                    </form>
                </div>
            </div>
            <div class="widget-box">
                <!-- 权限 -->
                <div class="widget-content" id="div_add_icon_right" style="height:180px;overflow: auto;"></div>
            </div>
        </div>
    </div>
</div>
<script>
    var tmpTroop = ${dto};
</script>
</body>
</html>