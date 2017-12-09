<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link href="${ctx}/assert/ol/ol-3.19.0.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/assert/p-ol3/p-ol3.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/css/jquery_zTree/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/assert/p-ol3/customControlls.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet" type="text/css">
<link href="${ctx}/js/jquery.spinner/jquery.spinner.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/assert/spectrum/spectrum.css">
<style type="text/css">
</style>
<html>
<body>
    <form id="user-info-form">
        <input type="hidden" id="txt_is_director" value="${login_user.isDirector}"/>
        <input type="hidden" id="txt_unit_id" value="${login_user.unitId}"/>
        <input type="hidden" id="txt_isCrossUnit" value="${login_user.isCrossUnit}"/>
    </form>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>态势图</h5>
                        <a href="javascript:;" id="btn_fl" class="btn btn-xs btn-primary buttons">日志</a>
                        <a href="javascript:;" id="btn_info" class="btn btn-xs btn-primary buttons">资料查询</a>
                        <%--<a href="javascript:;" id="btn_sm" class="btn btn-xs btn-primary buttons">文电拟制</a>--%>
                        <a href="javascript:;" id="btn_sh" class="btn btn-xs btn-primary buttons">文电收发</a>
                        <a href="javascript:;" id="btn_ua" class="btn btn-xs btn-primary buttons">要图标绘</a>
                        <c:if test="${login_user.isDirector==1}">
                            <a href="javascript:;" id="btn_end" class="btn btn-xs btn-primary buttons">结束</a>
                            <a href="javascript:;" id="btn_start" class="btn btn-xs btn-primary buttons">开始</a>
                        </c:if>
                    </div>
                    <div class="widget-content clearfix">
                        <div>
                            <div style="float: left;width: 33%;font-size: 18px;">
                                <label style="float: left;">天文时间:</label>
                                <div id="div_now_date" style="float: left;margin-top: 1px;"></div>
                            </div>
                            <div id="div_step" style="float: left;width: 34%;text-align: center;line-height: 30px;">
                                步长&nbsp;&nbsp;1&nbsp;:&nbsp;<lable id="txt_step_length">${stepLength}</lable>
                                <c:if test="${login_user.isDirector == 1}">
                                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_save_step_length">设置步长</a>
                                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_pause" data-sta="${isPause}">
                                        <c:if test="${isPause eq 0}"><i class="fa fa-pause" aria-hidden="true"></i></c:if>
                                        <c:if test="${isPause eq 1}"><i class="fa fa-play" aria-hidden="true"></i></c:if>
                                    </a>
                                </c:if>
                            </div>
                            <div style="float: right;width: 33%;font-size: 18px;">
                                <c:if test="${login_user.isDirector == 1}">
                                    <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="float: right;margin-left: 6px;" id="btn_fight_time">设置</a>
                                </c:if>
                                <div id="div_fight_date"  style="float: right;margin-top: 1px;display:none;"></div>
                                <input type="hidden" id="txt_ftHideDigit" value="${ftHideDigit}"/>
                                <div id="div_fight_date_view"  style="float: right;margin-top: 1px;"></div>
                                <label style="float: right;">作战时间:</label>
                            </div>
                            <div style="clear: both;"></div>
                        </div>
                        <div class="cc-div" id="cc-div">
                            <ul class="cc-tools">
                                <a href="javascript:;" id="btn_zoom_in" title="放大">+</a>
                                <a href="javascript:;" id="btn_zoom_out" title="缩小">−</a>
                                <a href="javascript:;" id="btn_measure" title="测距"><i class="iconfont">&#xe713;</i></a>
                            </ul>
                            <ul class="cc-tools">
                                <%--<c:forEach var="item" items="${mainTypeMap}">
                                    <a href="javascript:;" title="${item.value}" data-mt="${item.key}" class="cc-a-tmp">${item.value}</a>
                                </c:forEach>--%>
                                <a href="javascript:;" id="btn_text" title="文字"><i class="iconfont">&#xe669;</i></a>
                            </ul>
                            <ul class="cc-tools">
                                <a href="javascript:;" id="btn_crowd" title="集群设置"><i class="fa fa-star-half-o"></i></a>
                                <a href="javascript:;" id="btn_view" title="显示切换"><i class="fa fa-eye"></i></a>
                                <a href="javascript:;" id="btn_save_to_sa" title="保存为要图标绘"><i class="fa fa-floppy-o"></i></a>
                                <a href="javascript:;" id="btn_formula" title="计算公式"><i class="fa fa-calculator" aria-hidden="true"></i></a>
                            </ul>
                            <div style="clear: both;"></div>
                        </div>
                    </div>
                    <div class="widget-content" id="div_map" style="position: relative;"></div>
                    <div id="div_popup" class="ol-popup">
                        <div id="div_popup_content"></div>
                    </div>

                    <%--<div class="cc-div" id="cc-div">
                        <ul class="cc-tools">
                            <a href="javascript:;" id="btn_zoom_in" title="放大">+</a>
                            <a href="javascript:;" id="btn_zoom_out" title="缩小">−</a>
                            <a href="javascript:;" id="btn_measure" title="测距"><i class="iconfont">&#xe713;</i></a>
                        </ul>
                        <ul class="cc-tools">
                            &lt;%&ndash;<c:forEach var="item" items="${igList}">
                                <a href="javascript:;" title="${item.name}" data-id="${item.id}" class="cc-a-tmp">${item.name}</a>
                            </c:forEach>&ndash;%&gt;
                            <c:forEach var="item" items="${mainTypeMap}">
                                <a href="javascript:;" title="${item.value}" data-mt="${item.key}" class="cc-a-tmp">${item.value}</a>
                            </c:forEach>
                            <a href="javascript:;" id="btn_text" title="文字"><i class="iconfont">&#xe669;</i></a>
                        </ul>
                        <ul class="cc-tools">
                            <a href="javascript:;" id="btn_crowd" title="集群设置"><i class="fa fa-star-half-o"></i></a>
                            <a href="javascript:;" id="btn_view" title="显示切换"><i class="fa fa-eye"></i></a>
                            <a href="javascript:;" id="btn_save_to_sa" title="保存为要图标绘"><i class="fa fa-floppy-o"></i></a>
                            <a href="javascript:;" id="btn_formula" title="计算公式"><i class="fa fa-calculator" aria-hidden="true"></i></a>
                        </ul>
                        <div style="clear: both;"></div>
                    </div>--%>
                </div>
            </div>
        </div>
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

    <div id="order_popup" class="ol-popup" style="display: none;">
        <a href="javascript:;" id="popup-closer" onclick=" WEBGIS.mapOrder.overlay.setPosition(undefined);" class="ol-popup-closer"></a>
        <div id="div_features_list">
            <div style="margin-bottom: 10px; margin-left: 2px;">选择图标：</div>
            <div class="list-group" id="div_features_list_sub"></div>
        </div>
        <div id="popup-content" style="display: none;">
            <div style="margin-bottom: 10px; margin-left: 2px;">选择动作：</div>
            <div class="list-group" id="div_popup_list">
                <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.selectOrder(1);">目的地移动<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.selectOrder(4);">方向移动<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.selectOrder(2);">维修<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.selectOrder(3);">装载<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.selectOrder(6);">设置受损程度<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <c:if test="${isDirector eq 1}">
                    <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.setRepairStatus();">下达维修指令<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                    <a href="#" class="list-group-item move-order" onclick="WEBGIS.mapOrder.setEquipStatus();">下达装载指令<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                </c:if>
                <a href="#" class="list-group-item airport-order" onclick="WEBGIS.mapOrder.selectOrder(7);">起飞<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item airport-order" onclick="WEBGIS.mapOrder.selectOrder(8);">降落<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item plane-order" onclick="WEBGIS.mapOrder.selectOrder(9);">降落<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item" onclick="WEBGIS.mapOrder.selectOrder(5);">资料查看<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" class="list-group-item" onclick="WEBGIS.mapOrder.deleteIcon();" style="margin-top:5px;">删除图标</a>
                <c:if test="${execsta eq '0'}">
                    <a href="#" class="list-group-item" onclick="WEBGIS.mapOrder.modifyLocation();" style="margin-top:5px;">修改坐标</a>
                </c:if>
            </div>
        </div>
    </div>

    <form id="form_move_angle" class="form-horizontal" style="width:385px;display: none;" novalidate="novalidate">
        <div class="form-group">
            <label class="col-sm-3 control-label">速度:</label>
            <div class="col-sm-8">
                <input type="text" id="txt_move_speed" value="" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">航向:</label>
            <div class="col-sm-8">
                <input type="text" id="txt_move_angle" value="" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-3 pull-right">
                <input type="button" style="margin-right:20px;" id="btn_move_angle_order_send" value="发送指令" class="btn btn-sm btn-primary buttons pull-right" />
            </div>
            <div class="col-sm-3 pull-right">
                <input type="button" id="btn_move_angle_order_cancel" onclick="WEBGIS.mapOrder.cancelOrder();" value="取消指令" class="btn btn-sm btn-primary buttons pull-right" />
            </div>
        </div>
    </form>

    <form id="form_repair_level" class="form-horizontal" style="width:385px;display: none;" novalidate="novalidate">
        <div class="form-group">
            <label class="col-sm-3 control-label">受损等级:</label>
            <label class="col-sm-8 control-label" id="lab_icon_repair_level" style="text-align: left;"></label>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">受损内容:</label>
            <label class="col-sm-8 control-label" id="lab_icon_repair_cont" style="text-align: left;"></label>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修人数:</label>
            <div class="col-sm-8">
                <input type="text" id="txt_repair_num" value="" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-3 pull-right">
                <input type="button" style="margin-right:20px;" id="btn_repair_order_send" value="发送指令" class="btn btn-sm btn-primary buttons pull-right" />
            </div>
            <div class="col-sm-3 pull-right">
                <input type="button" id="btn_repair_order_cancel" onclick="WEBGIS.mapOrder.cancelOrder();" value="取消指令" class="btn btn-sm btn-primary buttons pull-right" />
            </div>
        </div>
    </form>

    <div id="div_icon_info" style="display: none;"></div>

    <div id="div_crowd_view" style="display: none;"></div>

    <div id="div_icon_show_hide" style="display: none;text-align: center;margin-top: 20px;">
        <label style="display: inline-block; margin: 0 5px;cursor: pointer;vertical-align: middle;">
            <span style="padding-right: 4px;">全部</span>
            <input type="radio" name="r_icon_show_hide" value="1" style="display: inline-block;margin: 0;vertical-align: middle;">
        </label>
        <label style="display: inline-block; margin: 0 5px;cursor: pointer;vertical-align: middle;">
            <span style="padding-right: 4px;">飞机</span>
            <input type="radio" name="r_icon_show_hide" value="2" style="display: inline-block;margin: 0;vertical-align: middle;">
        </label>
        <label style="display: inline-block; margin: 0 5px;cursor: pointer;vertical-align: middle;">
            <span style="padding-right: 4px;">舰船</span>
            <input type="radio" name="r_icon_show_hide" value="3" style="display: inline-block;margin: 0;vertical-align: middle;">
        </label>
        <label style="display: inline-block; margin: 0 5px;cursor: pointer;vertical-align: middle;">
            <span style="padding-right: 4px;">集群</span>
            <input type="radio" name="r_icon_show_hide" value="4" style="display: inline-block;margin: 0;vertical-align: middle;">
        </label>
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

    <form id="form_location" class="form-horizontal" style="width:385px;display: none;" novalidate="novalidate">
        <div class="form-group">
            <label class="col-sm-3 control-label">经度:</label>
            <div class="col-sm-8">
                <input type="text" placeholder="" class="form-control" id="txt_longitude" value="" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">纬度:</label>
            <div class="col-sm-8">
                <input type="text" placeholder="" class="form-control" id="txt_latitude" value="" />
            </div>
        </div>
    </form>

