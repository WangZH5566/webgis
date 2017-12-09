(function(a) {
	a.baseInfo = {
        treeObj:{},
		init:function() {
            a.baseInfo.treeObj = $.fn.zTree.init($("#ul_ztree"),setting,zTreeNodes);
            $("#btn_add_info").on("click", a.baseInfo.btnAddClick);
            $("#btn_icon").on("click", a.baseInfo.btnIconClick);
		},
        zTreeOnClick:function(event, treeId, treeNode) {
            $("#div_img_base_info_type").html("");
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/baseInfo/queryIcon",
                data:{"id":treeNode.id},
                cache:false,
                success:function(r){
                    if(r != null && r != ""){
                        $("#div_img_base_info_type").html("<img src=\"" + r + "\" style=\"margin-left:10px;width:50px;height:35px;\" />");
                    }/*else{
                        $("#div_img_base_info_type").html("暂无图标");
                    }*/
                }
            });
            var idArray = new Array();
            idArray.push(treeNode.id);
            var children = treeNode.children;
            a.baseInfo.buildChildrenID(idArray,children);
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "mt":treeNode.type,
                "ids":idArray.join(",")
            };
            $.post(ctx+"/baseInfo/baseInfo/queryBaseInfoCount",param,function(page){
                laypage({
                    cont: $("#div_page_btn"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                        $("#div_list").load(ctx+"/baseInfo/baseInfo/queryBaseInfoPage",param,function(){
                            $("#tbody_list tr td:last-child a:last-child").on("click", a.baseInfo.btnDeleteClick);
                            //当前总列宽大于table宽度
                            if($("#div_list").width()> $("#table_list").width()){
                                $("#table_list").removeAttr("style");
                            }
                        });
                    }
                });
            });
        },
        btnIconClick:function(){
            var nodes = a.baseInfo.treeObj.getSelectedNodes();
            if(nodes.length == 0){
                layer.msg("请选择[基础资料分类]！",{icon: 2,time: 1000});
                return false;
            }
            window.location.href = ctx + "/baseInfo/baseInfo/baseInfoIcon?ti="+nodes[0].id;
        },
        btnAddClick:function(){
            var nodes = a.baseInfo.treeObj.getSelectedNodes();
            if(nodes.length == 0){
                layer.msg("请选择[基础资料分类]！",{icon: 2,time: 1000});
                return false;
            }
            if(nodes[0].children != null){
                layer.msg("只能在[基础资料分类]的叶子节点上新增基础资料！",{icon: 2,time: 1000});
                return false;
            }
            window.location.href = ctx + "/baseInfo/baseInfo/addOrEditBaseInfo?mt="+nodes[0].type+"&ti="+nodes[0].id;
        },
        btnDeleteClick:function(){
            var biId = $(this).data("id");
            layer.confirm("确认要删除此基础资料么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $.ajax({
                    type:"POST",
                    url:ctx+"/baseInfo/baseInfo/deleteBaseInfo",
                    data:{"id":biId},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        if(r != null && r == "success"){
                            var selectNodes = a.baseInfo.treeObj.getSelectedNodes();
                            a.baseInfo.zTreeOnClick(null, null, selectNodes[0]);
                        }else{
                            layer.msg(r,{icon: 2,time:1000});
                        }
                    },
                    error:function() {
                        layer.close(index);
                        layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                    }
                });
            },function(index){
            });
        },
        addHoverDom:function(treeId, treeNode){
            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#diyBtnAdd_"+treeNode.id).length == 0){
                var addStr = "<span class=\"button diyBtnAdd\" id=\"diyBtnAdd_"+treeNode.id+"\" title=\"新增节点\" onfocus=\"this.blur();\"></span>";
                aObj.append(addStr);
                var btnAdd = $("#diyBtnAdd_"+treeNode.id);
                if (btnAdd) btnAdd.bind("click", function(){
                    $.ajax({
                        type:"POST",
                        url:ctx+"/baseInfo/baseInfo/addbaseInfoType",
                        data:{"pid":treeNode.id,"mt":treeNode.type,"np":treeNode.np},
                        dataType:"json",
                        cache:false,
                        success:function(r){
                            if(r.msg == null){
                                WEBGIS.baseInfo.treeObj.addNodes(treeNode,{"id": r.id,"pId":r.pid,"name":r.typeName,"type":r.mainTypeValue,"np":r.nodePath});
                            }else{
                                layer.msg(r.msg,{icon: 2,time:1000});
                            }
                        },
                        error:function() {
                            layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                        }
                    });
                    return false;
                });
            }
        },
        removeHoverDom:function(treeId, treeNode){
            $("#diyBtnAdd_"+treeNode.id).unbind().remove();
        },
        showRemoveBtn:function(treeId, treeNode){
            var rootNodes = WEBGIS.baseInfo.treeObj.getNodesByFilter(function(node){ return node.level == 0});
            var rootIdArr = new Array();
            for(var i=0;i<rootNodes.length;i++){
                rootIdArr.push(rootNodes[i].id);
            }
            return rootIdArr.indexOf(treeNode.id) == -1;
        },
        showRenameBtn:function(treeId, treeNode){
            var rootNodes = WEBGIS.baseInfo.treeObj.getNodesByFilter(function(node){ return node.level == 0});
            var rootIdArr = new Array();
            for(var i=0;i<rootNodes.length;i++){
                rootIdArr.push(rootNodes[i].id);
            }
            return rootIdArr.indexOf(treeNode.id) == -1;
        },
        beforeRemove:function(treeId, treeNode){
            WEBGIS.baseInfo.treeObj.selectNode(treeNode);
            return confirm("若删除该节点，会同时删除该节点下的所有数据,确认要删除吗？");
        },
        beforeRename:function(treeId, treeNode, newName, isCancel){
            if (newName.length == 0) {
                setTimeout(function() {
                    WEBGIS.baseInfo.treeObj.cancelEditName();
                    layer.msg("节点名称不能为空.",{icon: 2,time:1000});
                }, 0);
                return false;
            }
            return true;
        },
        onRemove:function(event, treeId, treeNode){
            var idArray = new Array();
            idArray.push(treeNode.id);
            var children = treeNode.children;
            a.baseInfo.buildChildrenID(idArray,children);
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/baseInfo/deleteBasicInfoType",
                data:{"ids":idArray==null?"":idArray.join(",")},
                cache:false,
                success:function(r){
                    if(r != null && r == "success"){
                    }else{
                        layer.msg(r.msg,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        },
        onRename:function(event, treeId, treeNode, isCancel){
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/baseInfo/modifyBasicInfoType",
                data:{"id":treeNode.id,"na":treeNode.name},
                cache:false,
                success:function(r){
                    if(r != null && r == "success"){
                    }else{
                        layer.msg(r.msg,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        },
        buildChildrenID:function(idArray,children){
            if(children==null||children.length<=0){
                return;
            }
            for(var i=0;i<children.length;i++){
                var node=children[i];
                idArray.push(node.id);
                var tmpChildren = node.children;
                a.baseInfo.buildChildrenID(idArray,tmpChildren);
            }
        },
    },
    a.baseInfoAddOrEdit = {
        init:function(){
            if(typeof laydate != "undefined"){
                laydate.skin('danlan');
            }
            if($('.spinnerInput').length > 0){
                $('.spinnerInput').spinner({});
            }
            $("#btn_save").bind("click",a.baseInfoAddOrEdit.btnSaveClick);
        },
        btnSaveClick:function(){
            var param = {
                "id":$("#txt_id").val(),
                "mainType":$("#txt_mt").val(),
                "typeId":$("#txt_ti").val()
            };
            if($("#txt_info_name").length > 0){
                var value = trim($("#txt_info_name").val());
                var name = $("#txt_info_name").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.infoName = value;
            }
            if($("#txt_info_code").length > 0){
                var value = trim($("#txt_info_code").val());
                var name = $("#txt_info_code").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.infoCode = value;
            }
            if($("#txt_belong_unit").length > 0){
                var value = trim($("#txt_belong_unit").val());
                var name = $("#txt_belong_unit").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.belongUnit = value;
            }
            if($("#txt_max_speed").length > 0){
                var value = trim($("#txt_max_speed").val());
                var name = $("#txt_max_speed").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                if(!value.match(/^-?\d+\.?\d{0,2}$/)){
                    layer.msg("["+name+"]请输入数字,最多支持2位小数",{icon: 2,time: 3000});
                    return false;
                }
                param.maxSpeed = value;
            }
            if($("#txt_endurance").length > 0){
                var value = trim($("#txt_endurance").val());
                var name = $("#txt_endurance").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                if(!value.match(/^-?\d+\.?\d{0,2}$/)){
                    layer.msg("["+name+"]请输入数字,最多支持2位小数",{icon: 2,time: 3000});
                    return false;
                }
                param.endurance = value;
            }
            if($("#txt_fight_radius").length > 0){
                var value = trim($("#txt_fight_radius").val());
                var name = $("#txt_fight_radius").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                if(!value.match(/^-?\d+\.?\d{0,2}$/)){
                    layer.msg("["+name+"]请输入数字,最多支持2位小数",{icon: 2,time: 3000});
                    return false;
                }
                param.fightRadius = value;
            }
            if($("#txt_max_displacement").length > 0){
                var value = trim($("#txt_max_displacement").val());
                var name = $("#txt_max_displacement").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                if(!value.match(/^-?\d+\.?\d{0,2}$/)){
                    layer.msg("["+name+"]请输入数字,最多支持2位小数",{icon: 2,time: 3000});
                    return false;
                }
                param.maxDisplacement = value;
            }
            if($("#txt_standard_displacement").length > 0){
                var value = trim($("#txt_standard_displacement").val());
                var name = $("#txt_standard_displacement").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                if(!value.match(/^-?\d+\.?\d{0,2}$/)){
                    layer.msg("["+name+"]请输入数字,最多支持2位小数",{icon: 2,time: 3000});
                    return false;
                }
                param.standardDisplacement = value;
            }
            if($("#txt_max_take_off_weight").length > 0){
                var value = trim($("#txt_max_take_off_weight").val());
                var name = $("#txt_max_take_off_weight").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                if(!value.match(/^-?\d+\.?\d{0,2}$/)){
                    layer.msg("["+name+"]请输入数字,最多支持2位小数",{icon: 2,time: 3000});
                    return false;
                }
                param.maxTakeOffWeight = value;
            }
            if($("#txt_development_unit").length > 0){
                var value = trim($("#txt_development_unit").val());
                var name = $("#txt_development_unit").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.developmentUnit = value;
            }
            if($("#txt_service_date").length > 0){
                var value = trim($("#txt_service_date").val());
                var name = $("#txt_service_date").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.serviceDate = value;
            }
            if($("#txt_repair_situation").length > 0){
                var value = trim($("#txt_repair_situation").val());
                var name = $("#txt_repair_situation").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.repairSituation = value;
            }
            if($("#txt_main_weapons").length > 0){
                var value = trim($("#txt_main_weapons").val());
                var name = $("#txt_main_weapons").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.mainWeapons = value;
            }
            if($("#txt_address").length > 0){
                var value = trim($("#txt_address").val());
                var name = $("#txt_address").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.address = value;
            }
            if($("#txt_longitude_and_latitude").length > 0){
                var value = trim($("#txt_longitude_and_latitude").val());
                var name = $("#txt_longitude_and_latitude").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.longitudeAndLatitude = value;
            }
            if($("#sel_technology_situation").length > 0){
                var value = trim($("#sel_technology_situation").val());
                var name = $("#sel_technology_situation").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.technologySituation = value;
            }
            if($("#txt_performance").length > 0){
                var value = trim($("#txt_performance").val());
                var name = $("#txt_performance").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.performance = value;
            }
            if($("#txt_switch_time").length > 0){
                var value = trim($("#txt_switch_time").val());
                var name = $("#txt_switch_time").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                //数据验证,只能输入整数(可为负值)
                if(!value.match(/^-?\d+$/)){
                    layer.msg("["+name+"]请输入数字,不支持输入小数",{icon: 2,time: 3000});
                    return false;
                }
                param.switchTime = value;
            }
            if($("#txt_load_time").length > 0){
                var value = trim($("#txt_load_time").val());
                var name = $("#txt_load_time").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                //数据验证,只能输入整数(可为负值)
                if(!value.match(/^-?\d+$/)){
                    layer.msg("["+name+"]请输入数字,不支持输入小数",{icon: 2,time: 3000});
                    return false;
                }
                param.loadTime = value;
            }
            if($("#sel_major").length > 0){
                var value = trim($("#sel_major").val());
                var name = $("#sel_major").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.majorId = value;
            }
            if($("#txt_count").length > 0){
                var value = trim($("#txt_count").val());
                var name = $("#txt_count").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                //数据验证,只能输入整数(可为负值)
                if(!value.match(/^-?\d+$/)){
                    layer.msg("["+name+"]请输入数字,不支持输入小数",{icon: 2,time: 3000});
                    return false;
                }
                param.count = value;
            }
            if($("#sel_technology_level").length > 0){
                var value = trim($("#sel_technology_level").val());
                var name = $("#sel_technology_level").data("na");
                if(value == ""){
                    layer.msg("请输入["+name+"]",{icon: 2,time: 3000});
                    return false;
                }
                param.technologyLevel = value;
            }

            if($("#tbody_ammunition_list").length > 0){
                var ammunitionIdArr = new Array();
                var ammunitionCountArr = new Array();
                $("#tbody_ammunition_list tr td input:checked").each(function(){
                    var biId = $(this).data("id");
                    ammunitionIdArr.push(biId);
                    ammunitionCountArr.push($("#am_"+biId+" td:nth-child(3) input").val());
                });
                param.ammunitionIds = ammunitionIdArr.join(",");
                param.ammunitionCounts = ammunitionCountArr.join(",");
            }

            if($("#tbody_person_list").length > 0){
                var personIdArr = new Array();
                $("#tbody_person_list tr td input:checked").each(function(){
                    var biId = $(this).data("id");
                    personIdArr.push(biId);
                });
                param.personIds = personIdArr.join(",");
            }

            if($("#tbody_equipment_list").length > 0){
                var equipmentIdArr = new Array();
                $("#tbody_equipment_list tr td input:checked").each(function(){
                    var biId = $(this).data("id");
                    equipmentIdArr.push(biId);
                });
                param.equipmentIds = equipmentIdArr.join(",");
            }

            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/baseInfo/saveBasicInfo",
                data:param,
                cache:false,
                success:function(r){
                    layer.close(ind);
                    if(r != null && r == "success"){
                        layer.msg("保存成功",{icon: 1,time:1000});
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.close(ind);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }
    },
    a.baseInfoIcon = {
        treeObj:{},
        init: function () {
            $.post(ctx+"/iconManage/iconGroupList",{pid:null},function(array){
                a.baseInfoIcon.treeObj = $.fn.zTree.init($("#ztree"),setting,array);
                a.baseInfoIcon.treeObj.expandAll(true);
            });
            $("#btn_save").bind("click", a.baseInfoIcon.btnSaveClick);
        },
        zTreeOnClick: function (event, treeId, treeNode) {
            var idArray=new Array();
            idArray.push(treeNode.id);
            var children=treeNode.children;
            a.baseInfo.buildChildrenID(idArray,children);
            var param = {
                "pageNo":"1",
                "pageSize":"21",
                "groupArray":idArray.join(",")
            };
            $.post(ctx+"/iconManage/queryIconListCount",param,function(page){
                laypage({
                    cont: $("#div_page_btn"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                        $("#div_list").load(ctx+"/iconManage/queryIconListPageForBaseInfo",param);
                    }
                });
            });
        },
        iconClick:function(_this){
            if($(_this).hasClass("selected")){
                return false;
            }
            $("#div_list div.icon-outer").removeClass("selected");
            $(_this).addClass("selected");
        },
        btnSaveClick:function(){
            if($("#div_list > div.selected").length == 0){
                layer.msg("请选择图标！",{icon: 2,time:1000});
                return false;
            }
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/baseInfo/modifyBasicInfoType",
                data:{"id":$("#txt_base_info_type").val(),"iconId":$("#div_list > div.selected").data("icon")},
                cache:false,
                success:function(r){
                    if(r != null && r == "success"){
                        window.location.href = ctx + "/baseInfo/baseInfo/baseInfoMain";
                    }else{
                        layer.msg(r.msg,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }
    }
})(WEBGIS);