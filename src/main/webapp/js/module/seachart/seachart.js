(function (a) {
    a.seachart = {

        main: {
            init: function () {
                $('#btn_delete').click(a.seachart.main.delete);
                $('#btn_upload').click(a.seachart.main.upload);
                a.seachart.main.loading();
            },

            delete: function () {
                var selected = $("input[name='id']:checked");
                if (selected.length == 0) {
                    return;
                }
                var ids = new Array();
                selected.each(function () {
                    ids.push($(this).val());
                });
                $.ajax({
                    url: ctx + '/sc/delete',
                    type: 'post',
                    data: {'ids': ids.join(",")},
                    dataType: 'html',
                    success: function (val) {
                        window.location.href = ctx + '/sc/main';
                    }
                });
            },

            upload: function () {
                window.location.href = ctx + '/sc/add';
            },

            loading: function () {
                var param = {};

                $("#div_list").load(ctx + "/sc/queryPage", param, function () {
                    $("#tbody_list tr").on("click", function () {
                        $("#tbody_list tr").removeClass("selected");
                        $(this).addClass("selected");
                        var url = $(this).attr("url");
                        var bound = $(this).attr("bound");
                        var layer = $(this).attr("layer");
                        var num = $(this).attr("num");
                        var resolution = $(this).attr("resolution");

                        a.seachart.main.clear();
                        a.seachart.main.preview(url, bound, layer, num, resolution);
                    });

                    $("#check_all").on("click", function () {
                        if ($(this).is(":checked")) {
                            $("#tbody_list input[type='checkbox']").prop("checked", true);
                        } else {
                            $("#tbody_list input[type='checkbox']").prop("checked", false);
                        }
                    });
                });
            },

            clear: function () {
                $('#map').empty();
                $('#btn_zoomin').unbind();
                $('#btn_zoomout').unbind();
                $('#btn_up').unbind();
                $('#btn_down').unbind();
                $('#btn_right').unbind();
                $('#btn_left').unbind();
            },
            preview: function (url, bound, layer, num, resolution) {
                var arraybound = bound.split(";");
                var arrayLayer = layer.split(";");
                var arrayResolution = resolution.split(";");
                if (num == "" || num == null || num == undefined || num == 0) {
                    num = arraybound.length;
                }

                var layers = new Array();
                for (var i = 0; i < num; i++) {
                    var imageLayer = new ol.layer.Image({
                        source: new ol.source.ImageWMS({
                            ratio: 1,
                            url: geoserver + "/" + url,
                            params: {
                                'FORMAT': 'image/png',
                                'VERSION': '1.1.0',
                                'SRS': 'EPSG:4326',
                                'LAYERS': arrayLayer[i],
                                'STYLES': ''
                            }
                        })
                    });
                    imageLayer.setVisible(false);
                    if(arrayResolution[i] == undefined || isNaN(arrayResolution[i])) {
                        imageLayer.compareResolution = parseFloat(arrayResolution[i]);
                    } else {
                        imageLayer.compareResolution = 1;
                    }

                    layers.push(imageLayer);
                };
                var map = new ol.Map({
                    layers: layers,
                    target: 'map',
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
                    })
                });

                map.getView().fit(a.util.geom4(arraybound[0]), map.getSize());
                a.seachart.main.chooseLayer(map);
                map.getView().on('change:resolution', function () {
                    a.seachart.main.chooseLayer(map);
                });

                $('#btn_zoomin').bind('click', function () {
                    var view = map.getView();
                    var zoom = view.getZoom();
                    view.setZoom(zoom + 1);
                });

                $('#btn_zoomout').bind('click', function () {
                    var view = map.getView();
                    var zoom = view.getZoom();
                    view.setZoom(zoom - 1);
                });

                $('#btn_up').bind('click', function () {
                    var view = map.getView();
                    var center = view.getCenter();
                    center[1] += -1;
                    view.setCenter(ol.proj.transform(center, 'EPSG:4326', 'EPSG:4326'));
                });

                $('#btn_down').bind('click', function () {
                    var view = map.getView();
                    var center = view.getCenter();
                    center[1] += 1;
                    view.setCenter(ol.proj.transform(center, 'EPSG:4326', 'EPSG:4326'));
                });

                $('#btn_left').bind('click', function () {
                    var view = map.getView();
                    var center = view.getCenter();
                    center[0] += 1;
                    view.setCenter(ol.proj.transform(center, 'EPSG:4326', 'EPSG:4326'));
                });

                $('#btn_right').bind('click', function () {
                    var view = map.getView();
                    var center = view.getCenter();
                    center[0] += -1;
                    view.setCenter(ol.proj.transform(center, 'EPSG:4326', 'EPSG:4326'));
                });
            },

            chooseLayer: function (map) {
                var layers = map.getLayers();
                var choosedLayer = undefined;
                var DIFF_RESOLUTION = 99999999999;
                layers.forEach(function (elem, index, arrays) {
                    elem.setVisible(false);
                    if (Math.abs(elem.compareResolution - map.getView().getResolution()) < DIFF_RESOLUTION) {
                        choosedLayer = elem;
                        DIFF_RESOLUTION = Math.abs(elem.compareResolution - map.getView().getResolution());
                    }
                });
                if (choosedLayer != undefined) {
                    choosedLayer.setVisible(true);
                }
            }
        },

        add: {
            init: function () {
                $('#btn_add_save').click(a.seachart.add.save);
                $('#btn_add_cancel').click(a.seachart.add.cancel);
            },

            init2: function () {
                $('#btn_add_save').click(a.seachart.add.save2);
                $('#btn_add_cancel').click(a.seachart.add.cancel);
            },

            save2: function () {
                $('#form_standard')[0].submit();
            },
            save: function () {
                if ($('#txt_exec_name').val() == '') {
                    layer.msg("请输入海图名称！", {icon: 2, time: 1000});
                    return false;
                }

                if ($('#txt_file_name').val() == '') {
                    layer.msg("请输入海图文件！", {icon: 2, time: 1000});
                    return false;
                }

                $('#form_standard')[0].submit();
            },
            cancel: function () {
                window.location.href = ctx + '/sc/main';
            },
            loading: function (subdir, images, name) {
                var $div = $('.widget-content');
                var $m_form = $('<form class="form-horizontal" action="' + ctx + '/sc/save_step2" method="post" novalidate="novalidate" id="form_standard"></form>');
                $m_form.append('<input type="hidden" name="subdir" value="' + subdir + '"><input type="hidden" name="name" value="' + name + '">');
                $div.append($m_form);
                for (var i = 0; i < images.length; i++) {
                    $form_group = $('<div class="form-group"></div>');
                    $image_left = $('<div class="col-sm-3">&nbsp;&nbsp;<img src="' + ctx + '/sc/thumbnailator?subdir=' + subdir + '&image=' + images[i] + '"/></div>');
                    $form_group.append($image_left);
                    $div_right = $('<div class="col-sm-9"></div>');
                    $form_group.append($div_right);
                    $div_row1 = $('<div class="row"><label class="col-sm-2">左上角经纬度：</label><div class="col-sm-3"><input type="input" name="leftlon" class="form-control"></div><div class="col-sm-3"><input type="input" name="leftlat" class="form-control"></div></div>')
                    $div_row2 = $('<div class="row"><label class="col-sm-2">右上角经纬度：</label><div class="col-sm-3"><input type="input" name="rightlon" class="form-control"></div><div class="col-sm-3"><input type="input" name="rightlat" class="form-control"></div></div>')
                    $div_right.append($div_row1);
                    $div_right.append($div_row2);
                    $div_right.append($('<input type="hidden" name="image" value="' + images[i] + '">'));
                    $m_form.append($form_group);
                }
            }
        }
    }
})(WEBGIS);