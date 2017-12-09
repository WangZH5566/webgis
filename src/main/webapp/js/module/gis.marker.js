(function (WEBGIS) {

    //已经装载的图标
    WEBGIS.gis.markerCache = {};

    WEBGIS.gis.marker = function (val) {
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
            if (WEBGIS.gis.markerCache[id] == undefined) { //如果是新的标注，则记录在markerCache中
                WEBGIS.gis.markerCache[id] = value[i];
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
        WEBGIS.gis.markerLayer.getSource().addFeature(anchor);
    };

    //私有函数，设置图标缩放
    var _featureScale = function (feature) {
        var style = feature.getStyle();
        // 重新设置图标的缩放率，基于层级10来做缩放
        style.getImage().setScale(WEBGIS.gis.map.getView().getZoom() / 8);
        feature.setStyle(style);
    };

    WEBGIS.gis.markerByxxx = function (val) {
        if (val == null || val == undefined || typeof (val) != 'object') {
            return;
        }
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
                anchor: [0.5, 1]
            }),
            text: new ol.style.Text({
                font: '12px Calibri,sans-serif',
                text: val.iconName == null ? "" : val.iconName,
                offsetY : 10
            })
        }));

        WEBGIS.gis.markerLayer.getSource().addFeature(anchor);
    };

    WEBGIS.gis.unmarker = function (id) {
        var anchor = WEBGIS.gis.getMarkerLayer().getSource().getFeatureById(id);
        if(anchor != null) WEBGIS.gis.getMarkerLayer().getSource().removeFeature(anchor);
    };

    WEBGIS.gis.getFeatureById = function(id){
        return WEBGIS.gis.getMarkerLayer().getSource().getFeatureById(id);
    }

})(WEBGIS);