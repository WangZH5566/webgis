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
    <title>${system_name}::<tiles:insertAttribute name="title"/></title>
</head>
<body>
<%@ include file="/common/js.jsp" %>
<tiles:insertAttribute name="body"/>
</body>
</html>