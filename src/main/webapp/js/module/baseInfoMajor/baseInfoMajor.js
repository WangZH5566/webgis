(function(a) {
	a.baseInfoMajor = {
		init:function() {
            $("#btn_add").on("click", a.baseInfoMajor.btnAddClick);
            a.baseInfoMajor.btnSearchClick();
		},
        btnSearchClick:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10"
            };
            $.post(ctx+"/baseInfo/baseInfoMajor/queryCount",param,function(page){
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
                        $("#div_list").load(ctx+"/baseInfo/baseInfoMajor/queryPage",param,function(){
                            /*$("#tbody_list tr").on("click",function(){
                                $("#tbody_list tr").removeClass("selected");
                                $(this).addClass("selected");
                            });*/
                            //绑定按钮
                            $("#tbody_list tr td:nth-child(3) a:nth-child(1)").on("click",a.baseInfoMajor.btnModifyClick);
                            $("#tbody_list tr td:nth-child(3) a:nth-child(2)").on("click",a.baseInfoMajor.btnDeleteClick);
                        });
                    }
                });
            });
        },
        btnAddClick:function(){
            $.post(ctx + "/baseInfo/baseInfoMajor/addOrEdit", function(str){
                layer.open({
                    type: 1,
                    title:['提示信息','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','200px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.baseInfoMajorAddOrEdit.btnSaveClick(index);
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
        btnModifyClick:function(){
            var trId = $(this).data("id");
            $.post(ctx + "/baseInfo/baseInfoMajor/addOrEdit",{"id":trId},function(str){
                layer.open({
                    type: 1,
                    title:['提示信息','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                    skin: 'layui-layer-lan',   //样式类名
                    area:['410px','200px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.baseInfoMajorAddOrEdit.btnSaveClick(index);
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
            layer.confirm("确认要删除此专业么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $.ajax({
                    type:"POST",
                    url:ctx+"/baseInfo/baseInfoMajor/deleteBaseInfoMajor",
                    data:{"id":trId},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        if(r != null && r == "success"){
                            a.baseInfoMajor.btnSearchClick();
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
    a.baseInfoMajorAddOrEdit = {
        btnSaveClick:function(layerIndex){
            var na = trim($("#txt_name").val());
            if(na == ""){
                layer.msg("请输入[专业名称]",{icon: 2,time: 1000});
                return false;
            }
            //组装参数
            var param = {
                "id":$("#txt_id").val(),
                "na":na
            };
            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"POST",
                url:ctx+"/baseInfo/baseInfoMajor/saveBaseInfoMajor",
                data:param,
                cache:false,
                success:function(r){
                    layer.close(ind);
                    if(r != null && r == "success"){
                        a.baseInfoMajor.btnSearchClick();
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