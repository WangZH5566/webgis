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
                                    <label class="col-sm-2 control-label form-label2">推演名称:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_exec_name" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">推演状态:</label>
                                    <div class="col-sm-8 form-div2">
                                        <select id="txt_exec_status" class="form-control">
                                            <option value=""></option>
                                            <c:forEach items="${statusMap}" var="item">
                                                <option value="${item.key}">${item.value}</option>
                                            </c:forEach>
                                        </select>
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
                        <h5>推演列表</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_review">历史回顾</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_delete">删除推演</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_modify">修改推演</a>
                        <a href="${ctx}/exec/addStepOne" class="btn btn-xs btn-primary buttons">新增推演</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_print">打印人员清单</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_exec_user">单位人员</a>
                    </div>
                    <div class="widget-content">
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                    <tr>
                                        <th style="width: 4%;text-align: center;"><input type="checkbox" id="check_all"></th>
                                        <th style="width: 8%;text-align: center;">推演序号</th>
                                        <th style="width: 18%;text-align: center;">推演名称</th>
                                        <th style="width: 12%;text-align: center;">创建时间</th>
                                        <th style="width: 12%;text-align: center;">开始时间</th>
                                        <th style="width: 12%;text-align: center;">结束时间</th>
                                        <th style="width: 10%;text-align: center;">海图类型</th>
                                        <th style="width: 10%;text-align: center;">推演状态</th>
                                        <%--<th style="width: 10%;text-align: center;">推演时长</th>--%>
                                    </tr>
                                </thead>
                                <tbody id="tbody_list">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn"></div>
                        <div id="div_print_list" style="display: none;">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/lodop/lodopFuncs.js"></script>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028002"></script>
    <script type="text/javascript">
        WEBGIS.exec.init();
    </script>
</body>
</html>