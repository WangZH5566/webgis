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
                        <h5>台位编辑</h5>
                    </div>
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="form_search">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">台位名称:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_name" value="${dto.departmentName}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">台位代码:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_code" value="${dto.departmentCode}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2"></label>
                                    <div class="col-sm-8 form-div2">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" style="margin-top: 2px;" id="chk_cu" <c:if test="${dto.isCrossUnit eq 1}">checked="checked"</c:if>>能否跨单位发送电文
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <div class="col-sm-10 form-div2">
                                        <a href="${ctx}/department/main" class="btn btn-xs btn-primary buttons" style="float: right;">返回</a>
                                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="float: right;margin-right: 10px;" id="btn_save">保存</a>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </form>
                    </div>
                    <input type="hidden" id="txt_id" value="${dto.id}">
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/department/department.js?v=20161028002"></script>
    <script type="text/javascript">
        WEBGIS.departmentAddOrEdit.init();
    </script>
</body>
</html>