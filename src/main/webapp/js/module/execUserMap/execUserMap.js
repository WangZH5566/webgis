(function (a) {
    a.execUserMap = {
        startIcon:null,
        init: function () {
            a.execUserMap.initmap();
            $("#btn_backup_list").on("click", a.execUserMap.showBackupList);
            $("#btn_save_map").on("click", a.execUserMap.saveMap);
            $("#btn_save_map_img").on("click", function(event) {
                layer.closeAll();
                WEBGIS.map.map.once('postcompose', function(event) {
                    var canvas = event.context.canvas;
                    canvas.toBlob(function(blob) {
                        saveAs(blob, 'map.png');
                    });
                });
                WEBGIS.map.map.renderSync();
            });
            $("#btn_save_plot").on("click", a.execUserMap.btnSavePlot);
            $("#btn_save_plot_cover").on("click", a.execUserMap.btnSavePlotCover);
            $("#btn_save_plot_another").on("click", a.execUserMap.btnSavePlotAnother);
        },
        initMapIcon:function(list){
            if(list!=null&&list.length>0) {
                for (var i = 0; i < list.length; i++) {
                    var json = list[i];
                    if(json.iconType == "0"){
                        WEBGIS.map.markerByxxx(json);
                    }else if(json.iconType == "1"){
                        WEBGIS.map.markerText(json);
                    }
                }
            }
        },
        /*initmap: function () {
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
            WEBGIS.map.show(param);

            //初始化自定义工具栏
            //var viewport = WEBGIS.map.map.getViewport();
            //$(viewport).find("div[class='ol-overlaycontainer-stopevent']").append($("#cc-div"));

            //放大
            $("#btn_zoom_in").on("click",function(){
                var view = WEBGIS.map.map.getView();
                var zoom = view.getZoom();
                view.setZoom(zoom + 1);
            });
            //缩小
            $("#btn_zoom_out").on("click",function(){
                var view = WEBGIS.map.map.getView();
                var zoom = view.getZoom();
                view.setZoom(zoom - 1);

            });
            //测距
            $("#btn_measure").on("click",function(){
                if($(this).hasClass("select-bg")){
                    WEBGIS.map.measure.unbindEvent();
                    $(this).removeClass("select-bg");
                }else{
                    WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
                    $("#cc-select-detail").hide();
                    WEBGIS.map.measure.bindEvent();
                    $(this).addClass("select-bg")
                }
            });

            //标注框
            $("#li_mark_label").on("click",function(){
                WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
                WEBGIS.map.map.on('click',WEBGIS.map.draw.bindClick);
                $("#cc-select-detail").hide();
                WEBGIS.map.draw.activate(P.PlotTypes.TIPS_PLACE);
            });

            //要图标绘
            $("#cc-div .cc-tools a.cc-a-tmp").on("click",function(){
                var mt = $(this).data("mt");
                $("#div_choose_icon").load(ctx+"/plot/chooseIcon",{"mt":mt},function(){
                    layer.open({
                        type: 1,
                        title:["选择图标","font-weight:bold;"],
                        area:['500px','400px'],
                        closeBtn: 1,                //不显示关闭按钮
                        btn: ['确认', '取消'],
                        yes: function(index, layero){
                            if($("#div_icon_list > div.selected").length == 0){
                                layer.msg("请选择图标！",{icon: 2,time:1000});
                                return false;
                            }
                            WEBGIS.plotChooseIcon.chooseTroop.iconName = $("#div_icon_list > div.selected").eq(0).data("icn");
                            WEBGIS.plotChooseIcon.chooseTroop.iconData = $("#div_icon_list > div.selected").eq(0).data("icd");
                            WEBGIS.plotChooseIcon.chooseTroop.colorArray = $("#div_icon_list > div.selected").eq(0).data("ca");
                            layer.close(index);
                            WEBGIS.map.chooseIcon.bindEvent("1");
                        },
                        shift: 0,
                        shadeClose: false,           //开启遮罩关闭
                        content: $("#div_choose_icon"),
                        zIndex:100,
                        success: function(layero, index){

                        },
                        end:function(){
                            $("#div_choose_icon").html("");
                        }
                    });
                });
            });

            $("#btn_text").on("click",function(){
                WEBGIS.map.chooseIcon.bindEvent("0");
            });

            $("#cc-div").show();

            //初始化测量工具
            WEBGIS.map.initMeasure();
            //初始化绘制工具
            WEBGIS.map.initDraw();

            //初始化图标click后的popupLayer以及相关事件
            var orderPopup = WEBGIS.map.addOverlay("div_order_popup");
            $("#btn_popup_closer").on("click",function(){
                orderPopup.setPosition(undefined);
            });

            $("#btn_delete_icon").on("click",function(){
                layer.confirm('确认删除该图标？', {
                    btn: ['确定','取消'] //按钮
                }, function(index){
                    layer.close(index);
                    orderPopup.setPosition(undefined);
                    WEBGIS.map.markerLayer.getSource().removeFeature(a.execUserMap.startIcon);
                    a.execUserMap.startIcon = null;
                }, function(){});
            });

            //初始化点击事件
            WEBGIS.map.initClick(function(feature){
                a.execUserMap.startIcon = feature;
                var coordinates = feature.getGeometry().getCoordinates();
                orderPopup.setPosition(coordinates);
                $("#div_order_popup").show();
            });

            //初始化个人标注
            var list=(mapiconjson.iconData==null||mapiconjson.iconData=="")?[]:eval("("+mapiconjson.iconData+")");
            a.execUserMap.initMapIcon(list);
        },*/
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
                            bound: val.result.bound,
                            num:val.result.layerNum,
                            resolution: val.result.resolution
                        },
                        target: 'div_map',
                        popup: 'div_popup'
                    };
                    //初始化地图
                    WEBGIS.map.show(param);

                    //初始化自定义工具栏
                    //var viewport = WEBGIS.map.map.getViewport();
                    //$(viewport).find("div[class='ol-overlaycontainer-stopevent']").append($("#cc-div"));



                    //放大
                    $("#btn_zoom_in").on("click",function(){
                        var view = WEBGIS.map.map.getView();
                        var zoom = view.getZoom();
                        view.setZoom(zoom + 1);
                    });
                    //缩小
                    $("#btn_zoom_out").on("click",function(){
                        var view = WEBGIS.map.map.getView();
                        var zoom = view.getZoom();
                        view.setZoom(zoom - 1);

                    });
                    //测距
                    $("#btn_measure").on("click",function(){
                        if($(this).hasClass("select-bg")){
                            WEBGIS.map.measure.unbindEvent();
                            $(this).removeClass("select-bg");
                        }else{
                            WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
                            $("#cc-select-detail").hide();
                            WEBGIS.map.measure.bindEvent();
                            $(this).addClass("select-bg")
                        }
                    });

                    //标注框
                    $("#li_mark_label").on("click",function(){
                        WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
                        WEBGIS.map.map.on('click',WEBGIS.map.draw.bindClick);
                        $("#cc-select-detail").hide();
                        WEBGIS.map.draw.activate(P.PlotTypes.TIPS_PLACE);
                    });

                    //要图标绘
                    $("#cc-div .cc-tools a.cc-a-tmp").on("click",function(){
                        var mt = $(this).data("mt");
                        $("#div_choose_icon").load(ctx+"/plot/chooseIcon",{"mt":mt},function(){
                            layer.open({
                                type: 1,
                                title:["选择图标","font-weight:bold;"],
                                area:['500px','400px'],
                                closeBtn: 1,                //不显示关闭按钮
                                btn: ['确认', '取消'],
                                yes: function(index, layero){
                                    if($("#div_icon_list > div.selected").length == 0){
                                        layer.msg("请选择图标！",{icon: 2,time:1000});
                                        return false;
                                    }
                                    WEBGIS.plotChooseIcon.chooseTroop.iconName = $("#div_icon_list > div.selected").eq(0).data("icn");
                                    WEBGIS.plotChooseIcon.chooseTroop.iconData = $("#div_icon_list > div.selected").eq(0).data("icd");
                                    WEBGIS.plotChooseIcon.chooseTroop.iconPath = $("#div_icon_list > div.selected img").attr("src");
                                    WEBGIS.plotChooseIcon.chooseTroop.colorArray = $("#div_icon_list > div.selected").eq(0).data("ca");
                                    layer.close(index);
                                    WEBGIS.map.chooseIcon.bindEvent("1");
                                },
                                shift: 0,
                                shadeClose: false,           //开启遮罩关闭
                                content: $("#div_choose_icon"),
                                zIndex:100,
                                success: function(layero, index){

                                },
                                end:function(){
                                    $("#div_choose_icon").html("");
                                }
                            });
                        });
                    });

                    $("#btn_text").on("click",function(){
                        WEBGIS.map.chooseIcon.bindEvent("0");
                    });

                    $("#cc-div").show();

                    //初始化测量工具
                    WEBGIS.map.initMeasure();
                    //初始化绘制工具
                    WEBGIS.map.initDraw();

                    //初始化图标click后的popupLayer以及相关事件
                    var orderPopup = WEBGIS.map.addOverlay("div_order_popup");
                    $("#btn_popup_closer").on("click",function(){
                        orderPopup.setPosition(undefined);
                    });

                    $("#btn_delete_icon").on("click",function(){
                        layer.confirm('确认删除该图标？', {
                            btn: ['确定','取消'] //按钮
                        }, function(index){
                            layer.close(index);
                            orderPopup.setPosition(undefined);
                            WEBGIS.map.markerLayer.getSource().removeFeature(a.execUserMap.startIcon);
                            a.execUserMap.startIcon = null;
                        }, function(){});
                    });

                    //初始化点击事件
                    WEBGIS.map.initClick(function(feature){
                        a.execUserMap.startIcon = feature;
                        var coordinates = feature.getGeometry().getCoordinates();
                        orderPopup.setPosition(coordinates);
                        $("#div_order_popup").show();
                    });

                    //初始化个人标注
                    var list=(mapiconjson.iconData==null||mapiconjson.iconData=="")?[]:eval("("+mapiconjson.iconData+")");
                    a.execUserMap.initMapIcon(list);
                }
            });
        },
        iconMark:function(feature){
            $("#div_popup_content").load(ctx+"/plot/plotIconPerson",function(){
                WEBGIS.map.popupLayer.setPosition(feature.getGeometry().getCoordinates());
                WEBGIS.map.draw.drawingIcon = feature;
            });
        },
        textMark:function(feature){
            $("#div_popup_content").load(ctx+"/plot/plotTextPerson",function(){
                WEBGIS.map.popupLayer.setPosition(feature.getGeometry().getCoordinates());
                WEBGIS.map.draw.drawingIcon = feature;
            });
        },
        showBackupList:function(){
            $.post(ctx+"/userIcon/backupList",{},function(str){
                layer.open({
                    type: 1,
                    area: ['600px', '400px'],
                    fix: false, //不固定
                    title: ["备份列表","font-weight:bold;"],
                    content: str //注意，如果str是object，那么需要字符拼接。
                });
            });
        },
        deleteBackup:function(id){
            var index=layer.confirm('确定要删除该备份？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                layer.close(index);
                $.post(ctx+"/userIcon/delete",{id:id},function(re){
                    if(re.msg=="SUCCESS"){
                        $("#list_tr_"+id).remove();
                        if(id == $("#txt_id").val()){
                            window.location.href = ctx + "/userIcon/main";
                        }
                        layer.msg("删除成功",{icon:1,time:1000});
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            }, function(){
            });
        },
        saveMap:function(){
            layer.open({
                type: 1,
                area: ['250px', '100px'],
                fix: false, //不固定
                title: ["保存标绘","font-weight:bold;"],
                content: $("#div_save_plot") //注意，如果str是object，那么需要字符拼接。
            });
        },
        btnSavePlot:function(){
            layer.closeAll();
            layer.open({
                type: 1,
                area: ['250px', '100px'],
                fix: false, //不固定
                title: ["保存为矢量格式","font-weight:bold;"],
                content: $("#div_save_plot_cover"), //注意，如果str是object，那么需要字符拼接。
                success: function(layero, index){
                    if($("#txt_id").val() == ""){
                        $("#btn_save_plot_cover").hide();
                    }else{
                        $("#btn_save_plot_cover").show();
                    }
                }
            });
        },
        btnSavePlotCover:function(){
            var features=WEBGIS.map.markerLayer.getSource().getFeatures();
            var array=new Array();
            for(var i=0;i<features.length;i++){
                var json={};
                var tmp=features[i];
                json.id=tmp.getId();
                if(tmp.getProperties()["marker"] != null){
                    json.marker = tmp.getProperties()["marker"];
                }
                if(tmp.getProperties()["colorArray"] != null){
                    json.colorArray = tmp.getProperties()["colorArray"];
                }
                json.startPoint = tmp.getGeometry().getCoordinates();
                if(tmp.getProperties()["iconType"] != null){
                    json.iconType = tmp.getProperties()["iconType"];
                }
                if(tmp.iconText != null){
                    json.iconText = tmp.iconText;
                }
                array.push(json);
            }
            var iconData=JSON.stringify(array);
            $.post(ctx+"/userIcon/save",{"id":$("#txt_id").val(),"iconData":iconData},function(re){
                if(re.indexOf("|") > -1){
                    layer.closeAll();
                    layer.msg("保存成功",{icon:1,time:1000});
                }else{
                    layer.msg(re.msg,{icon:2,time:1000});
                }
            });
        },
        btnSavePlotAnother:function(){
            layer.closeAll();
            layer.prompt({title: '请输入备注', formType: 2}, function(text, index){
                layer.close(index);

                var features=WEBGIS.map.markerLayer.getSource().getFeatures();
                var array=new Array();
                for(var i=0;i<features.length;i++){
                    var json={};
                    var tmp=features[i];
                    json.id=tmp.getId();
                    if(tmp.getProperties()["marker"] != null){
                        json.marker = tmp.getProperties()["marker"];
                    }
                    if(tmp.getProperties()["colorArray"] != null){
                        json.colorArray = tmp.getProperties()["colorArray"];
                    }
                    json.startPoint = tmp.getGeometry().getCoordinates();
                    if(tmp.getProperties()["iconType"] != null){
                        json.iconType = tmp.getProperties()["iconType"];
                    }
                    if(tmp.iconText != null){
                        json.iconText = tmp.iconText;
                    }
                    array.push(json);
                }
                var iconData=JSON.stringify(array);
                $.post(ctx+"/userIcon/save",{iconData:iconData,comment:text},function(re){
                    if(re.indexOf("|") > -1){
                        $("#txt_id").val(re.substring(re.indexOf("|") + 1));
                        layer.closeAll();
                        layer.msg("保存成功",{icon:1,time:1000});
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });

            });
        }
    };
})(WEBGIS);

