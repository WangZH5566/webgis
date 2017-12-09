(function (a) {
    a.util = {
        geom: function(val) {
            if(val == null || val == undefined) {
                return [ ,];
            }
            var vals = val.replace(/(^\s*)|(\s*$)/g, "").split(",");
            if(vals.length != 2) {
                return [ ,];
            }
            var geom = new Array();
            geom.push(Number(vals[0]));
            geom.push(Number(vals[1]));
            return geom;
        },
        geom4: function(val) {
            if(val == null || val == undefined) {
                return [ ,,,];
            }
            var vals = val.replace(/(^\s*)|(\s*$)/g, "").split(",");
            if(vals.length != 4) {
                return [ ,,,];
            }
            var geom = new Array();
            geom.push(Number(vals[0]));
            geom.push(Number(vals[1]));
            geom.push(Number(vals[2]));
            geom.push(Number(vals[3]));
            return geom;
        },
        map:{},
        markerlayer:{},
        overlay:{},
        drag:{},
        selectClick:{},
        /**
         *
         * @param url
         * @param layer
         * @param bound
         * @param mapId    地图id
         * @param popupId  tips框id
         * @param mpId     鼠标坐标显示栏id
         */
        buildmap: function (url,layer,bound,mapId,popupId,mpId) {
            //拖拽事件
            a.util.drag.Drag = function() {
                ol.interaction.Pointer.call(this, {
                    handleDownEvent: a.util.drag.Drag.prototype.handleDownEvent,
                    handleDragEvent: a.util.drag.Drag.prototype.handleDragEvent,
                    handleMoveEvent: a.util.drag.Drag.prototype.handleMoveEvent,
                    handleUpEvent: a.util.drag.Drag.prototype.handleUpEvent
                });
                this.coordinate_ = null;
                this.cursor_ = 'pointer';
                this.feature_ = null;
                this.previousCursor_ = undefined;
            };
            ol.inherits(a.util.drag.Drag, ol.interaction.Pointer);
            a.util.drag.Drag.prototype.handleDownEvent = function(evt) {
                var map = evt.map;
                var feature = map.forEachFeatureAtPixel(evt.pixel,
                    function(feature) {
                        return feature;
                    });
                if (feature) {
                    this.coordinate_ = evt.coordinate;
                    this.feature_ = feature;
                }
                return !!feature;
            };
            a.util.drag.Drag.prototype.handleDragEvent = function(evt) {
                var deltaX = evt.coordinate[0] - this.coordinate_[0];
                var deltaY = evt.coordinate[1] - this.coordinate_[1];

                var geometry = (this.feature_.getGeometry());
                geometry.translate(deltaX, deltaY);

                this.coordinate_[0] = evt.coordinate[0];
                this.coordinate_[1] = evt.coordinate[1];
            };
            a.util.drag.Drag.prototype.handleMoveEvent = function(evt) {
                if (this.cursor_) {
                    var map = evt.map;
                    var feature = map.forEachFeatureAtPixel(evt.pixel,
                        function(feature) {
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
            a.util.drag.Drag.prototype.handleUpEvent = function() {
                if(this.feature_.getId() != null && this.feature_.getId() != "" && this.feature_.getId() != "anchor"){
                    $.post(ctx + "/exec/modifyCoordinate",{"id":this.feature_.getId(),"coordinate":this.coordinate_.join(",")});
                }
                this.coordinate_ = null;
                this.feature_ = null;
                return false;
            };
            //弹出层
            a.util.overlay = new ol.Overlay(({
                element: document.getElementById(popupId),
                autoPan: true,
                autoPanAnimation: {
                    duration: 250
                }
            }));
            //标注层
            a.util.markerlayer = new ol.layer.Vector({
                source: new ol.source.Vector({

                })
            });
            //装载地图
            a.util.map = new ol.Map({
                layers: [
                    new ol.layer.Image({
                        source: new ol.source.ImageWMS({
                            ratio: 1,
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
                    a.util.markerlayer
                ],
                overlays:[
                    a.util.overlay
                ],
                target: document.getElementById(mapId),
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
                interactions: ol.interaction.defaults().extend([new a.util.drag.Drag()])
            });
            a.util.map.getView().fit(a.util.geom4(bound), a.util.map.getSize());
            var mousePositionControl = new ol.control.MousePosition({
                coordinateFormat: ol.coordinate.createStringXY(4),
                projection: "EPSG:4326",
                className: "custom-mouse-position",
                target: document.getElementById(mpId),
                undefinedHTML: "&nbsp"
            });
            a.util.selectClick = new ol.interaction.Select({
                // select interaction working on "click"
                condition: ol.events.condition.click
                // API文档里面有说明，可以设置style参数，用来设置选中后的样式，但是这个地方我们注释掉不用，因为就算不注释，也没作用，为什么？
                // 答案见http://weilin.me/ol3-primer/ch09/09-03.html
            });
            a.util.map.addControl(mousePositionControl);
            a.util.map.addInteraction(a.util.selectClick);
            a.util.map.on("singleclick",function(evt){
                var feature = a.util.map.forEachFeatureAtPixel(evt.pixel,
                    function(feature) {
                        return feature;
                    });
                if (feature){

                } else {
                    a.util.unmarker("anchor");
                    var coordinate = evt.coordinate;
                    var aaa = {
                        id:"anchor",
                        marker: 'https://openlayers.org/en/v3.19.1/examples/data/icon.png',
                        startPoint:coordinate
                    };
                    a.util.marker(aaa);
                }
            });
            // 监听选中事件，然后在事件处理函数中改变被选中的`feature`的样式
            a.util.selectClick.on('select', function(event){
                a.util.unmarker("anchor");
                //目前只会选中一个Feature
                //alert(event.selected[0].getId());
                //var imgObject = event.selected[0].getStyle().getImage();
                /*if(event.selected[0] != null){
                    var image = event.selected[0].getStyle().getImage().getImage();
                    var canvas = document.createElement('canvas');
                    var context = canvas.getContext('2d');
                    canvas.width = image.width;
                    canvas.height = image.height;
                    context.drawImage(image, 0, 0, image.width, image.height);
                    var imageData = context.getImageData(0, 0, canvas.width, canvas.height);
                    var data = imageData.data;
                    for (var i = 0, ii = data.length; i < ii; i = i + (i % 4 == 2 ? 2 : 1)) {
                        data[i] = 255 - data[i];
                    }
                    context.putImageData(imageData, 0, 0);
                    event.selected[0].setStyle(new ol.style.Style({
                        image: new ol.style.Icon(({
                            anchor: [0.5, 0.96],
                            src: undefined,
                            crossOrigin:"anonymous",
                            img: canvas,
                            imgSize: canvas ? [canvas.width, canvas.height] : undefined
                        }))
                    }));
                }*/
            });
            a.util.map.on('pointermove', function(e) {
                if (e.dragging) {
                    a.util.overlay.setPosition(undefined);
                    $("#div_popup_content").html("");
                    return;
                }
                var pixel = a.util.map.getEventPixel(e.originalEvent);
                var hit = a.util.map.hasFeatureAtPixel(pixel);
                if(hit){
                    var feature = a.util.map.forEachFeatureAtPixel(pixel,
                        function(feature) {
                            return feature;
                        });
                    if (feature && feature.getProperties()["iconName"] != null){
                        a.util.overlay.setPosition(e.coordinate);
                        $("#div_popup_content").html(feature.getProperties()["iconName"]);
                    }
                }else{
                    a.util.overlay.setPosition(undefined);
                    $("#div_popup_content").html("");
                }
                //a.util.map.getTarget().style.cursor = hit ? 'pointer' : '';
            });
            // 监听地图层级变化
            a.util.map.getView().on('change:resolution', function(){
                var features=a.util.markerlayer.getSource().getFeatures();
                for(var i=0;i<features.length;i++){
                    var style = features[i].getStyle();
                    // 重新设置图标的缩放率，基于层级10来做缩放
                    style.getImage().setScale(a.util.map.getView().getZoom()/8);
                    features[i].setStyle(style);
                }
            })
        },
        marker: function (val) {
            //设定锚点坐标位置
            var anchor = new ol.Feature({
                geometry: new ol.geom.Point(val.startPoint)
            });
            anchor.setId(val.id);
            if(val.iconId != null){
                anchor.setProperties({"iconId":val.iconId});
            }
            if(val.iconName != null){
                anchor.setProperties({"iconName":val.iconName});
            }
            //设定图标
            anchor.setStyle(new ol.style.Style({
                image: new ol.style.Icon({
                    src: val.marker,
                    anchor: [0.5, 1]/*,
                    crossOrigin:"anonymous",
                    img: undefined,
                    imgSize: undefined*/
                })/*,
                stroke: new ol.style.Stroke({
                    width: 2,
                    color: [122, 0, 0, 1]
                }),
                fill: new ol.style.Fill({
                    color: [0, 0, 255, 0.6]
                })*/
            }));
            a.util.markerlayer.getSource().addFeature(anchor);
        },
        unmarker: function (id) {
            var anchor = a.util.markerlayer.getSource().getFeatureById(id);
            if(anchor != null) a.util.markerlayer.getSource().removeFeature(anchor);
        },
        addFeature:function(anchor){
            a.util.markerlayer.getSource().addFeature(anchor);
        },
        getFeatureById:function(id){
            return a.util.markerlayer.getSource().getFeatureById(id);
        },
        getSelectedFeatures:function(){
            return a.util.selectClick.getFeatures();
        }
    }
})(WEBGIS);