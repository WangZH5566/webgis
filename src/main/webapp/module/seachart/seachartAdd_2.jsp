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
                    <h5>海图新增-2.设置每张图片的经纬度</h5>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_cancel">取消</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_save">保存</a>
                </div>
                <div class="widget-content no-padding">

                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/js/module/seachart/seachart.js?v=20161028012"></script>
<script>
    var image = new Array();
    <c:forEach items="${images}" var="image">
       image.push('${image}')
    </c:forEach>
WEBGIS.seachart.add.loading('${subdir}',image,'${name}');
WEBGIS.seachart.add.init2();
</script>
</body>
</html>
