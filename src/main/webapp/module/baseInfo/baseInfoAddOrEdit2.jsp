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
                    <h5>新增/修改基础资料</h5>
                    <a href="${ctx}/baseInfo/baseInfo/baseInfoMain" class="btn btn-xs btn-primary buttons">返回</a>
                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_save">保存</a>
                </div>
                <div class="widget-content no-padding">
                    <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="form_search">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">单位名称:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_info_name" value="${dto.infoName}" data-na="单位名称"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">地址:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_address" value="${dto.address}" data-na="地址"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">经纬度:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_longitude_and_latitude" value="${dto.longitudeAndLatitude}" data-na="经纬度"/>
                                </div>
                            </div>
                        </div>
                        <div style="clear: both"></div>
                        <input type="hidden" id="txt_id" value="${id}">
                        <input type="hidden" id="txt_ti" value="${ti}">
                        <input type="hidden" id="txt_mt" value="${mt}">
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="widget-box">
                <div class="widget-title">
                    <h5>雷弹关系</h5>
                </div>
                <div class="widget-content no-padding" style="height: 460px;overflow-y:auto;">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 10%;"></th>
                            <th style="width: 55%;">型号</th>
                            <th style="width: 35%;">最大装载数量</th>
                        </tr>
                        </thead>
                        <tbody id="tbody_ammunition_list">
                        <c:forEach var="item" items="${ammunitionDtos}" varStatus="st">
                            <tr id="am_${item.id}">
                                <td style="text-align: center;"><input type="checkbox" data-id="${item.id}" <c:if test="${not empty amMap[item.id]}">checked</c:if> ></td>
                                <td>${item.infoCode}</td>
                                <td style="text-align: center;"><input type="text" min="0" max="999" maxlength="3" class="spinnerInput" value="<c:if test="${not empty amMap[item.id]}">${amMap[item.id].ammunitionCount}</c:if>" /></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/js/jquery.spinner/jquery.spinner.js?v=2016102813"></script>
<script src="${ctx}/js/module/baseInfo/baseInfo.js?v=20161028002"></script>
<script type="text/javascript">
    WEBGIS.baseInfoAddOrEdit.init();
</script>
</body>
</html>