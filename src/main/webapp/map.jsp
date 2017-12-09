<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp"%>
<%@ include file="/common/vars.jsp" %>
<html>
<head>
    <title>Custom Icon</title>
    <link rel="stylesheet" href="./assert/ol/ol-3.19.0.css" type="text/css">
    <script src="./assert/global.js"></script>
    <script src="./assert/ol/ol-3.19.0.js"></script>
</head>
<body>
<div id="map" class="map"><div id="popup"></div></div>
<script>
    var logoElement = document.createElement('a');
    logoElement.href = 'https://www.osgeo.org/';
    logoElement.target = '_blank';

    var logoImage = document.createElement('img');
    logoImage.src = 'https://www.osgeo.org/sites/all/themes/osgeo/logo.png';

    logoElement.appendChild(logoImage);

    var map = new ol.Map({
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        target: 'map',
        view: new ol.View({
            center: [0, 0],
            zoom: 2
        }),
        logo: logoElement
    });
    alert(APP.CTX);
</script>
</body>
</html>