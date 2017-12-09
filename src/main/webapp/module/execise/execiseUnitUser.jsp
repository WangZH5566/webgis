<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/css/jquery_zTree/diybtn.css" rel="stylesheet">
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>单位列表</h5>
                    </div>
                    <div class="widget-content">
                        <div id="div_zTree" style="min-height:550px;overflow: auto;">
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>用户列表</h5>
                        <a href="${ctx}/exec/main" class="btn btn-xs btn-primary buttons">返回</a>
                    </div>
                    <div class="widget-content" >
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <thead>
                                <tr>
                                    <th style="width: 20%;">所属单位</th>
                                    <th style="width: 20%;">所属台位</th>
                                    <th style="width: 18%;">登录名</th>
                                    <th style="width: 18%;">用户名</th>
                                    <th style="width: 12%;">密码</th>
                                    <th style="width: 12%;">能否跨单位发送电文</th>
                                </tr>
                                </thead>
                                <tbody id="tbody_list">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn"></div>
                    </div>
                    <input type="hidden" id="txt_id" value="${id}">
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
    <script src="${ctx}/js/jquery_zTree/jquery.ztree.exedit-3.5.min.js"></script>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028001"></script>
    <script type="text/javascript">
        var zTreeNodes = ${zTreeNodes};
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                }
            },
            view: {
                showIcon: true,
                selectedMulti: false
            },
            callback:{
                onClick: WEBGIS.execUnitUser.zTreeOnClick,
            }
        };
        WEBGIS.execUnitUser.init();
    </script>
</body>
</html>