<script src="${ctx}/js/layer-v2.2/layer/extend/layer.ext.js"></script>
<script src="${ctx}/js/laydate-v1.1/laydate/laydate.js"></script>
<script src="${ctx}/assert/ol/ol-debug-3.19.0.js"></script>
<script src="${ctx}/assert/p-ol3/p-ol3.min.js"></script>
<script src="${ctx}/js/jquery_zTree/jquery.ztree.core-3.5.min.js"></script>
<script src="${ctx}/js/jquery_zTree/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${ctx}/js/module/map.js?v=2016102814"></script>
<script src="${ctx}/js/module/iconwithprogressbar.js?v=2016102814"></script>
<script src="${ctx}/js/module/lanlon.js?v=2016102814"></script>
<script src="${ctx}/assert/p-ol3/customControlls.js"></script>
<script src="${ctx}/assert/spectrum/spectrum.js"></script>
<script src="${ctx}/js/module/myspectrum.js"></script>
<script src="${ctx}/js/module/plot/plot.js"></script>
<script src="${ctx}/js/jquery.spinner/jquery.spinner.js?v=2016102813"></script>
<script src="${ctx}/js/module/situationAnnotation/order_map.js"></script>
<script src="${ctx}/js/module/situationMap/situationMap.js?v=20161128007"></script>
<script type="text/javascript">
    window.resizeBy(screen.availWidth,screen.availHeight);
    window.topwindow = null;
    //当前作战时间(毫秒数)
    var execft = parseInt("${execft}");
    //当前步长
    var stepLength = ${stepLength};
    //是否暂停
    var isPause = ${isPause};
    //状态
    var execsta = "${execsta}";
    //当前推演地图Feature数据
    var iconData = ${eiDtos};
    //当前推演地图Feature的图片路径
    var visitpath = "${visitpath}";
    //当前用户所属单位
    var curUnit = "${curUnit}";


    var FILESERVER_ICON_VISITPATH = "${FILESERVER_ICON_VISITPATH}";

    $(function(){
        window.topwindow = window.open(ctx + "/sh/main", 'newwindow', 'height=800, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');

        $("#btn_ua").on("click", function(){
            window.open(ctx + "/userIcon/main", 'newwindow');
        });
        $("#btn_sh").on("click", function(){
            window.open(ctx + "/sh/main", 'newwindow');
        });
        $("#btn_sm").on("click", function(){
            window.open(ctx + "/sm/main", 'newwindow');
        });
        $("#btn_info").on("click", function(){
            window.open(ctx + "/info/main", 'newwindow');
        });
        $("#btn_fl").on("click", function(){
            window.open(ctx + "/hl/main", 'newwindow');
        });

        setInterval(function(){
            var ymdhms = formatDate();
            $("#div_now_date").html(ymdhms);
        },1000);


        if(execsta == "0"){
            //初始状态,显示作战时间
            var ymdhms = formatDate(execft);
            $("#div_fight_date").html(ymdhms);
            $("#div_fight_date_view").html(buildFightDateView(ymdhms));
        }

        if(execsta == "5"){
            //推演进行中,显示作战时间
            if(isPause == "1"){
                var ymdhms = formatDate(execft);
                $("#div_fight_date").html(ymdhms);
                $("#div_fight_date_view").html(buildFightDateView(ymdhms));
                $.post(ctx + '/smap/steps', {}, function (val) {
                    WEBGIS.map.steps = val;
                });
            }else{
                var ymdhms = formatDate(execft);
                $("#div_fight_date").html(ymdhms);
                $("#div_fight_date_view").html(buildFightDateView(ymdhms));
                WEBGIS.situationMap.fightTimeInterval = setInterval(fightTimeInterval,1000);
            }
        }

        if(execsta == "10"){
            //结束状态,显示结束时的作战时间
            var ymdhms = formatDate(execft);
            $("#div_fight_date").html(ymdhms);
            $("#div_fight_date_view").html(buildFightDateView(ymdhms));
        }

        $("#div_step a").on("click",function(){
            if($(this).hasClass("btn-primary")){
                return false;
            }
            var btnObj = $("#div_step a[class*='btn-primary']");
            btnObj.removeClass("btn-primary");
            btnObj.addClass("btn-info");
            $(this).removeClass("btn-info");
            $(this).addClass("btn-primary");
        });

        WEBGIS.situationMap.init(${execsta}, function(){
            return $("#div_fight_date").text();
        },function(){
            return $("#txt_step_length").text();
        });
    });

    function fightTimeInterval(){
        $.post(ctx + "/smap/getStepLength", {}, function (step) {
            var s = step.split(",");
            $("#txt_step_length").html(s[0]);
            if(s[1] == "10"){
                execsta = "10";
                WEBGIS.map.execsta = "10";
                clearInterval(WEBGIS.situationMap.fightTimeInterval);
            }
        });
        $.post(ctx + '/smap/steps', {}, function (val) {
            WEBGIS.map.steps = val;
        });
        $.post(ctx + '/smap/getFightTime', {}, function (ft) {
            execft = ft;
            var ymdhms = formatDate(parseInt(ft));
            $("#div_fight_date").html(ymdhms);
            $("#div_fight_date_view").html(buildFightDateView(ymdhms));
        });
    }

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

    function buildFightDateView(date){
        var count=$("#txt_ftHideDigit").val();
        if(isNaN(count)||count>4||count<1){
            return date;
        }
        var replaceStr="";
        for(var xx=0;xx<count;xx++){
            replaceStr+="X";
        }
        var array=date.split('');
        array.splice(4-count,count,replaceStr);
        return array.join("");
    }

</script>
</body>
</html>