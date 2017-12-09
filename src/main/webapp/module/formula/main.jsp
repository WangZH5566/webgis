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
                    <h5>公式列表</h5>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_delete">删除公式</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_modify">修改公式</a>
                    <a href="${ctx}/formula/addOrEdit" class="btn btn-xs btn-primary buttons">新增公式</a>
                </div>
                <div class="widget-content">
                    <div id="div_list">
                        <table class="table table-bordered" id="table_list">
                            <thead>
                            <tr>
                                <th style="width: 4%;">序号</th>
                                <th style="width: 30%;">公式名称</th>
                                <th style="width: 66%;">公式描述</th>
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
</div>
<script src="${ctx}/js/module/formula/formula.js?v=20161028007"></script>
<script type="text/javascript">
    WEBGIS.formula.init();
</script>
</body>
</html>