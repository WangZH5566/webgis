<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/assert/ol/ol-3.19.0.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/assert/p-ol3/customControlls.css" rel="stylesheet" type="text/css">
<style type="text/css">
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>历史回顾</h5>
                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_begin_review">开始回顾</a>
                    </div>
                    <div class="widget-content clearfix">
                        <div style="float: left;width: 33%;font-size: 18px;">
                            <label style="float: left;">作战起始时间:</label>
                            <div id="div_fight_date_start" style="float: left;margin-top: 1px;"></div>
                            <label style="float: left;">作战结束时间:</label>
                            <div id="div_fight_date_end" style="float: left;margin-top: 1px;"></div>
                        </div>
                        <div id="div_step" style="float: left;width: 34%;text-align: center;line-height: 30px;">
                            步长&nbsp;&nbsp;1&nbsp;:&nbsp;<lable id="txt_step_length">1</lable>
                            <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_save_step_length">设置步长</a>
                        </div>
                        <div  style="float: right;width: 33%;font-size: 18px;">
                            <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="float: right;margin-left: 6px;" id="btn_fight_time">设置</a>
                            <div id="div_fight_date"  style="float: right;margin-top: 1px;"></div>
                            <label style="float: right;">作战时间:</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-content" id="div_map" style="position: relative;"></div>
                    <div id="div_popup" class="ol-popup">
                        <div id="div_popup_content"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-content">
                        <div id="div_list">
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" id="txt_exec_id" value="${execId}">
        </div>
    </div>

    <form id="form_step" class="form-horizontal" style="display: none;" novalidate="novalidate">
        <div class="form-group" style="margin: 0;">
            <label class="col-sm-3 control-label">步长:</label>
            <div class="col-sm-8">
                <input type="text" placeholder="" class="form-control" id="txt_step" value="" />
            </div>
        </div>
    </form>

    <form id="form_fight_time" class="form-horizontal" style="display: none;" novalidate="novalidate">
        <div class="form-group" style="margin: 0;">
            <label class="col-sm-3 control-label">作战时间:</label>
            <div class="col-sm-8">
                <input type="text" placeholder="" class="form-control" id="txt_fight_time" value="" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
            </div>
        </div>
    </form>

    <script src="${ctx}/js/layer-v2.2/layer/extend/layer.ext.js"></script>
    <script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
    <script src="${ctx}/assert/ol/ol-debug-3.19.0.js"></script>
    <script src="${ctx}/assert/p-ol3/p-ol3.min.js"></script>
    <script src="${ctx}/js/module/iconwithprogressbar.js?v=2016102814"></script>
    <script src="${ctx}/js/module/lanlon.js?v=2016102814"></script>
    <script src="${ctx}/js/module/map.js?v=2016102814"></script>
    <script src="${ctx}/js/module/execise/execise.js?v=20161028001"></script>
    <script type="text/javascript">
        //当前推演地图Feature数据
        var iconData = ${eiDtos};
        //当前推演地图Feature的图片路径
        var visitpath = "${visitpath}";
        //当前推演地图Feature数据
        var historyLogDtos = ${historyLogDtos};

        //当前作战时间(毫秒数)
        var execft = parseInt("${execft}");
        //当前作战起始时间(毫秒数)
        var execfts = parseInt("${execft}");
        //当前作战结束时间(毫秒数)
        var execfte = parseInt("${execfte}");
        //步长数组
        //var stepArr = new Array();

        $(function () {
            // 作战时间显示
            var ymdhms = formatDate(execft);
            var ymdhmsse = formatDate(execfte);
            $("#div_fight_date").html(ymdhms);
            $("#div_fight_date_start").html(ymdhms);
            $("#div_fight_date_end").html(ymdhmsse);
            WEBGIS.execReview.init();
        });

        function formatDate(millisecond){
            var currentDT = millisecond != null ? new Date(millisecond) : new Date();
            var y,m,d,hs,ms,ss,theDateStr;
            y = currentDT.getFullYear(); //四位整数表示的年份
            m = currentDT.getMonth() + 1; //月
            d= currentDT.getDate(); //日
            hs = currentDT.getHours(); //时
            ms = currentDT.getMinutes(); //分
            ss = currentDT.getSeconds(); //秒
            theDateStr = y +"-"+ WEBGIS.Helper.pad(m, 2) +"-"+ WEBGIS.Helper.pad(d, 2) +" " + WEBGIS.Helper.pad(hs, 2) +":" + WEBGIS.Helper.pad(ms, 2) + ":" + WEBGIS.Helper.pad(ss, 2);
            return theDateStr;
        }
    </script>
</body>
</html>