!function(t){"use strict";var e=t.HTMLCanvasElement&&t.HTMLCanvasElement.prototype,o=t.Blob&&function(){try{return Boolean(new Blob)}catch(t){return!1}}(),n=o&&t.Uint8Array&&function(){try{return 100===new Blob([new Uint8Array(100)]).size}catch(t){return!1}}(),r=t.BlobBuilder||t.WebKitBlobBuilder||t.MozBlobBuilder||t.MSBlobBuilder,a=/^data:((.*?)(;charset=.*?)?)(;base64)?,/,i=(o||r)&&t.atob&&t.ArrayBuffer&&t.Uint8Array&&function(t){var e,i,l,u,b,c,d,B,f;if(e=t.match(a),!e)throw new Error("invalid data URI");for(i=e[2]?e[1]:"text/plain"+(e[3]||";charset=US-ASCII"),l=!!e[4],u=t.slice(e[0].length),b=l?atob(u):decodeURIComponent(u),c=new ArrayBuffer(b.length),d=new Uint8Array(c),B=0;B<b.length;B+=1)d[B]=b.charCodeAt(B);return o?new Blob([n?d:c],{type:i}):(f=new r,f.append(c),f.getBlob(i))};t.HTMLCanvasElement&&!e.toBlob&&(e.mozGetAsFile?e.toBlob=function(t,o,n){t(n&&e.toDataURL&&i?i(this.toDataURL(o,n)):this.mozGetAsFile("blob",o))}:e.toDataURL&&i&&(e.toBlob=function(t,e,o){t(i(this.toDataURL(e,o)))})),"function"==typeof define&&define.amd?define(function(){return i}):"object"==typeof module&&module.exports?module.exports=i:t.dataURLtoBlob=i}(window);