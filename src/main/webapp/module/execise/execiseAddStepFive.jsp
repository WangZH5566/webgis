<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/js/jquery.spinner/jquery.spinner.css" rel="stylesheet">
<link href="${ctx}/assert/spectrum/spectrum.css" rel="stylesheet">
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}

    /* 图标列表的图标样式 */
    .icon-outer{
        margin: 4px;
        width: 90px;
        height: 115px;
        position: relative;
        float: left;
        overflow: hidden;
    }
    .icon-outer .icon-inner{
        border: solid 5px #fff;
        padding: 2px 2px;
    }
    .icon-outer .icon-inner img{
        width: 76px;
        height:76px;
    }
    .icon-outer label{
        margin-top: 5px;
    }

    .icon-outer.selected div.icon-inner{
        border-color: #99a3fd;
    }
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>第五步:推演兵力</h5>
                        <a href="${ctx}/exec/main" class="btn btn-xs btn-primary buttons">返回</a>
                        <a href="${ctx}/exec/addStepFour?id=${exec_id}" class="btn btn-xs btn-primary buttons">上一步</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_troop_import">兵力导入</a>
                        <div style="float: right;margin: 9px 5px 0 0;">
                            <select id="sel_option">
                                <option value=""></option>
                                <c:forEach var="item" items="${igList}">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="widget-content">
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                <tr>
                                    <th style="width: 4%;text-align: center;"></th>
                                    <th style="width: 10%;text-align: center;">图标</th>
                                    <th style="width: 10%;text-align: center;">图标名称</th>
                                    <th style="width: 10%;text-align: center;">图标类型</th>
                                    <th style="width: 10%;text-align: center;">速度</th>
                                    <th style="width: 10%;text-align: center;">最大速度</th>
                                    <th style="width: 10%;text-align: center;">速度单位</th>
                                    <th style="width: 10%;text-align: center;">所属机场</th>
                                    <th style="width: 10%;text-align: center;">所属单位</th>
                                    <th style="width: 6%;text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody id="tbody_list">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn"></div>
                        <div id="div_print_list" style="display: none;"> </div>
                    </div>
                    <input type="hidden" id="txt_id" value="${exec_id}">
                </div>
            </div>
        </div>
    </div>
    <div id="div_troop_import" style="display: none;">
        <div class="col-sm-12" style="margin: 10px 0;">
            <div class="form-group">
                <label class="col-sm-3 control-label form-label2">兵力模板:</label>
                <div class="col-sm-8 form-div2">
                    <select id="sel_troop_import" style="width: 100%;">
                        <option value=""></option>
                        <c:forEach var="item" items="${execiseDtos}">
                            <option value="${item.id}">${item.execiseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <div id="div_choose_icon" style="display: none;"></div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.excheck-3.5.min.js"></script>
    <script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
    <script src="${ctx}/assert/spectrum/spectrum.js"></script>
    <script src="${ctx}/js/module/myspectrum.js"></script>
    <script src="${ctx}/js/jquery.spinner/jquery.spinner.js?v=2016102813"></script>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028001"></script>
    <script type="text/javascript">
        WEBGIS.execStepFive.init();
    </script>
</body>
</html>