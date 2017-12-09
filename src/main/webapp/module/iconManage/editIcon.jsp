<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="widget-title">
	<h5>基础属性</h5>
	<a href="javascript:;" id="btn_save_icon" class="btn btn-xs btn-primary buttons">保存</a>
</div>
<div class="widget-content" style="min-height: 245px;text-align: center;">
	<form id="form_save_icon_edit" class="form-horizontal" action="#" method="post" novalidate="novalidate">
		<input type="hidden" id="id" name="id" value="${icon.id }" />
		<input type="hidden" id="txt_icon_group" name="iconGroup" value="${icon.iconGroup }" />
		<div class="col-sm-12">
			<div class="form-group">
				<label class="col-sm-4 control-label">运动属性:</label>
				<div class="col-sm-2">
					<input id="chk_speed" onclick="WEBGIS.iconEdit.checkboxClick(this);" name="hasSpeed" data-target="speed-attr" type="checkbox" <c:if test="${icon.hasSpeed!=null&&icon.hasSpeed }">checked</c:if> />
				</div>
				<label class="col-sm-4 control-label">维修属性:</label>
				<div class="col-sm-2">
					<input id="chk_damage" onclick="WEBGIS.iconEdit.checkboxClick(this);" name="hasDamage" data-target="damage-attr" type="checkbox" <c:if test="${icon.hasDamage!=null&&icon.hasDamage }">checked</c:if> />
				</div>
			</div>
		</div>
		<div name="speed-attr" class="col-sm-12" <c:if test="${icon.hasSpeed==null||!icon.hasSpeed }">style="display: none;"</c:if>>
			<div class="form-group">
				<label class="col-sm-4 control-label">最大速度:</label>
				<div class="col-sm-8">
					<input type="text" id="txt_speed" name="speed" placeholder="请输入最大速度" value="${icon.speed }" class="form-control" style="width:44%;float:left;"/>
					<select id="txt_speed_unit" name="speedUnit" class="form-control"  style="width:55%;float:left;">
						<option value="km" <c:if test="${icon.speedUnit=='km'}">selected</c:if>>千米/小时</option>
						<option value="kn" <c:if test="${icon.speedUnit=='kn'}">selected</c:if>>节/小时</option>
					</select>
				</div>
			</div>
		</div>
		<div name="damage-attr" class="col-sm-12" <c:if test="${icon.hasDamage==null||!icon.hasDamage }">style="display: none;"</c:if>>
			<div class="form-group">
				<label class="col-sm-4 control-label">受损等级:</label>
				<div class="col-sm-8">
					<input id="txt_damageLevel" name="damageLevel" class="form-control"  readonly value="${icon.damageLevel }" />
				</div>
			</div>
		</div>
		<div name="damage-attr" class="col-sm-12" <c:if test="${icon.hasDamage==null||!icon.hasDamage }">style="display: none;"</c:if>>
			<div class="form-group">
				<label class="col-sm-4 control-label">修理工时:</label>
				<div class="col-sm-8">
					<input id="txt_damageLevelTime" name="damageLevelTime" class="form-control" value="${icon.damageLevelTime }" />
				</div>
			</div>
		</div>
	</form>
</div>
<div class="widget-title">
	<h5>扩展属性(双击编辑)</h5><a href="javascript:;" id="btn_add_icon_field" class="btn btn-xs btn-primary buttons">添加属性</a>
</div>
<div class="widget-content" style="min-height: 255px;text-align: center;">
	<form id="form_ext_info" class="form-horizontal" >
		<c:forEach items="${extList}" var="item">
			<div id="ext_${item.id}" data-id="${item.id}" class="col-sm-12" ondblclick="WEBGIS.iconExtEdit.edit(this)">
				<div class="form-group">
					<label class="col-sm-4 control-label">${item.name}</label>
					<div class="col-sm-8">
						<input readonly type="text" placeholder="" class="form-control" value="${item.value}"/>
					</div>
				</div>
				<a class="btn-delete" data-id="${item.id}" onclick="WEBGIS.iconExtEdit.delete(this)" href="javascript:void(0);"><i class="fa fa-times" aria-hidden="true"></i></a>
			</div>
		</c:forEach>
	</form>
</div>

<div id="div_ext_template" class="col-sm-12" ondblclick="WEBGIS.iconExtEdit.edit(this)" style="display: none;">
	<div class="form-group">
		<label class="col-sm-4 control-label"></label>
		<div class="col-sm-8">
			<input readonly type="text" placeholder="" class="form-control" value=""/>
		</div>
	</div>
	<a class="btn-delete" data-id="" onclick="WEBGIS.iconExtEdit.delete(this)" href="javascript:void(0);"><i class="fa fa-times" aria-hidden="true"></i></a>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		WEBGIS.iconEdit.init();
	});
</script>