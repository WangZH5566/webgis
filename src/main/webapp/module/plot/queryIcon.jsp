<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<c:forEach items="${dtos}" var="item">
    <div class="icon-outer" data-troopId="${item.id}" data-icn="${item.iconName}" data-icd="${item.iconData}" data-ca="${item.colorArray}">
        <div class="icon-inner">
            <img src="${FILESERVER_ICON_VISITPATH}${item.iconData}"/>
        </div>
        <label>${item.iconName}</label>
    </div>
</c:forEach>
<%--
<form id="form_icon_search">
    <input type="hidden" id="txt_current_page" value="${searchDto.pageNo}"/>
    <input type="hidden" id="txt_current_groupArray" value="${searchDto.groupArray}"/>
</form>--%>
