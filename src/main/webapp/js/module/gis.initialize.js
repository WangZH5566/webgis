(function (WEBGIS) {
    WEBGIS.gis = {

        //变量
        mapLayer: new Array(), //1.地图层

        routeLayer: null, //2.路径层

        markerLayer: null,//3.标注层

        popupLayer: null, //4.POPUP层

        map: null //装载的地图
    };

    WEBGIS.gis.show = function (param) {
        //1. 初始化画面
        var initializer = function (param) {
            //初始化地图层
            var init_mapLayer = function (param) {
                WEBGIS.gis.mapLayer = new Array();
                var arrayLayer = param.map.layer.split(";");
                var arrayResolution = param.map.resolution.split(";");
                var arrayBound = param.map.bound.split(";");
                for (var i = 0; i < param.map.num; i++) {
                    var layer = new ol.layer.Image({
                        source: new ol.source.ImageWMS({
                            ratio: 1,
                            url: param.map.url,
                            params: {
                                'FORMAT': 'image/png',
                                'VERSION': '1.1.0',
                                'SRS': 'EPSG:4326',
                                'LAYERS': arrayLayer[i],
                                STYLES: ''
                            }
                        })
                    });
                    layer.compareResolution=parseFloat(arrayResolution[i]);
                    layer.bound=arrayBound[i];
                    layer.setVisible(false);
                    WEBGIS.gis.mapLayer.push(layer);
                }
                WEBGIS.gis.mapLayer[0].setVisible(true);
            };

            //初始化标注层
            var init_markerLayer = function (param) {
                WEBGIS.gis.markerLayer = new ol.layer.Vector({
                    source: new ol.source.Vector()
                });
            };

            //初始化路径层
            var init_routeLayer = function (param) {
                WEBGIS.gis.routeLayer = new ol.layer.Vector({
                    source: new ol.source.Vector({
                        features: []
                    })
                });
            };

            //初始化POPUP层
            var init_popupLayer = function (param) {
                WEBGIS.gis.popupLayer = new ol.Overlay(({
                    element: document.getElementById(param.popup),
                    autoPan: true,
                    autoPanAnimation: {
                        duration: 250
                    }
                }));
            };

            var init_map = function (param) {

                var chooseLayer = function (map) {
                    var layers = WEBGIS.gis.mapLayer;
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
                for (var i = 0; i < WEBGIS.gis.mapLayer.length; i++) {
                    layers.push(WEBGIS.gis.mapLayer[i]);
                }
                layers.push(WEBGIS.gis.routeLayer);
                layers.push(WEBGIS.gis.markerLayer);
                var mapParam = {
                    layers: layers,
                    target: document.getElementById(param.target),
                    overlays: [WEBGIS.gis.popupLayer],
                    view: new ol.View({
                        projection: new ol.proj.Projection({
                            code: 'EPSG:4326',
                            units: 'degrees',
                            axisOrientation: 'neu'
                        })
                    }),
                    controls: ol.control.defaults({
                        zoom: false,
                        rotate: false,
                        attributionOptions: ({
                            collapsible: false
                        })
                    }).extend([
                        new ol.control.ScaleLine({
                            unit: 'metric'
                        }),
                        new ol.control.MousePosition({})
                    ]),
                    interactions: WEBGIS.gis.drag ? ol.interaction.defaults().extend([new WEBGIS.gis.drag.Drag()]) : ol.interaction.defaults().extend([])
                };
                WEBGIS.gis.map = new ol.Map(mapParam);
                WEBGIS.gis.map.getView().fit(WEBGIS.util.geom4(WEBGIS.gis.mapLayer[0].bound), WEBGIS.gis.map.getSize());
                //chooseLayer(WEBGIS.gis.map);

                WEBGIS.gis.map.getView().on('change:resolution', function () {
                    chooseLayer(WEBGIS.gis.map);
                });

                WEBGIS.gis.map.getView().on('change:resolution', function () {
                    var features = WEBGIS.gis.markerLayer.getSource().getFeatures();
                    for (var i = 0; i < features.length; i++) {
                        var style = features[i].getStyle();
                        // 重新设置图标的缩放率，基于层级10来做缩放
                        style.getImage().setScale(WEBGIS.gis.map.getView().getZoom() / 8);
                        //style.getText().setScale(WEBGIS.gis.map.getView().getZoom() / 8);
                        style.getText().setScale(WEBGIS.gis.map.getView().getZoom() / 8);
                        features[i].setStyle(style);
                    }
                })
            };
            init_mapLayer(param);
            init_routeLayer(param);
            init_markerLayer(param);
            init_popupLayer(param);
            init_map(param);
        };
        initializer(param);
        //WEBGIS.gis.initializeMoveEvent(param);
    };

    WEBGIS.gis.getMarkerLayer = function () {
        return WEBGIS.gis.markerLayer;
    };

})(WEBGIS);
