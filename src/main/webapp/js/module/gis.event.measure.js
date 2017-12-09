(function (WEBGIS) {

    WEBGIS.gis.measure = {
        source:new ol.source.Vector(),
        draw:null,
        sketch:null,
        helpTooltipElement:null,
        helpTooltip:null,
        measureTooltipElement:null,
        measureTooltip:null,
        continueLineMsg:"单击确认地点,双击结束",
        wgs84Sphere:null
    };


    WEBGIS.gis.initializeMeasureEvent = function (param) {
        WEBGIS.gis.measure.wgs84Sphere = new ol.Sphere(6378137);
        var measure = new ol.layer.Vector({
            source:  WEBGIS.gis.measure.source,
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
        WEBGIS.gis.map.addLayer(measure);
        WEBGIS.gis.measure.addInteraction();
    };

    WEBGIS.gis.measure.bindEvent = function(){
        WEBGIS.gis.map.on('pointermove', WEBGIS.gis.measure.pointerMoveHandler);
        /*WEBGIS.gis.map.getViewport().addEventListener('mouseout', function() {
            WEBGIS.gis.measure.helpTooltipElement.classList.add('hidden');
        });*/
        WEBGIS.gis.map.addInteraction(WEBGIS.gis.measure.draw);
    };

    WEBGIS.gis.measure.unbindEvent = function(){
        WEBGIS.gis.map.removeInteraction(WEBGIS.gis.measure.draw);
        WEBGIS.gis.map.un('pointermove',WEBGIS.gis.measure.pointerMoveHandler);
        WEBGIS.gis.measure.helpTooltipElement.classList.add('hidden');
        /*WEBGIS.gis.map.getViewport().removeEventListener('mouseout');*/
    };

    WEBGIS.gis.measure.pointerMoveHandler = function(evt) {
        if (evt.dragging) {
            return;
        }
        var helpMsg = '单击确认起点';
        if (WEBGIS.gis.measure.sketch) {
            var geom = (WEBGIS.gis.measure.sketch.getGeometry());
            if (geom instanceof ol.geom.LineString) {
                helpMsg = WEBGIS.gis.measure.continueLineMsg;
            }
        }
        WEBGIS.gis.measure.helpTooltipElement.innerHTML = helpMsg;
        WEBGIS.gis.measure.helpTooltip.setPosition(evt.coordinate);
        WEBGIS.gis.measure.helpTooltipElement.classList.remove('hidden');
    };

    WEBGIS.gis.measure.formatLength = function(line) {
        var length = 0;
        var coordinates = line.getCoordinates();
        var sourceProj = WEBGIS.gis.map.getView().getProjection();
        for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
            var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
            var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
            length += WEBGIS.gis.measure.wgs84Sphere.haversineDistance(c1, c2);
        }
        var output;
        if (length > 100) {
            output = (Math.round(length / 1000 * 100) / 100) +
                ' ' + 'km';
        } else {
            output = (Math.round(length * 100) / 100) +
                ' ' + 'm';
        }
        return output;
    };

    WEBGIS.gis.measure.addInteraction = function(){
        WEBGIS.gis.measure.draw = new ol.interaction.Draw({
            source: WEBGIS.gis.measure.source,
            type:/** @type {ol.geom.GeometryType} */ ("LineString"),
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
        WEBGIS.gis.measure.createMeasureTooltip();
        WEBGIS.gis.measure.createHelpTooltip();
        var listener;
        WEBGIS.gis.measure.draw.on('drawstart',
            function(evt) {
                WEBGIS.gis.measure.sketch = evt.feature;
                var tooltipCoord = evt.coordinate;
                listener = WEBGIS.gis.measure.sketch.getGeometry().on('change', function(evt) {
                    var geom = evt.target;
                    var output = WEBGIS.gis.measure.formatLength(geom);
                    tooltipCoord = geom.getLastCoordinate();
                    WEBGIS.gis.measure.measureTooltipElement.innerHTML = output;
                    WEBGIS.gis.measure.measureTooltip.setPosition(tooltipCoord);
                });
            }, this);
        WEBGIS.gis.measure.draw.on('drawend',
            function() {
                WEBGIS.gis.measure.measureTooltipElement.className = 'tooltip tooltip-static';
                WEBGIS.gis.measure.measureTooltip.setOffset([0, -7]);
                WEBGIS.gis.measure.sketch = null;
                WEBGIS.gis.measure.measureTooltipElement = null;
                WEBGIS.gis.measure.createMeasureTooltip();
                ol.Observable.unByKey(listener);
                WEBGIS.gis.measure.unbindEvent();
            }, this);
    };

    WEBGIS.gis.measure.createHelpTooltip = function() {
        if (WEBGIS.gis.measure.helpTooltipElement) {
            WEBGIS.gis.measure.helpTooltipElement.parentNode.removeChild(WEBGIS.gis.measure.helpTooltipElement);
        }
        WEBGIS.gis.measure.helpTooltipElement = document.createElement('div');
        WEBGIS.gis.measure.helpTooltipElement.className = 'tooltip hidden';
        WEBGIS.gis.measure.helpTooltip = new ol.Overlay({
            element: WEBGIS.gis.measure.helpTooltipElement,
            offset: [15, 0],
            positioning: 'center-left'
        });
        WEBGIS.gis.map.addOverlay(WEBGIS.gis.measure.helpTooltip);
    };

    WEBGIS.gis.measure.createMeasureTooltip = function() {
        if (WEBGIS.gis.measure.measureTooltipElement) {
            WEBGIS.gis.measure.measureTooltipElement.parentNode.removeChild(WEBGIS.gis.measure.measureTooltipElement);
        }
        WEBGIS.gis.measure.measureTooltipElement = document.createElement('div');
        WEBGIS.gis.measure.measureTooltipElement.className = 'tooltip tooltip-measure';
        WEBGIS.gis.measure.measureTooltip = new ol.Overlay({
            element: WEBGIS.gis.measure.measureTooltipElement,
            offset: [0, -15],
            positioning: 'bottom-center'
        });
        WEBGIS.gis.map.addOverlay(WEBGIS.gis.measure.measureTooltip);
    };
})(WEBGIS);