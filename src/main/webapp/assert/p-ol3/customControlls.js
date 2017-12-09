(function (WEBGIS) {

    WEBGIS.customerControlls = {

    };

    WEBGIS.customerControlls.init = function(){
        //放大
        $("#btn_zoom_in").on("click",function(){
            var view = WEBGIS.map.map.getView();
            var zoom = view.getZoom();
            view.setZoom(zoom + 1);
        });
        //缩小
        $("#btn_zoom_out").on("click",function(){
            var view = WEBGIS.map.map.getView();
            var zoom = view.getZoom();
            view.setZoom(zoom - 1);

        });
        /*$(document).on("click",function(e) {
            if($(e.target).closest("cc-div").length == 0){
                $("#cc-select-detail").hide();
            }
        });
        $("#btn_tools").on("click",function(){
            $("#cc-select-detail").show();
        });*/
        //测距
        $("#btn_measure").on("click",function(){
            if($(this).hasClass("select-bg")){
                WEBGIS.map.measure.unbindEvent();
                $(this).removeClass("select-bg");
            }else{
                WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
                $("#cc-select-detail").hide();
                WEBGIS.map.measure.bindEvent();
                $(this).addClass("select-bg")
            }
        });

        //标注框
        /*$("#li_mark_label").on("click",function(){
            WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
            WEBGIS.map.map.on('click',WEBGIS.map.draw.bindClick);
            $("#cc-select-detail").hide();
            WEBGIS.map.draw.activate(P.PlotTypes.TIPS_PLACE);
        });*/

        //要图标绘
        $("#cc-div .cc-tools a.cc-a-tmp").on("click",function(){
            var mt = $(this).data("mt");
            $("#div_choose_icon").load(ctx+"/plot/chooseIcon",{"mt":mt},function(){
                layer.open({
                    type: 1,
                    title:["选择图标","font-weight:bold;"],
                    area:['500px','400px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        if($("#div_icon_list > div.selected").length == 0){
                            layer.msg("请选择图标！",{icon: 2,time:1000});
                            return false;
                        }
                        WEBGIS.plotChooseIcon.chooseTroopId = $("#div_icon_list > div.selected").eq(0).data("troopid");
                        layer.close(index);
                        WEBGIS.map.chooseIcon.bindEvent("1");
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_choose_icon"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_choose_icon").html("");
                    }
                });
            });
        });

        $("#btn_text").on("click",function(){
            WEBGIS.map.chooseIcon.bindEvent("0");
        });

        /*$("#li_icon").on("click",function(){
            WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
            WEBGIS.map.map.on('click',WEBGIS.map.draw.bindClick);
            $("#cc-select-detail").hide();
            WEBGIS.map.draw.activate(P.PlotTypes.MARKER,1);
        });*/

        //文字标绘
        /*$("#btn_text").on("click",function(){
            WEBGIS.map.map.un('click',WEBGIS.map.draw.bindClick);
            WEBGIS.map.map.on('click',WEBGIS.map.draw.bindClick);
            $("#cc-select-detail").hide();
            WEBGIS.map.draw.activate(P.PlotTypes.MARKER,2);
        });*/

        //集群设置
        $("#btn_crowd").on("click",function(){
            $("#div_crowd_view").load(ctx + "/sa/crowdSet",function(){
                $("#tbody_crowd_detail_list tr td:nth-child(1) input:checkbox").click(function(e){
                    e.stopPropagation();
                });

                $("#tbody_crowd_detail_list tr td:nth-child(4) input:checkbox").click(function(e){
                    if($(this).is(":checked")){
                        $("#tbody_crowd_detail_list tr td:nth-child(4) input:checkbox").prop("checked",false);
                        $(this).prop("checked",true);
                    }
                    e.stopPropagation();
                });

                $("#tbody_crowd_detail_list tr").click(function(){
                    var chkObj = $(this).find("td:nth-child(1)").find("input:checkbox");
                    if(typeof chkObj.attr("disabled") == "undefined"){
                        if(chkObj.is(":checked")){
                            chkObj.prop("checked",false);
                        }else{
                            chkObj.prop("checked",true);
                        }
                    }
                });

                $("#tbody_crowd_detail_list tr td:nth-child(5) a:nth-child(1)").click(function(){
                    var crowdId = $(this).data("id");
                    layer.confirm("确定要删除该集群吗？", {
                        btn: ['确定','取消'] //按钮
                    },function(index){
                        layer.close(index);
                        $.post(ctx + "/sa/deleteCrowd", {"id":crowdId}, function (msg) {
                            if (msg != null && msg.indexOf("|") > -1) {
                                var ids = msg.substring(msg.indexOf("|") + 1);
                                if(ids.length > 0){
                                    var arr = ids.split(",");
                                    for(var i=0;i<arr.length;i++){
                                        var f = WEBGIS.map.markerLayer.getSource().getFeatureById(arr[i]);
                                        f['crowdName'] = null;
                                        f['isMain'] = null;
                                        f.isCrowdShow = "0";
                                        if(f.tmpStyle != null && f.getStyle() == null){
                                            f.setStyle(f.tmpStyle);
                                            f.getStyle().getText().setText(f.iconName);
                                        }else{
                                            f.getStyle().getText().setText(f.iconName);
                                        }
                                    }
                                }
                                reloadCrowdDetail();
                                layer.msg("操作成功", {icon: 1, time: 1000});
                            } else {
                                layer.msg(msg, {icon: 2, time: 1000});
                            }
                        });
                    }, function(){
                    });
                });

                layer.open({
                    type: 1,
                    title:["集群设置","font-weight:bold;"],
                    area:['600px', '500px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ["确定","取消"],
                    yes: function(index, layero){
                        var cn = trim($("#txt_crowd_name").val());
                        if(cn == ""){
                            layer.msg("请输入[集群名称]", {icon:2, time: 1000});
                            return false;
                        }
                        var chkArr = new Array();
                        var chkNameArr = new Array();
                        $("#tbody_crowd_detail_list tr td:nth-child(1) input:checked").each(function(){
                            chkArr.push($(this).data("trid"));
                            chkNameArr.push($(this).data("na"));
                        });
                        if(chkArr.length == 0){
                            layer.msg("请勾选图标", {icon:2, time: 1000});
                            return false;
                        }

                        var mainId = null;
                        $("#tbody_crowd_detail_list tr td:nth-child(1) input:checked").each(function(){
                            var chk = $(this).parent().parent().find("td:nth-child(4) input:checkbox:checked");
                            if(chk.data("trid") == null){
                                return;
                            }
                            mainId = chk.data("trid");
                            return false;
                        });

                        if(mainId == null){
                            layer.msg("请勾选旗舰/长机", {icon:2, time: 1000});
                            return false;
                        }

                        var param = {
                            "cn":cn,
                            "iconIds":chkArr.join(","),
                            "mainId":mainId
                        };
                        $.post(ctx + "/sa/addCrowd",param, function (msg) {
                            if (msg != null && msg.indexOf("|") > -1) {
                                var id = msg.substring(msg.indexOf("|") + 1);
                                var crowdMsg = "";
                                for(var i=0;i<chkArr.length;i++){
                                    var anchor = WEBGIS.map.markerLayer.getSource().getFeatureById(chkArr[i]);
                                    anchor.crowdId = id;
                                    anchor.crowdName = cn;
                                    anchor.isMain = "0";

                                    if(mainId == chkArr[i]){
                                        anchor.isMain = "1";
                                        crowdMsg = chkNameArr[i] + "(旗舰/长机) <br>" + crowdMsg;
                                    }else{
                                        crowdMsg += chkNameArr[i] + "<br>";
                                    }
                                }
                                var mainIcon = WEBGIS.map.markerLayer.getSource().getFeatureById(mainId);
                                mainIcon.crowdDetailCont = crowdMsg;
                                layer.msg("操作成功", {icon: 1, time: 1000});
                                layer.close(index);
                            } else {
                                layer.msg(msg, {icon: 2, time: 1000});
                            }
                        });
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_crowd_view"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_crowd_view").html("");
                    }
                });
            });
        });

        //按类型显示
        $("#btn_view").on("click",function(){
            layer.open({
                type: 1,
                title:["显示切换","font-weight:bold;"],
                area:['300px','150px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var r = $("#div_icon_show_hide input:radio:checked").val();
                    if(typeof r == "undefined"){
                        layer.msg("请选择类型！", {icon: 2, time: 1000});
                        return false;
                    }
                    if(r == "4"){
                        //加载集群列表
                        $("#div_crowd_view").load(ctx + "/sa/crowdView",function(){
                            $("#tbody_crowd_list tr td:nth-child(1) input:checkbox").click(function(e){
                                e.stopPropagation();
                            });
                            $("#tbody_crowd_list tr").click(function(){
                                var chkObj = $(this).find("td:nth-child(1)").find("input:checkbox");
                                if(chkObj.is(":checked")){
                                    chkObj.prop("checked",false);
                                }else{
                                    chkObj.prop("checked",true);
                                }
                            });
                            layer.close(index);
                            layer.open({
                                type: 1,
                                title:["集群显示","font-weight:bold;"],
                                area:['600px', '500px'],
                                closeBtn: 1,                //不显示关闭按钮
                                btn: ["确定","取消"],
                                yes: function(ind, layero){
                                    var tmpArr = new Array();
                                    $("#tbody_crowd_list tr td:nth-child(1) input:checkbox:checked").each(function(){
                                        tmpArr.push($(this).data("id"));
                                    });
                                    corwdShow(tmpArr);
                                    layer.close(ind);
                                },
                                shift: 0,
                                shadeClose: false,           //开启遮罩关闭
                                content: $("#div_crowd_view"),
                                zIndex:100,
                                success: function(layero, index){},
                                end:function(){
                                    $("#div_crowd_view").html("");
                                }
                            });
                        });
                    }else{
                        iconTypeShow(r);
                    }
                    layer.close(index);
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#div_icon_show_hide"),
                zIndex:100,
                success: function(layero, index){},
                end:function(){
                    $('#div_icon_show_hide input:radio').prop("checked",false);
                }
            });
        });

        $("#btn_save_to_sa").on("click",function(){
            layer.closeAll();
            layer.prompt({title: '请输入备注', formType: 2}, function(text, index){
                var features=WEBGIS.map.markerLayer.getSource().getFeatures();
                var array=new Array();
                for(var i=0;i<features.length;i++){
                    var json={};
                    var tmp=features[i];
                    json.id=tmp.getId();
                    if(tmp.getProperties()["marker"] != null){
                        json.marker = tmp.getProperties()["marker"];
                    }
                    if(tmp.getProperties()["iconName"] != null){
                        json.iconName = tmp.getProperties()["iconName"];
                    }
                    if(tmp.getProperties()["colorArray"] != null){
                        json.colorArray = tmp.getProperties()["colorArray"];
                    }
                    json.startPoint = tmp.getGeometry().getCoordinates();
                    if(tmp.getProperties()["iconType"] != null){
                        json.iconType = tmp.getProperties()["iconType"];
                    }
                    if(tmp.iconText != null){
                        json.iconText = tmp.iconText;
                    }
                    array.push(json);
                }
                var iconData=JSON.stringify(array);
                $.post(ctx+"/userIcon/save",{iconData:iconData,comment:text},function(re){
                    if(re.indexOf("|") > -1){
                        layer.close(index);
                        layer.msg("保存成功",{icon:1,time:1000});
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            });
        });


        //计算公式
        $("#btn_formula").on("click",function(){
            $("#div_crowd_view").load(ctx + "/sa/formulaView",function(){
                $("#tbody_formula_list tr").on("click",function(){
                    if($(this).hasClass("selected")){
                        return false;
                    }
                    $("#tbody_formula_list tr").removeClass("selected");
                    $(this).addClass("selected")
                    $("#txt_formula_val").val($(this).data("vl"));
                    formulaParam();
                });

                $("#btn_fc").on("click",formulaCalculate);
                layer.open({
                    type: 1,
                    title:["计算公式","font-weight:bold;"],
                    area:['600px', '500px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ["关闭"],
                    yes: function(index, layero){
                        layer.close(index);
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_crowd_view"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_crowd_view").html("");
                    }
                });
            });
        });
    };

    /**
     * 集群设置模块,删除按钮事件触发后重新加载列表
     */
    function reloadCrowdDetail(){
        $("#div_crowd_view").load(ctx + "/sa/crowdSet",function() {
            $("#tbody_crowd_detail_list tr td:nth-child(1) input:checkbox").click(function (e) {
                e.stopPropagation();
            });

            $("#tbody_crowd_detail_list tr td:nth-child(4) input:checkbox").click(function (e) {
                if ($(this).is(":checked")) {
                    $("#tbody_crowd_detail_list tr td:nth-child(4) input:checkbox").prop("checked", false);
                    $(this).prop("checked", true);
                }
                e.stopPropagation();
            });

            $("#tbody_crowd_detail_list tr").click(function () {
                var chkObj = $(this).find("td:nth-child(1)").find("input:checkbox");
                if (typeof chkObj.attr("disabled") == "undefined") {
                    if (chkObj.is(":checked")) {
                        chkObj.prop("checked", false);
                    } else {
                        chkObj.prop("checked", true);
                    }
                }
            });

            $("#tbody_crowd_detail_list tr td:nth-child(5) a:nth-child(1)").click(function(){
                var crowdId = $(this).data("id");
                layer.confirm("确定要删除该集群吗？", {
                    btn: ['确定','取消'] //按钮
                },function(index){
                    layer.close(index);
                    $.post(ctx + "/sa/deleteCrowd", {"id":crowdId}, function (msg) {
                        if (msg != null && msg.indexOf("|") > -1) {
                            var ids = msg.substring(msg.indexOf("|") + 1);
                            if(ids.length > 0){
                                var arr = ids.split(",");
                                for(var i=0;i<arr.length;i++){
                                    var f = WEBGIS.map.markerLayer.getSource().getFeatureById(arr[i]);
                                    f["crowdId"] = null;
                                    f["crowdName"] = null;
                                    f["isMain"] = null;
                                }
                            }
                            reloadCrowdDetail();
                            layer.msg("操作成功", {icon: 1, time: 1000});
                        } else {
                            layer.msg(msg, {icon: 2, time: 1000});
                        }
                    });
                }, function(){
                });
            });
        });
    }

    /**
     * 集群显示
     * @param idArr 集群id
     */
    WEBGIS.customerControlls.corwdShow = function(idArr){
        corwdShow(idArr);
    };

    function corwdShow(idArr){
        var fArr = WEBGIS.map.markerLayer.getSource().getFeatures();
        for(var i = 0;i<idArr.length;i++){
            for(var j = 0;j<fArr.length;j++){
                var fea = fArr[j];
                if(typeof fea.mainType != "undefined" && fea.mainType != null && typeof fea.crowdId != "undefined" && fea.crowdId != null && idArr[i] == fea.crowdId){
                    //当前集群下的图标
                    if(typeof fea.isMain != "undefined" && fea.isMain != null && fea.isMain == "1"){
                        //掌机/旗舰
                        if(fea.getStyle() == null){
                            fea.setStyle(fea.tmpStyle);
                        }
                        //名称换成集群名称
                        fea.isCrowdShow = "1";
                        fea.getStyle().getText().setText(fea.crowdName);
                    }else{
                        if(fea.getStyle() != null){
                            fea.tmpStyle = fea.getStyle();
                            fea.setStyle(null);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param type 显示类型 1:全部  2:飞机  3:舰船
     */
    function iconTypeShow(type){
        var arr = WEBGIS.map.markerLayer.getSource().getFeatures();
        for(var i=0;i<arr.length;i++){
            if(arr[i].mainType != null){
                arr[i].isCrowdShow = "0";
                if(type == "1"){
                    //显示全部
                    if(arr[i].tmpStyle != null && arr[i].getStyle() == null){
                        arr[i].setStyle(arr[i].tmpStyle);
                        arr[i].getStyle().getText().setText(arr[i].iconName);
                    }else{
                        arr[i].getStyle().getText().setText(arr[i].iconName);
                    }
                }else if(type == "2"){
                    // 显示飞机
                    if(arr[i].mainType != "1" && arr[i].getStyle() != null){
                        arr[i].getStyle().getText().setText(arr[i].iconName);
                        arr[i].tmpStyle = arr[i].getStyle();
                        arr[i].setStyle(null);
                    }else if(arr[i].mainType == "1") {
                        if (arr[i].tmpStyle != null && arr[i].getStyle() == null) {
                            arr[i].setStyle(arr[i].tmpStyle);
                            arr[i].getStyle().getText().setText(arr[i].iconName);
                        }else{
                            arr[i].getStyle().getText().setText(arr[i].iconName);
                        }
                    }
                }else if(type == "3"){
                    //显示舰船
                    if(arr[i].mainType != "0" && arr[i].getStyle() != null){
                        arr[i].getStyle().getText().setText(arr[i].iconName);
                        arr[i].tmpStyle = arr[i].getStyle();
                        arr[i].setStyle(null);
                    }else if(arr[i].mainType == "0") {
                        if (arr[i].tmpStyle != null && arr[i].getStyle() == null) {
                            arr[i].setStyle(arr[i].tmpStyle);
                            arr[i].getStyle().getText().setText(arr[i].iconName);
                        }else{
                            arr[i].getStyle().getText().setText(arr[i].iconName);
                        }

                    }
                }
            }
        }
    }

    function formulaParam(){
        var vl = $("#txt_formula_val").val();
        if (vl == '') {
            return;
        }
        $.ajax({
            type: "POST",
            url: ctx + "/formula/validate",
            data: {"expr": vl},
            cache: false,
            success: function (r) {
                var $panel = $('#formula_panel');
                $panel.empty();
                for (var i = 0; i < r.length; i++) {
                    var $row = $('<div class="row"></div>');
                    var $label = $('<label class="col-sm-2 control-label form-label2">' + r[i] + ':</label>');
                    var $input = $('<div class="col-sm-8 form-div2"><input type="text" placeholder="" class="form-control" name="param_' + r[i] + '" value="" /></div>');
                    $row.append($label).append($input);
                    $panel.append($row);
                }
                var $row = $('<div class="row"></div>');
                var $label1 = $('<label class="col-sm-2 control-label form-label2">计算结果:</label>');
                var $label2 = $('<label class="col-sm-8 control-label form-label2" id="calcualtor_result" style="text-align:left;font-weight: 400;"></label>');
                $row.append($label1).append($label2);
                $panel.append($row);
            }
        });
    }

    function formulaCalculate(){
        var vl = $("#txt_formula_val").val();
        if (vl == '') {
            return;
        }
        var $panel = $('#formula_panel');
        var $inputs = $panel.find('input');
        var param = {};
        param.expr = vl;
        $inputs.each(function () {
            var $input = $(this);
            var name = $input.attr('name');
            var value = $input.val();
            param[name] = value;
        });
        $.ajax({
            type: "POST",
            url: ctx + "/formula/calculate",
            data: param,
            cache: false,
            success: function (r) {
                var $label2 = $('#calcualtor_result');
                if (r != '') {
                    $label2.text(r);
                } else {
                    $label2.text('公式或参数有不对');
                }
            }
        });
    }

    WEBGIS.customerControlls.iconMark = function(feature){
        $("#div_popup_content").load(ctx+"/plot/plotIcon",function(){
            WEBGIS.map.popupLayer.setPosition(feature.getGeometry().getCoordinates());
            WEBGIS.map.draw.drawingIcon = feature;
        });
    };

    WEBGIS.customerControlls.textMark = function(feature){
        $("#div_popup_content").load(ctx+"/plot/plotText",function(){
            WEBGIS.map.popupLayer.setPosition(feature.getGeometry().getCoordinates());
            WEBGIS.map.draw.drawingIcon = feature;
        });
    };
})(WEBGIS);