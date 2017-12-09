(function (WEBGIS) {

    WEBGIS.gis.getFightTime = function () {
        if (WEBGIS.gis.FIGHT_TIME == undefined) {
            WEBGIS.gis.FIGHT_TIME = new Date().getTime();
        }
        return WEBGIS.gis.FIGHT_TIME;
    };

    WEBGIS.gis.setFightTime = function (time) {
        WEBGIS.gis.FIGHT_TIME = time;
    };

    WEBGIS.gis.initializeMoveEvent = function (param) {
        //添加moving监控
        var push = 0;
        WEBGIS.gis.map.on('postcompose', function (event) {
            if (++push % 2 == 0) {
                WEBGIS.gis.markerLayer.setStyle(null);
            } else {
                WEBGIS.gis.markerLayer.setStyle(new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        width: 4,
                        color: [237, 212, 0, 0.8]
                    })
                }));
            }
            var start = WEBGIS.gis.getFightTime();
            var features = WEBGIS.gis.markerLayer.getSource().getFeatures();
            for (var i = 0; i < features.length; i++) {
                var feature = features[i];
                var icon = feature.ICON;
                for (key in feature.MOVING_ORDER_QUEUE) {
                    var order = feature.MOVING_ORDER_QUEUE[key];
                    if (order.status != 'moving') {
                        continue;
                    }
                    var array = order.pathCoordinate;
                    var k = (array[3] - array[1]) / (array[2] - array[1]);
                    var distance = ((event.frameState.time - start) / 1000 / 60 / 60) * icon.speed * 1.87;
                    var sin = Math.sqrt(k * k / (1 + k * k));
                    var cos = Math.sqrt(1 / (1 + k * k));
                    var y1 = distance * sin;
                    var x1 = distance * cos;
                    var geom = new ol.geom.Point(ol.proj.transform([x1, y1],
                        'EPSG:3857','EPSG:4326'));
                    //event.vectorContext.drawFeature(new ol.Feature(geom), feature.getStyle());
                    feature.getGeometry().translate(geom.getFlatCoordinates()[0], -1 * geom.getFlatCoordinates()[1]);
                    //feature.setGeometry('geometry',[, ]);

                    //画路径
                    var route = WEBGIS.gis.routeLayer.getSource().getFeatureById('route-' + order.id);
                    if (route == null) {
                        route = new ol.Feature({
                            geometry: new ol.geom.LineString([[array[0], array[1]], [array[2], array[3]]])
                        });
                        route.setStyle(new ol.style.Style({
                            stroke: new ol.style.Stroke({
                                width: 4,
                                color: [237, 212, 0, 0.8],
                                lineDash: [8]
                            })
                        }));
                        route.setId('route-' + order.id);
                        WEBGIS.gis.routeLayer.getSource().addFeature(route);
                    }
                    WEBGIS.gis.map.render();
                }
            }
        });


        WEBGIS.gis.map.on('postcompose', function () {
            //console.log('second postcompose');
        });
    };

    WEBGIS.gis.appendOrder = function (val) {
        if (val == null || val == undefined || val.length == 0) {
            return;
        }
        var features = WEBGIS.gis.markerLayer.getSource().getFeatures();
        for (var i = 0; i < val.length; i++) {
            var order = val[i];
            for (var j = 0; j < features.length; j++) {
                var feature = features[j];
                //如果指令非当前要素的，则尝试下一个要素
                if (order.iconOneId != feature.getId()) {
                    continue;
                }

                var _orderName = 'order-' + order.id;

                //如果当前指令已经记录，则跳出
                if (feature.ORDER_QUEUE[_orderName] != undefined) {
                    break;
                }
                feature.ORDER_QUEUE[_orderName] = order;

                //如果是移动指令
                if (order.orderType == 1) {
                    var exp = new RegExp("\\d{1,}\\.\\d{1,}", "ig");
                    order.pathCoordinate = order.pathCoordinate.match(exp);
                    order.status = 'moving';
                    order.onFinished = 'stop';
                    feature.MOVING_ORDER_QUEUE[_orderName] = order;
                }
            }
        }
    };
})(WEBGIS);