<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<c:forEach items="${list}" var="item">
    <div id="icon_${item.id}" class="icon-outer" data-icon="${item.id}">
        <div class="icon-inner">
            <img src="${FILESERVER_ICON_VISITPATH}${item.iconPath}" icon-data="${item.iconPath}"/>
        </div>
        <label>${item.name}</label>
    </div>
</c:forEach>
<form id="form_icon_search">
    <input type="hidden" id="txt_current_page" value="${searchDto.pageNo}"/>
    <input type="hidden" id="txt_current_groupArray" value="${searchDto.groupArray}"/>
</form>