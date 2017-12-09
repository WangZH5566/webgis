(function (WEBGIS) {
    WEBGIS.gis = {
        //常亮定义
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
    }
})(WEBGIS);