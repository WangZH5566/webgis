<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
    .private_search{width:100%;height:120px;}
    .private_list{width:100%;min-height:460px;margin-bottom:5px;}
</style>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-title">
                    <h5>海图新增-1. 上传图片文件ZIP包</h5>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_cancel">取消</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_save">下一步</a>
                </div>
                <div class="widget-content no-padding">
                    <form class="form-horizontal" action="${ctx}/sc/save_step1" method="post" novalidate="novalidate" id="form_standard" enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">海图名称:</label>
                            <div class="col-sm-9 form-div2">
                                <input type="text" placeholder="" name="name" class="form-control" id="txt_exec_name"/>
                            </div>
                        </div>
                        <!--
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">左上角的经纬度:</label>
                            <div class="col-sm-4 form-div2">
                                <input type="text" placeholder="" name="leftlon" class="form-control" id="txt_left_geo1"/>
                            </div>
                            <div class="col-sm-5 form-div2">
                                <input type="text" placeholder="" name="leftlat" class="form-control" id="txt_left_geo2"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">右下角的经纬度:</label>
                            <div class="col-sm-4 form-div2">
                                <input type="text" placeholder="" name="rightlon" class="form-control" id="txt_right_geo1"/>
                            </div>
                            <div class="col-sm-5 form-div2">
                                <input type="text" placeholder="" name="rightlat" class="form-control" id="txt_right_geo2"/>
                            </div>
                        </div>
                        -->
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">海图文件:</label>
                            <div class="col-sm-9 form-div2">
                                <input type="file" id="txt_file_name" name="file" placeholder="" class="form-control" style="border: 0px;font-size:12px;padding:0px 12px 0px 0px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">&nbsp;</label>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">&nbsp;</label>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/js/module/seachart/seachart.js?v=20161028007"></script>
<script>
    WEBGIS.seachart.add.init();
</script>
</body>
</html>
