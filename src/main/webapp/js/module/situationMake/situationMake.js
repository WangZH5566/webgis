(function(a) {
    a.sm = {
		init: function () {
            $("#btn_create").on("click",a.sm.btnCreateClick);
            $("#btn_delete").on("click",a.sm.btnDeleteClick);
            a.sm.btnSearchClick();
		},
        btnSearchClick:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10"
            };
            $.post(ctx+"/sm/queryTeleCount",param,function(page){
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
                        $("#div_list").load(ctx+"/sm/queryTelePage",param,function(){
                            $("#tbody_list tr").on("click",a.sm.trClick);
                        });
                    }
                });
            });
        },
        trClick:function(){
            $("#tbody_list tr").removeClass("selected");
            $(this).addClass("selected");
            if($(this).data("sts") == "0"){
                document.getElementById("f_cont").src = "";
                layer.msg("文电格式转换中...！",{icon: 2,time: 1000});
                return false;
            }
            if($(this).data("sts") == "2"){
                document.getElementById("f_cont").src = "";
                layer.msg("文电格式转换出错,请联系管理员！",{icon: 2,time: 1000});
                return false;
            }
            if($(this).data("sts") == "1"){
                document.getElementById("f_cont").src = tvisitPath + $(this).data("thtmlpath");
            }
        },
        btnCreateClick:function(){
            var cont = $("#d_rd").css("display","block");
            layer.open({
                type: 1,
                title:['创建文电','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                skin: 'layui-layer-lan',   //样式类名
                area:['410px','280px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    //数据验证
                    var openIndex = index;
                    if(trim($("#txt_tname").val()) == ""){
                        layer.msg("请输入文电名称！",{icon: 2,time: 1000});
                        return false;
                    }
                    if($("#txt_file").val() == ""){
                        layer.msg("请选择文电内容！",{icon: 2,time: 1000});
                        return false;
                    }
                    var fileName = $("#txt_file").val();
                    if(fileName != ""){
                        //后缀格式仅支持pdf
                        var suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        if(suffix.toLowerCase() != "doc" && suffix.toLowerCase() != "docx"){
                            $("#txt_file").val("");
                            layer.msg("本功能仅允许上传word格式的文件",{icon:2,time:2000});
                            return false;
                        }
                    }
                    $("#form_submit").submit();
                    layer.close(openIndex);
                    a.sm.btnUpload();
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: cont,
                zIndex:100,
                success: function(layero, index){
                },
                end:function(){
                    $("#txt_tname").val("");
                    $("#txt_file").val("");
                }
            });
        },
        btnUpload:function(){
            var bar = "<div style=\"margin:60px 10px 0 10px;\"><div id=\"txt_bytes\" style=\"float:left;\">0Kb/s</div><div id=\"txt_pre\" style=\"float:right;\">0%</div><div style=\"clear:both;\"></div></div><div class=\"progress progress-striped\"><div id=\"div_bar\" style=\"width: 0;\" class=\"bar\"></div></div>";
            layer.open({
                type: 1,
                title:['进度条'],
                skin: 'layui-layer-lan',   //样式类名
                area:['410px','250px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: [],
                yes: function(index, layero){},
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: bar,
                zIndex:100,
                success: function(layero, index){
                    window.setTimeout(function(){
                        PB.Method.getProgressBar(new Date().getTime(),index);
                    },500);
                },
                end:function(){
                    a.sm.btnSearchClick();
                }
            });
        },
        btnDeleteClick:function(){
            var id = $("#tbody_list tr.selected").attr("id");
            if(typeof id == "undefined" || id == null){
                layer.msg("请选择要删除的文电！",{icon: 2,time: 1000});
                return false;
            }
            layer.confirm("确认要删除选择的文电么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $("#btn_delete").off("click");
                $.ajax({
                    type:"POST",
                    url:ctx+"/sm/deleteTele",
                    data:{"id":id},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        $("#btn_delete").on("click",a.sm.btnDeleteClick);
                        if(r != null && r == "success"){
                            a.sm.btnSearchClick();
                            document.getElementById("f_cont").src = "";
                        }else{
                            layer.msg(r,{icon: 2,time:1000});
                        }
                    },
                    error:function() {
                        layer.close(index);
                        $("#btn_delete").on("click",a.sm.btnDeleteClick);
                        layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                    }
                });
            },function(index){
            });
        }
	}
})(WEBGIS);