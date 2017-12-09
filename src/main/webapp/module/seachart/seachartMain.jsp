<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link rel="stylesheet" href="${ctx}/assert/ol/ol-3.19.0.css" type="text/css">
<style type="text/css">
    .private_search{width:100%;height:120px;}
    .private_list{width:100%;min-height:460px;margin-bottom:5px;}
</style>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-5">
            <div class="widget-box">
                <div class="widget-title">
                    <h5>海图列表</h5>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_delete">删除</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_upload">上传</a>
                </div>
                <div class="widget-content">
                    <div id="div_list">
                        <table class="table table-bordered" id="table_list">
                            <thead>
                            <tr>
                                <th style="width: 10%;text-align: center;"><input type="checkbox" id="check_all"></th>
                                <th style="width: 18%;text-align: center;">序号</th>
                                <th style="width: 78%;text-align: center;">名称</th>
                            </tr>
                            </thead>
                            <tbody id="tbody_list">
                            </tbody>
                        </table>
                    </div>
                    <div id="div_page_btn"></div>
                </div>
            </div>
        </div>
        <div class="col-md-7">
            <div class="widget-box">
                <div class="widget-title">
                    <h5>海图预览</h5>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_zoomin">放大</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_zoomout">缩小</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_up">上移</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_down">下移</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_left">左移</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_right">右移</a>
                </div>
                <div class="widget-content" id="map">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/assert/ol/ol-3.19.0.js"></script>
<script src="${ctx}/js/module/util.js?v=2016102813"></script>
<script src="${ctx}/js/module/seachart/seachart.js?v=2016102811"></script>
<script>
    WEBGIS.seachart.main.init();
</script>
</body>
</html>
