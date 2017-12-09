<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<head>
    <title>编辑装备</title>
</head>
<body>
<div class="container-fluid" style="width:370px;">
    <div style="height:20px;"></div>
    <div class="row">
        <form class="form-horizontal" action="#" method="post" novalidate="novalidate">
            <input type="hidden" id="id" value="${eqi.id }" />
            <div class="form-group">
                <label class="col-sm-3 control-label">装备名称:</label>
                <div class="col-sm-8">
                    <input type="text" id="txt_name" placeholder="" value="${eqi.name}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">装载时间:</label>
                <div class="col-sm-8">
                    <input type="text" id="txt_loadTime" placeholder="" value="${eqi.loadTime}" class="form-control"/>
                </div>
            </div>
            <input type="button" id="btn_save" value="保存" class="btn btn-sm btn-success" style="float:right;margin-top:30px;margin-right:5px;"/>
        </form>
    </div>
</div>
<script>
    $(document).ready(function(){
        WEBGIS.equipmentEdit.init();
    });
</script>
</body>
</html>
