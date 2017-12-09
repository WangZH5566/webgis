(function(a) {
	a.damage = {
		init:function() {
            $("#btn_add").on("click", a.damage.btnAddClick);
            $("#btn_add_detail").on("click", a.damage.btnAddDetailClick);
            a.damage.damageList();
		},
        damageList:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10"
            };
            $.post(ctx+"/baseInfo/damage/queryCount",param,function(page){
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
                        $("#div_list").load(ctx+"/baseInfo/damage/queryPage",param,function(){
                            $("#tbody_list tr").on("click",function(){
                                $("#tbody_list tr").removeClass("selected");
                                $(this).addClass("selected");
                                //行选中事件
                                a.damage.damageDetailList();
                            });
                            //绑定按钮
                            //$("#tbody_list tr td:nth-child(4) a:nth-child(1)").on("click",a.damage.btnModifyWithoutImgClick);
                            //$("#tbody_list tr td:nth-child(4) a:nth-child(2)").on("click",a.damage.btnModifyImgClick);
                            //$("#tbody_list tr td:nth-child(4) a:nth-child(3)").on("click",a.damage.btnDeleteClick);
                            $("#tbody_list tr td:nth-child(3) a:nth-child(1)").on("click",a.damage.btnModifyWithoutImgClick);
                            $("#tbody_list tr td:nth-child(3) a:nth-child(2)").on("click",a.damage.btnDeleteClick);
                        });
                    }
                });
            });
        },
        btnAddClick:function(){
            $.post(ctx + "/baseInfo/damage/addOrEdit",{"modifyType":"0"}, function(str){
                layer.open({
                    type: 1,
                    title:['新增','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','240px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.damageAddOrEdit.btnAddWithImgClick(index);
                    },
                    shift: 0,
                    shadeClose: false,         //开启遮罩关闭
                    content: str,               //注意，如果str是object，那么需要字符拼接。
                    zIndex:100,
                    success: function(layero, index){
                    },
                    end:function(){
                    }
                });
            });
        },
        btnModifyWithoutImgClick:function(){
            var trId = $(this).data("id");
            $.post(ctx + "/baseInfo/damage/addOrEdit",{"id":trId,"modifyType":"1"}, function(str){
                layer.open({
                    type: 1,
                    title:['修改','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','240px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.damageAddOrEdit.btnModifyWithoutImgClick(index);
                    },
                    shift: 0,
                    shadeClose: false,         //开启遮罩关闭
                    content: str,               //注意，如果str是object，那么需要字符拼接。
                    zIndex:100,
                    success: function(layero, index){
                    },
                    end:function(){
                    }
                });
            });
        },
        btnModifyImgClick:function(){
            var trId = $(this).data("id");
            $.post(ctx + "/baseInfo/damage/addOrEdit",{"id":trId,"modifyType":"2"}, function(str){
                layer.open({
                    type: 1,
                    title:['修改图标','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','240px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.damageAddOrEdit.btnModifyImgClick(index);
                    },
                    shift: 0,
                    shadeClose: false,         //开启遮罩关闭
                    content: str,               //注意，如果str是object，那么需要字符拼接。
                    zIndex:100,
                    success: function(layero, index){
                    },
                    end:function(){
                    }
                });
            });
        },
        btnDeleteClick:function(){
            var trId = $(this).data("id");
            layer.confirm("确认要删除此受损程度么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $.ajax({
                    type:"POST",
                    url:ctx+"/baseInfo/damage/deleteDamage",
                    data:{"id":trId},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        if(r != null && r == "success"){
                            a.damage.damageList();
                            layer.msg("删除成功!",{icon: 1,time:1000});
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
        damageDetailList:function(){
            var pid = $("#tbody_list tr[class='selected']").attr("id");
            if(typeof pid == "undefined"){
                return false;
            }
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "pid":pid
            };
            $.post(ctx+"/baseInfo/damage/queryDetailCount",param,function(page){
                laypage({
                    cont: $("#div_page_btn_detail"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                        $("#div_list_detail").load(ctx+"/baseInfo/damage/queryDetailPage",param,function(){
                            //绑定按钮
                            $("#tbody_list_detail tr td:nth-child(3) a:nth-child(1)").on("click",a.damage.btnModifyDetailClick);
                            $("#tbody_list_detail tr td:nth-child(3) a:nth-child(2)").on("click",a.damage.btnDeleteDetailClick);
                        });
                    }
                });
            });
        },
        btnAddDetailClick:function(){
            var pid = $("#tbody_list tr[class='selected']").attr("id");
            if(typeof pid == "undefined"){
                layer.msg("请选择[受损程度]",{icon: 2,time: 1000});
                return false;
            }
            $.post(ctx + "/baseInfo/damage/addOrEditDetail",{"pid":pid}, function(str){
                layer.open({
                    type: 1,
                    title:['新增','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','240px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.damageDetailAddOrEdit.btnSaveClick(index);
                    },
                    shift: 0,
                    shadeClose: false,         //开启遮罩关闭
                    content: str,               //注意，如果str是object，那么需要字符拼接。
                    zIndex:100,
                    success: function(layero, index){
                    },
                    end:function(){
                    }
                });
            });
        },
        btnModifyDetailClick:function(){
            var trId = $(this).data("id");
            $.post(ctx + "/baseInfo/damage/addOrEditDetail",{"id":trId}, function(str){
                layer.open({
                    type: 1,
                    title:['修改','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','240px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.damageDetailAddOrEdit.btnSaveClick(index);
                    },
                    shift: 0,
                    shadeClose: false,         //开启遮罩关闭
                    content: str,               //注意，如果str是object，那么需要字符拼接。
                    zIndex:100,
                    success: function(layero, index){
                    },
                    end:function(){
                    }
                });
            });
        },
        btnDeleteDetailClick:function(){
            var trId = $(this).data("id");
            layer.confirm("确认要删除此受损程度详情么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $.ajax({
                    type:"POST",
                    url:ctx+"/baseInfo/damage/deleteDamageDetail",
                    data:{"id":trId},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        if(r != null && r == "success"){
                            a.damage.damageDetailList();
                            layer.msg("删除成功!",{icon: 1,time:1000});
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
        }
	},
    a.damageAddOrEdit = {
        btnAddWithImgClick:function(layerIndex){
            if($("#txt_name").length > 0){
                var na = trim($("#txt_name").val());
                if(na == ""){
                    layer.msg("请输入[受损程度]",{icon: 2,time: 1000});
                    return false;
                }
            }
            /*if($("#f_icon_img").length > 0){
                var img = $("#f_icon_img").val();
                if(img == ""){
                    layer.msg("请选择[图标文件]",{icon: 2,time: 1000});
                    return false;
                }
            }*/
            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $("#from_save").ajaxSubmit({
                type: "POST",
                url:ctx+"/baseInfo/damage/addWithImg",
                success: function(r){
                    layer.close(ind);
                    if(r == "success"){
                        a.damage.damageList();
                        layer.close(layerIndex);
                        layer.msg("保存成功",{icon: 1,time:1000});
                    }else{
                        layer.msg(r,{icon:2,time:1000});
                    }
                }
            });
        },
        btnModifyWithoutImgClick:function(layerIndex){
            if($("#txt_name").length > 0){
                var na = trim($("#txt_name").val());
                if(na == ""){
                    layer.msg("请输入[受损程度]",{icon: 2,time: 1000});
                    return false;
                }
            }
            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $("#from_save").ajaxSubmit({
                type: "POST",
                url:ctx+"/baseInfo/damage/modifyWithoutImg",
                success: function(r){
                    layer.close(ind);
                    if(r == "success"){
                        a.damage.damageList();
                        layer.close(layerIndex);
                        layer.msg("保存成功",{icon: 1,time:1000});
                    }else{
                        layer.msg(r,{icon:2,time:1000});
                    }
                }
            });
        },
        btnModifyImgClick:function(layerIndex){
            if($("#f_icon_img").length > 0){
                var img = $("#f_icon_img").val();
                if(img == ""){
                    layer.msg("请选择[图标文件]",{icon: 2,time: 1000});
                    return false;
                }
            }
            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $("#from_save").ajaxSubmit({
                type: "POST",
                url:ctx+"/baseInfo/damage/modifyImg",
                success: function(r){
                    layer.close(ind);
                    if(r == "success"){
                        a.damage.damageList();
                        layer.close(layerIndex);
                        layer.msg("保存成功",{icon: 1,time:1000});
                    }else{
                        layer.msg(r,{icon:2,time:1000});
                    }
                }
            });
        }
    },
    a.damageDetailAddOrEdit = {
        btnSaveClick:function(layerIndex){
            var na = trim($("#txt_cont").val());
            if(na == ""){
                layer.msg("请输入[受损程度详情]",{icon: 2,time: 1000});
                return false;
            }
            //组装参数
            var param = {
                "id":$("#txt_id").val(),
                "pid":$("#txt_pid").val(),
                "na":na
            };
            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/damage/saveDamageDetail",
                data:param,
                cache:false,
                success:function(r){
                    layer.close(ind);
                    if(r != null && r == "success"){
                        a.damage.damageDetailList();
                        layer.close(layerIndex);
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
    }
})(WEBGIS);