<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<table class="table table-bordered">
	<tr>
		<th style="width:50%;">装备名称</th>
		<th style="width:30%;">装载速度(分/单位)</th>
		<th style="width:20%;">操作</th>
	</tr>
	<c:forEach items="${list }" var="item">
		<tr id="${item.id }">
			<td title="${item.name }">${item.name }</td>
			<td title="${item.loadTime }">${item.loadTime }</td>
			<td>
				<a onclick="WEBGIS.equipmentManage.btn_edit_equipment_onclick('${item.id}')">修改</a> &nbsp;&nbsp;<a onclick="WEBGIS.equipmentManage.btn_delete_equipment_onclick('${item.id}')">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
<form id="query_condition_form">
	<input type="hidden" id="current_page_no" value="${searchDto.pageNo }"/>
</form>