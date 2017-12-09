<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加图标</title>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12" style="float: left;">
				<form id="form_save_icon" class="form-horizontal" action="#" method="post" novalidate="novalidate">
					<input type="hidden" id="id" name="id" value="${icon.id }" />
					<input type="hidden" id="txt_icon_group" name="iconGroup" value="${icon.iconGroup }" />
					<div class="form-group">
						<label class="col-sm-3 control-label">编号/弦号:</label>
						<div class="col-sm-7">
							<input type="text" id="txt_name" name="name" placeholder="请输入图标名称" value="${icon.name }" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">图标文件:</label>
						<div class="col-sm-7">
							<input type="file" id="f_icon_img" name="image" placeholder="请选择图片" style="border:none;box-shadow:none;" class="form-control"/>
						</div>
					</div>
					<%--<div class="form-group">--%>
						<%--<label class="col-sm-3 control-label">运动属性:</label>--%>
						<%--<div class="col-sm-2">--%>
							<%--<input id="chk_speed" onclick="WEBGIS.iconAdd.checkboxClick(this);" name="hasSpeed" data-target="speed-attr" type="checkbox" <c:if test="${icon.hasSpeed!=null&&icon.hasSpeed }">checked</c:if> />--%>
						<%--</div>--%>
						<%--<label class="col-sm-3 control-label">维修属性:</label>--%>
						<%--<div class="col-sm-2">--%>
							<%--<input id="chk_damage" onclick="WEBGIS.iconAdd.checkboxClick(this);" name="hasDamage" data-target="damage-attr" type="checkbox" <c:if test="${icon.hasDamage!=null&&icon.hasDamage }">checked</c:if> />--%>
						<%--</div>--%>
					<%--</div>--%>
					<%--<div name="speed-attr" class="form-group" style="display: none;">--%>
						<%--<label class="col-sm-3 control-label">最大速度:</label>--%>
						<%--<div class="col-sm-7">--%>
							<%--<input type="text" id="txt_speed" name="speed" placeholder="请输入最大速度" value="${icon.speed }" class="form-control" style="width:44%;float:left;"/>--%>
							<%--<select id="txt_speed_unit" name="speedUnit" class="form-control"  style="width:55%;float:left;">--%>
								<%--<option value="km">千米/小时</option>--%>
								<%--<option value="kn">节/小时</option>--%>
							<%--</select>--%>
						<%--</div>--%>
					<%--</div>--%>
					<%--<div name="damage-attr" class="form-group" style="display: none;">--%>
						<%--<label class="col-sm-3 control-label">受损等级:</label>--%>
						<%--<div class="col-sm-7">--%>
							<%--<input id="txt_damageLevel" name="damageLevel" readonly class="form-control" value="正常、轻、中、重" />--%>
						<%--</div>--%>
					<%--</div>--%>
					<%--<div name="damage-attr" class="form-group" style="display: none;">--%>
						<%--<label class="col-sm-3 control-label">修理工时:</label>--%>
						<%--<div class="col-sm-7">--%>
							<%--<input id="txt_damageLevelTime" name="damageLevelTime" class="form-control" value="${icon.damageLevelTime }" />--%>
						<%--</div>--%>
					<%--</div>--%>
					<input type="button" id="btn_save" value="保存" class="btn btn-sm btn-success" />
				</form>
		</div>
	</div>
</div>
<script>
    $(document).ready(function(){
        WEBGIS.iconAdd.init();
    });
</script>
</body>
</html>
