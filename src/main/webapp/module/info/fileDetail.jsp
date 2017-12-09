<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
    .div-outer{
        width: 75px;
        height: 75px;
        display: block;
        float: left;
        margin: 15px 15px 8px;
        border-radius: 5px;
    }
    .div-outer:hover{
        border:1px solid deepskyblue;
        background-color: #ccfffb;
    }
    .div-outer .div-inner-top{
        width: 100%;
        height: 55px;
        text-align: center;
    }
    .div-outer .div-inner-top i{
        font-size:50px;
        margin-top: 5px;
    }
    .div-outer .div-inner-top i.fa-folder{
        color: #e5ff1d;
    }
    .div-outer .div-inner-top i.fa-file-text{
        color: #b3b3b3;
        font-size: 45px;
    }
    .div-outer .div_inner-bottom{
        text-align: center;
        height: 20px;
        padding:0px 10px;
        overflow: hidden;
    }
</style>
<c:forEach items="${list}" var="item">
    <div class="div-outer">
        <div class="div-inner-top">
            <c:if test="${item.isDirectory==true}">
                <i class="fa fa-folder"></i>
            </c:if>
            <c:if test="${item.isDirectory==null||item.isDirectory==false}">
                <i class="fa fa-file-text"></i>
            </c:if>
        </div>
        <div class="div_inner-bottom">
            <label>${item.name}</label>
        </div>
    </div>
</c:forEach>

<div style="clear:both;"></div>
<input type="hidden" id="txt_current_directory" value="${currentDirectory}" />