(function (a) {
    a.situationMap = {
        init: function () {
            $("#btn_start").on("click", a.situationMap.btnStartClick);
            $("#btn_end").on("click", a.situationMap.btnEndClick);
            a.situationMap.initmap();
        },
        btnStartClick: function () {
            $.post(ctx + "/exec/startExecise", {}, function (msg) {
                if (msg != null && msg.indexOf("|") > -1) {
                    layer.msg("推演已开始", {icon: 1, time: 1000});
                } else {
                    layer.msg(msg, {icon: 2, time: 1000});
                }
            });
        },
        btnEndClick: function () {
            $.post(ctx + "/exec/endExecise", {}, function (msg) {
                if (msg != null && msg == "success") {
                    layer.msg("推演已结束", {icon: 1, time: 1000});
                } else {
                    layer.msg(msg, {icon: 2, time: 1000});
                }
            });
        },
        initmap: function () {
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
                            bound: val.result.bound
                        },
                        target: 'div_map',
                        popup: 'div_popup'
                    };
                    //初始化地图
                    WEBGIS.map.show(param);

                    //初始化自定义工具栏
                    var viewport = WEBGIS.map.map.getViewport();
                    $(viewport).find("div[class='ol-overlaycontainer-stopevent']").append($("#cc-div"));
                    WEBGIS.customerControlls.init();
                    $("#cc-div").show();

                    //初始化测量工具
                    WEBGIS.map.initMeasure();
                    //初始化绘制工具
                    WEBGIS.map.initDraw();

                    //加载地图上的指标
                    /*if(iconData.length > 0){
                     for(var i=0;i<iconData.length;i++){
                     var www = iconData[i].newestCoordinate.split(",");
                     var ccc = new Array();
                     ccc.push(parseFloat(www[0]));
                     ccc.push(parseFloat(www[1]));

                     var icon = {
                     "id":iconData[i].id,
                     "startPoint":ccc,
                     "marker":visitpath + iconData[i].iconData,
                     "iconId":iconData[i].iconId,
                     "iconName":iconData[i].iconName
                     };
                     WEBGIS.gis.markerByxxx(icon);
                     }
                     }*/
                }
            });
        }
    }
})(WEBGIS);