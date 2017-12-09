<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>日志列表</h5>
                        <input type="hidden" id="txt_execID" value="${execID}"/>
                    </div>
                    <div class="widget-content" id="div_list">
                        <table class="table table-bordered" id="table_list">
                            <thead>
                            <tr>
                                <th style="width: 15%;text-align: center;">时间(作战时间)</th>
                                <%--<th style="width: 10%;text-align: center;">操作人</th>--%>
                                <th style="width: 70%;text-align: center;">内容</th>
                            </tr>
                            </thead>
                            <tbody id="tbody_list">

                            </tbody>
                        </table>
                        <div id="div_for_page_btn"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/historyLog/historyLog.js?v=20161028001"></script>
    <script type="text/javascript">
        var totalPage=${totalPage}
        $(document).ready(function(){
            WEBGIS.hl.init(totalPage);
        });
    </script>
</body>
</html>