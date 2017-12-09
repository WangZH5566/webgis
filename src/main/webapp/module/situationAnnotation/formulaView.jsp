<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content" style="height:315px;overflow: auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30%;">公式名称</th>
                            <th style="width: 30%;">公式描述</th>
                            <th style="width: 40%;">公式内容</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_formula_list">
                        <c:forEach var="item" items="${dtos}" varStatus="st">
                            <tr data-id="${item.id}" data-vl="${item.val}">
                                <td>${item.name}</td>
                                <td>${item.remark}</td>
                                <td>${item.val}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="widget-content no-padding">
                <form class="form-horizontal" action="javascript:;" method="post" novalidate="novalidate">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="col-sm-2 control-label form-label2">公式参数:</label>
                            <div class="col-sm-9 form-div2" id="formula_panel"></div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            <a href="javascript:;" id="btn_fc" class="btn btn-xs btn-primary buttons" style="float: right;">公式计算</a>
                        </div>
                    </div>
                    <div style="clear: both;"></div>
                </form>
            </div>
            <input type="hidden" id="txt_formula_val" value="">
        </div>
    </div>
</div>
</body>
</html>