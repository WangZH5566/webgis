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
            <div class="col-md-4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>文电列表</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_delete">删除</a>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_create">创建</a>
                    </div>
                    <div class="widget-content">
                        <div id="div_list">
                            <table class="table table-bordered" id="table_list">
                                <tbody id="tbody_list">
                                </tbody>
                            </table>
                        </div>
                        <div id="div_page_btn"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="widget-box">
                    <div class="widget-content">
                        <iframe id="f_cont" src="" width="100%" height="510px" seamless></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="d_rd" style="display: none;">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content">
                    <form class="form-horizontal" action="${ctx}/sm/upload" method="post" enctype="multipart/form-data" target="target_upload" id="form_submit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label form-label2">文电名称:</label>
                            <div class="col-sm-8 form-div2">
                                <input type="text" placeholder="" class="form-control" id="txt_tname" name="tname" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label form-label2">文电内容:</label>
                            <div class="col-sm-8 form-div2">
                                <input type="file" placeholder="" class="form-control" id="txt_file" name="tfile" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <iframe id="target_upload" name="target_upload" src="" style="display:none"></iframe>
    <script src="${ctx}/js/global/progressBar.js" type="text/javascript"></script>
    <script src="${ctx}/js/module/situationMake/situationMake.js?v=20161028001"></script>
    <script type="text/javascript">
        var tvisitPath = '${tvisitPath}';
        WEBGIS.sm.init();
    </script>
</body>
</html>