(function (WEBGIS) {
    WEBGIS.gis.drag = {};
    WEBGIS.gis.drag.Drag = function() {
        ol.interaction.Pointer.call(this, {
            handleDownEvent: WEBGIS.gis.drag.Drag.prototype.handleDownEvent,
            handleDragEvent: WEBGIS.gis.drag.Drag.prototype.handleDragEvent,
            handleMoveEvent: WEBGIS.gis.drag.Drag.prototype.handleMoveEvent,
            handleUpEvent: WEBGIS.gis.drag.Drag.prototype.handleUpEvent
        });
        this.coordinate_ = null;
        this.cursor_ = 'pointer';
        this.feature_ = null;
        this.previousCursor_ = undefined;
    };
    ol.inherits(WEBGIS.gis.drag.Drag, ol.interaction.Pointer);

    WEBGIS.gis.drag.Drag.prototype.handleDownEvent = function(evt) {
        var map = evt.map;
        var feature = map.forEachFeatureAtPixel(evt.pixel,
            function(feature,layer) {
                //alert(layer.get("name"));
                return feature;
            });
        if (feature) {
            //if(feature.getSource()) console.log("222");
            this.coordinate_ = evt.coordinate;
            this.feature_ = feature;
        }
        return !!feature;
    };
    WEBGIS.gis.drag.Drag.prototype.handleDragEvent = function(evt) {
        var deltaX = evt.coordinate[0] - this.coordinate_[0];
        var deltaY = evt.coordinate[1] - this.coordinate_[1];

        var geometry = (this.feature_.getGeometry());
        geometry.translate(deltaX, deltaY);

        this.coordinate_[0] = evt.coordinate[0];
        this.coordinate_[1] = evt.coordinate[1];
    };
    WEBGIS.gis.drag.Drag.prototype.handleMoveEvent = function(evt) {
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
    WEBGIS.gis.drag.Drag.prototype.handleUpEvent = function() {
        /*if(this.feature_.getId() != null && this.feature_.getId() != "" && this.feature_.getId() != "anchor"){
         $.post(ctx + "/exec/modifyCoordinate",{"id":this.feature_.getId(),"coordinate":this.coordinate_.join(",")});
         }*/
        this.coordinate_ = null;
        this.feature_ = null;
        return false;
    };

    WEBGIS.gis.initializeDragEvent = function (param) {
        //ol.interaction.defaults().extend([new WEBGIS.gis.drag.Drag()]);
        //WEBGIS.gis.addInteraction(ol.interaction.defaults().extend([new WEBGIS.gis.drag.Drag()]));

        //WEBGIS.gis.addInteraction(ol.interaction.defaults().extend([new WEBGIS.gis.drag.Drag()])[0]);
    };
})(WEBGIS);