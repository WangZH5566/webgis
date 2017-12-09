(function (WEBGIS) {
    WEBGIS.gis.selectClick = new ol.interaction.Select({
        // select interaction working on "click"
        condition: ol.events.condition.click
        // API文档里面有说明，可以设置style参数，用来设置选中后的样式，但是这个地方我们注释掉不用，因为就算不注释，也没作用，为什么？
        // 答案见http://weilin.me/ol3-primer/ch09/09-03.html
    });

    WEBGIS.gis.initializeClickEvent = function (param) {
        WEBGIS.gis.map.addInteraction(WEBGIS.gis.selectClick);
        WEBGIS.gis.map.on("singleclick",function(evt){
            var feature = WEBGIS.gis.map.forEachFeatureAtPixel(evt.pixel,
                function(feature) {
                    return feature;
                });
            if (feature){} else {
                WEBGIS.gis.unmarker("anchor");
                var coordinate = evt.coordinate;
                var aaa = {
                    id:"anchor",
                    marker: ctx + "/image/tmp_icon.png",
                    startPoint:coordinate
                };
                WEBGIS.gis.markerByxxx(aaa);
            }
        });
        // 监听选中事件，然后在事件处理函数中改变被选中的`feature`的样式
        WEBGIS.gis.selectClick.on('select', function(event){
            WEBGIS.gis.unmarker("anchor");
        });
        WEBGIS.gis.map.on('pointermove', function(e) {
            if (e.dragging) {
                WEBGIS.gis.popupLayer.setPosition(undefined);
                $("#div_popup_content").html("");
                return;
            }
            var pixel = WEBGIS.gis.map.getEventPixel(e.originalEvent);
            var hit = WEBGIS.gis.map.hasFeatureAtPixel(pixel);
            if(hit){
                var feature = WEBGIS.gis.map.forEachFeatureAtPixel(pixel,
                    function(feature) {
                        return feature;
                    });
                if (feature && feature.getProperties()["iconName"] != null){
                    WEBGIS.gis.popupLayer.setPosition(e.coordinate);
                    $("#div_popup_content").html(feature.getProperties()["iconName"]);
                }
            }else{
                WEBGIS.gis.popupLayer.setPosition(undefined);
                $("#div_popup_content").html("");
            }
        });
    };

    WEBGIS.gis.getSelectedFeatures = function(){
        return WEBGIS.gis.selectClick.getFeatures();
    }

})(WEBGIS);