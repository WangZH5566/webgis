(function(a) {
    a.tt = {
		treeObj:{},
		init: function () {
            a.tt.treeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
		},
        zTreeOnClick: function (event, treeId, treeNode) {
            $("#ttId").val(treeNode.id);
            if(treeNode.tpath == 'null' || treeNode.tpath == ""){
                document.getElementById("f_cont").src = "";
                layer.msg("文电模板还未上传！",{icon: 2,time: 1000});
                return false;
            }
            if(treeNode.tstatus == "0"){
                document.getElementById("f_cont").src = "";
                layer.msg("文电模板格式转换中...！",{icon: 2,time: 1000});
                return false;
            }
            if(treeNode.tstatus == "2"){
                document.getElementById("f_cont").src = "";
                layer.msg("文电模板格式转换出错,请联系管理员！",{icon: 2,time: 1000});
                return false;
            }
            if(treeNode.tstatus == "1"){
                document.getElementById("f_cont").src = treeNode.thtmlpath;
            }
        },
        btnUpload:function(){
            var treeNode = a.tt.treeObj.getSelectedNodes();
            if(treeNode.length == 0){
                layer.msg("请选择一个模板",{icon:2,time:2000});
                return false;
            }
            var fileName = $("#btn_upLoad").val();
            if(fileName != ""){
                //后缀格式仅支持pdf
                var suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if(suffix.toLowerCase() != "doc" && suffix.toLowerCase() != "docx"){
                    $("#btn_upLoad").val("");
                    layer.msg("本功能仅允许上传word格式的文件",{icon:2,time:2000});
                    return false;
                }
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
                        $("#uploadForm").submit();
                        window.setTimeout(function(){
                            PB.Method.getProgressBar(new Date().getTime(),index);
                        },500);
                    },
                    end:function(){
                        $("#btn_upLoad").val("");
                        $.ajax({
                            type:"POST",
                            url:ctx+"/tt/getTreeNodes",
                            data:{},
                            datatype:"json",
                            cache:false,
                            success:function(r){
                                $.fn.zTree.destroy("ztree");
                                a.tt.treeObj = $.fn.zTree.init($("#ztree"), setting, r);
                            },
                            error:function() {
                            }
                        });
                    }
                });
            }
        }
	}
})(WEBGIS);