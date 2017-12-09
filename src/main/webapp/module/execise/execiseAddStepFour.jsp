<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>第四步:选择海图</h5>
                        <a href="${ctx}/exec/main" class="btn btn-xs btn-primary buttons">返回</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_save">下一步</a>
                        <%--<a href="${ctx}/exec/addStepFour?id=${exec_id}" class="btn btn-xs btn-primary buttons">下一步</a>--%>
                        <a href="${ctx}/exec/addStepThree?id=${exec_id}" class="btn btn-xs btn-primary buttons">上一步</a>
                    </div>
                    <div class="widget-content" id="div_list">
                        <table class="table table-bordered" id="table_list">
                            <thead>
                                <tr>
                                    <th style="width: 4%;text-align: center;"></th>
                                    <th style="width: 96%;text-align: center;">海图名称</th>
                                </tr>
                            </thead>
                            <tbody id="tbody_list">
                                <c:forEach var="item" items="${seaChartDtos}">
                                    <tr>
                                        <td style="text-align: center;"><input type="radio" name="ht" data-id="${item.id}" <c:if test="${item.id eq sea_chart_id}">checked</c:if>></td>
                                        <td style="text-align: center;">${item.seaChartName}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <input type="hidden" id="txt_id" value="${exec_id}">
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028001"></script>
    <script type="text/javascript">
        WEBGIS.execStepFour.init();
    </script>
</body>
</html>