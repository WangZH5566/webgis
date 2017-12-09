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
                <div class="widget-content  no-padding" style="height:270px;overflow: auto;">
                    <div id="div_icon_list"></div>
                    <div id="div_icon_list_page_btn"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="txt_choose_icon_win_mt" value="${mt}">
<script>
    WEBGIS.plotChooseIcon.init();
</script>
</body>
</html>