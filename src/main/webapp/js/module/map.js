(function (WEBGIS) {
    WEBGIS.map = {
        //变量
        mapLayer: new Array(),             //1.地图层
        routeLayer: null,           //2.路径层
        markerLayer: null,          //3.标注层
        popupLayer: null,           //4.POPUP层
        measureLayer: null,         //5.测距层
        map: null,                  //装载的地图
        wgs84Sphere: null,
        execsta: null,               //作战起始时间
        currentExecft: null,        //当前的作战时间
        speedRatio: null,           //时间比例
        steps: new Array(),         //步长列表
        //常量定义
        STYLES: {
            //路径虚线风格
            'route_dash': new ol.style.Style({
                stroke: new ol.style.Stroke({
                    width: 4,
                    color: [237, 212, 0, 0.8],
                    lineDash: [8]
                })
            }),

            //路径实线风格
            'route_solid': new ol.style.Style({
                stroke: new ol.style.Stroke({
                    width: 4,
                    color: [237, 212, 0, 0.8]
                })
            })
        }
    };

    WEBGIS.map.util = {
        geom: function (val) {
            if (val == null || val == undefined) {
                return [,];
            }
            var vals = val.replace(/(^\s*)|(\s*$)/g, "").split(",");
            if (vals.length != 2) {
                return [,];
            }
            var geom = new Array();
            geom.push(Number(vals[0]));
            geom.push(Number(vals[1]));
            return geom;
        },
        geom4: function (val) {
            if (val == null || val == undefined) {
                return [, , ,];
            }
            var vals = val.replace(/(^\s*)|(\s*$)/g, "").split(",");
            if (vals.length != 4) {
                return [, , ,];
            }
            var geom = new Array();
            geom.push(Number(vals[0]));
            geom.push(Number(vals[1]));
            geom.push(Number(vals[2]));
            geom.push(Number(vals[3]));
            return geom;
        }
    };

    WEBGIS.map.show = function (param) {
        //1. 初始化画面
        var initializer = function (param) {

            //初始化地图层
            var init_mapLayer = function (param) {
                var arrayLayer = param.map.layer.split(";");
                var arrayBound = param.map.bound.split(";");
                if (param.map.num == null || param.map.num == undefined || param.map.num == 0) {
                    param.map.num = arrayLayer.length;
                }

                var arrayResolution = new Array();
                if (param.map.resolution == null || param.map.resolution == undefined) {
                    param.map.resolution = new Array();
                    for (var i = 0; i < param.map.num; i++) {
                        arrayResolution.push(i);
                    }
                } else {
                    arrayResolution = param.map.resolution.split(";");
                }

                for (var i = 0; i < param.map.num; i++) {
                    var layer = new ol.layer.Image({
                        source: new ol.source.ImageWMS({
                            ratio: 1,
                            url: param.map.url,
                            crossOrigin: 'anonymous',
                            params: {
                                'FORMAT': 'image/png',
                                'VERSION': '1.1.0',
                                'SRS': 'EPSG:4326',
                                'LAYERS': arrayLayer[i],
                                STYLES: ''
                            }
                        })
                    });
                    layer.compareResolution = parseFloat(arrayResolution[i]);
                    layer.bound = arrayBound[i];
                    layer.setVisible(false);
                    WEBGIS.map.mapLayer.push(layer);
                }
                WEBGIS.map.mapLayer[0].setVisible(true);
            };

            //初始化标注层
            var init_markerLayer = function (param) {
                WEBGIS.map.markerLayer = new ol.layer.Vector({
                    source: new ol.source.Vector()
                });
                WEBGIS.map.markerLayer.setProperties({"na": "makerLayer"});
            };

            //初始化路径层
            var init_routeLayer = function (param) {
                WEBGIS.map.routeLayer = new ol.layer.Vector({
                    source: new ol.source.Vector({
                        features: []
                    })
                });
            };

            //初始化POPUP层
            var init_popupLayer = function (param) {
                WEBGIS.map.popupLayer = new ol.Overlay(({
                    element: document.getElementById(param.popup),
                    autoPan: true,
                    autoPanAnimation: {
                        duration: 250
                    }
                }));
            };

            var init_map = function (param) {
                var chooseLayer = function (map) {
                    var layers = WEBGIS.map.mapLayer;
                    var choosedLayer = undefined;
                    var DIFF_RESOLUTION = 99999999999;
                    for (var index = 0; index < layers.length; index++) {
                        var elem = layers[index];
                        elem.setVisible(false);
                        if (Math.abs(elem.compareResolution - map.getView().getResolution()) < DIFF_RESOLUTION) {
                            choosedLayer = elem;
                            DIFF_RESOLUTION = Math.abs(elem.compareResolution - map.getView().getResolution());
                        }
                    }
                    if (choosedLayer != undefined) {
                        choosedLayer.setVisible(true);
                    }
                };

                var layers = new Array();
                layers = layers.concat(WEBGIS.map.mapLayer);
                layers.push(WEBGIS.map.routeLayer);
                layers.push(WEBGIS.map.markerLayer);

                var mapParam = {
                    layers: layers,
                    target: document.getElementById(param.target),
                    overlays: [WEBGIS.map.popupLayer],
                    view: new ol.View({
                        projection: new ol.proj.Projection({
                            code: 'EPSG:4326',
                            units: 'degrees',
                            axisOrientation: 'neu'
                        }),
                        zoom: 2
                    }),
                    //map.getView().setCenter(ol.extent.getCenter(extent));    // 动态设置中心点
                    controls: ol.control.defaults({
                        zoom: false,
                        rotate: false,
                        attribution: false
                        /*attributionOptions: ({
                            collapsible: false
                        })*/
                    }).extend([
                        new ol.control.ScaleLine({
                            unit: 'metric'
                        }),
                        new ol.control.MousePosition({
                            projection: 'EPSG:4326',
                            coordinateFormat: function(coordinate) {
                                //return ol.coordinate.format(coordinate, '{x}, {y}', 4);
                                return ol.coordinate.toStringHDMS(coordinate,2);
                            }
                        })
                    ]),
                    interactions: WEBGIS.map.drag.needDrag ? ol.interaction.defaults().extend([new WEBGIS.map.drag.Drag()]) : ol.interaction.defaults().extend([])
                };
                WEBGIS.map.map = new ol.Map(mapParam);
                WEBGIS.map.map.getView().fit(WEBGIS.map.util.geom4(WEBGIS.map.mapLayer[0].bound), WEBGIS.map.map.getSize());

                WEBGIS.map.map.getView().on('change:resolution', function () {
                    chooseLayer(WEBGIS.map.map);
                });

                // WEBGIS.map.map.getView().on('change:resolution', function () {
                //     var features = WEBGIS.map.markerLayer.getSource().getFeatures();
                //     for (var i = 0; i < features.length; i++) {
                //         var startZoom = features[i].getProperties()["startZoom"];
                //         var zo = WEBGIS.map.map.getView().getZoom() / startZoom;
                //         var style = features[i].getStyle();
                //         if(style != null){
                //             if(style.getImage() != null){
                //                 style.getImage().setScale(zo);
                //             }
                //             if(style.getText() != null){
                //                 style.getText().setScale(zo);
                //             }
                //             features[i].setStyle(style);
                //         }
                //     }
                // });
            };
            init_mapLayer(param);
            init_routeLayer(param);
            init_markerLayer(param);
            init_popupLayer(param);
            init_map(param);
        };
        initializer(param);
    };

    WEBGIS.map.addOverlay = function (elementId) {
        var overlay = new ol.Overlay(({
            element: document.getElementById(elementId),
            autoPan: true,
            autoPanAnimation: {
                duration: 250
            }
        }));
        WEBGIS.map.map.addOverlay(overlay);
        return overlay;
    };

    /**
     * 拖拽start
     */
    WEBGIS.map.drag = {
        needDrag: true
    };
    WEBGIS.map.drag.Drag = function () {
        ol.interaction.Pointer.call(this, {
            handleDownEvent: WEBGIS.map.drag.Drag.prototype.handleDownEvent,
            handleDragEvent: WEBGIS.map.drag.Drag.prototype.handleDragEvent,
            handleMoveEvent: WEBGIS.map.drag.Drag.prototype.handleMoveEvent,
            handleUpEvent: WEBGIS.map.drag.Drag.prototype.handleUpEvent
        });
        this.coordinate_ = null;
        this.cursor_ = 'pointer';
        this.feature_ = null;
        this.previousCursor_ = undefined;
    };
    ol.inherits(WEBGIS.map.drag.Drag, ol.interaction.Pointer);

    WEBGIS.map.drag.Drag.prototype.handleDownEvent = function (evt) {
        var map = evt.map;
        var feature = map.forEachFeatureAtPixel(evt.pixel,
            function (feature, layer) {
                //alert(layer.getProperties()["na"]);
                //WEBGIS.map.markerLayer.setProperties({"na":"makerLayer"});
                if (layer == null) {
                    return null;
                }
                if (layer != null && layer.getProperties() != null && layer.getProperties()["na"] != "makerLayer") {
                    return null;
                }
                return feature;
            });
        if (feature) {
            this.coordinate_ = evt.coordinate;
            this.feature_ = feature;
        }
        return !!feature;
    };
    WEBGIS.map.drag.Drag.prototype.handleDragEvent = function (evt) {
        var deltaX = evt.coordinate[0] - this.coordinate_[0];
        var deltaY = evt.coordinate[1] - this.coordinate_[1];

        var geometry = (this.feature_.getGeometry());
        geometry.translate(deltaX, deltaY);

        this.coordinate_[0] = evt.coordinate[0];
        this.coordinate_[1] = evt.coordinate[1];
    };
    WEBGIS.map.drag.Drag.prototype.handleMoveEvent = function (evt) {
        if (this.cursor_) {
            var map = evt.map;
            var feature = map.forEachFeatureAtPixel(evt.pixel,
                function (feature) {
                    return feature;
                });
            var element = evt.map.getTargetElement();
            if (feature) {
                if (element.style.cursor != this.cursor_) {
                    this.previousCursor_ = element.style.cursor;
                    element.style.cursor = this.cursor_;
                }
            } else if (this.previousCursor_ !== undefined) {
                element.style.cursor = this.previousCursor_;
                this.previousCursor_ = undefined;
            }
        }
    };
    WEBGIS.map.drag.Drag.prototype.handleUpEvent = function () {
        /*if(this.feature_.getId() != null && this.feature_.getId() != "" && this.feature_.getId() != "anchor"){
         $.post(ctx + "/exec/modifyCoordinate",{"id":this.feature_.getId(),"coordinate":this.coordinate_.join(",")});
         }*/
        this.coordinate_ = null;
        this.feature_ = null;
        return false;
    };
    /**
     * 拖拽end
     */


    /**
     * 标记start
     */
    //已经装载的图标
    WEBGIS.map.markerCache = {};

    WEBGIS.map.marker = function (val) {
        if (val == null || val == undefined || typeof (val) != 'object') {
            return;
        }
        var value = new Array();
        if (val instanceof Array) {
            value = val;
        } else {
            value.push(val);
        }
        for (var i = 0; i < value.length; i++) {
            var id = 'icon' + value[i].id;
            if (WEBGIS.map.markerCache[id] == undefined) { //如果是新的标注，则记录在markerCache中
                WEBGIS.map.markerCache[id] = value[i];
                _marker(value[i]);
            }
        }
    };

    //私有函数，标注图标到地图上
    var _marker = function (icon) {
        var anchor = new ol.Feature({
            geometry: new ol.geom.Point(WEBGIS.util.geom(icon.newestCoordinate))
        });
        //设定图标
        anchor.setStyle(new ol.style.Style({
            image: new ol.style.Icon({
                src: FILESERVER_ICON_VISITPATH + icon.iconData,
                anchor: [0.5, 1]
            })
        }));
        //设定ID
        anchor.setId(icon.id);

        //拓展属性
        anchor.ICON = icon; //挂载图标属性
        anchor.ORDER_QUEUE = {}; //所有指令队列
        anchor.MOVING_ORDER_QUEUE = {}; //移动指令队列
        anchor.REPAIR_ORDER_QUEUE = {}; //维修指令队列
        anchor.EQUIPMENT_ORDER_QUEUE = {};//装载指令队列


        _featureScale(anchor);
        WEBGIS.map.markerLayer.getSource().addFeature(anchor);
    };

    //私有函数，设置图标缩放
    var _featureScale = function (feature) {
        var style = feature.getStyle();
        // 重新设置图标的缩放率，基于层级10来做缩放
        style.getImage().setScale(WEBGIS.map.map.getView().getZoom() / 8);
        feature.setStyle(style);
    };

    WEBGIS.map.markerTest = function(){
        var anchor = new ol.Feature({
            geometry: new ol.geom.Point([108.61698150634766, 39.645311117172234])
        });
        var style = new ol.style.Style({
            image: new ol.style.Icon({
                anchorXUnits: 'fraction',
                anchorYUnits: 'pixels',
                src: 'http://120.25.216.91:8888/file/icon/test2.svg'
            })
        });
        anchor.setStyle(style);
        WEBGIS.map.markerLayer.getSource().addFeature(anchor);
    };

    WEBGIS.map.markerByxxx = function (val) {
        if (val == null || val == undefined || typeof (val) != 'object') {
            return;
        }
        /*var c = val.startPoint;
        var c1 = [c[0] + 1,c[1]];
        var c2 = [c[0] + 1,c[1] + 1];
        var c3 = [c[0],c[1] + 1];*/
        //设定锚点坐标位置
        var anchor = new ol.Feature({
            geometry: new ol.geom.Point(val.startPoint)
            //geometry: new ol.geom.Polygon([[c,c1,c2,c3]])
        });
        for (var key in val) {
            anchor[key] = val[key];
        }
        anchor.setId(val.id);
        if (val.iconId != null) {
            anchor.setProperties({"iconId": val.iconId});
        }
        if (val.iconName != null) {
            anchor.setProperties({"iconName": val.iconName});
        }
        anchor.setProperties({"iconType": "0"});
        if (val.colorArray != null) {
            anchor.setProperties({"colorArray": val.colorArray});
        }
        if (val.marker != null) {
            anchor.setProperties({"marker": val.marker});
        }
        if (val.speed != null) {
            anchor.setProperties({"speed": val.speed});
        }
        if (val.speed_unit != null) {
            anchor.setProperties({"speed_unit": val.speed_unit});
        }
        anchor.setProperties({"startZoom": WEBGIS.map.map.getView().getZoom()});
        //设定图标

        WEBGIS.map.initColorStyle(anchor, function (style) {
            anchor.setStyle(style);

            if(anchor.damage == "7"){
                //轻度
                anchor.getStyle().getImage().setDamageLevel(1);
            }else if(anchor.damage == "8"){
                //中度
                anchor.getStyle().getImage().setDamageLevel(2);
            }else if(anchor.damage == "9"){
                //重度
                anchor.getStyle().getImage().setDamageLevel(3);
            }else if(anchor.damage == "10"){
                //完全损毁
                anchor.getStyle().getImage().setDamageLevel(0);
            }
        });

        WEBGIS.map.markerLayer.getSource().addFeature(anchor);
    };

    WEBGIS.map.markerWhenCrowd = function (val) {
        if (val == null || val == undefined || typeof (val) != 'object') {
            return;
        }
        /*var c = val.startPoint;
         var c1 = [c[0] + 1,c[1]];
         var c2 = [c[0] + 1,c[1] + 1];
         var c3 = [c[0],c[1] + 1];*/
        //设定锚点坐标位置
        var anchor = new ol.Feature({
            geometry: new ol.geom.Point(val.startPoint)
            //geometry: new ol.geom.Polygon([[c,c1,c2,c3]])
        });
        for (var key in val) {
            anchor[key] = val[key];
        }
        anchor.setId(val.id);
        if (val.iconId != null) {
            anchor.setProperties({"iconId": val.iconId});
        }
        if (val.iconName != null) {
            anchor.setProperties({"iconName": val.iconName});
        }
        anchor.setProperties({"iconType": "0"});
        if (val.colorArray != null) {
            anchor.setProperties({"colorArray": val.colorArray});
        }
        if (val.marker != null) {
            anchor.setProperties({"marker": val.marker});
        }
        if (val.speed != null) {
            anchor.setProperties({"speed": val.speed});
        }
        if (val.speed_unit != null) {
            anchor.setProperties({"speed_unit": val.speed_unit});
        }
        anchor.setProperties({"startZoom": WEBGIS.map.map.getView().getZoom()});
        //设定图标

        WEBGIS.map.initColorStyle(anchor, function (style) {

            anchor.setStyle(style);

            if(anchor.damage == "7"){
                //轻度
                anchor.getStyle().getImage().setDamageLevel(1);
            }else if(anchor.damage == "8"){
                //中度
                anchor.getStyle().getImage().setDamageLevel(2);
            }else if(anchor.damage == "9"){
                //重度
                anchor.getStyle().getImage().setDamageLevel(3);
            }else if(anchor.damage == "10"){
                //完全损毁
                anchor.getStyle().getImage().setDamageLevel(0);
            }

            //与markerByxxx方法的唯一区别 start
            if(typeof anchor.isMain != "undefined" && anchor.isMain != null && anchor.isMain == "1"){
                //名称换成集群名称
                anchor.isCrowdShow = "1";
                anchor.getStyle().getText().setText(anchor.crowdName);
            }else{
                anchor.tmpStyle = anchor.getStyle();
                anchor.setStyle(null);
            }
            //与markerByxxx方法的唯一区别 end

        });

        WEBGIS.map.markerLayer.getSource().addFeature(anchor);
    };

    WEBGIS.map.markerText = function (val) {
        if (val == null || val == undefined || typeof (val) != 'object') {
            return;
        }
        //设定锚点坐标位置
        var pointFeature = new ol.Feature(new ol.geom.Point(val.startPoint));
        pointFeature.setId(val.id);
        pointFeature.setProperties({"iconType": "1"});
        pointFeature.feature_is_icon = true;
        pointFeature.iconText = val.iconText;
        pointFeature.setProperties({"startZoom": WEBGIS.map.map.getView().getZoom()});
        pointFeature.setStyle(new ol.style.Style({
            text: new ol.style.Text({
                font: '12px Calibri,sans-serif',
                text: val.iconText,
                offsetY: 10
                /*,scale : WEBGIS.map.map.getView().getZoom() / 4*/
            })
        }));
        WEBGIS.map.markerLayer.getSource().addFeature(pointFeature);

    };

    WEBGIS.map.initColorStyle = function (options, callback) {
        var image = new Image();
        image.crossOrigin = "anonymous";
        var canvas = document.createElement('canvas');
        var context = canvas.getContext('2d');
        image.onload = function () {
            if(options.size != null){
                image.width = options.size;
                image.height = options.size;
            }
            canvas.width = image.width;
            canvas.height = image.height;
            // Load the image into the context.
            context.drawImage(image, 0, 0, image.width, image.height);

            if (options.colorArray != null && options.colorArray != "" && options.colorArray.length > 0) {
                var colorArray = JSON.parse(options.colorArray);
                // Get and modify the image data.
                var pixels = context.getImageData(0, 0, image.width, image.height);
                var d = pixels.data;
                for (var i = 0; i < d.length; i += 4) {
                    var r = d[i];
                    var g = d[i + 1];
                    var b = d[i + 2];
                    var a = d[i + 3];
                    for (var x = 0; x < colorArray.length; x++) {
                        if (colorArray[x][0].join(",") == (r + "," + g + "," + b + "," + a)) {
                            d[i] = colorArray[x][1][0];
                            d[i + 1] = colorArray[x][1][1];
                            d[i + 2] = colorArray[x][1][2];
                            d[i + 3] = colorArray[x][1][3];
                        }
                    }
                }
                context.putImageData(pixels, 0, 0);
            }

            if (callback) {
                options.img_data = canvas;
                callback(WEBGIS.map.createStyle_img(options));
            }
        };
        image.src = options.marker+"?_=img_init";
    };

    WEBGIS.map.createStyle_img = function (options) {
        return new ol.style.Style({
            image: new ol.style.IconWithProgressBar({
                anchor: [0.5, 1],
                img: options.img_data,
                imgSize: options.img_data ? [options.img_data.width * 2, options.img_data.height + 20] : undefined,
                crossOrigin: "anonymous"
            }),
            text: new ol.style.Text({
                font: '12px Calibri,sans-serif',
                text: options.iconName == null ? "" : options.iconName,
                offsetY: 10
            })
        });
    };

    /**
     * 标记方法end
     */


    /**
     * 绘制方法begin
     */
    WEBGIS.map.initDraw = function () {
        // 初始化标绘绘制工具，添加绘制结束事件响应
        WEBGIS.map.draw.plotDraw = new P.PlotDraw(WEBGIS.map.map);
        WEBGIS.map.draw.plotDraw.on(P.Event.PlotDrawEvent.DRAW_END, WEBGIS.map.draw.onDrawEnd, false, this);

        // 初始化标绘编辑工具
        WEBGIS.map.draw.plotEdit = new P.PlotEdit(WEBGIS.map.map);

        // 设置标绘符号显示的默认样式
        var stroke = new ol.style.Stroke({color: '#FF0000', width: 2});
        var fill = new ol.style.Fill({color: 'rgba(0,255,0,0.4)'});
        var image = new ol.style.Circle({fill: fill, stroke: stroke, radius: 8});
        WEBGIS.map.draw.drawStyle = new ol.style.Style({image: image, fill: fill, stroke: stroke});

        // 绘制好的标绘符号，添加到FeatureOverlay显示。
        WEBGIS.map.draw.drawOverlay = new ol.layer.Vector({
            source: new ol.source.Vector()
        });
        WEBGIS.map.draw.drawOverlay.setStyle(WEBGIS.map.draw.drawStyle);
        WEBGIS.map.draw.drawOverlay.setMap(WEBGIS.map.map);
    };

    WEBGIS.map.draw = {
        plotDraw: {},
        plotEdit: {},
        drawOverlay: {},
        drawStyle: {},
        drawingIcon: {},
        onDrawEnd: function (event) {
            // 绘制结束后，添加到FeatureOverlay显示。
            var feature = event.feature;
            WEBGIS.map.draw.drawOverlay.getSource().addFeature(feature);
            // 开始编辑
            WEBGIS.map.draw.plotEdit.activate(feature);
        },
        activate: function (type, param) {
            $("#cc-select-detail").hide();
            WEBGIS.map.draw.plotEdit.deactivate();
            WEBGIS.map.draw.plotDraw.activate(type, param);
        },
        removeFeature: function () {
            if (WEBGIS.map.draw.drawOverlay && WEBGIS.map.draw.plotEdit && WEBGIS.map.draw.plotEdit.activePlot) {
                WEBGIS.map.draw.drawOverlay.getSource().removeFeature(WEBGIS.map.draw.plotEdit.activePlot);
                WEBGIS.map.draw.plotEdit.deactivate();
            }
        }
    };

    /**
     * 态势图用
     * @param e
     */
    WEBGIS.map.draw.bindClick = function (e) {
        if (WEBGIS.map.draw.plotDraw.isDrawing()) {
            return;
        }
        var feature = WEBGIS.map.map.forEachFeatureAtPixel(e.pixel, function (feature, layer) {
            if (layer != null && layer.getProperties() != null && layer.getProperties()["na"] == "makerLayer") {
                return null;
            }
            return feature;
        });
        if (feature) {
            // 开始编辑
            var plotType = feature.getProperties()["plotType"];
            var plotParams = feature.getProperties()["plotParams"];
            if (plotType == P.PlotTypes.MARKER && plotParams != "undefined") {
                //plotParams表示当前feature的功能类型:1为要图标绘,2为文字标绘
                if (plotParams == 1) {
                    WEBGIS.customerControlls.iconMark(feature);
                } else if (plotParams == 2) {
                    WEBGIS.customerControlls.textMark(feature);
                }
            }
            WEBGIS.map.draw.plotEdit.activate(feature);
        } else {
            // 结束编辑
            WEBGIS.map.draw.plotEdit.deactivate();
        }
    };

    /**
     * 要图标绘用
     * @param e
     */
    WEBGIS.map.draw.bindClickForPerson = function (e) {
        if (WEBGIS.map.draw.plotDraw.isDrawing()) {
            return;
        }
        var feature = WEBGIS.map.map.forEachFeatureAtPixel(e.pixel, function (feature, layer) {
            if (layer != null && layer.getProperties() != null && layer.getProperties()["na"] == "makerLayer") {
                return null;
            }
            return feature;
        });
        if (feature) {
            // 开始编辑
            var plotType = feature.getProperties()["plotType"];
            var plotParams = feature.getProperties()["plotParams"];
            if (plotType == P.PlotTypes.MARKER && plotParams != "undefined") {
                //plotParams表示当前feature的功能类型:1为要图标绘,2为文字标绘
                if (plotParams == 1) {
                    WEBGIS.execUserMap.iconMark(feature);
                } else if (plotParams == 2) {
                    WEBGIS.execUserMap.textMark(feature);
                }
            }
            WEBGIS.map.draw.plotEdit.activate(feature);
        } else {
            // 结束编辑
            WEBGIS.map.draw.plotEdit.deactivate();
        }
    };

    /**
     * 绘制方法end
     */

    /**
     * 单击事件begin
     */
    WEBGIS.map.initClick = function (callback) {
        WEBGIS.map.map.on("singleclick", function (evt) {
            var feature = WEBGIS.map.map.forEachFeatureAtPixel(evt.pixel,
                function (feature, layer) {
                    if (layer == null) {
                        return null;
                    }
                    if (layer != null && layer.getProperties() != null && layer.getProperties()["na"] != "makerLayer") {
                        return null;
                    }
                    return feature;
                });
            if (feature) {
                if (callback) {
                    callback(feature);
                }
            }
        });
    };
    /**
     * 单击事件end
     */

    /**
     * 移动事件begin
     */
    WEBGIS.map.initMove = function (callback) {
        var push = 0;
        var nullStyle = new ol.style.Style({
            stroke: new ol.style.Stroke({
                width: 4,
                color: [237, 212, 0, 0.8]
            })
        });

        //依据线来运动
        //http://www.movable-type.co.uk/scripts/latlong.html
        var orderByLineString = function (feature, order, stepTimes) {
            if (WEBGIS.map.execsta == "0" || WEBGIS.map.execsta == "10") {
                return;
            }
            var speedunit = feature.speed_unit == undefined ? 'km' : feature.speed_unit;
            var speed = feature.speed;
            //统一速度单位 : 米/毫秒
            if (speedunit == 'km') { //如果是速度的单位   公里/小时
                speed = speed * 1000 / 3600 / 1000;
            } else {
                speed = 1.852 * speed * 1000 / 3600 / 1000;
            }

            //计算跑的距离(单位米)
            var distance = 0;
            for (var i = 0; i < stepTimes.length; i++) {
                var steptime = stepTimes[i];
                // distance = distance + speed * steptime.speedRatio * steptime.diff;
                distance = distance + speed * steptime.diff;
            }

            var coordinates = order.coordinates;
            var index = null;
            for (var i = 0; i < coordinates.length; i++) {
                if (i + 1 >= coordinates.length) {
                    break;
                }
                var piece_distantce = WEBGIS.map.wgs84Sphere.haversineDistance(coordinates[i], coordinates[i + 1]);
                if (distance >= piece_distantce) {
                    distance = distance - piece_distantce;
                } else if (distance < piece_distantce) {
                    index = i;
                    break;
                }
            }
            if (index == null) {
                order.status = 'finished';
                if (order.orderType == 2) { //维修
                    WEBGIS.map.appendRepairOrder(feature,order);
                }
                if (order.orderType == 3) { //装载
                    WEBGIS.map.appendEquipmentOrder(feature,order);
                }
                if (order.orderType == 5) { //降落,已经到达机场,隐藏图标
                    WEBGIS.map.appendLangingOrder(feature,order);
                }

                var p1 = feature.getGeometry().getCoordinates();
                var p3 = coordinates[coordinates.length - 1];
                feature.getGeometry().translate(p3[0] - p1[0], p3[1] - p1[1]);
                WEBGIS.map.map.render();
                return;
            }
            var p1 = LatLon(coordinates[index][1], coordinates[index][0]);
            var p2 = LatLon(coordinates[index + 1][1], coordinates[index + 1][0]);
            var brng = p1.bearingTo(p2);
            var p3 = p1.destinationPoint(distance, brng);
            var coord = feature.getGeometry().getCoordinates();
            feature.getGeometry().translate(p3.lon - coord[0], p3.lat - coord[1]);
        };
        var orderByAngle = function (feature, order, stepTimes) {
            if (WEBGIS.map.execsta == "0" || WEBGIS.map.execsta == "10") {
                return;
            }

            var speedunit = feature.speed_unit == undefined ? 'km' : feature.speed_unit;
            var speed = feature.speed;

            //统一速度单位 : 米/毫秒
            if (speedunit == 'km') { //如果是速度的单位   公里/小时
                speed = speed * 1000 / 3600 / 1000;
            } else {
                speed = 1.852 * speed * 1000 / 3600 / 1000;
            }

            //计算跑的距离(单位米)
            var distance = 0;
            for (var i = 0; i < stepTimes.length; i++) {
                var steptime = stepTimes[i];
                // distance = distance + speed * steptime.speedRatio * steptime.diff;
                distance = distance + speed * steptime.diff;
            }

            var p1 = LatLon(order.coordinates[0][1], order.coordinates[0][0]);
            var p2 = p1.destinationPoint(distance, order.moveAngle);
            var coord = feature.getGeometry().getCoordinates();
            feature.getGeometry().translate(p2.lon - coord[0], p2.lat - coord[1]);
            WEBGIS.map.map.render();
        };

        WEBGIS.map.map.on('postcompose', function (event) {
            if (++push % 2 == 0) {
                WEBGIS.map.markerLayer.setStyle(null);
            } else {
                WEBGIS.map.markerLayer.setStyle(nullStyle);
            }
            var features = WEBGIS.map.markerLayer.getSource().getFeatures();
            for (var i = 0; i < features.length; i++) {
                var feature = features[i];
                for (var key in feature.MOVING_ORDER_QUEUE) {
                    var order = feature.MOVING_ORDER_QUEUE[key];
                    if (order.status != 'moving') {
                        continue;
                    }
                    var stepTimes = new Array();
                    var steps = WEBGIS.map.steps;
                    for (var j = 0; j < steps.length; j++) {
                        var step = steps[j];

                        if (step.fightEndTime == null) {
                            var beginTime = step.fightBeginTime >= order.fightBeginTime ? step.fightBeginTime : order.fightBeginTime;
                            //stepTimes.push({speedRatio: step.stepLength, diff: new Date().getTime() - beginTime});
                            stepTimes.push({speedRatio: step.stepLength, diff: execft - beginTime});
                        } else {
                            if (step.fightBeginTime <= order.fightBeginTime && step.fightEndTime >= order.fightBeginTime) {
                                stepTimes.push({speedRatio: step.stepLength, diff: step.fightEndTime - order.fightBeginTime});
                            } else if (order.fightBeginTime < step.fightBeginTime) {
                                stepTimes.push({speedRatio: step.stepLength, diff: step.fightEndTime - step.fightBeginTime});
                            } else {
                                continue;
                            }
                        }
                    }

                    if (order.orderType == 1 || order.orderType == 2 || order.orderType == 3 || order.orderType == 5) {//依据直线进行运动
                        orderByLineString(feature, order, stepTimes)
                    }
                    if (order.orderType == 4) { //依据角度进行运动
                        orderByAngle(feature, order, stepTimes);
                    }
                }

                for (var key in feature.REPAIR_ORDER_QUEUE) {
                    var order = feature.REPAIR_ORDER_QUEUE[key];
                    if(order.repairBeginTimeMills == 0){
                        continue;
                    }
                    if (order.status == 'over') {
                        continue;
                    }
                    //维修开始时间(作战时间):
                    var repairStart = order.repairBeginTimeMills;
                    //时间差(当前作战时间 - 维修开始时间)
                    var timeSubtract = execft - repairStart;
                    //维修进度条总时间= 总人时/维修人员总数
                    //进度 = 时间差/总时间
                    //console.log(order.damageTime);
                    //console.log(order.damageTime * 3600 * 1000);
                    //console.log(timeSubtract);
                    var pro = (timeSubtract / (order.damageTime * 3600 * 1000) * order.repairNum * 100).toFixed(0);
                    //console.log("percent is :" + pro);
                    //如果pro大于100,则指令执行完成,往数据库写入数据
                    if(pro >= 100){
                        order.status = 'over';
                        if(order.isEnd != 1) {
                            var aaa = feature;
                            $.post(ctx + "/sa/repairEnd", {"id": feature.getId(), "oid": order.id}, function () {
                                aaa.damage = null;
                                aaa.damageDetail = null;
                                aaa.damageTime = null;
                                aaa.damageCont = null;
                                aaa.damageDetailCont = null;
                                aaa.getStyle().getImage().setDamageLevel(-1);
                                console.log("the repairing is over");
                            });
                        }
                    }
                    feature.getStyle().getImage().setRepair(pro);
                }

                for (var key in feature.EQUIPMENT_ORDER_QUEUE) {
                    var order = feature.EQUIPMENT_ORDER_QUEUE[key];
                    if(order.addEquipmentBeginTimeMills == 0){
                        continue;
                    }
                    if (order.status == 'over') {
                        continue;
                    }
                    //时间差(当前作战时间 - 装载开始时间)
                    var timeSubtract = execft - order.addEquipmentBeginTimeMills;
                    //进度 = 时间差/总时间
                    //console.log(timeSubtract);
                    var pro = (timeSubtract / (order.addEquipmentTime * 3600 * 1000) * 100).toFixed(0);
                    //console.log("percent is :" + pro);
                    //如果pro大于100,则指令执行完成,往数据库写入数据
                    if(pro >= 100){
                        order.status = 'over';
                        if(order.isEnd != 1) {
                            var aaa = feature;
                            $.post(ctx + "/sa/addEquipmentEnd", {"id": feature.getId(), "oid": order.id}, function () {
                                console.log("the add equipment is over");
                            });
                        }
                    }
                    feature.getStyle().getImage().setCapacity(pro);
                }
            }
        });
    };

    WEBGIS.map.appendOrder = function (val) {
        if (val == null || val == undefined || val.length == 0) {
            return;
        }
        var features = WEBGIS.map.markerLayer.getSource().getFeatures();
        for (var i = 0; i < val.length; i++) {
            var order = val[i];
            for (var j = 0; j < features.length; j++) {
                var feature = features[j];
                feature.ORDER_QUEUE = feature.ORDER_QUEUE || new Array();
                feature.MOVING_ORDER_QUEUE = feature.MOVING_ORDER_QUEUE || new Array();
                if (feature.getId() != order.iconOneId) {
                    continue;
                }
                var _orderName = 'order-' + order.id;

                if (feature.ORDER_QUEUE[_orderName] != undefined) {
                    break;
                }
                feature.ORDER_QUEUE[_orderName] = order;

                if (order.orderType == 1 || order.orderType == 2 || order.orderType == 3  || order.orderType == 5) {
                    var exp = new RegExp("\\d{1,}\\.\\d{1,}", "ig");
                    order.status = 'moving';
                    for (key in feature.MOVING_ORDER_QUEUE) {
                        feature.MOVING_ORDER_QUEUE[key].status = 'finished';
                    }
                    feature.MOVING_ORDER_QUEUE[_orderName] = order;
                    var pathCoordinate = order.pathCoordinate.match(exp);
                    order.coordinates = new Array();
                    for (var index = 0; index < pathCoordinate.length; index = index + 2) {
                        order.coordinates.push([pathCoordinate[index], pathCoordinate[index + 1]]);
                    }
                }

                if (order.orderType == 4) {
                    var exp = new RegExp("\\d{1,}\\.\\d{1,}", "ig");
                    order.status = 'moving';
                    for (key in feature.MOVING_ORDER_QUEUE) {
                        feature.MOVING_ORDER_QUEUE[key].status = 'finished';
                    }
                    feature.MOVING_ORDER_QUEUE[_orderName] = order;
                    var pathCoordinate = order.pathCoordinate.match(exp);
                    order.coordinates = new Array();
                    for (var index = 0; index < pathCoordinate.length; index = index + 2) {
                        order.coordinates.push([pathCoordinate[index], pathCoordinate[index + 1]]);
                    }
                }
            }
        }
    };

    WEBGIS.map.appendRepairOrder = function (feature,order) {
        feature.ORDER_QUEUE = feature.ORDER_QUEUE || new Array();
        feature.REPAIR_ORDER_QUEUE = feature.REPAIR_ORDER_QUEUE || new Array();
        if (feature.getId() != order.iconOneId) {
            return;
        }
        if(order.isEnd == 1){
            return;
        }
        var _orderName = 'order-' + order.id;

        order.status = 'finished';
        feature.REPAIR_ORDER_QUEUE[_orderName] = order;

        //第一次执行进度条,要往数据库写入维修开始时间(作战时间)
        if(order.repairBeginTimeMills == 0){
            $.post(ctx + "/sa/saveRepairBeginTime", {"id":order.id,"rbt":execft}, function (mi) {
                console.log("insert repair begin time into the database");
                order.repairBeginTimeMills = mi;
            });
        }
    };

    WEBGIS.map.appendEquipmentOrder = function (feature,order) {
        feature.ORDER_QUEUE = feature.ORDER_QUEUE || new Array();
        feature.EQUIPMENT_ORDER_QUEUE = feature.EQUIPMENT_ORDER_QUEUE || new Array();
        if (feature.getId() != order.iconOneId) {
            return;
        }
        if(order.isEnd == 1){
            return;
        }
        var _orderName = 'order-' + order.id;

        order.status = 'finished';
        feature.EQUIPMENT_ORDER_QUEUE[_orderName] = order;

        //第一次执行,要往数据库写入装载开始时间(作战时间)
        $.post(ctx + "/sa/saveAddEquipmentBeginTime", {"id":order.id,"aebt":execft}, function (mi) {
            console.log("insert add equipment begin time into the database");
            order.addEquipmentBeginTimeMills = mi;
        });
    };


    WEBGIS.map.appendLangingOrder = function (feature,order) {
        if (feature.getId() != order.iconOneId) {
            return;
        }
        if(order.isEnd == 1){
            return;
        }
        //指令结束,页面上移除图标,并更新数据
        $.post(ctx + "/sa/landingEnd", {"oid":order.id,"fid":feature.getId(),"aid":order.iconTwoId},function(){
            console.log("langing end");
            WEBGIS.map.markerLayer.getSource().removeFeature(feature);
        });
    };


    /**
     * 推演回顾移动
     */
    WEBGIS.map.appendOrderForReview = function (val) {
        if (val == null || val == undefined || val.length == 0) {
            return;
        }
        var features = WEBGIS.map.markerLayer.getSource().getFeatures();
        for (var i = 0; i < val.length; i++) {
            var order = val[i];
            for (var j = 0; j < features.length; j++) {
                var feature = features[j];
                feature.ORDER_QUEUE = feature.ORDER_QUEUE || new Array();
                if (feature.getId() != order.iconOneId) {
                    continue;
                }
                if (order.orderType == 1 || order.orderType == 2 || order.orderType == 3  || order.orderType == 5) {
                    var exp = new RegExp("\\d{1,}\\.\\d{1,}", "ig");
                    order.status = 'moving';
                    var pathCoordinate = order.pathCoordinate.match(exp);
                    order.coordinates = new Array();
                    for (var index = 0; index < pathCoordinate.length; index = index + 2) {
                        order.coordinates.push([pathCoordinate[index], pathCoordinate[index + 1]]);
                    }
                }
                if (order.orderType == 4) {
                    var exp = new RegExp("\\d{1,}\\.\\d{1,}", "ig");
                    order.status = 'moving';
                    var pathCoordinate = order.pathCoordinate.match(exp);
                    order.coordinates = new Array();
                    for (var index = 0; index < pathCoordinate.length; index = index + 2) {
                        order.coordinates.push([pathCoordinate[index], pathCoordinate[index + 1]]);
                    }
                }
                var cc = 0;
                for (var q in feature.ORDER_QUEUE){
                    cc++;
                }
                feature.ORDER_QUEUE[cc] = order;
            }
        }
    };


    WEBGIS.map.initMoveForReview = function (callback) {
        var push = 0;
        var nullStyle = new ol.style.Style({
            stroke: new ol.style.Stroke({
                width: 4,
                color: [237, 212, 0, 0.8]
            })
        });

        //依据线来运动
        //http://www.movable-type.co.uk/scripts/latlong.html
        var orderByLineString = function (feature, order, diff,counti) {
            if (WEBGIS.map.execsta == "0" || WEBGIS.map.execsta == "10") {
                return;
            }

            var speedunit = feature.speed_unit == undefined ? 'km' : feature.speed_unit;
            var speed = feature.speed;
            //统一速度单位 : 米/毫秒
            if (speedunit == 'km') { //如果是速度的单位   公里/小时
                speed = speed * 1000 / 3600 / 1000;
            } else {
                speed = 1.852 * speed * 1000 / 3600 / 1000;
            }

            //计算跑的距离(单位米)
            var distance = 0;
            distance = distance + speed * diff;
            /*for (var i = 0; i < stepTimes.length; i++) {
                var steptime = stepTimes[i];
                distance = distance + speed * steptime.speedRatio * steptime.diff;
            }*/

            var coordinates = order.coordinates;
            var index = null;
            for (var i = 0; i < coordinates.length; i++) {
                if (i + 1 >= coordinates.length) {
                    break;
                }
                var piece_distantce = WEBGIS.map.wgs84Sphere.haversineDistance(coordinates[i], coordinates[i + 1]);
                if (distance >= piece_distantce) {
                    distance = distance - piece_distantce;
                } else if (distance < piece_distantce) {
                    index = i;
                    break;
                }
            }
            if (index == null) {
                order.status = 'over';
                feature.ORDER_QUEUE[counti].status = "over";
                if (order.orderType == 2) { //维修
                    if (feature.getId() == order.iconOneId) {
                        order.status = 'movingfinished';
                        feature.ORDER_QUEUE[counti].status = "movingfinished";
                    }
                }
                if (order.orderType == 3) { //装载
                    if (feature.getId() == order.iconOneId) {
                        order.status = 'movingfinished';
                        feature.ORDER_QUEUE[counti].status = "movingfinished";
                    }
                }
                if (order.orderType == 5) { //降落,已经到达机场,隐藏图标
                    appendLangingOrder(feature,order);
                }
                var p1 = feature.getGeometry().getCoordinates();
                var p3 = coordinates[coordinates.length - 1];
                feature.getGeometry().translate(p3[0] - p1[0], p3[1] - p1[1]);
                WEBGIS.map.map.render();
                return;
            }
            var p1 = LatLon(coordinates[index][1], coordinates[index][0]);
            var p2 = LatLon(coordinates[index + 1][1], coordinates[index + 1][0]);
            var brng = p1.bearingTo(p2);
            var p3 = p1.destinationPoint(distance, brng);
            var coord = feature.getGeometry().getCoordinates();
            feature.getGeometry().translate(p3.lon - coord[0], p3.lat - coord[1]);
        };

        var orderByAngle = function (feature, order, diff) {
            if (WEBGIS.map.execsta == "0" || WEBGIS.map.execsta == "10") {
                return;
            }

            var speedunit = feature.speed_unit == undefined ? 'km' : feature.speed_unit;
            var speed = feature.speed;

            //统一速度单位 : 米/毫秒
            if (speedunit == 'km') { //如果是速度的单位   公里/小时
                speed = speed * 1000 / 3600 / 1000;
            } else {
                speed = 1.852 * speed * 1000 / 3600 / 1000;
            }

            //计算跑的距离(单位米)
            var distance = 0;
            distance = distance + speed * diff;
            /*for (var i = 0; i < stepTimes.length; i++) {
                var steptime = stepTimes[i];
                distance = distance + speed * steptime.speedRatio * steptime.diff;
            }*/
            var p1 = LatLon(order.coordinates[0][1], order.coordinates[0][0]);
            var p2 = p1.destinationPoint(distance, order.moveAngle);
            var coord = feature.getGeometry().getCoordinates();
            feature.getGeometry().translate(p2.lon - coord[0], p2.lat - coord[1]);
            WEBGIS.map.map.render();
        };

        var appendLangingOrder = function (feature,order) {
            if (feature.getId() != order.iconOneId) {
                return;
            }
            if(order.isEnd == 1){
                return;
            }
            // WEBGIS.map.markerLayer.getSource().removeFeature(feature);
        };

        WEBGIS.map.map.on('postcompose', function (event) {
            if (++push % 2 == 0) {
                WEBGIS.map.markerLayer.setStyle(null);
            } else {
                WEBGIS.map.markerLayer.setStyle(nullStyle);
            }
            var features = WEBGIS.map.markerLayer.getSource().getFeatures();

            for (var i = 0; i < features.length; i++) {
                var feature = features[i];
                var currentOrder = null;
                if(typeof feature.ORDER_QUEUE != "undefined"){
                    for(var key in feature.ORDER_QUEUE){
                        if(execft < feature.ORDER_QUEUE[key].fightBeginTime){
                            continue;
                        }
                        if(feature.ORDER_QUEUE[key].status == "over"){
                            continue;
                        }
                        currentOrder = feature.ORDER_QUEUE[key];
                        var counti = parseInt(key);
                        var qwe = parseInt(key) + 1;
                        if(feature.ORDER_QUEUE[qwe] != null ){
                            var order2 = feature.ORDER_QUEUE[qwe];
                            if(execft >= order2.fightBeginTime){
                                feature.ORDER_QUEUE[key].status = "over";
                                currentOrder = order2;
                                counti = qwe;
                            }
                        }
                        break;
                    }
                    if(currentOrder != null){
                        if (currentOrder.orderType == 1 || currentOrder.orderType == 2 || currentOrder.orderType == 3 || currentOrder.orderType == 5) {
                            //依据直线进行运动
                            var diff = execft - currentOrder.fightBeginTime;
                            orderByLineString(feature, currentOrder, diff,counti)
                        }
                        if (currentOrder.orderType == 4) { //依据角度进行运动
                            var diff = execft - currentOrder.fightBeginTime;
                            orderByAngle(feature, currentOrder, diff);
                        }
                        if(currentOrder.status == "movingfinished"){
                            if(currentOrder.orderType == 2){
                                //维修开始时间(作战时间):
                                var repairStart = currentOrder.repairBeginTimeMills;
                                //时间差(当前作战时间 - 维修开始时间)
                                var timeSubtract = execft - parseInt(repairStart);
                                //维修进度条总时间= 总人时/维修人员总数
                                //进度 = 时间差/总时间
                                var pro = (timeSubtract / (currentOrder.damageTime * 3600 * 1000) * currentOrder.repairNum * 100).toFixed(0);
                                //如果pro大于100,则指令执行完成,往数据库写入数据
                                if(pro >= 100){
                                    currentOrder.status = 'over';
                                    feature.ORDER_QUEUE[counti].status = "over";
                                }
                                console.log("pro="+pro);
                                console.log("timeSubtract="+timeSubtract);
                                console.log("execft="+execft);
                                console.log("repairStart="+repairStart);
                                feature.getStyle().getImage().setRepair(pro);
                            }else if(currentOrder.orderType == 3){
                                //时间差(当前作战时间 - 装载开始时间)
                                var timeSubtract = execft - currentOrder.addEquipmentBeginTimeMills;
                                //进度 = 时间差/总时间
                                var pro = (timeSubtract / (currentOrder.addEquipmentTime * 3600 * 1000) * 100).toFixed(0);
                                //如果pro大于100,则指令执行完成,往数据库写入数据
                                if(pro >= 100){
                                    currentOrder.status = 'over';
                                    feature.ORDER_QUEUE[counti].status = "over";
                                }
                                feature.getStyle().getImage().setCapacity(pro);
                            }
                        }
                    }
                }
            }
        });
    };


    /**
     * 移动事件end
     */

    /**
     * 测距方法begin
     */
    WEBGIS.map.initMeasure = function () {
        WEBGIS.map.wgs84Sphere = new ol.Sphere(6378137);
        WEBGIS.map.measureLayer = new ol.layer.Vector({
            source: WEBGIS.map.measure.source,
            style: new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'rgba(255, 255, 255, 0.2)'
                }),
                stroke: new ol.style.Stroke({
                    color: '#ffcc33',
                    width: 2
                }),
                image: new ol.style.Circle({
                    radius: 7,
                    fill: new ol.style.Fill({
                        color: '#ffcc33'
                    })
                })
            })
        });
        WEBGIS.map.map.addLayer(WEBGIS.map.measureLayer);
        WEBGIS.map.measure.addMeasureInteraction();
    };

    WEBGIS.map.measure = {
        source: new ol.source.Vector(),
        draw: null,
        sketch: null,
        helpTooltipElement: null,
        helpTooltip: null,
        measureTooltipElement: null,
        measureTooltip: null,
        continueLineMsg: "单击确认地点,双击结束",
        tmpListen:null,
        addMeasureInteraction: function () {
            WEBGIS.map.measure.draw = new ol.interaction.Draw({
                source: WEBGIS.map.measure.source,
                type: /** @type {ol.geom.GeometryType} */ ("LineString"),
                style: new ol.style.Style({
                    fill: new ol.style.Fill({
                        color: 'rgba(255, 255, 255, 0.2)'
                    }),
                    stroke: new ol.style.Stroke({
                        color: 'rgba(0, 0, 0, 0.5)',
                        lineDash: [10, 10],
                        width: 2
                    }),
                    image: new ol.style.Circle({
                        radius: 5,
                        stroke: new ol.style.Stroke({
                            color: 'rgba(0, 0, 0, 0.7)'
                        }),
                        fill: new ol.style.Fill({
                            color: 'rgba(255, 255, 255, 0.2)'
                        })
                    })
                })
            });
            WEBGIS.map.measure.createMeasureTooltip();
            WEBGIS.map.measure.createHelpTooltip();
            WEBGIS.map.measure.draw.on('drawstart',
                function (evt) {
                    WEBGIS.map.measure.sketch = evt.feature;
                    var tooltipCoord = evt.coordinate;
                    WEBGIS.map.measure.tmpListen = WEBGIS.map.measure.sketch.getGeometry().on('change', function (evt) {
                        var geom = evt.target;
                        var output = WEBGIS.map.measure.formatLength(geom);
                        tooltipCoord = geom.getLastCoordinate();
                        WEBGIS.map.measure.measureTooltipElement.innerHTML = output;
                        WEBGIS.map.measure.measureTooltip.setPosition(tooltipCoord);
                    });
                }, this);
            WEBGIS.map.measure.draw.on('drawend',
                function (evt) {
                    /*WEBGIS.map.measure.measureTooltipElement.className = 'tooltip tooltip-static';
                    WEBGIS.map.measure.measureTooltip.setOffset([0, -7]);
                    WEBGIS.map.measure.sketch = null;
                    WEBGIS.map.measure.measureTooltipElement = null;
                    WEBGIS.map.measure.createMeasureTooltip();
                    ol.Observable.unByKey(WEBGIS.map.measure.tmpListen);
                    WEBGIS.map.measure.unbindEvent();*/

                    var helpMsg = '单击确认起点';
                    WEBGIS.map.measure.measureTooltipElement.className = "";
                    WEBGIS.map.measure.measureTooltipElement.innerHTML = "";
                    WEBGIS.map.measure.sketch = null;
                    WEBGIS.map.measure.measureTooltipElement = null;
                    WEBGIS.map.measure.createMeasureTooltip();
                    WEBGIS.map.measure.measureTooltip.setPosition("undefined");
                    WEBGIS.map.measure.helpTooltipElement.innerHTML = helpMsg;

                    //WEBGIS.map.measure.draw.finishDrawing();
                    var arr = evt.feature;
                    setTimeout(function(){
                        WEBGIS.map.measure.source.removeFeature(arr);
                    },1000)
                }, this);

        },
        pointerMoveHandler: function (evt) {
            if (evt.dragging) {
                return;
            }
            var helpMsg = '单击确认起点';
            if (WEBGIS.map.measure.sketch) {
                var geom = (WEBGIS.map.measure.sketch.getGeometry());
                if (geom instanceof ol.geom.LineString) {
                    helpMsg = WEBGIS.map.measure.continueLineMsg;
                }
            }
            WEBGIS.map.measure.helpTooltipElement.innerHTML = helpMsg;
            WEBGIS.map.measure.helpTooltip.setPosition(evt.coordinate);
            WEBGIS.map.measure.helpTooltipElement.classList.remove('hidden');
        },
        formatLength: function (line) {
            var length = 0;
            var coordinates = line.getCoordinates();
            console.log(coordinates);
            var sourceProj = WEBGIS.map.map.getView().getProjection();
            for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
                var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
                var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
                length += WEBGIS.map.wgs84Sphere.haversineDistance(c1, c2);
            }
            var output;
            if (length > 100) {
                output = (Math.round(length / 1000 * 100) / 100) + ' ' + '公里'
                    + "/" + (length / 1000 * 0.5399568).toFixed(2) + ' ' + '海里';
            } else {
                output = (Math.round(length * 100) / 100) + ' ' + '米'
                    + "/" + (length / 1000 * 0.5399568).toFixed(2) + ' ' + '海里';
            }
            if(coordinates.length>1){
                var start=coordinates[coordinates.length-2];
                var end=coordinates[coordinates.length-1];
                if(start.join(",")==end.join(",")){
                    output+="[方位角:"+0+"]";
                }else{
                    output+="[方位角:"+WEBGIS.util.getAzimuth(start,end)+"]";
                }
            }
            return output;
        },
        createHelpTooltip: function () {
            if (WEBGIS.map.measure.helpTooltipElement) {
                WEBGIS.map.measure.helpTooltipElement.parentNode.removeChild(WEBGIS.map.measure.helpTooltipElement);
            }
            WEBGIS.map.measure.helpTooltipElement = document.createElement('div');
            WEBGIS.map.measure.helpTooltipElement.className = 'tooltip hidden';
            WEBGIS.map.measure.helpTooltip = new ol.Overlay({
                element: WEBGIS.map.measure.helpTooltipElement,
                offset: [15, 0],
                positioning: 'center-left'
            });
            WEBGIS.map.map.addOverlay(WEBGIS.map.measure.helpTooltip);
        },
        createMeasureTooltip: function () {
            if (WEBGIS.map.measure.measureTooltipElement) {
                WEBGIS.map.measure.measureTooltipElement.parentNode.removeChild(WEBGIS.map.measure.measureTooltipElement);
            }
            WEBGIS.map.measure.measureTooltipElement = document.createElement('div');
            WEBGIS.map.measure.measureTooltipElement.className = 'tooltip tooltip-measure';
            WEBGIS.map.measure.measureTooltip = new ol.Overlay({
                element: WEBGIS.map.measure.measureTooltipElement,
                offset: [0, -15],
                positioning: 'bottom-center'
            });
            WEBGIS.map.map.addOverlay(WEBGIS.map.measure.measureTooltip);
        },
        olunbykey:function(){
            ol.Observable.unByKey(WEBGIS.map.measure.tmpListen);
        }
    };

    WEBGIS.map.measure.bindEvent = function () {
        WEBGIS.map.map.on('pointermove', WEBGIS.map.measure.pointerMoveHandler);
        /*WEBGIS.map.map.getViewport().addEventListener('mouseout', function() {
         WEBGIS.map.measure.helpTooltipElement.classList.add('hidden');
         });*/
        WEBGIS.map.map.addInteraction(WEBGIS.map.measure.draw);
    };

    WEBGIS.map.measure.unbindEvent = function () {
        WEBGIS.map.measure.olunbykey();
        WEBGIS.map.map.removeInteraction(WEBGIS.map.measure.draw);
        WEBGIS.map.map.un('pointermove', WEBGIS.map.measure.pointerMoveHandler);
        WEBGIS.map.measure.helpTooltipElement.classList.add('hidden');
        /*WEBGIS.map.map.getViewport().removeEventListener('mouseout');*/
    };
    /**
     * 测距方法end
     */



    /**
     * hover方法begin
     */
    WEBGIS.map.initHover = function () {
        WEBGIS.map.hover.createHoverOverlay();
        WEBGIS.map.map.on('pointermove',WEBGIS.map.hover.hoverEvent);
    };

    WEBGIS.map.hover = {
        hoverElement: null,
        hoverOverlay: null,
        createHoverOverlay: function () {
            if (WEBGIS.map.hover.hoverElement) {
                WEBGIS.map.hover.hoverElement.parentNode.removeChild(WEBGIS.map.hover.hoverElement);
            }
            WEBGIS.map.hover.hoverElement = document.createElement('div');
            WEBGIS.map.hover.hoverElement.className = 'ol-popup';
            WEBGIS.map.hover.hoverOverlay = new ol.Overlay({
                element: WEBGIS.map.hover.hoverElement,
                /*offset: [15, 0],*/
                autoPan: true,
                autoPanAnimation: {
                    duration: 250
                }/*,
                positioning: 'center-left'*/
            });
            WEBGIS.map.map.addOverlay(WEBGIS.map.hover.hoverOverlay);
        },
        hoverEvent : function(evt){
            if (evt.dragging) {
                return;
            }
            var pixel = WEBGIS.map.map.getEventPixel(evt.originalEvent);
            var hit = WEBGIS.map.map.hasFeatureAtPixel(pixel);
            if(hit){
                var feature = WEBGIS.map.map.forEachFeatureAtPixel(pixel,
                    function(feature) {
                        return feature;
                    });
                if (feature){
                    if(feature.isCrowdShow == "0" && feature.getProperties()["iconName"] != null){
                        var cont = "";
                        var su = "公里/时";
                        if(feature.speed_unit == "kn"){
                            su = "海里/时";
                        }
                        if(feature.speed != null){
                            cont += "速度:" + feature.speed + su;
                        }
                        if(feature.damageCont != null){
                            cont += "<br />" + "受损等级:" + feature.damageCont;
                        }
                        if(feature.damageDetailCont != null){
                            cont += "<br />" + "受损内容:" + feature.damageDetailCont;
                        }
                        if(cont != ""){
                            WEBGIS.map.hover.hoverElement.innerHTML = cont;
                            WEBGIS.map.hover.hoverOverlay.setPosition(evt.coordinate);
                        }
                    }else if(feature.isCrowdShow == "1" && typeof feature.crowdDetailCont != "undefined" && feature.crowdDetailCont != null){
                        var cont = "";
                        var su = "公里/时";
                        if(feature.speed_unit == "kn"){
                            su = "海里/时";
                        }
                        if(feature.speed != null){
                            cont += "速度:" + feature.speed + su;
                        }
                        cont += "<br /> 集群信息:" + feature.crowdDetailCont;
                        WEBGIS.map.hover.hoverElement.innerHTML = cont;
                        WEBGIS.map.hover.hoverOverlay.setPosition(evt.coordinate);
                    }
                }
            }else{
                WEBGIS.map.hover.hoverOverlay.setPosition(undefined);
                WEBGIS.map.hover.hoverElement.innerHTML = "";
            }
        }
    };



    /**
     * hover方法end
     */

    /**
     *
     * 选择图标后的放置图标方法
     */
    WEBGIS.map.chooseIcon = {
        mouseIconElement: null,
        mouseIcon: null,
        mouseHelpElement: null,
        mouseHelp: null,
        isMoving:false,
        bindEvent:function(type){
            WEBGIS.map.chooseIcon.isMoving = true;
            WEBGIS.map.chooseIcon.createMouseIcon();
            WEBGIS.map.chooseIcon.createMouseHelp();
            WEBGIS.map.map.on('pointermove', WEBGIS.map.chooseIcon.pointerMoveHandler);
            if(type == "1"){
                //图标
                WEBGIS.map.map.on('singleclick', WEBGIS.map.chooseIcon.singleClickHandlerForIcon);
            }else if(type == "0"){
                //文本
                WEBGIS.map.map.on('singleclick', WEBGIS.map.chooseIcon.singleClickHandlerForText);
            }
        },
        unbindEvent:function(){
            WEBGIS.map.chooseIcon.isMoving = false;
            WEBGIS.map.map.un('pointermove', WEBGIS.map.chooseIcon.pointerMoveHandler);
            WEBGIS.map.map.un('singleclick', WEBGIS.map.chooseIcon.singleClickHandlerForIcon);
            WEBGIS.map.map.un('singleclick', WEBGIS.map.chooseIcon.singleClickHandlerForText);
            WEBGIS.map.chooseIcon.mouseIconElement.parentNode.removeChild(WEBGIS.map.chooseIcon.mouseIconElement);
            WEBGIS.map.chooseIcon.mouseHelpElement.parentNode.removeChild(WEBGIS.map.chooseIcon.mouseHelpElement);
            WEBGIS.map.chooseIcon.mouseIcon.setPosition("undefined");
            WEBGIS.map.chooseIcon.mouseHelp.setPosition("undefined");
        },
        createMouseIcon: function () {
            WEBGIS.map.chooseIcon.mouseIconElement = document.createElement('div');
            WEBGIS.map.chooseIcon.mouseIconElement.innerHTML = "<img src=\""+ctx+"/image/icon-flag.png\" width=\"14\" height=\"20\">"
            //WEBGIS.map.chooseIcon.mouseIconElement.className = 'tooltip';
            WEBGIS.map.chooseIcon.mouseIcon = new ol.Overlay({
                element: WEBGIS.map.chooseIcon.mouseIconElement,
                offset: [0, -5],
                positioning: 'bottom-center'
            });
            WEBGIS.map.map.addOverlay(WEBGIS.map.chooseIcon.mouseIcon);
        },
        createMouseHelp: function () {
            WEBGIS.map.chooseIcon.mouseHelpElement = document.createElement('div');
            WEBGIS.map.chooseIcon.mouseHelpElement.innerHTML = "单击左键标记位置,右键取消此次操作";
            WEBGIS.map.chooseIcon.mouseHelpElement.className = 'tooltip';
            WEBGIS.map.chooseIcon.mouseHelp = new ol.Overlay({
                element: WEBGIS.map.chooseIcon.mouseHelpElement,
                offset: [15, 0],
                positioning: 'center-left'
            });
            WEBGIS.map.map.addOverlay(WEBGIS.map.chooseIcon.mouseHelp);
        },
        pointerMoveHandler: function (evt) {
            if (evt.dragging) {
                return;
            }
            WEBGIS.map.chooseIcon.mouseIcon.setPosition(evt.coordinate);
            WEBGIS.map.chooseIcon.mouseHelp.setPosition(evt.coordinate);
        },
        singleClickHandlerForIcon:function(evt){
            WEBGIS.map.chooseIcon.unbindEvent("1");
            var icon = {
                "id":"tmpId",
                "startPoint":evt.coordinate,
                "marker":ctx + "/image/icon-flag.png"
            };
            var anchor = new ol.Feature({
                geometry: new ol.geom.Point(icon.startPoint)
            });
            anchor.setStyle(new ol.style.Style({
                image: new ol.style.Icon({
                    src: icon.marker,
                    anchor: [0.5, 1],
                    crossOrigin:"anonymous"
                })
            }));
            WEBGIS.map.markerLayer.getSource().addFeature(anchor);
            // WEBGIS.plotChooseIcon.saveIcon(anchor);
            WEBGIS.plotChooseIcon.addIcon(anchor);
        },
        singleClickHandlerForText:function(evt){
            WEBGIS.map.chooseIcon.unbindEvent("0");
            var icon = {
                "id":"tmpId",
                "startPoint":evt.coordinate,
                "marker":ctx + "/image/icon-flag.png"
            };
            var anchor = new ol.Feature({
                geometry: new ol.geom.Point(icon.startPoint)
            });
            anchor.setStyle(new ol.style.Style({
                image: new ol.style.Icon({
                    src: icon.marker,
                    anchor: [0.5, 1],
                    crossOrigin:"anonymous"
                })
            }));
            WEBGIS.map.markerLayer.getSource().addFeature(anchor);
            WEBGIS.plotText.addText(anchor);
        }
    };

    /**
     * 右键
     */
    WEBGIS.map.rightClickObj = {
        bindEvent:function(){
            $(WEBGIS.map.map.getViewport()).on('contextmenu',WEBGIS.map.rightClickObj.rightClick);
        },
        rightClick:function(e){
            e.preventDefault();
            // 书写事件触发后的函数
            if(WEBGIS.map.chooseIcon.isMoving){
                //取消选择图标
                WEBGIS.map.chooseIcon.unbindEvent();
                var tmpIcon = WEBGIS.map.markerLayer.getSource().getFeatureById("tmpId");
                if (tmpIcon != null) WEBGIS.map.markerLayer.getSource().removeFeature(tmpIcon);
                return;
            }
            //var coordinate = WEBGIS.map.map.getEventCoordinate(e);
            var pixel = WEBGIS.map.map.getEventPixel(e);
            var features = WEBGIS.mapOrder.hasFeatureAtPixel(pixel);
            if(features.length == 0) {
                return;
            }
            if (features.length==1){
                var feature = features[0];
                $("#div_features_list").hide();
                if($("#txt_is_director").val()!="1" && ($("#txt_unit_id").val()-feature.unit_id)!=0){
                    return false;
                }
                $("#div_popup_list a:lt(8)").show();
                if(feature.getProperties()["iconType"] == "1"){
                    $("#div_popup_list a:lt(8)").hide();
                }
                $("#popup-content").show();
                WEBGIS.mapOrder.featureSelect(feature);
                //WEBGIS.mapOrder.chooseFeature(feature);  待研究
                return;
            }

            if(features.length > 1) {
                $("#popup-content").hide();
                $("#div_features_list_sub").html("");
                var co = null;
                for(var i=0;i<features.length;i++){
                    var f = features[i];
                    /*var aObj = $("<a href=\"javascript:;\" class=\"list-group-item move-order\" onclick=\"WEBGIS.mapOrder.chooseFeature("+features[i]+");\">"+ features[i].iconName + "<span class=\"glyphicon glyphicon-chevron-right pull-right\"></span></a>");*/
                    var aObj = $("<a href=\"javascript:;\" class=\"list-group-item\">"+ f.iconName + "<span class=\"glyphicon glyphicon-chevron-right pull-right\"></span></a>").on("click",{"f":f},WEBGIS.mapOrder.chooseFeature);
                    co = f.getGeometry().getCoordinates();
                    $("#div_features_list_sub").append(aObj);
                }
                $("#div_features_list").show();
                WEBGIS.mapOrder.overlay.setPosition(co);
                $("#order_popup").show();
            }
        }
    };
    WEBGIS.util={
        /**
         * 度换成弧度
         * @param  {Float} d  度
         * @return {[Float}   弧度
         */
        rad:function(d)
        {
            return d * Math.PI / 180.0;
        },

        /**
         * 弧度换成度
         * @param  {Float} x 弧度
         * @return {Float}   度
         */
        deg:function(x) {
            return x*180/Math.PI;
        },
        quadrant:function(start,end){
            var x=end[0]-start[0];
            var y=end[1]-start[1];
            var ret=0;
            if(x==0&&y==0){
                ret = 0;
            }else if(x>=0&&y>=0){
                ret=1;
            }else if(x>=0&&y<=0){
                ret = 4;
            }else if(x<=0&&y>=0){
                ret=2;
            }else if(x<=0&&y<=0){
                ret=3;
            }
            return ret;
        },
        /**
         * 求两经纬度方向角
         *
         * @param lon1
         *            第一点的经度
         * @param lat1
         *            第一点的纬度
         * @param lon2
         *            第二点的经度
         * @param lat2
         *            第二点的纬度
         * @return azimuth 方位角，角度（单位：°）
         * */
        getAzimuth:function(start,end) {
            lat1 = WEBGIS.util.rad(start[1]);
            lat2 = WEBGIS.util.rad(end[1]);
            lon1 = WEBGIS.util.rad(start[0]);
            lon2 = WEBGIS.util.rad(end[0]);
            var azimuth = Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1)
                * Math.cos(lat2) * Math.cos(lon2 - lon1);
            azimuth = Math.sqrt(1 - azimuth * azimuth);
            azimuth = Math.cos(lat2) * Math.sin(lon2 - lon1) / azimuth;
            azimuth = Math.asin(azimuth) * 180 / Math.PI;
            if (isNaN(azimuth)) {
                if (lon1 < lon2) {
                    azimuth = 90.0;
                } else {
                    azimuth = 270.0;
                }
            }
            var quadrant=WEBGIS.util.quadrant(start,end);
            if(quadrant==2){
                azimuth=360+azimuth;
            }
            if(quadrant==3){
                azimuth=180-azimuth;
            }
            if(quadrant==4){
                azimuth=180-azimuth;
            }
            return azimuth.toFixed(2);
        }
    }
})(WEBGIS);
