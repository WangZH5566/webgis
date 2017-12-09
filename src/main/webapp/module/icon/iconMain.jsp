<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
    .a-upload {
        position: relative;
        overflow: hidden;
        *display: inline;
        *zoom: 1
    }
    .a-upload  input {
        position: absolute;
        line-height: 26px;
        font-size: 10px;
        right: 0;
        top: 0;
        filter:alpha(opacity=0);
        -moz-opacity:0;
        -khtml-opacity:0;
        opacity:0;
        cursor: pointer
    }

    #tbody_list .icon_img{
        width: 38px;
        height: 38px;
        cursor: pointer;
    }
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>图标列表</h5>
                        <a href="javascript:;" id="btn_delete" class="btn btn-xs btn-primary buttons">删除</a>
                        <form style="float:right;margin: 0;" action="${ctx}/ic/upload.do" id="uploadForm" enctype="multipart/form-data" method="post" target="target_upload">
                            <a href="javascript:;" class="btn btn-xs btn-primary buttons a-upload">
                                <input type="file" name="btn_upLoad" id="btn_upLoad">上传
                            </a>
                        </form>
                        <iframe id="target_upload" name="target_upload" src="" style="display:none"></iframe>
                    </div>
                    <div class="widget-content clearfix" id="div_list">

                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="widget-box">
                    <div class="widget-content" style="min-height: 550px;text-align: center;" id="preview">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/global/progressBar.js" type="text/javascript"></script>
    <script src="${ctx}/js/module/icon/icon.js?v=20161028004"></script>
    <script type="text/javascript">
        WEBGIS.icon.init();
    </script>
</body>
</html>