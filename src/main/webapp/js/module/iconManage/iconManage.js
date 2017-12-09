(function(a) {
    a.iconManage = {
        init:function(){
            a.iconGroupManage.init();
            $("#btn_add_icon").on("click", a.iconManage.btn_addIconClick)
        },
        btn_addIconClick:function(){
            var nodes=a.iconGroupManage.treeObj.getSelectedNodes();
            if(nodes==null||nodes.length==0||nodes.length>1){
                layer.msg("请先选择分组",{icon:2,time:1000});
                return false;
            }
            $.post(ctx+"/iconManage/addPage",{iconGroup:nodes[0].id},function(str){
                layer.open({
                    type: 1,
                    area: ['500px', '380px'],
                    fix: false, //不固定
                    title: ["添加图标","font-weight:bold;"],
                    content: str //注意，如果str是object，那么需要字符拼接。
                });
            });
        },
        iconClick:function(_this){
            if($(_this).hasClass("selected")){
                return false;
            }
            $("#div_list div.icon-outer").removeClass("selected");
            $(_this).addClass("selected");
            //$("#div_icon_edit_info").load(ctx+"/iconManage/editPage",{id:$(_this).data("icon")},function(){
            //    $(_this).addClass("selected");
            //});
        },
        deleteIcon:function(id){
            layer.confirm('确认删除该图标？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                layer.closeAll();
                $.post(ctx+"/iconManage/deleteIcon",{id:id},function(re){
                    if(re.msg=="SUCCESS"){
                        a.iconGroupManage.iconListRefresh();
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            }, function(){
                layer.closeAll();
            });
        },
        clearRightInfo:function(){
            $("#div_icon_edit_info .widget-content").empty();
        }
    };
    a.iconAdd = {
        init:function(){
            $("#btn_save").bind("click", a.iconAdd.save);
        },
        checkboxClick:function(_this){
            var checked=$(_this).prop("checked");
            var target=$(_this).data("target");
            if(checked){
                $("#form_save_icon div[name="+target+"]").show();
            }else{
                $("#form_save_icon div[name="+target+"] input").val("");
                $("#form_save_icon div[name="+target+"]").hide();
            }
        },
        validate:function(json){
            if(json.iconGroup==""){
                return "请选择分组后再新增";
            }
            if(json.name==""){
                return "请输入图标名称";
            }
            //if(json.name.length>30){
            //    return "图标名长度不能超过30字"
            //}
            if(json.hasSpeed&&json.speed==""){
                return "请输入速度值";
            }
            if(json.hasDamage){
                if(json.damageLevel==null){
                    return "请输入受损分级";
                }
                if(json.damageLevelTime==null){
                    return "请输入受损分级维修时间";
                }
            }
            if((json.id==null||trim(json.id)=="")&&(json.img==null||json.img=="")){
                //新增时图片为空
                return "请选择图标文件";
            }
            return null;
        },
        save:function(){
            var json={
                id:$("#form_save_icon #id").val(),
                iconGroup:$("#form_save_icon #txt_icon_group").val(),
                name:$("#form_save_icon #txt_name").val(),
                hasSpeed:$("#form_save_icon #chk_speed").prop("checked"),
                hasDamage:$("#form_save_icon #chk_damage").prop("checked"),
                speed:$("#form_save_icon #txt_speed").val(),
                speedUnit:$("#form_save_icon #txt_speed_unit").val(),
                damageLevel:$("#form_save_icon #txt_damageLevel").val(),
                damageLevelTime:$("#form_save_icon #txt_damageLevelTime").val(),
                img:$("#form_save_icon #f_icon_img").val()
            }
            var msg=a.iconAdd.validate(json);
            if(msg!=null){
                layer.msg(msg,{icon:2});
                return;
            }
            $("#btn_save").unbind("click");
            $("#form_save_icon").ajaxSubmit({
                type: "POST",
                url:ctx+"/iconManage/saveIcon",
                success: function(re){
                    if(re.msg=="SUCCESS"){
                        layer.closeAll();
                        a.iconGroupManage.iconListRefresh();
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                    $("#btn_save").bind("click", a.iconEdit.save);
                }
            });
        }
    };
    a.iconEdit={
        init:function(){
            $("#btn_save_icon").bind("click", a.iconEdit.save);
            $("#btn_add_icon_field").bind("click", a.iconEdit.addField)
        },
        checkboxClick:function(_this){
            var checked=$(_this).prop("checked");
            var target=$(_this).data("target");
            if(checked){
                $("#form_save_icon_edit div[name="+target+"]").show();
            }else{
                $("#form_save_icon_edit div[name="+target+"] input").val("");
                $("#form_save_icon_edit div[name="+target+"]").hide();
            }
        },
        validate:function(json){
            if(json.iconGroup==""){
                return "请选择分组后再新增";
            }
            //if(json.name.length>30){
            //    return "图标名长度不能超过30字"
            //}
            if(json.hasSpeed&&json.speed==""){
                return "请输入速度值";
            }
            if(json.hasDamage){
                if(json.damageLevel==null){
                    return "请输入受损分级";
                }
                if(json.damageLevelTime==null){
                    return "请输入受损分级维修时间";
                }
            }
            return null;
        },
        save:function(){
            if($("#form_save_icon_edit #id").val()==null||$("#form_save_icon_edit #id").val()==""){
                layer.msg("请选择图标",{icon:2,time:1000});
                return;
            }
            var json={
                id:$("#form_save_icon_edit #id").val(),
                iconGroup:$("#form_save_icon_edit #txt_icon_group").val(),
                hasSpeed:$("#form_save_icon_edit #chk_speed").prop("checked"),
                hasDamage:$("#form_save_icon_edit #chk_damage").prop("checked"),
                speed:$("#form_save_icon_edit #txt_speed").val(),
                speedUnit:$("#form_save_icon #txt_speed_unit").val(),
                damageLevel:$("#form_save_icon_edit #txt_damageLevel").val(),
                damageLevelTime:$("#form_save_icon_edit #txt_damageLevelTime").val()
            }
            var msg=a.iconEdit.validate(json);
            if(msg!=null){
                layer.msg(msg,{icon:2});
                return;
            }
            $("#btn_save_icon").unbind("click");
            $("#form_save_icon_edit").ajaxSubmit({
                type: "POST",
                url:ctx+"/iconManage/saveIcon",
                success: function(re){
                    if(re.msg=="SUCCESS"){
                        layer.closeAll();
                        layer.msg("保存成功",{icon:1,time:1000});
                        //a.iconGroupManage.iconListRefresh();
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                    $("#btn_save_icon").bind("click", a.iconEdit.save);
                }
            });
        },
        addField:function(){
            var icon=$("#form_save_icon_edit #id").val();
            if(icon==null||icon==""){
                layer.msg("请选择图标",{icon:2,time:1000});
                return;
            }
            $.post(ctx+"/iconManage/addFieldPage",{icon:icon},function(str){
                layer.open({
                    type: 1,
                    area: ['300px', '200px'],
                    fix: false, //不固定
                    title: ["添加属性","font-weight:bold;"],
                    content: str //注意，如果str是object，那么需要字符拼接。
                });
            });
        }
    };
    a.iconExtEdit = {
        init:function(){
            $("#btn_save_ext").bind("click", a.iconExtEdit.save);
        },
        save:function(){
            var param={
                id:$("#form_save_icon_ext #txt_id").val(),
                icon:$("#form_save_icon_ext #txt_icon_id").val(),
                name:$("#form_save_icon_ext #txt_name").val(),
                value:$("#form_save_icon_ext #txt_value").val()
            };
            $("#btn_save_ext").unbind("click");
            $.post(ctx+"/iconManage/saveIconField",param,function(re){
                if(re.msg=="SUCCESS"){
                    var json=re.result;
                    if($("#form_ext_info #ext_"+json.id)&&$("#form_ext_info #ext_"+json.id).length>0){
                        //修改
                        var tmpObj=$("#form_ext_info #ext_"+json.id);
                        tmpObj.find("div.form-group label").text(json.name);
                        tmpObj.find("div.form-group div input").val(json.value);
                    }else{
                        //新增
                        var tmpObj=$("#div_ext_template").clone(true);
                        tmpObj.attr("id","ext_"+json.id);
                        tmpObj.data("id",json.id);
                        tmpObj.find("div.form-group label").text(json.name);
                        tmpObj.find("div.form-group div input").val(json.value);
                        tmpObj.find("a.btn-delete").data("id");
                        $("#form_ext_info").append(tmpObj);
                        tmpObj.show();
                    }
                    layer.closeAll();
                    layer.msg("保存成功",{icon:1,time:1000});
                }else{
                    layer.msg(re.msg,{icon:2,time:1000});
                }
                $("#btn_save_ext").bind("click", a.iconExtEdit.save);
            })
        },
        edit:function(_this){
            $.post(ctx+"/iconManage/editFieldPage",{id:$(_this).data("id")},function(str){
                layer.open({
                    type: 1,
                    area: ['300px', '200px'],
                    fix: false, //不固定
                    title: ["修改属性","font-weight:bold;"],
                    content: str //注意，如果str是object，那么需要字符拼接。
                });
            });
        },
        delete:function(_this){
            var id=$(_this).data("id");
            layer.confirm('是否确认删除该属性？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                layer.closeAll();
                $.post(ctx+"/iconManage/deleteIconExt",{id:id},function(re){
                    if(re.msg=="SUCCESS"){
                        $("#form_ext_info #ext_"+id).remove();
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            }, function(){
                layer.closeAll();
            });
        }
    };
    a.iconGroupManage = {
		treeObj:{},
        init: function () {
            $.post(ctx+"/iconManage/iconGroupList",{pid:null},function(array){
                a.iconGroupManage.treeObj = $.fn.zTree.init($("#ztree"),setting,array);
                a.iconGroupManage.treeObj.expandAll(true);
            })
            $("#btn_add_root_node").on("click",a.iconGroupManage.addRootNode);
            //WEBGIS.Helper.onlyNumber($("#txt_exec_time"));
        },
        addRootNode:function(){
            layer.prompt({title: '请输入根节点名称', formType: 0}, function(text, index){
                $.post(ctx+"/iconManage/saveIconGroup",{name:text,pid:0},function(re){
                    layer.close(index);
                    if(re.msg=="SUCCESS"){
                        var json=re.result;
                        a.iconGroupManage.treeObj.addNodes(null, json);
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            });
        },
        removeHoverDom:function(treeId, treeNode) {
            $("#diyBtnAdd_"+treeNode.id).unbind().remove();
        },
        addHoverDom:function(treeId, treeNode) {
            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#diyBtnAdd_"+treeNode.id).length == 0){
                var addStr = "<span class=\"button diyBtnAdd\" id=\"diyBtnAdd_"+treeNode.id+"\" title=\"新增节点\" onfocus=\"this.blur();\"></span>";
                aObj.append(addStr);
                var btnAdd = $("#diyBtnAdd_"+treeNode.id);
                if (btnAdd) btnAdd.bind("click", function(){
                    var zTree = a.iconGroupManage.treeObj;
                    $.post(ctx+"/iconManage/saveIconGroup",{name:"分组",pid:treeNode.id},function(re){
                        if(re.msg=="SUCCESS"){
                            var json=re.result;
                            a.iconGroupManage.treeObj.addNodes(treeNode, json);
                        }else{
                            layer.msg(re.msg,{icon:2,time:1000});
                        }
                    });
                    return false;
                });
            }
        },
        beforeRemove:function(treeId, treeNode) {
            var zTree = a.iconGroupManage.treeObj;
            if(treeNode.children!=null&&treeNode.children.length>0){
                layer.msg("请先删除该分组下的子分组",{icon:2,time:1000});
                return false;
            }
            layer.confirm('删除分组会连带删除该分组下图标，是否确认？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                layer.closeAll();
                $.post(ctx+"/iconManage/deleteIconGroup",{id:treeNode.id},function(re){
                    if(re.msg=="SUCCESS"){
                        zTree.removeNode(treeNode);
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                });
            }, function(){
                layer.closeAll();
            });
            return false;
        },
        beforeRename:function(treeId, treeNode, newName, isCancel) {
            if (newName.length == 0) {
                setTimeout(function() {
                    var zTree = $.fn.zTree.getZTreeObj("ztree");
                    zTree.cancelEditName();
                    layer.msg("分组名称不能为空",{icon:2,time:1000});
                }, 0);
                return false;
            }
            $.post(ctx+"/iconManage/saveIconGroup",{id:treeNode.id,name:newName,pid:treeNode.pid},function(re){
                if(re.msg=="SUCCESS"){
                    var json=re.result;
                }else{
                    layer.msg(re.msg,{icon:2,time:1000});
                }
            });
            return true;
        },
        beforeDrag:function(treeId, treeNodes) {
            return true;
        },
        beforeDrop:function(treeId, treeNodes, targetNode, moveType) {
            var pid=null;
            if(moveType=="prev"||moveType=="next"){
                pid=targetNode.pid;
            }else{
                pid=(targetNode==null?0:targetNode.id);
            }
            $.post(ctx+"/iconManage/saveIconGroup",{id:treeNodes[0].id,name:treeNodes[0].name,pid:pid},function(re){
                if(re.msg=="SUCCESS"){
                    return true;
                }else{
                    layer.msg("移动失败",{icon:2,time:1000});
                }
                return false;
            });
        },
        zTreeOnClick: function (event, treeId, treeNode) {
            var idArray=new Array();
            idArray.push(treeNode.id);
            var children=treeNode.children;
            a.iconGroupManage.buildChildrenID(idArray,children);
            a.iconGroupManage.loadIconList(idArray.join(","));
            a.iconManage.clearRightInfo();
        },
        buildChildrenID:function(idArray,children){
            if(children==null||children.length<=0){
                return;
            }
            for(var i=0;i<children.length;i++){
                var node=children[i];
                idArray.push(node.id);
                tmpChildren=node.children;
                a.iconGroupManage.buildChildrenID(idArray,tmpChildren);
            }
        },
        iconListRefresh:function(){
            var param = {
                "pageNo":$("#txt_current_page").val(),
                "pageSize":"10",
                "groupArray":$("#txt_current_groupArray").val()
            };
            $("#div_list").load(ctx+"/iconManage/queryIconListPage",param,function(){
                a.iconManage.clearRightInfo();
            });
        },
        loadIconList:function(groupArray){
            var param = {
                "pageNo":"1",
                "pageSize":"21",
                "groupArray":groupArray==null?"":groupArray
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
                        $("#div_list").load(ctx+"/iconManage/queryIconListPage",param);
                    }
                });
            });
        }
    }
})(WEBGIS);