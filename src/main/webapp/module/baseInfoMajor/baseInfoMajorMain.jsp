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
                        <h5>专业列表</h5>
                        <a id="btn_add" class="btn btn-xs btn-primary buttons">添加专业</a>
                    </div>
                    <div class="widget-content">
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                    <tr>
                                        <th style="width: 8%;">序号</th>
                                        <th style="width: 72%;">专业名称</th>
                                        <th style="width: 20%;">操作</th>
                                    </tr>
                                </thead>
                                <tbody id="tbody_list">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/baseInfoMajor/baseInfoMajor.js?v=20161028002"></script>
    <script type="text/javascript">
        WEBGIS.baseInfoMajor.init();
    </script>
</body>
</html>