<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/assert/ol/ol-3.19.0.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/assert/p-ol3/p-ol3.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/assert/p-ol3/customControlls.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet" type="text/css">
<link href="${ctx}/js/jquery.spinner/jquery.spinner.css" rel="stylesheet" type="text/css">
<link href="${ctx}/assert/spectrum/spectrum.css" rel="stylesheet" type="text/css">
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-title">
                    <h5>要图标绘</h5>
                    <a href="javascript:;" id="btn_save_map" class="btn btn-xs btn-primary buttons">保存标绘</a>
                    <a href="javascript:;" id="btn_backup_list" class="btn btn-xs btn-primary buttons">备份列表</a>
                    <a href="${ctx}/userIcon/main" class="btn btn-xs btn-primary buttons">新增标绘</a>
                </div>
                <div class="widget-content clearfix">
                    <div class="cc-div" id="cc-div">
                        <ul class="cc-tools">
                            <a href="javascript:;" id="btn_zoom_in" title="放大">+</a>
                            <a href="javascript:;" id="btn_zoom_out" title="缩小">−</a>
                            <a href="javascript:;" id="btn_measure" title="测距"><i class="iconfont">&#xe713;</i></a>
                        </ul>
                        <ul class="cc-tools">
                            <c:forEach var="item" items="${mainTypeMap}">
                                <a href="javascript:;" title="${item.value}" data-mt="${item.key}" class="cc-a-tmp">${item.value}</a>
                            </c:forEach>
                            <a href="javascript:;" id="btn_text" title="文字"><i class="iconfont">&#xe669;</i></a>
                        </ul>
                        <ul class="cc-tools">
                            <a href="javascript:;" id="li_mark_label" title="标注框"><i class="iconfont">&#xe63e;</i></a>
                        </ul>
                        <div style="clear: both;"></div>
                    </div>
                </div>
                <div class="widget-content" id="div_map" style="position: relative;"></div>
                <div id="div_popup" class="ol-popup">
                    <div id="div_popup_content"></div>
                </div>
            </div>
        </div>
    </div>
    <div id="div_order_popup" class="ol-popup" style="display: none;">
        <a href="javascript:;" id="btn_popup_closer" class="ol-popup-closer"></a>
        <div id="popup-content">
            <div class="list-group">
                <a href="javascript:;" id="btn_delete_icon" class="list-group-item" style="margin-top:5px;">删除图标</a>
            </div>
        </div>
    </div>
    <div id="div_save_plot" style="display: none;padding-top: 12px;">
        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-left: 30px;" id="btn_save_map_img">保存为图片</a>
        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-left: 20px;" id="btn_save_plot">保存为矢量格式</a>
    </div>

    <div id="div_save_plot_cover" style="display: none;padding-top: 12px;">
        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-left: 30px;" id="btn_save_plot_cover">覆盖保存</a>
        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="margin-left: 20px;" id="btn_save_plot_another">另存为</a>
    </div>
    <div id="div_choose_icon" style="display: none;"></div>

    <div id="div_add_text" style="display: none;">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content">
                    <div>
                        <form class="form-horizontal" action="javascript:;" method="post" novalidate="novalidate">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="padding-left: 0;padding-right: 0;">文字内容:</label>
                                    <div class="col-sm-9">
                                        <textarea class="form-control" id="txt_textarea" rows="3" placeholder="" style="width: 315px;height: 120px;"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both;;"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id="txt_id" value="${uiid}">
</div>
<script src="${ctx}/js/layer-v2.2/layer/extend/layer.ext.js"></script>
<script src="${ctx}/assert/ol/ol-debug-3.19.0.js"></script>
<script src="${ctx}/assert/p-ol3/p-ol3.min.js"></script>
<script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
<script src="${ctx}/js/module/map.js?v=2016102813"></script>
<script src="${ctx}/js/module/iconwithprogressbar.js?v=2016102814"></script>
<script src="${ctx}/assert/spectrum/spectrum.js"></script>
<script src="${ctx}/js/module/myspectrum.js"></script>
<script src="${ctx}/js/module/execUserMap/FileSaver.min.js?v=20161028106"></script>
<script src="${ctx}/js/module/plot/plot_user.js"></script>
<script src="${ctx}/js/module/execUserMap/execUserMap.js?v=20161028106"></script>
<script type="text/javascript">
    var FILESERVER_ICON_VISITPATH="${FILESERVER_ICON_VISITPATH}";
    var mapiconjson=${icon};
    $(document).ready(function(){
        WEBGIS.execUserMap.init();
    });
</script>
</body>
</html>