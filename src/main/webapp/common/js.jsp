<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.ydh.service.GeoServerService" %>
<%@page language="java" pageEncoding="utf-8"%>
<script>
    var ctx = "${ctx}";
    var iev = 0;
    <%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    GeoServerService geoServerService = (GeoServerService)ac.getBean("geoServerService");
    out.println("var geoserver='" + geoServerService.getUrl() +"';");
    %>
</script>