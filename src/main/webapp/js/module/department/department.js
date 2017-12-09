(function(a) {
	a.department = {
		init:function() {
            $("#btn_search").on("click", a.department.btnSearchClick);
            $("#btn_modify").on("click", a.department.btnModifyClick);
            $("#btn_delete").on("click", a.department.btnDeleteClick);
            $("#btn_search").trigger("click");
		},
        btnSearchClick:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "na":$.trim($("#txt_name").val()),
                "co":$.trim($("#txt_code").val())
            };
            $.post(ctx+"/department/queryCount",param,function(page){
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
                        $("#div_list").load(ctx+"/department/queryPage",param,function(){
                            $("#tbody_list tr").on("click",function(){
                                $("#tbody_list tr").removeClass("selected");
                                $(this).addClass("selected");
                            });
                        });
                    }
                });
            });
        },
        btnModifyClick:function(){
            var deId = $("#tbody_list tr.selected").attr("id");
            if(typeof deId == "undefined"){
                layer.msg("请选择要修改的数据！",{icon: 2,time: 1000});
                return false;
            }
            window.location.href = ctx + "/department/addOrEdit?id=" + deId;
        },
        btnDeleteClick:function(){
            var deId = $("#tbody_list tr.selected").attr("id");
            if(typeof deId == "undefined"){
                layer.msg("请选择要删除的数据！",{icon: 2,time: 1000});
                return false;
            }

            layer.confirm("确认要删除选择的台位么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $("#btn_delete").off("click");
                $.ajax({
                    type:"POST",
                    url:ctx+"/department/deleteDepartment",
                    data:{"id":deId},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        $("#btn_delete").on("click", a.department.btnDeleteClick);
                        if(r != null && r == "success"){
                            $("#btn_search").trigger("click");
                        }else{
                            layer.msg(r,{icon: 2,time:1000});
                        }
                    },
                    error:function() {
                        layer.close(index);
                        $("#btn_delete").on("click", a.department.btnDeleteClick);
                        layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                    }
                });
            },function(index){
            });
        }
	},
    a.departmentAddOrEdit = {
        init: function () {
            $("#btn_save").on("click",a.departmentAddOrEdit.btnSaveClick);
        },
        btnSaveClick:function(){
            var na = trim($("#txt_name").val());
            var co = trim($("#txt_code").val());
            if(na == ""){
                layer.msg("请输入[台位名称]",{icon: 2,time: 1000});
                return false;
            }
            if(co == ""){
                layer.msg("请输入[台位代码]",{icon: 2,time: 1000});
                return false;
            }

            //组装参数
            var param = {
                "id":$("#txt_id").val(),
                "na":na,
                "co":co,
                "cu":$("#chk_cu").is(":checked")?1:0,
            };
            //解绑按钮事件
            $("#btn_save").off("click");
            var ind = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"POST",
                url:ctx+"/department/saveDepartment",
                data:param,
                cache:false,
                success:function(r){
                    $("#btn_save").on("click",a.departmentAddOrEdit.btnSaveClick);
                    layer.close(ind);
                    if(r != null && r == "success"){
                        layer.msg("保存成功",{icon: 1,time:1000});
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    $("#btn_save").on("click",a.departmentAddOrEdit.btnSaveClick);
                    layer.close(ind);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }
    }
})(WEBGIS);