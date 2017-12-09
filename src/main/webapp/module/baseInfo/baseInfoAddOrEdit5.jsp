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
                                <label class="col-sm-3 control-label form-label2">型号:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_info_code" value="${dto.infoCode}" data-na="型号"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">生产研制单位:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_development_unit" value="${dto.developmentUnit}" data-na="生产研制单位"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">服役日期:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_service_date" value="${dto.serviceDate}" onclick="laydate({istime: true})" data-na="服役日期"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">高清图片:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" value="${dto.imageUrl}" data-na="高清图片" data-pa="infoName" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">技术状态:</label>
                                <div class="col-sm-8 form-div2">
                                    <select class="form-control no-blank" id="sel_technology_situation" data-na="技术状态">
                                        <option value="1" <c:if test="${dto.technologySituation eq 1}">selected</c:if>>1级</option>
                                        <option value="2" <c:if test="${dto.technologySituation eq 2}">selected</c:if>>2级</option>
                                        <option value="3" <c:if test="${dto.technologySituation eq 3}">selected</c:if>>3级</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">性能:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_performance" value="${dto.performance}" data-na="性能"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">转进时间（小时）:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_switch_time" value="${dto.switchTime}" data-na="战备等级转进时间（小时）"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">吊装时间（小时）:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_load_time" value="${dto.loadTime}" data-na="吊装时间（小时）"/>
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
    </div>
</div>
<script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
<script src="${ctx}/js/module/baseInfo/baseInfo.js?v=20161028002"></script>
<script type="text/javascript">
    WEBGIS.baseInfoAddOrEdit.init();
</script>
</body>
</html>