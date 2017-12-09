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
                        <h5>第三步:创建用户</h5>
                        <a href="${ctx}/exec/main" class="btn btn-xs btn-primary buttons">返回</a>
                        <a href="${ctx}/exec/addStepFour?id=${exec_id}" class="btn btn-xs btn-primary buttons">下一步</a>
                        <a href="${ctx}/exec/addStepTwo?id=${exec_id}" class="btn btn-xs btn-primary buttons">上一步</a>
                        <%--<a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_add_user">新增用户</a>--%>
                    </div>
                    <div class="widget-content" >
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                <tr>
                                    <th style="width: 18%;">所属单位</th>
                                    <th style="width: 18%;">所属台位</th>
                                    <th style="width: 18%;">登录名</th>
                                    <th style="width: 18%;">用户名</th>
                                    <th style="width: 10%;">密码</th>
                                    <th style="width: 10%;">能否跨单位发送电文</th>
                                    <th style="width: 8%;">操作</th>
                                </tr>
                                </thead>
                                <tbody id="tbody_list">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn"></div>
                    </div>
                    <input type="hidden" id="txt_id" value="${exec_id}">
                </div>
            </div>
        </div>
    </div>

    <div  style="display: none;" id="div_user_info">
        <div class="widget-content">
            <form class="form-horizontal" action="#" method="post" novalidate="novalidate">
                <div class="col-sm-12">
                    <div class="form-group">
                        <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">登录名:</label>
                        <div class="col-sm-8 form-div2">
                            <input type="text" placeholder="" class="form-control" id="txt_login_name" value="" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">用户名:</label>
                        <div class="col-sm-8 form-div2">
                            <input type="text" placeholder="" class="form-control" id="txt_user_name" value="" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label form-label2" style="padding-left: 0;">密码:</label>
                        <div class="col-sm-8 form-div2">
                            <input type="text" placeholder="" class="form-control" id="txt_password" value="" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label form-label2"></label>
                        <div class="col-sm-8 form-div2">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" style="margin-top: 2px;" id="chk_cu">能否跨单位发送电文
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
            </form>
        </div>
    </div>

    <script src="${ctx}/js/module/execise/execise.js?v=20161028001"></script>
    <script type="text/javascript">
        WEBGIS.execStepThree.init();
    </script>
</body>
</html>