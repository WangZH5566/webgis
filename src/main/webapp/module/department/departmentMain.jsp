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
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="form_search">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">台位名称:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_name" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">台位代码:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_code" />
                                    </div>
                                    <div class="col-sm-2 form-div2" style="line-height: 30px;">
                                        <button type="button" class="btn btn-xs btn-primary buttons" id="btn_search">&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;</button>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </form>
                    </div>
                </div>
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>台位列表</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_delete">删除台位</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_modify">修改台位</a>
                        <a href="${ctx}/department/addOrEdit" class="btn btn-xs btn-primary buttons">新增台位</a>
                    </div>
                    <div class="widget-content">
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                    <tr>
                                        <th style="width: 4%;">序号</th>
                                        <th style="width: 50%;">台位名称</th>
                                        <th style="width: 26%;">台位代码</th>
                                        <th style="width: 20%;">能否跨单位发送电文</th>
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
    <script src="${ctx}/js/module/department/department.js?v=20161028002"></script>
    <script type="text/javascript">
        WEBGIS.department.init();
    </script>
</body>
</html>