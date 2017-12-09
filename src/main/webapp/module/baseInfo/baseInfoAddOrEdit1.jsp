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
                                <label class="col-sm-3 control-label form-label2">编号:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_info_code" value="${dto.infoCode}" data-na="编号"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">隶属单位:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_belong_unit" value="${dto.belongUnit}" data-na="隶属单位"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">最大航速(公里/时):</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_max_speed" value="${dto.maxSpeed}" data-na="最大航速(公里/时)"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">作战半径（公里）:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_fight_radius" value="${dto.fightRadius}" data-na="作战半径（公里）"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">最大起飞重量（吨）:</label>
                                <div class="col-sm-8 form-div2">
                                    <input type="text" placeholder="" class="form-control" id="txt_max_take_off_weight" value="${dto.maxTakeOffWeight}" data-na="最大起飞重量（吨）"/>
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
                                <label class="col-sm-3 control-label form-label2">修理情况:</label>
                                <div class="col-sm-8 form-div2">
                                    <textarea style="width: 100%;" rows="5" id="txt_repair_situation" data-na="修理情况">${dto.repairSituation}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label form-label2">主要武备:</label>
                                <div class="col-sm-8 form-div2">
                                    <textarea style="width: 100%;" rows="5" id="txt_main_weapons" data-na="主要武备">${dto.mainWeapons}</textarea>
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
<script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
<script src="${ctx}/js/jquery.spinner/jquery.spinner.js?v=2016102813"></script>
<script src="${ctx}/js/module/baseInfo/baseInfo.js?v=20161028002"></script>
<script type="text/javascript">
    WEBGIS.baseInfoAddOrEdit.init();
</script>
</body>
</html>