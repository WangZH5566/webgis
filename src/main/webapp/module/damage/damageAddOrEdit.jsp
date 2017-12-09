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
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="from_save">
                            <c:if test="${modifyType eq '0'}">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">受损程度:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_name" name="damageName" value="${dto.damageName}" />
                                    </div>
                                </div>
                            </div>
                            <%--<div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">图标文件:</label>
                                    <div class="col-sm-9 form-div2">
                                        <input type="file" placeholder="请选择图片" class="form-control" id="f_icon_img" name="image" style="border:none;box-shadow:none;" />
                                    </div>
                                </div>
                            </div>--%>
                            </c:if>
                            <c:if test="${modifyType eq '1'}">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">受损程度:</label>
                                        <div class="col-sm-9 form-div2">
                                            <input type="text" placeholder="" class="form-control" id="txt_name" name="damageName" value="${dto.damageName}" />
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${modifyType eq '2'}">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">图标文件:</label>
                                        <div class="col-sm-9 form-div2">
                                            <input type="file" placeholder="请选择图片" class="form-control" id="f_icon_img" name="image" style="border:none;box-shadow:none;" />
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div style="clear: both"></div>
                            <input type="hidden" id="txt_id" name="id" value="${dto.id}">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/jquery/jquery.form-3.51.0.js"></script>
    <script src="${ctx}/js/module/damage/damage.js?v=20161028002"></script>
    <script type="text/javascript">
    </script>
</body>
</html>