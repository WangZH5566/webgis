<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="/common/tags.jsp"%>
<%@ include file="/common/vars.jsp" %>
<!--[if lt IE 9]>
<script>
(function() {
if (!
/*@cc_on!@*/
0) return;
var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
var i= e.length;
while (i--){
document.createElement(e[i])
}
})()
</script>
<![endif]-->
<style>
    /*html5*/
    article,aside,dialog,footer,header,section,footer,nav,figure,menu{display:block}
</style>
<html lang="zh_CN">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title></title>
    <!-- Bootstrap -->
    <link href="${ctx}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- font-awesome -->
    <link href="${ctx}/css/bootstrap/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- custom -->
    <%--<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css">--%>
    <link href="${ctx}/css/global.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/css/responsive.css" rel="stylesheet" type="text/css">
    <!-- jquery-zTree -->
    <%--<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet" type="text/css">--%>
    <!-- jquery-easyUI -->
    <link href="${ctx}/js/jquery_easyUI/themes/default/easyui.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/js/jquery_easyUI/themes/icon.css" rel="stylesheet" type="text/css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="${ctx}/js/bootstrap/respond.min.js"></script>
    <script src="${ctx}/js/bootstrap/html5shiv.min.js"></script>
    <![endif]-->
</head>
<body>
<%@ include file="/common/js.jsp" %>
<script src="${ctx}/js/jquery/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/layer-v2.2/layer/layer.js" ></script>
<script src="${ctx}/js/laypage-v1.3/laypage/laypage.js" ></script>
<script src="${ctx}/js/global/global.js?20150716013"></script>
<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="left" />
<div class="content">
    <tiles:insertAttribute name="body" />
</div>
</body>
</html>