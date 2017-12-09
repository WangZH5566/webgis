<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<head>
    <title>装载列表管理</title>
</head>
<body>
<div class="content-header">
	<div class="breadcrumb">
		<a href="#" data-original-title="Go to home">
			<i class="glyphicon glyphicon-home"></i>
			装载列表管理
		</a>
		<a href="#" data-original-title="user limit" class="current">

		</a>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12" style="float: left;">
			<div class="widget-box">
				<div class="widget-title">
					<h5>装载列表</h5><a id="btn_add_equipment" onclick="WEBGIS.equipmentManage.btn_add_equipment_onclick()" class="btn btn-xs btn-primary buttons">添加装备</a>
				</div>
				<div class="widget-content">
					<div id="tableList_div">
					</div>
					<div id="div_for_pagebtn"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/js/laypage-v1.3/laypage/laypage.js" ></script>
<script src="${ctx}/js/module/equipmentManage/equipmentManage.js" ></script>

<script>
var totalPages="${totalPages}";
$(document).ready(function(){
	WEBGIS.equipmentManage.init(totalPages);
});
</script>
</body>
</html>
