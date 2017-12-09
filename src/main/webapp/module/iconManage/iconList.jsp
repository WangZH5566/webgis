<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<c:forEach items="${list}" var="item">
    <div class="icon-outer" onclick="WEBGIS.iconManage.iconClick(this);" data-icon="${item.id}">
        <div id="icon_${item.id}" class="icon-inner">
            <img src="${FILESERVER_ICON_VISITPATH}${item.iconPath}"/>
        </div>
        <label>${item.name}</label>
        <a class="btn-delete" href="javascript:WEBGIS.iconManage.deleteIcon(${item.id});"><i class="fa fa-times" aria-hidden="true"></i></a>
    </div>
</c:forEach>

<form id="form_icon_search">
    <input type="hidden" id="txt_current_page" value="${searchDto.pageNo}"/>
    <input type="hidden" id="txt_current_groupArray" value="${searchDto.groupArray}"/>
</form>