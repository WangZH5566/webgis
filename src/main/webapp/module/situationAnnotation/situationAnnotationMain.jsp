<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery.spinner/jquery.spinner.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assert/spectrum/spectrum.css">
<style type="text/css">
    .ol-popup {
        position: absolute;
        background-color: white;
        -webkit-filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));
        filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));
        padding: 15px;
        border-radius: 10px;
        border: 1px solid #cccccc;
        bottom: 12px;
        left: -50px;
        min-width: 280px;
    }
    .ol-popup:after, .ol-popup:before {
        top: 100%;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
    }
    .ol-popup:after {
        border-top-color: white;
        border-width: 10px;
        left: 48px;
        margin-left: -10px;
    }
    .ol-popup:before {
        border-top-color: #cccccc;
        border-width: 11px;
        left: 48px;
        margin-left: -11px;
    }
    .ol-popup-closer {
        text-decoration: none;
        position: absolute;
        top: 2px;
        right: 8px;
    }
    .ol-popup-closer:after {
        content: "✖";
    }
</style>
<html>
<body>
<canvas id="canvas">

</canvas>
<div id="div_canvas">

</div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>态势图</h5>
                        <h5 id="btn_save_map">保存</h5>
                    </div>
                    <div class="widget-content" id="map">
                        <div id="mouse-position"></div>
                    </div>
                        <div id="popup" class="ol-popup" style="display: none;">
                            <a href="javascript:;" id="popup-closer" onclick=" WEBGIS.situationAnnotation.overlay.setPosition(undefined);" class="ol-popup-closer"></a>
                            <div id="popup-content">
                                <div style="margin-bottom: 10px; margin-left: 2px;">选择动作：</div>
                                <div class="list-group">
                                    <a href="#" class="list-group-item" onclick="WEBGIS.situationAnnotation.selectOrder(1);">目的地移动<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                                    <a href="#" class="list-group-item" onclick="WEBGIS.situationAnnotation.selectOrder(4);">方向移动<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                                    <a href="#" class="list-group-item" onclick="WEBGIS.situationAnnotation.selectOrder(2);">维修<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                                    <a href="#" class="list-group-item" onclick="WEBGIS.situationAnnotation.selectOrder(3);">装载<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>

                                    <%--<a href="#" class="list-group-item" style="margin-top:5px;">指令管理</a>--%>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
<form id="form_move_angle" class="form-horizontal" style="width:385px;display: none;" novalidate="novalidate">
    <div class="form-group">
        <label class="col-sm-3 control-label">航向:</label>
        <div class="col-sm-8">
            <input type="text" id="txt_move_angle" value="" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-3 pull-right">
            <input type="button" style="margin-right:20px;" id="btn_move_angle_order_send" value="发送指令" class="btn btn-sm btn-primary buttons pull-right" />
        </div>
        <div class="col-sm-3 pull-right">
            <input type="button" id="btn_move_angle_order_cancel" onclick="WEBGIS.situationAnnotation.cancelOrder();" value="取消指令" class="btn btn-sm btn-primary buttons pull-right" />
        </div>
    </div>
</form>

    <form id="form_repair_level" class="form-horizontal" style="width:385px;display: none;" novalidate="novalidate">
        <div class="form-group">
            <label class="col-sm-3 control-label">受损等级:</label>
            <div class="col-sm-8">
                <select id="sel_icon_repair_level" class="form-control">
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-3 pull-right">
                <input type="button" style="margin-right:20px;" id="btn_repair_order_send" value="发送指令" class="btn btn-sm btn-primary buttons pull-right" />
            </div>
            <div class="col-sm-3 pull-right">
                <input type="button" id="btn_repair_order_cancel" onclick="WEBGIS.situationAnnotation.cancelOrder();" value="取消指令" class="btn btn-sm btn-primary buttons pull-right" />
            </div>
        </div>
    </form>
    <script src="${ctx}/js/layer-v2.2/layer/extend/layer.ext.js"></script>
    <script src="${ctx}/assert/ol/ol-3.19.0.js"></script>
    <script src="${ctx}/assert/spectrum/spectrum.js"></script>
    <script src="${ctx}/js/jquery.spinner/jquery.spinner.js?v=2016102813"></script>
    <script src="${ctx}/js/module/util.js?v=2016102813"></script>
    <script src="${ctx}/js/flipcountdown/flipcountdown.js"></script>
    <script src="${ctx}/js/module/situationAnnotation/situationAnnotation.js?v=20161028106"></script>
    <script type="text/javascript">
        var FILESERVER_ICON_VISITPATH="${FILESERVER_ICON_VISITPATH}";


