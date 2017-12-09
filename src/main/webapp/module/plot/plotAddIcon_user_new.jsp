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
                                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add">+</a>
                                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_minus">-</a>
                                </div>
                            </div>
                        </div>
                        <div style="clear: both;"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
</script>
</body>
</html>