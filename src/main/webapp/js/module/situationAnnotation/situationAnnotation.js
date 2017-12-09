(function (a) {
    a.situationAnnotation = {
        map: null,
        markerlayer: null,
        lineLayer:null,
        lineDraw:null,
        selectStyle:{},
        overlay:
            new ol.Overlay(({
                element: document.getElementById('popup'),
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
        damageLevel:null,
        moveAngle:null,
        init: function () {
            a.situationAnnotation.initmap();
            $("#btn_repair_order_send").on("click", a.iconRepairOrder.sendRepairOrder);
            $("#btn_move_angle_order_send").on("click", a.situationAnnotation.sendMoveAngleOrder);
        },
        initMapIcon:function(){
            $.post(ctx+"/sa/execIconList",{},function(list){
                if(list!=null&&list.length>0){
                    for(var i=0;i<list.length;i++){
                        var json=list[i];
                        var www = json.newestCoordinate.split(",");
                        var ccc = new Array();
                        ccc.push(parseFloat(www[0]));
                        ccc.push(parseFloat(www[1]));
                        var feature = {
                            id:json.id,
                            feature_is_icon:true,
                            marker: FILESERVER_ICON_VISITPATH+json.iconData,
                            iconPath:json.iconData,
                            startPoint:ccc,
                            icon_attr_speed:json.speed,
                            icon_attr_damageLevel:json.damageLevel,
                            icon_attr_damageLevelTime:json.damageLevelTime
                        };
                        a.situationAnnotation.marker(feature);
                    }
                }
            });
        },
        selectOrder:function(order){
            a.situationAnnotation.order=order;
            a.situationAnnotation.firstSelect=false;
            a.situationAnnotation.pathArray= [];
            a.situationAnnotation.overlay.setPosition(undefined);
            if(order==4){
                a.situationAnnotation.pathArray.push(a.situationAnnotation.startIcon.getGeometry().getCoordinates());
                layer.open({
                    type: 1,
                    area: ['400px', '200px'],
                    fix: false, //不固定
                    title: ["航向设置","font-weight:bold;"],
                    content: $("#form_move_angle"), //注意，如果str是object，那么需要字符拼接。
                    cancel :function(){
                        a.situationAnnotation.cancelOrder();
                    }
                });
            }else{
                if(order==1){
                    layer.msg("请选择移动目的地",{time:1000});
                    //移动选中，开始选点
                }else if(order==2){
                    layer.msg("请选择维修点",{time:1000});
                }else if(order==3){
                    layer.msg("请选择装载目的地",{time:1000});
                }
                a.situationAnnotation.readyToDrawPath();
            }
        },
        readyToDrawPath:function(){
            // 添加一个绘制的线使用的layer
            a.situationAnnotation.lineLayer = new ol.layer.Vector({
                source: new ol.source.Vector(),
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: 'red',
                        size: 2
                    })
                })
            });
            a.situationAnnotation.map.addLayer(a.situationAnnotation.lineLayer);
            a.situationAnnotation.lineDraw = new ol.interaction.Draw({
                type: 'LineString',
                source: a.situationAnnotation.lineLayer.getSource(),    // 注意设置source，这样绘制好的线，就会添加到这个source里
                style: new ol.style.Style({            // 设置绘制时的样式
                    stroke: new ol.style.Stroke({
                        color: 'red',
                        size: 2
                    })
                }),
                finishCondition:function(evt){
                    var pixel = a.situationAnnotation.map.getEventPixel(evt.originalEvent);
                    var hit = a.situationAnnotation.hasFeatureAtPixel(pixel);
                    if(a.situationAnnotation.order!=1&&!hit){
                        return false;
                    }
                    if(hit&&hit.getId()==a.situationAnnotation.startIcon.getId()){
                        return false;
                    }
                    if(hit){
                        a.situationAnnotation.endIcon=hit;
                        evt.coordinate=hit.getGeometry().getCoordinates();
                    }
                    return true;
                }
            });

            var feature = new ol.Feature({
                geometry:new ol.geom.LineString(
                    [a.situationAnnotation.startIcon.getGeometry().getCoordinates(), a.situationAnnotation.startIcon.getGeometry().getCoordinates()])
            });
            feature.setStyle(new ol.style.Style({
                stroke: new ol.style.Stroke({
                    width: 3,
                    color: [255, 0, 0, 1]
                })
            }));
            a.situationAnnotation.lineDraw.extend(feature);
            // 监听线绘制结束事件，获取坐标
            a.situationAnnotation.lineDraw.on('drawend', function(event){
                // event.feature 就是当前绘制完成的线的Feature
                a.situationAnnotation.pathArray=event.feature.getGeometry().getCoordinates().slice(1);
                if(a.situationAnnotation.endIcon!=null){
                    a.situationAnnotation.pathArray[a.situationAnnotation.pathArray.length-1]=a.situationAnnotation.endIcon.getGeometry().getCoordinates();
                }
                if(a.situationAnnotation.order==1){
                    a.situationAnnotation.moveSelectDes(event.feature.getGeometry().getCoordinates()[event.feature.getGeometry().getCoordinates().length-1]);
                }else if(a.situationAnnotation.order==2){
                    var damageLevel=a.situationAnnotation.startIcon.icon_attr_damageLevel;
                    var list=damageLevel.split(",");
                    $("#form_repair_level #sel_icon_repair_level").empty();
                    for(var i=0;i<list.length;i++){
                        $("#form_repair_level #sel_icon_repair_level").append(
                            $("<option></option>").val(list[i]).text(list[i]+"级受损")
                        );
                    }
                    layer.open({
                        type: 1,
                        area: ['400px', '200px'],
                        fix: false, //不固定
                        title: ["受损等级设置","font-weight:bold;"],
                        content: $("#form_repair_level"), //注意，如果str是object，那么需要字符拼接。
                        cancel :function(){
                            a.situationAnnotation.clearLineDraw();
                            a.situationAnnotation.readyToDrawPath();
                            a.situationAnnotation.removeDes();
                            a.situationAnnotation.endIcon=null;
                        }
                    });
                    //a.situationAnnotation.pathArray.push(feature.getGeometry().getCoordinates());
                    ////移动或维修，立即发送指令
                    //a.situationAnnotation.sendOrder();
                }else if(a.situationAnnotation.order==3){
                    //装载，弹出装载列表
                    $.post(ctx+"/sa/equipmentAdd",{iconID: a.situationAnnotation.endIcon.getId()},function(str){
                        layer.open({
                            type: 1,
                            area: ['700px', '500px'],
                            fix: false, //不固定
                            title: ["装载列表","font-weight:bold;"],
                            content: str, //注意，如果str是object，那么需要字符拼接。
                            cancel :function(){
                                a.situationAnnotation.clearLineDraw();
                                a.situationAnnotation.readyToDrawPath();
                                a.situationAnnotation.removeDes();
                                a.situationAnnotation.endIcon=null;
                            }
                        });
                    })
                }else{
                    //指令错误
                    a.situationAnnotation.resetOrderStatus();
                }
            });
            a.situationAnnotation.map.addInteraction(a.situationAnnotation.lineDraw);
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
                    var url = val.result.url;
                    var layers = val.result.layer;
                    var bound = val.result.bound;
                    a.situationAnnotation.buildmap(url, layers, bound);
                }
            });
        },
        buildmap: function (url, layer, bound) {

            //标注层
            a.situationAnnotation.markerlayer = new ol.layer.Vector({
                style: function(feature) {
                    return feature.get('style');
                },
                source: new ol.source.Vector({})
            });

            //装载地图
            a.situationAnnotation.map = new ol.Map({
                layers: [
                    new ol.layer.Image({
                        source: new ol.source.ImageWMS({
                            ratio: 1,
                            crossOrigin: 'anonymous',
                            url: geoserver + "/" + url,
                            params: {
                                'FORMAT': 'image/png',
                                'VERSION': '1.1.0',
                                'SRS': 'EPSG:4326',
                                'LAYERS': layer,
                                STYLES: ''
                            }
                        })
                    }),
                    a.situationAnnotation.markerlayer
                ],
                overlays:[
                    a.situationAnnotation.overlay
                ],
                target: document.getElementById('map'),
                controls: ol.control.defaults({
                    attributionOptions: ({
                        collapsible: false
                    })
                }),
                view: new ol.View({
                    projection: new ol.proj.Projection({
                        code: 'EPSG:4326',
                        units: 'degrees',
                        axisOrientation: 'neu'
                    })
                }),
                //interactions: ol.interaction.defaults().extend([new sa.Drag()])
            });

            a.situationAnnotation.initMapIcon();

            a.situationAnnotation.map.getView().fit(a.util.geom4(bound), a.situationAnnotation.map.getSize());
            var select = new ol.interaction.Select({
            });
            a.situationAnnotation.map.addInteraction(select);
            var mousePositionControl = new ol.control.MousePosition({
                coordinateFormat: ol.coordinate.createStringXY(4),
                projection: "EPSG:4326",
                className: "custom-mouse-position",
                target: document.getElementById("mouse-position"),
                undefinedHTML: "&nbsp"
            });
            a.situationAnnotation.map.addControl(mousePositionControl);
            // 监听地图层级变化
            a.situationAnnotation.map.getView().on('change:resolution', function(){
                var features=a.situationAnnotation.markerlayer.getSource().getFeatures();
                for(var i=0;i<features.length;i++){
                    a.situationAnnotation.setFeatureScale(features[i]);
                }
            });
            a.situationAnnotation.map.on("singleclick",function(evt){
                var feature = a.situationAnnotation.hasFeatureAtPixel(evt.pixel);
                if (feature){
                    a.situationAnnotation.featureSelect(feature);
                }else{
                    //a.situationAnnotation.clearSelect();
                }
            });
            a.situationAnnotation.map.on('pointermove', function(e) {
                if (e.dragging) {
                    a.situationAnnotation.overlay.setPosition(undefined);
                    return;
                }

                var pixel = a.situationAnnotation.map.getEventPixel(e.originalEvent);
                var hit = a.situationAnnotation.hasFeatureAtPixel(pixel);
                a.situationAnnotation.map.getTarget().style.cursor = hit ? 'pointer' : '';
            });
        },
        hasFeatureAtPixel:function(pixel){
            return a.situationAnnotation.map.forEachFeatureAtPixel(pixel, function(feature, layer) {
                if(layer= a.situationAnnotation.markerlayer&&feature.feature_is_icon){
                    return feature;
                }else{
                    return null;
                }
            });
        },
        clearSelect:function(){
            var features=a.situationAnnotation.markerlayer.getSource().getFeatures();
            for(var i=0;i<features.length;i++){
                var feature=features[i];
                if(feature.icon_attr_isSelect){
                    feature.icon_attr_damageLevel= Number(feature.icon_attr_damageLevel==null?"0":feature.icon_attr_damageLevel)-5;
                    feature.icon_attr_isSelect=false;
                    a.situationAnnotation.createStyle_src(feature,function(style){
                        feature.setStyle(style);
                        a.situationAnnotation.setFeatureScale(feature);
                    });
                }
            }
        },
        featureSelect:function(feature){
            var coordinates = feature.getGeometry().getCoordinates();
            //if(feature.icon_attr_isSelect){
            //
            //}else{
            //    a.situationAnnotation.clearSelect();
            //    feature.icon_attr_damageLevel= Number(feature.icon_attr_damageLevel==null?"0":feature.icon_attr_damageLevel)+5;
            //    feature.icon_attr_isSelect=true;
            //    a.situationAnnotation.createStyle_src(feature,function(style){
            //        feature.setStyle(style);
            //        a.situationAnnotation.setFeatureScale(feature);
            //    });
            //}
            if(a.situationAnnotation.firstSelect){
                //if(feature.icon_attr_speed==null||feature.icon_attr_speed==""||feature.icon_attr_speed==0){
                //    $("#popup #popup-content .list-group a:eq(0)").hide();
                //}else{
                //    $("#popup #popup-content .list-group a:eq(0)").show();
                //}
                //if(feature.icon_attr_damageLevel==null||feature.icon_attr_damageLevel==""||feature.icon_attr_damageLevelTime==null||feature.icon_attr_damageLevelTime==""){
                //    $("#popup #popup-content .list-group a:eq(1)").hide();
                //}else{
                //    $("#popup #popup-content .list-group a:eq(1)").show();
                //}
                a.situationAnnotation.overlay.setPosition(coordinates);
                $("#popup").show();
                a.situationAnnotation.startIcon=feature;
            }
        },
        //newPointSelect:function(coordinates){
        //    //if(!a.situationAnnotation.firstSelect&&a.situationAnnotation.order==1){
        //    //    a.situationAnnotation.moveSelectDes(coordinates);
        //    //}
        //},
        moveSelectDes:function(coordinates){
            if(a.situationAnnotation.endIcon==null){
                a.situationAnnotation.markerDes(coordinates);
            }
            layer.confirm('确定要移动到该位置？', {
                btn: ['确定','取消指令'] //按钮
                ,cancel :function(){
                    a.situationAnnotation.cancelOrder();
                    a.situationAnnotation.removeDes();
                }
            }, function(){
                //发送移动指令
                a.situationAnnotation.sendOrder();
                a.situationAnnotation.removeDes();
            }, function(){
                a.situationAnnotation.cancelOrder();
                a.situationAnnotation.removeDes();
            });
        },
        sendMoveAngleOrder:function(){
            var moveAngel=$("#txt_move_angle").val();
            if(moveAngel==""){
                layer.msg("请填写航向",{icon:2,time:1000});
                return;
            }
            a.situationAnnotation.moveAngle=moveAngel
            a.situationAnnotation.sendOrder();
        },
        sendOrder:function(){
            var startIconID = a.situationAnnotation.startIcon.getId();
            var endIcon = a.situationAnnotation.endIcon==null?null:a.situationAnnotation.endIcon.getId();
            var order = a.situationAnnotation.order;
            var loadListStr = a.situationAnnotation.loadListStr;
            var pathStr= JSON.stringify(a.situationAnnotation.pathArray);
            var damageLevel=a.situationAnnotation.damageLevel;
            var moveAngle= a.situationAnnotation.moveAngle;
            var param={
                orderType:order,
                pathCoordinate:pathStr,
                iconOneId:startIconID,
                iconTwoId:endIcon,
                loadListStr:loadListStr,
                damageLevel:damageLevel,
                moveAngle:moveAngle
            }
            $.post(ctx+"/sa/sendOrder",param,function(re){
                if(re.msg=="SUCCESS"){
                    layer.closeAll();
                    layer.msg("指令发送成功",{icon:1,time:1000});
                }else{
                    layer.msg(re.msg,{icon:2,time:1000});
                }
            });
            a.situationAnnotation.resetOrderStatus();
        },
        resetOrderStatus:function(){
            a.situationAnnotation.startIcon=null;
            a.situationAnnotation.endIcon=null;
            a.situationAnnotation.firstSelect=true;
            a.situationAnnotation.order=null;
            a.situationAnnotation.loadListStr=null;
            a.situationAnnotation.pathArray= [];
            a.situationAnnotation.damageLevel=null;
            a.situationAnnotation.moveAngle=null;
            a.situationAnnotation.clearLineDraw();
        },
        clearLineDraw:function(){
            if(a.situationAnnotation.lineLayer!=null){
                a.situationAnnotation.lineLayer.setVisible(false);
                a.situationAnnotation.map.removeLayer(a.situationAnnotation.lineLayer);
                a.situationAnnotation.lineLayer=null;
            }
            if(a.situationAnnotation.lineDraw!=null){
                a.situationAnnotation.map.removeInteraction(a.situationAnnotation.lineDraw);
                a.situationAnnotation.lineDraw=null;
            }
        },
        cancelOrder:function(){
            layer.closeAll();
            a.situationAnnotation.resetOrderStatus();
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
            a.situationAnnotation.setFeatureScale(star);
            a.situationAnnotation.markerlayer.getSource().addFeature(star);
        },
        removeDes:function(){
            a.situationAnnotation.unmarker("POINT_DES");
        },
        marker: function (val) {
            //设定锚点坐标位置
            var anchor = new ol.Feature({
                geometry: new ol.geom.Point(val.startPoint)
            });
            anchor = $.extend({}, val, anchor);
            anchor.setId(val.id);
            a.situationAnnotation.createStyle_src(val,function(style){
                anchor.setStyle(style);
                a.situationAnnotation.setFeatureScale(anchor);
            });
            a.situationAnnotation.markerlayer.getSource().addFeature(anchor);
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
                a.situationAnnotation.colorMatrixFilter(image,grayscaleMatrix,options);
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
                a.situationAnnotation.buildColorData(context,image,options);

                if(callback){
                    callback(a.situationAnnotation.createStyle_img(canvas));
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
            style.getImage().setScale(((a.situationAnnotation.map.getView().getZoom()-4)/4+1));
            feature.setStyle(style);
        },
        unmarker: function (id) {
            var anchor = a.situationAnnotation.markerlayer.getSource().getFeatureById(id);
            if(anchor != null) a.situationAnnotation.markerlayer.getSource().removeFeature(anchor);
        }
    };
    a.iconRepairOrder = {
        sendRepairOrder:function(){
            a.situationAnnotation.damageLevel=$("#form_repair_level #sel_icon_repair_level").val();
            a.situationAnnotation.sendOrder();
            layer.closeAll();
        }
    };
    a.equipmentAdd = {
        init:function(){
            $('.spinnerInput').spinner({});
            $("#btn_save_equipment").on("click", a.equipmentAdd.sendLoadMsg);
        },
        sendLoadMsg:function(){
            var array=new Array();
            $('input.spinnerInput').each(function(){
                var id = $(this).data("id");
                var value=$(this).val();
                if(value!=""&&value>0){
                    array.push(id+"-"+value);
                }
            });
            a.situationAnnotation.loadListStr=array.join(",");
            a.situationAnnotation.sendOrder();
            layer.closeAll();
        }
    };
})(WEBGIS);