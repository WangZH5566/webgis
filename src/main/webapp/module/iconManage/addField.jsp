<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加属性</title>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12" style="float: left;">
				<form id="form_save_icon_ext" class="form-horizontal" action="#" method="post" novalidate="novalidate">
					<input type="hidden" id="txt_id" name="id" value="${ext.id }" />
					<input type="hidden" id="txt_icon_id" name="icon" value="${ext.icon }" />
					<div class="form-group">
						<label class="col-sm-4 control-label">属性名称:</label>
						<div class="col-sm-8">
							<input type="text" id="txt_name" name="name" placeholder="请输入图标名称" value="${ext.name }" class="form-control"/>
						</div>
					</div>
					<div name="speed-attr" class="form-group">
						<label class="col-sm-4 control-label">属性内容:</label>
						<div class="col-sm-8">
							<input type="text" id="txt_value" name="value" placeholder="请输入属性内容" value="${ext.value }" class="form-control"/>
						</div>
					</div>
					<input type="button" id="btn_save_ext" value="保存" class="btn btn-sm btn-success" />
				</form>
		</div>
	</div>
</div>
<script>
    $(document).ready(function(){
        WEBGIS.iconExtEdit.init();
    });
</script>
</body>
</html>
