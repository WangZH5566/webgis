(function(a) {
    a.plotChooseIcon = {
        chooseTroop:{
            iconName:null,
            iconData:null,
            iconPath:null,
            colorArray:null,
            tmpFeature:null,
            size:32
        },
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
        /*saveIcon:function(tmpFeature){
            var coordinates = tmpFeature.getGeometry().getCoordinates();
            var iconId = parseInt(Math.random() * 10000) + 100;
            var anchor = {
                "id":iconId,
                "startPoint":coordinates,
                "marker":FILESERVER_ICON_VISITPATH+ a.plotChooseIcon.chooseTroop.iconData,
                "iconId":a.plotChooseIcon.chooseTroop.iconData,
                "iconName":a.plotChooseIcon.chooseTroop.iconName,
                "colorArray": a.plotChooseIcon.chooseTroop.colorArray
            };
            //删掉之前的定位图标
            WEBGIS.map.markerLayer.getSource().removeFeature(tmpFeature);
            //放置新的图标
            WEBGIS.map.markerByxxx(anchor);
        }*/
        addIcon:function(tmpFeature){
            $("#div_choose_icon").load(ctx+"/plot/addIcon_user_new",{},function(){
                WEBGIS.plotChooseIcon.chooseTroop.tmpFeature = tmpFeature;
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
                        //图标属性
                        $("#txt_icon_name").val(WEBGIS.plotChooseIcon.chooseTroop.iconName);
                        //图标颜色
                        $.imgspectrum.init({container:$("#img_canvas"),imgSrc:WEBGIS.plotChooseIcon.chooseTroop.iconPath});
                        $("#btn_add").on("click",function () {
                            WEBGIS.plotChooseIcon.chooseTroop.size++;
                            $.imgspectrum.reloadxxx(WEBGIS.plotChooseIcon.chooseTroop.size,WEBGIS.plotChooseIcon.chooseTroop.size);
                        });
                        $("#btn_minus").on("click",function () {
                            WEBGIS.plotChooseIcon.chooseTroop.size--;
                            $.imgspectrum.reloadxxx(WEBGIS.plotChooseIcon.chooseTroop.size,WEBGIS.plotChooseIcon.chooseTroop.size);
                        });
                    },
                    end:function(){
                        $("#div_choose_icon").html("");
                        WEBGIS.plotChooseIcon.chooseTroop.iconName = null;
                        WEBGIS.plotChooseIcon.chooseTroop.iconData = null;
                        WEBGIS.plotChooseIcon.chooseTroop.iconPath = null;
                        WEBGIS.plotChooseIcon.chooseTroop.colorArray = null;
                        WEBGIS.plotChooseIcon.chooseTroop.size = 32;
                        if (tmpFeature != null) WEBGIS.map.markerLayer.getSource().removeFeature(tmpFeature);
                    }
                });
            });
        },
        saveIcon:function(ind){
            //图标数据
            var iconName = trim($("#txt_icon_name").val());
            if(iconName == ""){
                layer.msg("请输入图标名称",{"icon":2,"time":1000});
                return false;
            }
            WEBGIS.plotChooseIcon.chooseTroop.colorArray=JSON.stringify($.imgspectrum.recalColorJson());
            var coordinates = WEBGIS.plotChooseIcon.chooseTroop.tmpFeature.getGeometry().getCoordinates();
            var iconId = parseInt(Math.random() * 10000) + 100;
            var anchor = {
                "id":iconId,
                "startPoint":coordinates,
                "marker":WEBGIS.plotChooseIcon.chooseTroop.iconPath,
                "iconId":WEBGIS.plotChooseIcon.chooseTroop.iconData,
                "iconName":iconName,
                "colorArray": WEBGIS.plotChooseIcon.chooseTroop.colorArray,
                "size":WEBGIS.plotChooseIcon.chooseTroop.size
            };
            //删掉之前的定位图标
            WEBGIS.map.markerLayer.getSource().removeFeature(WEBGIS.plotChooseIcon.chooseTroop.tmpFeature);
            //放置新的图标
            WEBGIS.map.markerByxxx(anchor);
            //关闭弹窗
            layer.closeAll();
        }
        /*addIcon:function(tmpFeature){
            $("#div_choose_icon").load(ctx+"/plot/addIcon_user",{iconId:a.plotChooseIcon.iconTmp.iconID},function(){
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
            });
        },
        baseInfoClick:function(){
            //图标属性
            $("#txt_icon_name").val(a.plotChooseIcon.iconTmp.baseInfoName);
            //图标颜色
            $.imgspectrum.init({container:$("#img_canvas"),imgSrc:a.plotChooseIcon.iconTmp.iconPath});
            $("#div_add_icon_attr").show();
        },
        saveIcon:function(ind){
            if($("#tbody_bi_list tr td:nth-child(1) input:radio:checked").size() == 0){
                layer.msg("选择图标型号",{"icon":2,"time":1000});
                return false;
            }
            //图标数据
            var iconName = trim($("#txt_icon_name").val());
            if(iconName == ""){
                layer.msg("请输入图标名称",{"icon":2,"time":1000});
                return false;
            }
            a.plotChooseIcon.iconTmp.colorArray=JSON.stringify($.imgspectrum.recalColorJson());
            var coordinates = a.plotChooseIcon.iconTmp.tmpFeature.getGeometry().getCoordinates();
            var iconId = parseInt(Math.random() * 10000) + 100;
            var anchor = {
                "id":iconId,
                "startPoint":coordinates,
                "marker":a.plotChooseIcon.iconTmp.iconPath,
                "iconId":a.plotChooseIcon.iconTmp.iconData,
                "iconName":iconName,
                "colorArray": a.plotChooseIcon.iconTmp.colorArray
            };
            //删掉之前的定位图标
            WEBGIS.map.markerLayer.getSource().removeFeature(a.plotChooseIcon.iconTmp.tmpFeature);
            //放置新的图标
            WEBGIS.map.markerByxxx(anchor);
            //关闭弹窗
            layer.closeAll();
        },
        resetValue:function(){
            a.plotChooseIcon.iconTmp.iconID = null;
            a.plotChooseIcon.iconTmp.iconPath = null;
            a.plotChooseIcon.iconTmp.iconData = null;
            a.plotChooseIcon.iconTmp.baseInfo = null;
            a.plotChooseIcon.iconTmp.baseInfoName = null;
            a.plotChooseIcon.iconTmp.mainType = null;
            a.plotChooseIcon.iconTmp.maxSpeed = null;
            a.plotChooseIcon.iconTmp.speed = null;
            a.plotChooseIcon.iconTmp.moveAngle = null;
            a.plotChooseIcon.iconTmp.colorArray = "[]";
            a.plotChooseIcon.iconTmp.planeAmmu = {};
        }*/
    };
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
                    var iconId = parseInt(Math.random() * 10000) + 100;
                    var json = {
                        "id":iconId,
                        "startPoint":coordinates,
                        "iconText":text
                    };
                    WEBGIS.map.markerText(json);
                    layer.close(index);
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