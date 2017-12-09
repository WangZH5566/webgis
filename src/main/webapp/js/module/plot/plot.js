(function(a) {
    a.plotChooseIcon = {
        chooseTroopId:null,
        init:function(){
            a.plotChooseIcon.searchIcon();
        },
        searchIcon:function(){
            //开始查询
            var param = {
                "pageNo":"1",
                "pageSize":"8",
                "mt":$("#txt_choose_icon_win_mt").val()
            };
            $.post(ctx+"/plot/queryIconListCountForMainType",param,function(page){
                laypage({
                    cont: $("#div_icon_list_page_btn"),             //分页按钮容器。值支持id名、原生dom对象，jquery对象,
                    pages: page,                                    //总页数
                    skip: false,                                   //是否开启跳页
                    skin: '#AF0000',
                    prev:'<',
                    next:'>',
                    first:'<<',
                    last:'>>',
                    groups: 5, //连续显示分页数
                    jump:function(obj,first){
                        param.pageNo = obj.curr;
                        $("#div_icon_list").load(ctx+"/plot/queryIconListPageForMainType",param,function(){
                            $("#div_icon_list div.icon-outer").on("click",function(){
                                if($(this).hasClass("selected")){
                                    return false;
                                }
                                $("#div_icon_list div.icon-outer").removeClass("selected");
                                $(this).addClass("selected");
                            });
                        });
                    }
                });
            });
        },
        saveIcon:function(tmpFeature){
            var param = {
                troopId:null,
                coordinates:null,
                fightBeginTimeView:null
            };
            param.troopId = a.plotChooseIcon.chooseTroopId;
            var coordinates = tmpFeature.getGeometry().getCoordinates();
            param.coordinates= coordinates.join(",");
            param.fightBeginTimeView = $("#div_fight_date").html();
            //开始保存
            $.post(ctx+"/plot/saveExecIcon",{jsonString:JSON.stringify(param)},function(re_json){
                if(re_json!=null&&re_json.msg=="success"){
                    //保存成功
                    var json=re_json.icon;
                    var iconId = json.id;
                    var anchor = {
                        "id":iconId,
                        "startPoint":coordinates,
                        "marker": re_json.visitpath + json.iconData,
                        "iconId":json.iconId,
                        "iconName": json.iconName,
                        "colorArray": json.colorArray,
                        "feature_is_icon":true,
                        "speed":json.speed,
                        "speed_unit":json.speedUnit,
                        "unit_id":json.unitId,
                        "baseInfo":json.baseInfo,
                        "mainType":json.mainType,
                        "max_speed":json.maxSpeed
                    };
                    //删掉之前的定位图标
                    WEBGIS.map.markerLayer.getSource().removeFeature(tmpFeature);
                    //放置新的图标
                    WEBGIS.map.markerByxxx(anchor);
                }else{
                    layer.msg("保存失败！",{icon:2,time:1000});
                }
            });

            /*$("#div_choose_icon").load(ctx+"/plot/addIcon",{iconId:a.plotChooseIcon.iconTmp.iconID},function(){
                a.plotChooseIcon.iconTmp.tmpFeature = tmpFeature;
                $("#tbody_bi_list tr td:nth-child(1) input:radio").click(function(e){
                    a.plotChooseIcon.resetValue();
                    a.plotChooseIcon.iconTmp.baseInfo = $(this).data("bi");
                    a.plotChooseIcon.iconTmp.baseInfoName = $(this).data("bin");
                    a.plotChooseIcon.iconTmp.mainType = $(this).data("mt");
                    a.plotChooseIcon.iconTmp.maxSpeed = $(this).data("ms");
                    a.plotChooseIcon.baseInfoClick();
                    e.stopPropagation();
                });
                $("#tbody_bi_list tr").click(function(){
                    var chkObj = $(this).find("td:nth-child(1)").find("input:radio");
                    if(!chkObj.is(":checked")){
                        chkObj.prop("checked",true);
                        a.plotChooseIcon.resetValue();
                        a.plotChooseIcon.iconTmp.baseInfo = $(this).data("bi");
                        a.plotChooseIcon.iconTmp.baseInfoName = $(this).data("bin");
                        a.plotChooseIcon.iconTmp.mainType = $(this).data("mt");
                        a.plotChooseIcon.iconTmp.maxSpeed = $(this).data("ms");
                        a.plotChooseIcon.baseInfoClick();
                    }
                });
                layer.open({
                    type: 1,
                    title:["添加图标","font-weight:bold;"],
                    area:['500px','400px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.plotChooseIcon.saveIcon(index);
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_choose_icon"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_choose_icon").html("");
                        a.plotChooseIcon.resetValue();
                        a.plotChooseIcon.iconTmp.iconID = null;
                        a.plotChooseIcon.iconTmp.iconPath = null;
                        a.plotChooseIcon.iconTmp.iconData = null;
                        a.plotChooseIcon.iconTmp.tmpFeature = [];
                        if (tmpFeature != null) WEBGIS.map.markerLayer.getSource().removeFeature(tmpFeature);
                    }
                });
            });*/
        }
        /*baseInfoClick:function(){
            $("#div_add_icon_attr").hide();
            $("#div_add_icon_ammu").parent().hide();
            $("#div_add_icon_equip").parent().hide();
            $("#div_add_icon_right").parent().hide();
            //图标属性
            $("#txt_icon_name").val(a.plotChooseIcon.iconTmp.baseInfoName);
            $("#txt_move_speed").val("");
            $("#txt_move_angle").val("");
            a.plotChooseIcon.iconTmp.speed = null;
            a.plotChooseIcon.iconTmp.moveAngle = null;
            $("#div_speed_attr_container input").val("");
            $("#div_speed_attr_container").hide();
            if(a.plotChooseIcon.iconTmp.mainType == 0 || a.plotChooseIcon.iconTmp.mainType == 1){
                $("#lab_speed_unit").html("海里/时");
                if(a.plotChooseIcon.iconTmp.mainType == 1){
                    $("#lab_speed_unit").html("公里/时");
                }
                $("#div_speed_attr_container").show();
            }
            //图标颜色
            $.imgspectrum.init({container:$("#img_canvas"),imgSrc:a.plotChooseIcon.iconTmp.iconPath});
            $("#div_add_icon_attr").show();
            //图标关联属性
            if(a.plotChooseIcon.iconTmp.mainType>=0 && a.plotChooseIcon.iconTmp.mainType<=3){
                //雷弹: 舰船(0)、飞机(1)、岸导(2)、仓储机构(3)
                $("#div_add_icon_ammu").load(ctx+"/plot/iconAmmunitionSetting",{"biId":a.plotChooseIcon.iconTmp.baseInfo},function(){
                    $("#div_add_icon_ammu").parent().show();
                    $('#table_ammunition_list .spinnerInput').spinner({});
                });
            }
            if(a.plotChooseIcon.iconTmp.mainType==3 || a.plotChooseIcon.iconTmp.mainType==4){
                //器材: 仓储机构(3)、修理机构(4)
                $("#div_add_icon_equip").load(ctx+"/plot/iconEquipmentSetting",{"biId":a.plotChooseIcon.iconTmp.baseInfo},function(){
                    $("#div_add_icon_equip").parent().show();
                    $('#table_equipment_list .spinnerInput').spinner({});
                });
            }
            if(a.plotChooseIcon.iconTmp.mainType == 6){
                //特殊: 机场(7)
                $("#div_plane").load(ctx+"/plot/iconPlaneSetting",{"iconId":a.plotChooseIcon.iconTmp.iconID},function(){
                    $("#tbody_plane_list tr").on("click",function(){
                        if($(this).hasClass("selected")){
                            return;
                        }
                        var prevBiId = $("#tbody_plane_ammu_list").data("biid");
                        var ammu = {};
                        $("#tbody_plane_ammu_list tr td:nth-child(3)").each(function(){
                            var inputObj = $(this).find("input:text");
                            if(inputObj.val() != "" && inputObj.val() != "0"){
                                ammu[inputObj.data("id")] = {"na":inputObj.data("name"),"co":inputObj.val()};
                            }
                        });
                        a.plotChooseIcon.iconTmp.planeAmmu[prevBiId] = ammu;
                        var bi = $(this).data("bi");
                        $("#div_plane_ammu").load(ctx+"/plot/iconAmmunitionSetting",{"biId":bi,"isPl":"1"},function(){
                            $('#tbody_plane_ammu_list .spinnerInput').spinner({});
                            var tmpData = a.plotChooseIcon.iconTmp.planeAmmu[bi];
                            if(typeof tmpData != "undefined"){
                                for(var o in tmpData){
                                    $("#tr_ammu_" + o + " td:nth-child(3) input:text").val(tmpData[o]["co"]);
                                }
                            }
                        });
                        $("#tbody_plane_list tr").removeClass("selected");
                        $(this).addClass("selected");
                    });
                    $("#tbody_plane_list tr input:checkbox").on("click",function(e){
                        e.stopPropagation();
                    });
                    $("#div_add_icon_plane").show();
                });
            }
            //图标权限
            $("#div_add_icon_right").load(ctx+"/plot/iconRightSetting",{},function(){
                a.plotChooseIcon.unit_treeObj = $.fn.zTree.init($("#unit_ztree"),unit_setting,unit_zTreeNodes);
                a.plotChooseIcon.unit_treeObj.expandAll(true);
                if($("#user-info-form #txt_is_director").val()!=1){
                    var unit=$("#user-info-form #txt_unit_id").val();
                    var node = a.plotChooseIcon.unit_treeObj.getNodeByParam("id", Number(unit), null);
                    a.plotChooseIcon.unit_treeObj.checkNode(node, true, true);
                    var nodes =  a.plotChooseIcon.unit_treeObj.getNodes();
                    if(nodes!=null&&nodes.length>0){
                        for(var i=0;i<nodes.length;i++){
                            a.plotChooseIcon.unit_treeObj.setChkDisabled(nodes[i], true,true,true);
                        }
                    }
                }
                $("#div_add_icon_right").parent().show();
            });
        },*/
        /*saveIcon:function(ind){
            if($("#tbody_bi_list tr td:nth-child(1) input:radio:checked").size() == 0){
                layer.msg("选择图标型号",{"icon":2,"time":1000});
                return false;
            }
            var param = {
                iconID:null,
                iconPath:null,
                iconData:null,
                baseInfo:null,
                mainType:null,
                ammunitions:[],
                equipments:[],
                colorArray:"[]",
                unit:null,
                name:"",
                moveAngle:null,
                maxSpeed:null,
                speed:null,
                coordinates:[],
                fightBeginTimeView:null,
                planeAmmu:null
            };
            param.iconID = a.plotChooseIcon.iconTmp.iconID;
            param.iconPath = a.plotChooseIcon.iconTmp.iconPath;
            param.iconData = a.plotChooseIcon.iconTmp.iconData;
            param.baseInfo = a.plotChooseIcon.iconTmp.baseInfo;
            param.mainType = a.plotChooseIcon.iconTmp.mainType;
            param.maxSpeed = a.plotChooseIcon.iconTmp.maxSpeed;

            //图标数据
            var iconName = trim($("#txt_icon_name").val());
            if(iconName == ""){
                layer.msg("请输入图标名称",{"icon":2,"time":1000});
                return false;
            }
            param.name = iconName;
            param.colorArray = a.plotChooseIcon.iconTmp.colorArray;
            if($("#div_speed_attr_container").is(":visible")){
                var moveSpeed =trim($("#txt_move_speed").val());
                var moveAngle =trim($("#txt_move_angle").val());

                if(isNaN(moveSpeed)){
                    layer.msg("速度请输入数值！",{icon:2,time:1000});
                    return false;
                }
                if(a.plotChooseIcon.iconTmp.maxSpeed!=null && a.plotChooseIcon.iconTmp.maxSpeed!=""&&(Number(moveSpeed)> a.plotChooseIcon.iconTmp.maxSpeed||Number(moveSpeed)<0)){
                    layer.msg("速度最大值为"+ a.plotChooseIcon.iconTmp.maxSpeed+",最小值为0,请输入合法速度",{icon:2,time:1000});
                    return false;
                }
                if(isNaN(moveAngle)){
                    layer.msg("航向请输入数值！",{icon:2,time:1000});
                    return false;
                }
                if(Number(moveAngle)>359||Number(moveSpeed)<0){
                    layer.msg("航向的范围为0~359度,请输入合法航向",{icon:2,time:1000});
                    return false;
                }
                param.speed = moveSpeed;
                param.moveAngle = moveAngle;
            }
            //雷弹数据
            if($("#div_add_icon_ammu").parent().is(":visible")){
                var ldArray=new Array();
                $("#table_ammunition_list input.spinnerInput").each(function(){
                    var id = $(this).data("id");
                    var name=$(this).data("name");
                    var value=$(this).val();
                    var json={"id":id,"count":value,"name":name};
                    if(value!=""&&value>0){
                        ldArray.push(json);
                    }
                });
                param.ammunitions=ldArray;
            }
            //装备数据
            if($("#div_add_icon_equip").parent().is(":visible")){
                var zbArray=new Array();
                $('#table_equipment_list input.spinnerInput').each(function(){
                    var id = $(this).data("id");
                    var name = $(this).data("name");
                    var value=$(this).val();
                    var json={"id":id,"count":value,"name":name};
                    if(value!=""&&value>0){
                        zbArray.push(json);
                    }
                });
                param.equipments=zbArray;
            }
            //飞机数据
            if($("#div_add_icon_plane").is(":visible")){
                /!*if($("#tbody_plane_list tr td:nth-child(1) input:checked").size == 0){
                    layer.msg("请选择飞机",{icon:2,time:1000});
                    return false;
                }*!/
                var prevBiId = $("#tbody_plane_ammu_list").data("biid");
                var ammu = {};
                $("#tbody_plane_ammu_list tr td:nth-child(3)").each(function(){
                    var inputObj = $(this).find("input:text");
                    if(inputObj.val() != "" && inputObj.val() != "0"){
                        ammu[inputObj.data("id")] = {"na":inputObj.data("name"),"co":inputObj.val()};
                    }
                });
                a.plotChooseIcon.iconTmp.planeAmmu[prevBiId] = ammu;
                var tmpJson = {};
                $("#tbody_plane_list tr td:nth-child(1) input:checked").each(function(){
                    var tmpId = $(this).data("bi");
                    tmpJson[tmpId] = a.plotChooseIcon.iconTmp.planeAmmu[tmpId];
                });
                param.planeAmmu = JSON.stringify(tmpJson);

            }
            //权限数据
            var nodes =  a.plotChooseIcon.unit_treeObj.getNodes();
            if(nodes!=null&&nodes.length>0){
                for(var i=0;i<nodes.length;i++){
                    a.plotChooseIcon.unit_treeObj.setChkDisabled(nodes[i], false,true,true);
                }
            }
            var selectedNodes=a.plotChooseIcon.unit_treeObj.getCheckedNodes(true);
            if(nodes!=null&&nodes.length>0){
                for(var i=0;i<nodes.length;i++){
                    a.plotChooseIcon.unit_treeObj.setChkDisabled(nodes[i], true,true,true);
                }
            }
            if(selectedNodes!=null&&selectedNodes.length>0){
                param.unit=selectedNodes[0].id;
            }
            var coordinates = a.plotChooseIcon.iconTmp.tmpFeature.getGeometry().getCoordinates();
            param.coordinates= coordinates.join(",");
            param.fightBeginTimeView = $("#div_fight_date").html();
            a.plotChooseIcon.iconTmp.colorArray=JSON.stringify($.imgspectrum.recalColorJson());
            param.colorArray=a.plotChooseIcon.iconTmp.colorArray;
            //开始保存
            $.post(ctx+"/plot/saveExecIcon",{jsonString:JSON.stringify(param)},function(re_json){
                if(re_json!=null&&re_json.msg=="success"){
                    //保存成功
                    var json=re_json.icon;
                    var iconId = json.id;

                    var anchor = {
                        "id":iconId,
                        "startPoint":coordinates,
                        "marker": a.plotChooseIcon.iconTmp.iconPath,
                        "iconId":a.plotChooseIcon.iconTmp.iconID,
                        "iconName": iconName,
                        "colorArray": a.plotChooseIcon.iconTmp.colorArray,
                        "feature_is_icon":true,
                        "speed":json.speed,
                        "speed_unit":json.speedUnit,
                        "unit_id":json.unitId,
                        "baseInfo":a.plotChooseIcon.iconTmp.baseInfo,
                        "mainType":a.plotChooseIcon.iconTmp.mainType,
                        "max_speed":json.maxSpeed
                    };
                    //删掉之前的定位图标
                    WEBGIS.map.markerLayer.getSource().removeFeature(a.plotChooseIcon.iconTmp.tmpFeature);
                    //放置新的图标
                    WEBGIS.map.markerByxxx(anchor);
                    layer.close(ind);
                }else{
                    layer.msg("保存失败！",{icon:2,time:1000});
                }
            });

        },*/
        /*resetValue:function(){
            /!*a.plotChooseIcon.iconTmp.iconID = null;
            a.plotChooseIcon.iconTmp.iconPath = null;
            a.plotChooseIcon.iconTmp.iconData = null;*!/
            a.plotChooseIcon.iconTmp.baseInfo = null;
            a.plotChooseIcon.iconTmp.baseInfoName = null;
            a.plotChooseIcon.iconTmp.mainType = null;
            a.plotChooseIcon.iconTmp.maxSpeed = null;
            a.plotChooseIcon.iconTmp.speed = null;
            a.plotChooseIcon.iconTmp.moveAngle = null;
            a.plotChooseIcon.iconTmp.colorArray = "[]";
            a.plotChooseIcon.iconTmp.planeAmmu = {};
        }*/
    },
    a.plotText = {
        addText:function(tmpFeature){
            layer.open({
                type: 1,
                title:["添加文字","font-weight:bold;"],
                area:['500px','310px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var text = trim($("#txt_textarea").val());
                    if(text == ""){
                        layer.msg("请输入[文字内容]！",{icon: 2,time:1000});
                        return false;
                    }
                    var coordinates = tmpFeature.getGeometry().getCoordinates();
                    var json = {
                        "startPoint":coordinates.join(","),
                        "iconText":text
                    };
                    $.post(ctx+"/plot/saveExecText",{jsonString:JSON.stringify(json)},function(re_json){
                        if(re_json!=null&&re_json.msg=="success"){
                            //保存成功
                            json.id = re_json.icon.id;
                            json.startPoint = coordinates;
                            //放置新的图标
                            WEBGIS.map.markerText(json);
                            layer.close(index);
                        }else{
                            layer.msg("保存失败！",{icon:2,time:1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#div_add_text"),
                zIndex:100,
                success: function(layero, index){

                },
                end:function(){
                    $("#txt_textarea").text("");
                    if (tmpFeature != null) WEBGIS.map.markerLayer.getSource().removeFeature(tmpFeature);
                }
            });
        }
    };
})(WEBGIS);