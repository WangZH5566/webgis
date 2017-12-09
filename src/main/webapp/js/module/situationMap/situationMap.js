(function (a) {
    a.situationMap = {
        fightTimeInterval:null,
        init: function (execsta,currentExecft,speedRatio) {
            laydate.skin('danlan');
            $("#btn_start").on("click", a.situationMap.btnStartClick);
            $("#btn_end").on("click", a.situationMap.btnEndClick);
            $("#btn_pause").on("click", a.situationMap.btnPauseClick);
            WEBGIS.Helper.onlyNumber($("#txt_step"));
            if($("#btn_save_step_length").length > 0){
                $("#btn_save_step_length").on("click", a.situationMap.btnStepSaveClick);
            }
            if($("#btn_fight_time").length > 0){
                $("#btn_fight_time").on("click", a.situationMap.btnFightTimeClick);
            }
            a.situationMap.initmap(execsta,currentExecft,speedRatio);
        },
        btnStartClick: function () {
            var step = $("#txt_step_length").html();
            if(step == ""){
                layer.msg("请输入步长", {icon: 2, time: 1000});
                return false;
            }
            $.post(ctx + "/exec/startExecise", {"step":step}, function (msg) {
                if (msg != null && msg.indexOf("|") > -1) {
                    window.location.href = ctx + "/smap/main";
                } else {
                    layer.msg(msg, {icon: 2, time: 1000});
                }
            });
        },
        btnEndClick: function () {
            $.post(ctx + "/exec/endExecise", {"endFightTime":$("#div_fight_date").text()}, function (msg) {
                if (msg != null && msg == "success") {
                    layer.msg("推演已结束", {icon: 2, time: 1000});
                    clearInterval(WEBGIS.situationMap.fightTimeInterval);
                } else {
                    layer.msg(msg, {icon: 2, time: 1000});
                }
            });
        },
        btnStepSaveClick: function () {
            if(execsta == "0"){
                layer.msg("推演未开始", {icon: 2, time: 1000});
                return false;
            }
            if(execsta == "10"){
                layer.msg("推演已结束", {icon: 2, time: 1000});
                return false;
            }
            if($("#btn_pause").data("sta") == "1"){
                layer.msg("暂停时不能修改步长", {icon: 2, time: 1000});
                return false;
            }
            layer.open({
                type: 1,
                title:["设置步长","font-weight:bold;"],
                area:['410px','250px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var st = trim($("#txt_step").val());
                    if(st == ""){
                        layer.msg("请输入步长", {icon: 2, time: 1000});
                        return false;
                    }
                    $.post(ctx + "/exec/stepSave", {"step":st}, function (msg) {
                        if (msg != null && msg == "success") {
                            $("#txt_step_length").html(st);
                            layer.close(index);
                            layer.msg("修改成功", {icon: 1, time: 1000});
                        } else {
                            layer.msg(msg, {icon: 2, time: 1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#form_step"),
                zIndex:100,
                success: function(layero, index){},
                end:function(){
                    $("#txt_step").val("");
                }
            });

        },
        btnPauseClick:function(){
            if(execsta == "0"){
                layer.msg("推演未开始", {icon: 2, time: 1000});
                return false;
            }
            if(execsta == "10"){
                layer.msg("推演已结束", {icon: 2, time: 1000});
                return false;
            }
            var btnObj = $(this);
            if(btnObj.data("sta") == "0"){
                //暂停
                layer.confirm("确认要暂停吗?", {
                    btn: ["确认","取消"] //按钮
                }, function(index){
                    btnObj.data("sta","1");
                    btnObj.find("i").removeClass("fa-pause").addClass("fa-play");
                    $.post(ctx + "/smap/pauseExecise", {"isPause":"1"}, function (msg) {
                        if (msg != null && msg == "success") {
                            clearInterval(WEBGIS.situationMap.fightTimeInterval);
                        } else {
                            layer.msg(msg, {icon: 2, time: 1000});
                        }
                    });
                    layer.close(index);
                }, function(){});
            }else if(btnObj.data("sta") == "1"){
                //播放
                layer.confirm("确认要开始吗?", {
                    btn: ["确认","取消"] //按钮
                }, function(index){
                    btnObj.data("sta","0");
                    btnObj.find("i").removeClass("fa-play").addClass("fa-pause");
                    $.post(ctx + "/smap/pauseExecise", {"step":$("#txt_step_length").html(),"isPause":"0"}, function (msg) {
                        if (msg != null && msg == "success") {
                            execft -= 1000 * parseInt($("#txt_step_length").html());
                            WEBGIS.situationMap.fightTimeInterval = setInterval(fightTimeInterval,1000);
                        } else {
                            layer.msg(msg, {icon: 2, time: 1000});
                        }
                    });
                    layer.close(index);
                }, function(){});
            }
        },
        btnFightTimeClick:function(){
            if(execsta == "0"){
                layer.msg("推演未开始", {icon: 2, time: 1000});
                return false;
            }
            if(execsta == "10"){
                layer.msg("推演已结束", {icon: 2, time: 1000});
                return false;
            }
            if($("#btn_pause").data("sta") == "1"){
                layer.msg("暂停时不能修改作战时间", {icon: 2, time: 1000});
                return false;
            }
            var fbt = $("#div_fight_date").html();
            $("#txt_fight_time").val($("#div_fight_date").html());
            layer.open({
                type: 1,
                title:['设置作战时间','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                skin: 'layui-layer-lan',   //样式类名
                area:['410px','250px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var dataStr = trim($("#txt_fight_time").val());
                    if(dataStr == ""){
                        layer.msg("请输入[作战时间]", {icon: 2, time: 1000});
                        return false;
                    }
                    var dataNow = dataStr.replace(/-/g,"/");
                    var dataPrev = fbt.replace(/-/g,"/");
                    var dataNowTime = new Date(dataNow).getTime();
                    var dataPrevTime = new Date(dataPrev).getTime();
                    if(dataNowTime <= dataPrevTime){
                        layer.msg("[作战时间]不能回跳", {icon: 2, time: 1000});
                        return false;
                    }
                    $.post(ctx + "/smap/fightTime", {"fbt":fbt,"fet":dataStr}, function (msg) {
                        if (msg != null && msg == "success") {
                            layer.close(index);
                        } else {
                            layer.msg(msg, {icon: 2, time: 1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#form_fight_time"),
                zIndex:100,
                success: function(layero, index){
                },
                end:function(){
                    $("#txt_fight_time").val("");
                }
            });
        },
        /*initmap: function (execsta,currentExecft,speedRatio) {
            // ?service=WMS&version=1.1.0&request=GetMap&layers=cite:test1&styles=&bbox=0.0,-5.684E-13,115.39583333333371,169.64583333333331&width=522&height=768&srs=EPSG:4326&format=application/openlayers
            var param = {
                map: {
                    url: geoserver + "/" + "cite/wms",
                    layer: "cite:test1",
                    bound: "0.0,-5.684E-13,115.39583333333371,169.64583333333331",
                    num:1,
                    resolution: null
                },
                target: 'div_map',
                popup: 'div_popup'
            };
            //初始化地图
            WEBGIS.map.execsta = execsta;
            WEBGIS.map.currentExecft = currentExecft;
            WEBGIS.map.speedRatio = speedRatio;
            WEBGIS.map.drag.needDrag = false;
            WEBGIS.map.show(param);

            //初始化自定义工具栏
            //var viewport = WEBGIS.map.map.getViewport();
            //$(viewport).find("div[class='ol-overlaycontainer-stopevent']").append($("#cc-div"));
            WEBGIS.customerControlls.init();
            $("#cc-div").show();

            //初始化测量工具
            WEBGIS.map.initMeasure();
            //初始化绘制工具
            WEBGIS.map.initDraw();
            //初始化运动
            WEBGIS.map.initMove();
            //初始化hover事件
            WEBGIS.map.initHover();

            //加载地图上的指标
            if(iconData.length > 0){
                for(var i=0;i<iconData.length;i++){
                    if(typeof iconData[i].newestCoordinate == "undefined" || iconData[i].newestCoordinate == null){
                        //还在机场内部的飞机
                        continue;
                    }
                    var www = iconData[i].newestCoordinate.split(",");
                    var ccc = new Array();
                    ccc.push(parseFloat(www[0]));
                    ccc.push(parseFloat(www[1]));
                    if(iconData[i].iconType == "0"){
                        if(curUnit != "" && iconData[i].unitId != curUnit){
                            //只能看到属于自己权限下的图标(导演可看到所有)
                            continue;
                        }
                        var icon = {
                            "id":iconData[i].id,
                            "startPoint":ccc,
                            "marker":visitpath + iconData[i].iconData,
                            "iconId":iconData[i].iconId,
                            "iconName":iconData[i].iconName,
                            "feature_is_icon":true,
                            "colorArray":iconData[i].colorArray,
                            "speed":iconData[i].speed,
                            "speed_unit":iconData[i].speedUnit,
                            "unit_id":iconData[i].unitId,
                            "baseInfo":iconData[i].baseInfoId,
                            "mainType":iconData[i].mainType,
                            "max_speed":iconData[i].maxSpeed,
                            "damage":iconData[i].damage,
                            "damageDetail":iconData[i].damageDetail,
                            "damageTime":iconData[i].damageTime,
                            "damageCont":iconData[i].damageCont,
                            "damageDetailCont":iconData[i].damageDetailCont,
                            "crowdId":iconData[i].crowdId,
                            "crowdName":iconData[i].crowdName,
                            "isMain":iconData[i].isMain,
                            "belongAirport":iconData[i].belongAirport,
                            "crowdDetailCont":iconData[i].crowdDetailCont,
                            "isCrowdShow":"0"
                        };
                        WEBGIS.map.markerByxxx(icon);
                    }else if(iconData[i].iconType == "1"){
                        var json = {
                            "id":iconData[i].id,
                            "startPoint":ccc,
                            "iconText":iconData[i].iconName
                        };
                        WEBGIS.map.markerText(json);
                    }
                }
            }

            if(execsta == "5"){
                setInterval(function () {
                    $.ajax({
                        url: ctx + '/sa/listOrder',
                        success: function (val) {
                            if (val.code != 0) {
                                return;
                            }
                            WEBGIS.map.appendOrder(val.result);
                        }
                    });
                }, 1000);
            }
            WEBGIS.mapOrder.init();
        }*/
        initmap: function (execsta,currentExecft,speedRatio) {
             // http://localhost:8888/geoserver/cite/wms?service=WMS&version=1.1.0&request=GetMap&layers=cite:test1&styles=&bbox=0.0,-5.684E-13,115.39583333333371,169.64583333333331&width=522&height=768&srs=EPSG:4326&format=application/openlayers
            $.ajax({
                url: ctx + '/smap/map',
                type: 'post',
                data: {},
                dataType: 'json',
                success: function (val) {
                    if (val.code != 0) {
                        layer.msg("海图装载失败！", {icon: 2, time: 1000});
                        return;
                    }
                    var param = {
                        map: {
                            url: geoserver + "/" + val.result.url,
                            layer: val.result.layer,
                            bound: val.result.bound,
                            num:val.result.layerNum,
                            resolution: val.result.resolution
                        },
                        target: 'div_map',
                        popup: 'div_popup'
                    };
                    //初始化地图
                    WEBGIS.map.execsta = execsta;
                    WEBGIS.map.currentExecft = currentExecft;
                    WEBGIS.map.speedRatio = speedRatio;
                    WEBGIS.map.drag.needDrag = false;
                    WEBGIS.map.show(param);

                    //初始化自定义工具栏
                    // var viewport = WEBGIS.map.map.getViewport();
                    // $(viewport).find("div[class='ol-overlaycontainer-stopevent']").append($("#cc-div"));
                    WEBGIS.customerControlls.init();
                    $("#cc-div").show();

                    //初始化测量工具
                    WEBGIS.map.initMeasure();
                    //初始化绘制工具
                    WEBGIS.map.initDraw();
                    //初始化运动
                    WEBGIS.map.initMove();
                    //初始化hover事件
                    WEBGIS.map.initHover();

                    //加载地图上的指标
                    if(iconData.length > 0){
                        for(var i=0;i<iconData.length;i++){
                            if(typeof iconData[i].newestCoordinate == "undefined" || iconData[i].newestCoordinate == null){
                                //还在机场内部的飞机
                                continue;
                            }
                            var www = iconData[i].newestCoordinate.split(",");
                            var ccc = new Array();
                            ccc.push(parseFloat(www[0]));
                            ccc.push(parseFloat(www[1]));
                            if(iconData[i].iconType == "0"){
                                if(curUnit != "" && iconData[i].unitId != curUnit){
                                    //只能看到属于自己权限下的图标(导演可看到所有)
                                    continue;
                                }
                                var icon = {
                                    "id":iconData[i].id,
                                    "startPoint":ccc,
                                    "marker":visitpath + iconData[i].iconData,
                                    "iconId":iconData[i].iconId,
                                    "iconName":iconData[i].iconName,
                                    "feature_is_icon":true,
                                    "colorArray":iconData[i].colorArray,
                                    "speed":iconData[i].speed,
                                    "speed_unit":iconData[i].speedUnit,
                                    "unit_id":iconData[i].unitId,
                                    "baseInfo":iconData[i].baseInfoId,
                                    "mainType":iconData[i].mainType,
                                    "max_speed":iconData[i].maxSpeed,
                                    "damage":iconData[i].damage,
                                    "damageDetail":iconData[i].damageDetail,
                                    "damageTime":iconData[i].damageTime,
                                    "damageCont":iconData[i].damageCont,
                                    "damageDetailCont":iconData[i].damageDetailCont,
                                    "crowdId":iconData[i].crowdId,
                                    "crowdName":iconData[i].crowdName,
                                    "isMain":iconData[i].isMain,
                                    "belongAirport":iconData[i].belongAirport,
                                    "crowdDetailCont":iconData[i].crowdDetailCont,
                                    "isCrowdShow":"0"
                                };
                                WEBGIS.map.markerByxxx(icon);
                            }else if(iconData[i].iconType == "1"){
                                var json = {
                                    "id":iconData[i].id,
                                    "startPoint":ccc,
                                    "iconText":iconData[i].iconName
                                };
                                WEBGIS.map.markerText(json);
                            }
                        }
                    }

                    if(execsta == "5"){
                        setInterval(function () {
                            $.ajax({
                                url: ctx + '/sa/listOrder',
                                success: function (val) {
                                    if (val.code != 0) {
                                        return;
                                    }
                                    WEBGIS.map.appendOrder(val.result);
                                }
                            });
                        }, 1000);
                    }
                    WEBGIS.mapOrder.init();
                }
            });
        }
    }
})(WEBGIS);