//        var sa={};
//                /**
//                 * @constructor
//                 * @extends {ol.interaction.Pointer}
//                 */
//        sa.Drag = function() {
//            ol.interaction.Pointer.call(this, {
//                handleDownEvent: sa.Drag.prototype.handleDownEvent,
//                handleDragEvent: sa.Drag.prototype.handleDragEvent,
//                handleMoveEvent: sa.Drag.prototype.handleMoveEvent,
//                handleUpEvent: sa.Drag.prototype.handleUpEvent
//            });
//
//            /**
//             * @type {ol.Pixel}
//             * @private
//             */
//            this.coordinate_ = null;
//
//            /**
//             * @type {string|undefined}
//             * @private
//             */
//            this.cursor_ = 'pointer';
//
//            /**
//             * @type {ol.Feature}
//             * @private
//             */
//            this.feature_ = null;
//
//            /**
//             * @type {string|undefined}
//             * @private
//             */
//            this.previousCursor_ = undefined;
//
//        };
//        ol.inherits(sa.Drag, ol.interaction.Pointer);
//
//        /**
//         * @param {ol.MapBrowserEvent} evt Map browser event.
//         * @return {boolean} `true` to start the drag sequence.
//         */
//        sa.Drag.prototype.handleDownEvent = function(evt) {
//            var map = evt.map;
//
//            var feature = WEBGIS.situationAnnotation.hasFeatureAtPixel(evt.pixel);
//
//            if (feature) {
//                this.coordinate_ = evt.coordinate;
//                this.feature_ = feature;
//            }
//            return feature?true:false;
//        };
//
//        /**
//         * @param {ol.MapBrowserEvent} evt Map browser event.
//         */
//        sa.Drag.prototype.handleDragEvent = function(evt) {
//            var deltaX = evt.coordinate[0] - this.coordinate_[0];
//            var deltaY = evt.coordinate[1] - this.coordinate_[1];
//
//            var geometry = /** @type {ol.geom.SimpleGeometry} */
//                    (this.feature_.getGeometry());
//            geometry.translate(deltaX, deltaY);
//
//            this.coordinate_[0] = evt.coordinate[0];
//            this.coordinate_[1] = evt.coordinate[1];
//        };
//
//        /**
//         * @param {ol.MapBrowserEvent} evt Event.
//         */
//        sa.Drag.prototype.handleMoveEvent = function(evt) {
//            if (this.cursor_) {
//                var map = evt.map;
//                var feature = map.forEachFeatureAtPixel(evt.pixel,
//                        function(feature) {
//                            return feature;
//                        });
//                var element = evt.map.getTargetElement();
//                if (feature) {
//                    if (element.style.cursor != this.cursor_) {
//                        this.previousCursor_ = element.style.cursor;
//                        element.style.cursor = this.cursor_;
//                    }
//                } else if (this.previousCursor_ !== undefined) {
//                    element.style.cursor = this.previousCursor_;
//                    this.previousCursor_ = undefined;
//                }
//            }
//        };
//
//        /**
//         * @return {boolean} `false` to stop the drag sequence.
//         */
//        sa.Drag.prototype.handleUpEvent = function(evt) {
//            this.coordinate_ = null;
//            this.feature_ = null;
//            return false;
//        };
//

        $(document).ready(function(){
            WEBGIS.situationAnnotation.init();
        });

    </script>
</body>
</html>