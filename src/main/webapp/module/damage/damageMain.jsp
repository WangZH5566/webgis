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
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>受损程度</h5>
                        <a href="javascript:;" id="btn_add" class="btn btn-xs btn-primary buttons">添加</a>
                    </div>
                    <div class="widget-content">
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                <tr>
                                    <th style="width: 12%;">序号</th>
                                    <th style="width: 38%;">受损程度</th>
                                    <th style="width: 16%;">图标</th>
                                    <th style="width: 34%;">操作</th>
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
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>受损程度详情</h5>
                        <a href="javascript:;" id="btn_add_detail" class="btn btn-xs btn-primary buttons">添加</a>
                    </div>
                    <div class="widget-content">
                        <div id="div_list_detail">
                            <table class="table table-bordered" id="table_list_detail">
                                <thead>
                                <tr>
                                    <th style="width: 12%;">序号</th>
                                    <th style="width: 68%;">受损程度详情</th>
                                    <th style="width: 20%;">操作</th>
                                </tr>
                                </thead>
                                <tbody id="tbody_list_detail">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn_detail"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/damage/damage.js?v=20161028002"></script>
    <script type="text/javascript">
        WEBGIS.damage.init();
    </script>
</body>
</html>