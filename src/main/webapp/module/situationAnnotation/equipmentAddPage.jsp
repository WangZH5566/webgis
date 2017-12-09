<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<div class="container-fluid">
    <div class="row">
        <form class="form-horizontal" action="#" novalidate="novalidate">
            <div class="form-group" style="margin-left: 0;margin-right: 0;margin-bottom: 0;">
                <label class="col-sm-3 control-label">装载总时长(小时):</label>
                <div class="col-sm-8">
                    <input type="text" value="" class="form-control" id="txt_eqt"/>
                </div>
            </div>
        </form>
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-title">
                    <a href="javascript:void(0);" id="btn_save_equipment" class="btn btn-xs btn-primary buttons">发送指令</a>
                    <a href="javascript:void(0);" onclick="WEBGIS.mapOrder.cancelOrder();" class="btn btn-xs btn-primary buttons">取消指令</a>
                </div>
                <div class="widget-content" style="min-height:340px;overflow: auto;">
                    <table id="table_equipment_list" class="table table-bordered">
                        <tr>
                            <th style="width:40%;">装备名称</th>
                            <th style="width:15%;">装载速度</th>
                            <th style="width:10%;">库存</th>
                            <th style="width:35%;text-align: center;">数量</th>
                        </tr>
                        <c:forEach items="${list }" var="item">
                            <tr id="${item.equipment }">
                                <td title="${item.equipmentName }">${item.equipmentName }</td>
                                <td title="${item.loadTime }">${item.loadTime }</td>
                                <td title="${item.equipmentCount }">${item.equipmentCount }</td>
                                <td style="text-align: center;padding:4px;"><input type="text" min="0" max="${item.equipmentCount }" data-id="${item.equipment}" maxlength="3" class="spinnerInput" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        WEBGIS.equipmentAdd.init();
    });
</script>