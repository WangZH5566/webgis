(function (a) {
    a.mapOrder = {
        map: null,
        markerlayer: null,
        lineLayer:null,
        lineDraw:null,
        selectStyle:{},
        overlay:
            new ol.Overlay(({
                element: document.getElementById('order_popup'),
                autoPan: true,
                autoPanAnimation: {
                    duration: 250
                }
            })),
        firstSelect:true,
        order:null,
        startIcon:null,
        endIcon:null,
        pathArray:new Array(),
        loadListStr:null,
        moveAngle:null,
        moveSpeed:null,
        addEquipmentTime:null,
        repairNum:null,
        init: function () {
            a.mapOrder.initmap();
            WEBGIS.Helper.onlyNumber($("#txt_repair_num"));
            $("#btn_repair_order_send").on("click", a.iconRepairOrder.sendRepairOrder);
            $("#btn_move_angle_order_send").on("click", a.mapOrder.sendMoveAngleOrder);
        },
        newCrowdIconIdArr:new Array(),
        selectOrder:function(order){
            a.mapOrder.order=order;
            a.mapOrder.firstSelect=false;
            a.mapOrder.pathArray= [];
            a.mapOrder.overlay.setPosition(undefined);
            if(order==4){
                $("#txt_move_speed").val("");
                $("#txt_move_angle").val("");
                a.mapOrder.pathArray.push(a.mapOrder.startIcon.getGeometry().getCoordinates());
                layer.open({
                    type: 1,
                    area: ['400px', '200px'],
                    fix: false, //不固定
                    title: ["航向设置","font-weight:bold;"],
                    content: $("#form_move_angle"), //注意，如果str是object，那么需要字符拼接。
                    cancel :function(){
                        a.mapOrder.cancelOrder();
                    }
                });
            }else if(order==5){
                $("#div_icon_info").load(ctx + "/sa/queryIconInfo",{"id":a.mapOrder.startIcon.baseInfo,"mt":a.mapOrder.startIcon.mainType},function(){
                    layer.open({
                        type: 1,
                        area: ['600px', '300px'],
                        fix: false, //不固定
                        title: ["资料查看","font-weight:bold;"],
                        content: $("#div_icon_info"), //注意，如果str是object，那么需要字符拼接。
                        cancel :function(index){
                            a.mapOrder.cancelOrder();
                        }
                    });
                });
            }else if(order==6){
                //正在维修时,不可修改受损程度
                /*if(){
                    return false;
                }*/
                $("#div_icon_info").load(ctx + "/sa/queryIconDamage",{"id":a.mapOrder.startIcon.getId()},function(){
                    layer.open({
                        type: 1,
                        title:["设置受损程度","font-weight:bold;"],
                        area:['600px', '500px'],
                        closeBtn: 1,                //不显示关闭按钮
                        btn: ['确认', '取消'],
                        yes: function(index, layero){
                            var dt = trim($("#txt_dt").val());
                            if(dt == ""){
                                layer.msg("请输入维修总时长",{icon:2,time:1000});
                                return false;
                            }
                            var radioId = $("#table_damage_list input:radio:checked").data("trid");
                            if(typeof radioId == "undefined"){
                                layer.msg("请选择受损程度",{icon:2,time:1000});
                                return false;
                            }
                            var damageCont = $("#table_damage_list input:radio:checked").parent().next().text();
                            var chkIdArr = new Array();
                            var chkContArr = new Array();
                            $("#table_dd_" + radioId + " input:checkbox:checked").each(function(){
                                chkIdArr.push($(this).data("trid"));
                                chkContArr.push($(this).parent().next().text());
                            });

                            if(chkIdArr.length == 0){
                                layer.msg("请选择受损内容",{icon:2,time:1000});
                                return false;
                            }
                            $.post(ctx + "/sa/saveIconDamage", {"id":a.mapOrder.startIcon.getId(),"d":radioId,"dt":dt,"dd":chkIdArr.join(",")}, function (msg) {
                                if (msg != null && msg == "success") {
                                    a.mapOrder.startIcon.damage = radioId;
                                    a.mapOrder.startIcon.damageCont = damageCont;
                                    a.mapOrder.startIcon.damageDetail = chkIdArr.join(",");
                                    a.mapOrder.startIcon.damageDetailCont = chkContArr.join(",");
                                    a.mapOrder.startIcon.damageTime = dt;
                                    if(a.mapOrder.startIcon.damage == "7"){
                                        //轻度
                                        a.mapOrder.startIcon.getStyle().getImage().setDamageLevel(1);
                                    }else if(a.mapOrder.startIcon.damage == "8"){
                                        //中度
                                        a.mapOrder.startIcon.getStyle().getImage().setDamageLevel(2);
                                    }else if(a.mapOrder.startIcon.damage == "9"){
                                        //重度
                                        a.mapOrder.startIcon.getStyle().getImage().setDamageLevel(3);
                                    }else if(a.mapOrder.startIcon.damage == "10"){
                                        //完全损毁
                                        a.mapOrder.startIcon.getStyle().getImage().setDamageLevel(0);
                                    }
                                    layer.close(index);
                                    layer.msg("设置成功", {icon: 1, time: 1000});
                                } else {
                                    layer.msg(msg, {icon: 2, time: 1000});
                                }
                            });
                        },
                        shift: 0,
                        shadeClose: false,           //开启遮罩关闭
                        content: $("#div_icon_info"),
                        zIndex:100,
                        success: function(layero, index){
                            //文本框只允许输入数字
                            WEBGIS.Helper.onlyNumber($("#txt_dt"));
                            //左侧列表绑定行选中,radio选中事件
                            $("#table_damage_list tr:gt(0)").click(function(){
                                var trId = $(this).attr("id");
                                $("#table_damage_list #" + trId + " input:radio").prop("checked",true);
                                $("table[id^='table_dd_']").hide();
                                $("#table_dd_" + trId).show();
                            });
                            $("#table_damage_list input:radio").click(function(){
                                var trId = $(this).data("trid");
                                $("table[id^='table_dd_']").hide();
                                $("#table_dd_" + trId).show();
                            });
                            $("table[id^='table_dd_'] tr:gt(0)").click(function(){
                                var chkObj = $(this).find("input:checkbox");
                                if(chkObj.is(":checked")){
                                    chkObj.prop("checked",false);
                                }else{
                                    chkObj.prop("checked",true);
                                }
                            });
                        },
                        end:function(){
                            a.mapOrder.cancelOrder();
                            $("#div_icon_info").html("");
                        }
                    });
                });
            }else if(order==7){
                //起飞
                $("#div_icon_info").load(ctx + "/sa/takeOffSet",{"id":a.mapOrder.startIcon.getId()},function(){

                    $("#ul_tab li").click(function(){
                        if($(this).hasClass("active")){
                            return false;
                        }
                        $("#ul_tab li").removeClass("active");
                        $(this).addClass("active");

                        var type = $(this).data("type");
                        var divID="div_"+type;
                        $("div[name='div_show_for_tab']").hide();
                        $("#"+divID).show();
                    });

                    $("#tbody_plane_crowd tr td:nth-child(1) input:checkbox").click(function(e){
                        e.stopPropagation();
                    });

                    $("#tbody_plane_crowd tr td:nth-child(4) input:checkbox").click(function(e){
                        if($(this).is(":checked")){
                            $("#tbody_plane_crowd tr td:nth-child(4) input:checkbox").prop("checked",false);
                            $(this).prop("checked",true);
                        }
                        e.stopPropagation();
                    });

                    $("#tbody_plane_crowd tr").click(function(){
                        var chkObj = $(this).find("td:nth-child(1)").find("input:checkbox");
                        if(typeof chkObj.attr("disabled") == "undefined"){
                            if(chkObj.is(":checked")){
                                chkObj.prop("checked",false);
                            }else{
                                chkObj.prop("checked",true);
                            }
                        }
                    });

                    layer.open({
                        type: 1,
                        title:["起飞设置","font-weight:bold;"],
                        area:['600px', '500px'],
                        closeBtn: 1,                //不显示关闭按钮
                        btn: ['确认', '取消'],
                        yes: function(index, layero){
                            var cn = trim($("#txt_crowd_name").val());
                            if(cn == ""){
                                layer.msg("请输入[批号]", {icon:2, time: 1000});
                                return false;
                            }
                            var cs = trim($("#txt_crowd_speed").val());
                            if(cs == ""){
                                layer.msg("请输入[航速]", {icon:2, time: 1000});
                                return false;
                            }
                            if(isNaN(cs)){
                                layer.msg("[航速]请输入数值！",{icon:2,time:1000});
                                return false;
                            }
                            var ca = trim($("#txt_crowd_angle").val());
                            if(ca == ""){
                                layer.msg("请输入[航向]", {icon:2, time: 1000});
                                return false;
                            }
                            if(isNaN(ca)){
                                layer.msg("[航向]请输入数值！",{icon:2,time:1000});
                                return false;
                            }
                            if(Number(ca)>359 || Number(ca)<0){
                                layer.msg("[航向]的范围为0~359度,请输入合法[航向]",{icon:2,time:1000});
                                return false;
                            }

                            var chkArr = new Array();
                            $("#tbody_plane_crowd tr td:nth-child(1) input:checked").each(function(){
                                chkArr.push($(this).data("trid"));
                            });
                            if(chkArr.length == 0){
                                layer.msg("请勾选飞机", {icon:2, time: 1000});
                                return false;
                            }

                            var mainId = null;
                            $("#tbody_plane_crowd tr td:nth-child(1) input:checked").each(function(){
                                var chk = $(this).parent().parent().find("td:nth-child(4) input:checkbox:checked");
                                if(chk.data("trid") == null){
                                    return;
                                }
                                mainId = chk.data("trid");
                                return false;
                            });

                            if(mainId == null){
                                layer.msg("请勾选长机", {icon:2, time: 1000});
                                return false;
                            }

                            var param = {
                                "cn":cn,
                                "cs":cs,
                                "ca":ca,
                                "co":a.mapOrder.startIcon.getGeometry().getCoordinates().join(","),
                                "iconIds":chkArr.join(","),
                                "mainId":mainId,
                                "ft":$("#div_fight_date").html()
                            };

                            $.post(ctx + "/sa/addPlaneCrowd",param, function (msg) {
                                if (msg != null && msg.indexOf("|") > -1) {
                                    var jsonStr = msg.substring(msg.indexOf("|") + 1);
                                    var jsonObj = eval('(' + jsonStr + ')');
                                    for(var i=0;i<jsonObj.length;i++){
                                        var www = jsonObj[i].newestCoordinate.split(",");
                                        var ccc = new Array();
                                        ccc.push(parseFloat(www[0]));
                                        ccc.push(parseFloat(www[1]));
                                        var icon = {
                                            "id":jsonObj[i].id,
                                            "startPoint":ccc,
                                            "marker":visitpath + jsonObj[i].iconData,
                                            "iconId":jsonObj[i].iconId,
                                            "iconName":jsonObj[i].iconName,
                                            "feature_is_icon":true,
                                            "colorArray":jsonObj[i].colorArray,
                                            "speed":jsonObj[i].speed,
                                            "speed_unit":jsonObj[i].speedUnit,
                                            "unit_id":jsonObj[i].unitId,
                                            "baseInfo":jsonObj[i].baseInfoId,
                                            "mainType":jsonObj[i].mainType,
                                            "max_speed":jsonObj[i].maxSpeed,
                                            "damage":jsonObj[i].damage,
                                            "damageDetail":jsonObj[i].damageDetail,
                                            "damageTime":jsonObj[i].damageTime,
                                            "damageCont":jsonObj[i].damageCont,
                                            "damageDetailCont":jsonObj[i].damageDetailCont,
                                            "crowdId":jsonObj[i].crowdId,
                                            "crowdName":jsonObj[i].crowdName,
                                            "isMain":jsonObj[i].isMain,
                                            "belongAirport":jsonObj[i].belongAirport,
                                            "crowdDetailCont":jsonObj[i].crowdDetailCont,
                                            "isCrowdShow":"0"
                                        };
                                        WEBGIS.map.markerWhenCrowd(icon);
                                        //a.mapOrder.newCrowdIconIdArr.push(icon.id);
                                    }
                                    layer.msg("操作成功", {icon: 1, time: 1000});
                                    layer.close(index);
                                } else {
                                    layer.msg(msg, {icon: 2, time: 1000});
                                }
                            });
                        },
                        shift: 0,
                        shadeClose: false,           //开启遮罩关闭
                        content: $("#div_icon_info"),
                        zIndex:100,
                        success: function(layero, index){

                        },
                        end:function(){
                            a.mapOrder.cancelOrder();
                            $("#div_icon_info").html("");
                            /*if(a.mapOrder.newCrowdIconIdArr.length > 0){
                                setTimeout(function(){
                                    WEBGIS.customerControlls.corwdShow(a.mapOrder.newCrowdIconIdArr);
                                    a.mapOrder.newCrowdIconIdArr.length = 0;
                                },1000);
                            }*/
                        }
                    });
                });
            }else if(order==8){
                //降落
                $("#div_icon_info").load(ctx + "/sa/landingSet",{"id":a.mapOrder.startIcon.getId()},function(){
                    $("#tbody_langding_crowd tr td:nth-child(1) input:radio").click(function(e){
                        e.stopPropagation();
                    });
                    $("#tbody_langding_crowd tr").click(function(){
                        var radObj = $(this).find("td:nth-child(1)").find("input:radio");
                        if(!radObj.is(":checked")){
                            radObj.prop("checked",true);
                        }
                    });

                    $("#tbody_airport_list tr td:nth-child(1) input:radio").click(function(e){
                        e.stopPropagation();
                    });
                    $("#tbody_airport_list tr").click(function(){
                        var radObj = $(this).find("td:nth-child(1)").find("input:radio");
                        if(!radObj.is(":checked")){
                            radObj.prop("checked",true);
                        }
                    });

                    layer.open({
                        type: 1,
                        title:["降落设置","font-weight:bold;"],
                        area:['600px', '500px'],
                        closeBtn: 1,                //不显示关闭按钮
                        btn: ['确认', '取消'],
                        yes: function(index, layero){
                            if($("#tbody_langding_crowd tr td:nth-child(1) input:radio:checked").size() == 0){
                                layer.msg("请选择飞机集群",{icon:2,time:1000});
                                return false;
                            }
                            if($("#tbody_airport_list tr td:nth-child(1) input:radio:checked").size() == 0){
                                layer.msg("请选择所降落的机场",{icon:2,time:1000});
                                return false;
                            }
                            var param = {
                                "cid":null,
                                "plid":a.mapOrder.startIcon.getId(),
                                "mco":null,
                                "aco":a.mapOrder.startIcon.getGeometry().getCoordinates().join(","),
                                "ft":$("#div_fight_date").html()
                            };
                            $("#tbody_langding_crowd tr td:nth-child(1) input:radio:checked").each(function(){
                                param.cid = $(this).data("crowdid");
                                //集群旗舰id
                                var fid = $(this).data("iconid");
                                var fe = WEBGIS.map.markerLayer.getSource().getFeatureById(fid);
                                param.mco = fe.getGeometry().getCoordinates().join(",");
                                return false;
                            });

                            $("#tbody_airport_list tr td:nth-child(1) input:radio:checked").each(function(){
                                param.plid = $(this).data("id");
                                param.aco = $(this).data("co");
                                return false;
                            });
                            $.post(ctx + "/sa/addLandingOrder",param, function (msg) {
                                if (msg != null && msg == "success") {
                                    layer.msg("操作成功", {icon: 1, time: 1000});
                                    layer.close(index);
                                } else {
                                    layer.msg(msg, {icon: 2, time: 1000});
                                }
                            });
                        },
                        shift: 0,
                        shadeClose: false,           //开启遮罩关闭
                        content: $("#div_icon_info"),
                        zIndex:100,
                        success: function(layero, index){

                        },
                        end:function(){
                            a.mapOrder.cancelOrder();
                            $("#div_icon_info").html("");
                        }
                    });
                });
            }else if(order==9){
                var isCrowd = false;
                var isMain = false;
                var msg = "是否要单独降落飞机?";
                if(typeof  a.mapOrder.startIcon.crowdId != "undefined" && a.mapOrder.startIcon.crowdId != null){
                    isCrowd =true;
                }
                if(typeof  a.mapOrder.startIcon.isMain != "undefined" && a.mapOrder.startIcon.isMain != null && a.mapOrder.startIcon.isMain == "1"){
                    isMain = true;
                }
                if(isCrowd && isMain){
                    //msg = "当前飞机是所在集群长机，若要降落此长机，则当前集群会一同降落,是否继续?"

                    a.mapOrder.cancelOrder();
                    layer.msg("长机不允许单独降落",{icon:2,time:1000});
                    return;
                }
                if(isCrowd && !isMain){
                    msg = "当前飞机在集群中，若要单独降落，会将此飞机从集群中移除，是否继续?"
                }
                layer.confirm(msg, {
                    btn: ['确定','取消'] //按钮
                }, function(index){
                    layer.close(index);

                    $("#div_icon_info").load(ctx + "/sa/landingSet",{"id":a.mapOrder.startIcon.getId()},function(){
                        $("#tbody_langding_crowd").parent().parent().remove();

                        $("#tbody_airport_list tr td:nth-child(1) input:radio").click(function(e){
                            e.stopPropagation();
                        });
                        $("#tbody_airport_list tr").click(function(){
                            var radObj = $(this).find("td:nth-child(1)").find("input:radio");
                            if(!radObj.is(":checked")){
                                radObj.prop("checked",true);
                            }
                        });

                        layer.open({
                            type: 1,
                            title:["降落设置","font-weight:bold;"],
                            area:['600px', '500px'],
                            closeBtn: 1,                //不显示关闭按钮
                            btn: ['确认', '取消'],
                            yes: function(index, layero){

                                if($("#tbody_airport_list tr td:nth-child(1) input:radio:checked").size() == 0){
                                    layer.msg("请选择所降落的机场",{icon:2,time:1000});
                                    return false;
                                }
                                var param = {
                                    "fid":a.mapOrder.startIcon.getId(),
                                    "plid":null,
                                    "mco":a.mapOrder.startIcon.getGeometry().getCoordinates().join(","),
                                    "aco":null,
                                    "ft":$("#div_fight_date").html()
                                };

                                $("#tbody_airport_list tr td:nth-child(1) input:radio:checked").each(function(){
                                    param.plid = $(this).data("id");
                                    param.aco = $(this).data("co");
                                    return false;
                                });
                                $.post(ctx + "/sa/addLandingOrder",param, function (msg) {
                                    if (msg != null && msg == "success") {
                                        layer.msg("操作成功", {icon: 1, time: 1000});
                                        layer.close(index);
                                    } else {
                                        layer.msg(msg, {icon: 2, time: 1000});
                                    }
                                });
                            },
                            shift: 0,
                            shadeClose: false,           //开启遮罩关闭
                            content: $("#div_icon_info"),
                            zIndex:100,
                            success: function(layero, index){

                            },
                            end:function(){
                                a.mapOrder.cancelOrder();
                                $("#div_icon_info").html("");
                            }
                        });
                    });
                }, function(index){
                    a.mapOrder.cancelOrder();
                });
            }
            else{
                if(order==1){
                    layer.msg("请选择移动目的地",{time:1000});
                    //移动选中，开始选点
                }else if(order==2){
                    if(a.mapOrder.startIcon.damage == null){
                        a.mapOrder.cancelOrder();
                        layer.msg("请先设置受损程度",{icon:2,time:1000});
                        return false;
                    }
                    layer.msg("请选择维修点",{time:1000});
                }else if(order==3){
                    layer.msg("请选择装载目的地",{time:1000});
                }
                a.mapOrder.readyToDrawPath();
            }
        },
        deleteIcon:function(){
            a.mapOrder.overlay.setPosition(undefined);
            layer.confirm('确认删除该图标？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                layer.closeAll();
                $.post(ctx+"/sa/deleteIcon",{"id":a.mapOrder.startIcon.getId(),"mt":a.mapOrder.startIcon.mainType},function(re){
                    if(re.msg=="SUCCESS"){
                        var features=a.mapOrder.markerlayer.getSource().getFeatures();
                        for(var i=0;i<features.length;i++) {
                            var feature = features[i];
                            if(feature.belongAirport == a.mapOrder.startIcon.getId()){
                                a.mapOrder.markerlayer.getSource().removeFeature(feature);
                            }
                        }

                        a.mapOrder.markerlayer.getSource().removeFeature(a.mapOrder.startIcon);
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            }, function(){
                layer.closeAll();
            });
        },
        modifyLocation:function(){
            var fea = a.mapOrder.startIcon;
            var id = a.mapOrder.startIcon.getId();
            a.mapOrder.overlay.setPosition(undefined);
            layer.open({
                type: 1,
                title:["修改坐标","font-weight:bold;"],
                area:['400px','200px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var lot = $.trim($("#txt_longitude").val());
                    if(lot == ""){
                        layer.msg("请输入经度",{"icon":2,"time":1000});
                        return false;
                    }
                    var lat = $.trim($("#txt_latitude").val());
                    if(lat == ""){
                        layer.msg("请输入纬度",{"icon":2,"time":1000});
                        return false;
                    }
                    if(!/^\d{1,3},{0,1}\d{0,3},{0,1}\d{0,3}\.{0,1}\d*$/.test(lot)){
                        layer.msg("经度请输入时,分,秒的格式",{"icon":2,"time":1000});
                        return false;
                    }
                    if(!/^\d{1,3},{0,1}\d{0,3},{0,1}\d{0,3}\.{0,1}\d*$/.test(lat)){
                        layer.msg("纬度只允许输入数字或小数",{"icon":2,"time":1000});
                        return false;
                    }

                    $.post(ctx+"/sa/modifyLocation",{"id":id,"lot":lot,"lat":lat},function(r){
                        if(r != null && r.msg == "SUCCESS"){
                            //重新设置坐标
                            var www = r.result.split(",");
                            var ccc = new Array();
                            ccc.push(parseFloat(www[0]));
                            ccc.push(parseFloat(www[1]));
                            fea.getGeometry().setCoordinates(ccc);
                            layer.msg("操作成功！",{icon:1,time:1000});
                            layer.close(index);
                        }else{
                            layer.msg("操作失败！",{icon:2,time:1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#form_location"),
                zIndex:100,
                success: function(layero, index){

                },
                end:function(){
                    $("#txt_longitude").val("");
                    $("#txt_latitude").val("");
                }
            });
        },
        setRepairStatus:function(){
            layer.msg("操作成功！",{icon:1,time:1000});
            a.mapOrder.overlay.setPosition(undefined);
        },
        setEquipStatus:function(){
            layer.msg("操作成功！",{icon:1,time:1000});
            a.mapOrder.overlay.setPosition(undefined);
        },
        readyToDrawPath:function(){
            // 添加一个绘制的线使用的layer
            a.mapOrder.lineLayer = new ol.layer.Vector({
                source: new ol.source.Vector(),
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: 'red',
                        size: 2
                    })
                })
            });
            a.mapOrder.map.addLayer(a.mapOrder.lineLayer);
            a.mapOrder.lineDraw = new ol.interaction.Draw({
                type: 'LineString',
                source: a.mapOrder.lineLayer.getSource(),    // 注意设置source，这样绘制好的线，就会添加到这个source里
                style: new ol.style.Style({            // 设置绘制时的样式
                    stroke: new ol.style.Stroke({
                        color: 'red',
                        size: 2
                    })
                }),
                finishCondition:function(evt){
                    var pixel = a.mapOrder.map.getEventPixel(evt.originalEvent);
                    var hitFeatures = a.mapOrder.hasFeatureAtPixel(pixel);
                    if(hitFeatures.length == 0){
                        return false;
                    }
                    var hit = hitFeatures[0];
                    if(a.mapOrder.order!=1&&!hit){
                        return false;
                    }
                    if(hit&&hit.getId()==a.mapOrder.startIcon.getId()){
                        return false;
                    }
                    if(hit){
                        a.mapOrder.endIcon=hit;
                        evt.coordinate=hit.getGeometry().getCoordinates();
                    }
                    return true;
                }
            });

            var feature = new ol.Feature({
                geometry:new ol.geom.LineString(
                    [a.mapOrder.startIcon.getGeometry().getCoordinates(), a.mapOrder.startIcon.getGeometry().getCoordinates()])
            });
            feature.setStyle(new ol.style.Style({
                stroke: new ol.style.Stroke({
                    width: 3,
                    color: [255, 0, 0, 1]
                })
            }));
            a.mapOrder.lineDraw.extend(feature);
            // 监听线绘制结束事件，获取坐标
            a.mapOrder.lineDraw.on('drawend', function(event){
                // event.feature 就是当前绘制完成的线的Feature
                a.mapOrder.pathArray=event.feature.getGeometry().getCoordinates().slice(1);
                if(a.mapOrder.endIcon!=null){
                    a.mapOrder.pathArray[a.mapOrder.pathArray.length-1]=a.mapOrder.endIcon.getGeometry().getCoordinates();
                }
                if(a.mapOrder.order==1){
                    a.mapOrder.moveSelectDes(event.feature.getGeometry().getCoordinates()[event.feature.getGeometry().getCoordinates().length-1]);
                }else if(a.mapOrder.order==2){
                    $("#form_repair_level #lab_icon_repair_level").empty();
                    $("#form_repair_level #lab_icon_repair_level").html(a.mapOrder.startIcon.damageCont);
                    $("#form_repair_level #lab_icon_repair_cont").empty();
                    $("#form_repair_level #lab_icon_repair_cont").html(a.mapOrder.startIcon.damageDetailCont);
                    layer.open({
                        type: 1,
                        area: ['400px', '240px'],
                        fix: false, //不固定
                        title: ["受损等级设置","font-weight:bold;"],
                        content: $("#form_repair_level"), //注意，如果str是object，那么需要字符拼接。
                        cancel :function(){
                            a.mapOrder.clearLineDraw();
                            a.mapOrder.readyToDrawPath();
                            a.mapOrder.removeDes();
                            a.mapOrder.endIcon=null;
                        }
                    });
                    //a.mapOrder.pathArray.push(feature.getGeometry().getCoordinates());
                    ////移动或维修，立即发送指令
                    //a.mapOrder.sendOrder();
                }else if(a.mapOrder.order==3){
                    //装载，弹出装载列表
                    $.post(ctx+"/sa/equipmentAdd",{iconID: a.mapOrder.endIcon.getId()},function(str){
                        layer.open({
                            type: 1,
                            area: ['700px', '500px'],
                            fix: false, //不固定
                            title: ["装载列表","font-weight:bold;"],
                            content: str, //注意，如果str是object，那么需要字符拼接。
                            cancel :function(){
                                a.mapOrder.clearLineDraw();
                                a.mapOrder.readyToDrawPath();
                                a.mapOrder.removeDes();
                                a.mapOrder.endIcon=null;
                            }
                        });
                    })
                }else{
                    //指令错误
                    a.mapOrder.resetOrderStatus();
                }
            });
            a.mapOrder.map.addInteraction(a.mapOrder.lineDraw);
        },
        initmap: function () {
            a.mapOrder.map = WEBGIS.map.map;
            a.mapOrder.markerlayer = WEBGIS.map.markerLayer;
            a.mapOrder.map.addOverlay(a.mapOrder.overlay);
            //初始化右键
            WEBGIS.map.rightClickObj.bindEvent();
        },
        chooseFeature:function(event){
            var feature = event.data.f;
            $("#div_features_list").hide();
            if($("#txt_is_director").val()!="1" && ($("#txt_unit_id").val()-feature.unit_id)!=0){
                return false;
            }
            $("#div_popup_list a:lt(8)").show();
            if(feature.getProperties()["iconType"] == "1"){
                $("#div_popup_list a:lt(8)").hide();
            }
            $("#popup-content").show();
            a.mapOrder.featureSelect(feature);
        },
        hasFeatureAtPixel:function(pixel){
            var features = [];
            a.mapOrder.map.forEachFeatureAtPixel(pixel, function(feature, layer) {
                if(layer == a.mapOrder.markerlayer&&feature.feature_is_icon){
                    features.push(feature);
                }else{
                    //return null;
                }
            });
            return features;
        },
        clearSelect:function(){
            var features=a.mapOrder.markerlayer.getSource().getFeatures();
            for(var i=0;i<features.length;i++){
                var feature=features[i];
                if(feature.icon_attr_isSelect){
                    feature.icon_attr_damageLevel= Number(feature.icon_attr_damageLevel==null?"0":feature.icon_attr_damageLevel)-5;
                    feature.icon_attr_isSelect=false;
                    a.mapOrder.createStyle_src(feature,function(style){
                        feature.setStyle(style);
                        a.mapOrder.setFeatureScale(feature);
                    });
                }
            }
        },
        featureSelect:function(feature){
            var coordinates = feature.getGeometry().getCoordinates();
            //if(feature.icon_attr_isSelect){
            //
            //}else{
            //    a.mapOrder.clearSelect();
            //    feature.icon_attr_damageLevel= Number(feature.icon_attr_damageLevel==null?"0":feature.icon_attr_damageLevel)+5;
            //    feature.icon_attr_isSelect=true;
            //    a.mapOrder.createStyle_src(feature,function(style){
            //        feature.setStyle(style);
            //        a.mapOrder.setFeatureScale(feature);
            //    });
            //}
            if(a.mapOrder.firstSelect){
                $("#order_popup .list-group .plane-order").hide();
                if(feature.mainType!=0&&feature.mainType!=1){
                    $("#order_popup .list-group .move-order").hide();
                    if(feature.mainType == 7){
                        $("#order_popup .list-group .airport-order").show();
                    }
                }else{
                    $("#order_popup .list-group .move-order").show();
                    $("#order_popup .list-group .airport-order").hide();
                    if(feature.mainType == 1){
                        $("#order_popup .list-group .plane-order").show();
                    }
                }
                a.mapOrder.overlay.setPosition(coordinates);
                $("#order_popup").show();
                a.mapOrder.startIcon=feature;
            }
        },
        //newPointSelect:function(coordinates){
        //    //if(!a.mapOrder.firstSelect&&a.mapOrder.order==1){
        //    //    a.mapOrder.moveSelectDes(coordinates);
        //    //}
        //},
        moveSelectDes:function(coordinates){
            if(a.mapOrder.endIcon==null){
                a.mapOrder.markerDes(coordinates);
            }
            layer.confirm('确定要移动到该位置？', {
                btn: ['确定','取消指令'] //按钮
                ,cancel :function(){
                    a.mapOrder.cancelOrder();
                    a.mapOrder.removeDes();
                }
            }, function(){
                //发送移动指令
                a.mapOrder.sendOrder();
                a.mapOrder.removeDes();
            }, function(){
                a.mapOrder.cancelOrder();
                a.mapOrder.removeDes();
            });
        },
        sendMoveAngleOrder:function(){
            var moveAngle=$("#txt_move_angle").val();
            if(moveAngle==""){
                layer.msg("请填写航向",{icon:2,time:1000});
                return;
            }
            var moveSpeed=$("#txt_move_speed").val();
            if(moveSpeed==""){
                layer.msg("请填写速度",{icon:2,time:1000});
                return;
            }
            if(isNaN(moveSpeed)||isNaN(moveAngle)){
                layer.msg("速度和航向请输入数值！",{icon:2,time:1000});
                return false;
            }
            if(a.mapOrder.startIcon.max_speed!=null&& a.mapOrder.startIcon.max_speed!=""&&(Number(moveSpeed)> a.mapOrder.startIcon.max_speed||Number(moveSpeed)<0)){
                layer.msg("速度最大值为"+ a.mapOrder.startIcon.max_speed+",最小值为0,请输入合法速度",{icon:2,time:1000});
                return false;
            }
            a.mapOrder.moveAngle=moveAngle;
            a.mapOrder.moveSpeed=moveSpeed;
            a.mapOrder.sendOrder();
        },
        sendOrder:function(){
            var startIconID = a.mapOrder.startIcon.getId();
            var startIconName = a.mapOrder.startIcon.iconName;
            var endIcon = a.mapOrder.endIcon==null?null:a.mapOrder.endIcon.getId();
            var order = a.mapOrder.order;
            var loadListStr = a.mapOrder.loadListStr;
            var pathStr= JSON.stringify(a.mapOrder.pathArray);
            var repairNum=a.mapOrder.repairNum;
            var moveAngle= a.mapOrder.moveAngle;
            var moveSpeed= a.mapOrder.moveSpeed;
            var addEquipmentTime = a.mapOrder.addEquipmentTime;
            var param={
                "orderType":order,
                "pathCoordinate":pathStr,
                "iconOneId":startIconID,
                "iconTwoId":endIcon,
                "iconName":startIconName,
                "loadListStr":loadListStr,
                "moveAngle":moveAngle,
                "moveSpeed":moveSpeed,
                "fightBeginTimeView":$("#div_fight_date").html(),
                "addEquipmentTime":addEquipmentTime,
                "damage":a.mapOrder.startIcon.damage,
                "damageDetail":a.mapOrder.startIcon.damageDetail,
                "damageTime":a.mapOrder.startIcon.damageTime,
                "repairNum":repairNum
            };
            $.post(ctx+"/sa/sendOrder",param,function(re){
                if(re.msg=="SUCCESS"){
                    layer.closeAll();
                    layer.msg("指令发送成功",{icon:1,time:1000});
                }else{
                    layer.msg(re.msg,{icon:2,time:1000});
                }
            });
            a.mapOrder.resetOrderStatus();
        },
        resetOrderStatus:function(){
            a.mapOrder.startIcon=null;
            a.mapOrder.endIcon=null;
            a.mapOrder.firstSelect=true;
            a.mapOrder.order=null;
            a.mapOrder.loadListStr=null;
            a.mapOrder.pathArray= [];
            a.mapOrder.repairNum=null;
            a.mapOrder.moveAngle=null;
            a.mapOrder.clearLineDraw();
        },
        clearLineDraw:function(){
            if(a.mapOrder.lineLayer!=null){
                a.mapOrder.lineLayer.setVisible(false);
                a.mapOrder.map.removeLayer(a.mapOrder.lineLayer);
                a.mapOrder.lineLayer=null;
            }
            if(a.mapOrder.lineDraw!=null){
                a.mapOrder.map.removeInteraction(a.mapOrder.lineDraw);
                a.mapOrder.lineDraw=null;
            }
        },
        cancelOrder:function(){
            layer.closeAll();
            a.mapOrder.resetOrderStatus();
        },
        markerDes:function(point){
            // 添加一个空心的五星
            var star = new ol.Feature({
                geometry: new ol.geom.Point(point)
            });
            star.setStyle(new ol.style.Style({
                image: new ol.style.RegularShape({
                    points: 5,
                    radius1: 20,
                    radius2: 10,
                    stroke: new ol.style.Stroke({
                        color: 'red',
                        size: 2
                    }),
                    fill: new ol.style.Fill({
                        color: 'red'
                    })
                })
            }));
            star.setId("POINT_DES");
            a.mapOrder.setFeatureScale(star);
            a.mapOrder.markerlayer.getSource().addFeature(star);
        },
        removeDes:function(){
            a.mapOrder.unmarker("POINT_DES");
        },
        marker: function (val) {
            //设定锚点坐标位置
            var anchor = new ol.Feature({
                geometry: new ol.geom.Point(val.startPoint)
            });
            anchor = $.extend({}, val, anchor);
            anchor.setId(val.id);
            a.mapOrder.createStyle_src(val,function(style){
                anchor.setStyle(style);
                a.mapOrder.setFeatureScale(anchor);
            });
            a.mapOrder.markerlayer.getSource().addFeature(anchor);
        },
        buildColorData:function(context,image,options){
                var r =
                    [
                        0.6, 0.2, 0.2, 0, 0,
                        0.4, 0.1, 0.1, 0, 0,
                        0.3, 0.1, 0.05, 0, 0,
                        0,    0,    0, 1, 0,
                    ];
                var g =
                    [
                        0.4, 0.1, 0.1, 0, 0,
                        0.6, 0.2, 0.2, 0, 0,
                        0.3, 0.1, 0.05, 0, 0,
                        0,    0,    0, 1, 0,
                    ];
                var b =
                    [
                        0.4, 0.1, 0.1, 0, 0,
                        0.3, 0.1, 0.05, 0, 0,
                        0.6, 0.2, 0.2, 0, 0,
                        0,    0,    0, 1, 0,
                    ];
                var grayscaleMatrix =
                    [
                        0.33, 0.34, 0.33, 0, 0,
                        0.33, 0.34, 0.33, 0, 0,
                        0.33, 0.34, 0.33, 0, 0,
                        0,    0,    0, 1, 0,
                    ];
                var sepiaMatrix =
                    [
                        0.393, 0.769, 0.189, 0, 0,
                        0.349, 0.686, 0.168, 0, 0,
                        0.272, 0.534, 0.131, 0, 0,
                        0,     0,     0, 1, 0,
                    ];
                var image = context.getImageData(0, 0, image.width, image.height);
                a.mapOrder.colorMatrixFilter(image,grayscaleMatrix,options);
                context.putImageData(image, 0, 0);
        },
        colorMatrixFilter:function(pixels, m,options){
            var d = pixels.data;
            for (var i = 0; i < d.length; i += 4) {
                var r = d[i];
                var g = d[i + 1];
                var b = d[i + 2];
                var a = d[i + 3];
                d[i]   = r * m[0] + g * m[1] + b * m[2] + a * m[3] + m[4];
                d[i+1] = r * m[5] + g * m[6] + b * m[7] + a * m[8] + m[9];
                d[i+2] = r * m[10]+ g * m[11]+ b * m[12]+ a * m[13]+ m[14];
                d[i+3] = r * m[15]+ g * m[16]+ b * m[17]+ a * m[18]+ m[19];

                if(a==0){
                    d[i]   = 0;
                    d[i+1] =0;
                    d[i+2] = 0;
                    d[i+3] = 128;
                    if(options.icon_attr_damageLevel==null||options.icon_attr_damageLevel==""||options.icon_attr_damageLevel==0){
                        d[i+1] =255;
                    }else if(options.icon_attr_damageLevel<5){
                        d[i] =255;
                    }else{
                        d[i] =255;
                        d[i+3] = 255;
                    }

                }
            }
            return pixels;
        },
        createStyle_src:function(options,callback) {
            var image=new Image();
            image.crossOrigin="anonymous";
            var canvas = document.createElement('canvas');
            var context = canvas.getContext('2d');
            image.onload = function() {
                canvas.width = image.width;
                canvas.height = image.height;
                // Load the image into the context.
                context.drawImage(image, 0, 0, image.width, image.height);
                // Get and modify the image data.
                a.mapOrder.buildColorData(context,image,options);

                if(callback){
                    callback(a.mapOrder.createStyle_img(canvas));
                }
            }
            image.src=options.marker;
        },
        createStyle_img:function(img){
            return new ol.style.Style({
                image: new ol.style.Icon({
                    anchor: [0.5, 0.96],
                    img: img,
                    imgSize: img ? [img.width, img.height] : undefined,
                    crossOrigin : "anonymous"
                })
            });
        },
        setFeatureScale:function(feature){
            var style = feature.getStyle();
            // 重新设置图标的缩放率，基于层级10来做缩放
            style.getImage().setScale(((a.mapOrder.map.getView().getZoom()-4)/4+1));
            feature.setStyle(style);
        },
        unmarker: function (id) {
            var anchor = a.mapOrder.markerlayer.getSource().getFeatureById(id);
            if(anchor != null) a.mapOrder.markerlayer.getSource().removeFeature(anchor);
        }
    };
    a.iconRepairOrder = {
        sendRepairOrder:function(){
            if($("#txt_repair_num").val() == ""){
                layer.msg("请输入维修人数",{icon:2,time:1000});
                return false;
            }
            a.mapOrder.repairNum=$("#txt_repair_num").val();
            a.mapOrder.sendOrder();
            layer.closeAll();
        }
    };
    a.equipmentAdd = {
        init:function(){
            $('.spinnerInput').spinner({});
            $("#btn_save_equipment").on("click", a.equipmentAdd.sendLoadMsg);
            //文本框只允许输入数字
            WEBGIS.Helper.onlyNumber($("#txt_eqt"));
        },
        sendLoadMsg:function(){
            if(trim($("#txt_eqt").val()) == ""){
                layer.msg("请输入装载总时长",{icon:2,time:1000});
                return false;
            }
            var array=new Array();
            $('input.spinnerInput').each(function(){
                var id = $(this).data("id");
                var value=$(this).val();
                if(value!=""&&value>0){
                    array.push(id+"-"+value);
                }
            });
            a.mapOrder.addEquipmentTime = trim($("#txt_eqt").val());
            a.mapOrder.loadListStr=array.join(",");
            a.mapOrder.sendOrder();
            layer.closeAll();
        }
    };
})(WEBGIS);