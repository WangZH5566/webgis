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
        <div class="col-md-4">
            <div class="widget-box">
                <div class="widget-content  no-padding" style="height:270px;overflow: auto;">
                    <ul id="ul_icon_ztree" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="widget-box">
                <div class="widget-content  no-padding" style="height:270px;overflow: auto;">
                    <div id="div_icon_list"></div>
                    <div id="div_icon_list_page_btn"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var zTreeNodes = ${zTreeNodes};
    var setting={
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0
            }
        },
        view: {
            showIcon: true,
            selectedMulti: false,
            nameIsHTML: true
        },
        callback: {
            onClick: WEBGIS.execStepFiveChooseIcon.zTreeOnClick
        }
    };
    WEBGIS.execStepFiveChooseIcon.init();
</script>
</body>
</html>