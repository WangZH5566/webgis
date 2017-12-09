(function(a) {
    a.sh = {
        docVisitPath:null,
        interval:null,
		init: function () {
            $("#ul_tab li").click(a.sh.tabClick);
            a.sh.btnSearchClick();
            a.sm.init();
        },
        tabClick:function(){
            if($(this).hasClass("active")){
                return false;
            }
            $("#ul_tab li").removeClass("active");
            $(this).addClass("active");
            a.sh.btnSearchClick();
        },
        btnSearchClick:function(){
            var type = $("#ul_tab li[class='active']").data("type");
            var param = {
                "pageNo":"1",
                "pageSize":"10"
            };
            var divID="div_"+type;
            $("#tab_div_list div[name='div_show_for_tab']").hide();
            $("#"+divID).show();
            if(type == "0"){
                $.post(ctx+"/sh/queryTeleReceiveCount",param,function(page){
                    laypage({
                        cont: $("#div_page_btn_0"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                            $("#div_list_0").load(ctx+"/sh/queryTeleReceivePage",param,function(){
                                $("#tbody_list tr").on("click",a.sh.trClick);
                            });
                        }
                    });
                });
            }else if(type == "1"){
                $.post(ctx+"/sh/queryTeleSendCount",param,function(page){
                    laypage({
                        cont: $("#div_page_btn_1"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                            $("#div_list_1").load(ctx+"/sh/queryTeleSendPage",param,function(){
                                $("#tbody_list tr").on("click",a.sh.trClick);
                                $("#tbody_list tr td:nth-child(3) a:nth-child(1)").on("click",a.sh.btnSendDetail);
                            });
                        }
                    });
                });
            }else if(type=="2"){

            }
        },
        btnSendDetail:function(){
            var ttId = $(this).data("id");
            var param = {
                "ttId":ttId,
                "pageNo":"1",
                "pageSize":"10"
            };
            $.post(ctx+"/sh/queryTeleSendDetailCount",param,function(page){
                laypage({
                    cont: $("#div_detail_page_btn"),                //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                        $("#div_detail_list").load(ctx+"/sh/queryTeleSendDetailPage",param,function(){
                        });
                    }
                });
            });
            layer.open({
                type: 1,
                title:["发送详情查看","font-weight:bold;"],
                area:['600px', '500px'],
                closeBtn: 1,                //不显示关闭按钮
                //btn: ['确认', '取消'],
                btn: '关闭',
                yes: function(index, layero){
                    layer.close(index);
                },
                /*cancel:function(index, layero){

                },*/
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#div_send_detail"),
                zIndex:100,
                success: function(layero, index){
                },
                end:function(){
                    $("#div_detail_list").html("");
                    $("#div_detail_page_btn").html("");
                }
            });
        },
        trClick:function(){
            if($(this).hasClass("selected")){
                return;
            }
            var thisObj=$(this);
            $(this).parent().children("tr").removeClass("selected");
            var id=$(this).data("id");
            $.post(ctx+"/sh/queryTele",{id:id},function(json){
                thisObj.addClass("selected");
                if(json!=null&&json!=""){
                    if((json.thtmlpath == null||json.thtmlpath=="") && (json.msg ==null||json.msg== "")){
                        document.getElementById("f_cont").src = "";
                        layer.msg("文电格式转换中...！",{icon: 2,time: 1000});
                        return false;
                    }
                    if((json.thtmlpath == null||json.thtmlpath=="") && json.msg!=null&&json.msg!= ""){
                        document.getElementById("f_cont").src = "";
                        layer.msg("文电格式转换出错,请联系管理员！",{icon: 2,time: 1000});
                        return false;
                    }
                    if(json.thtmlpath != null&&json.thtmlpath!=""){
                        document.getElementById("f_cont").src = tvisitPath + json.thtmlpath;
                    }
                }
            });
        },
        isReceiptClick:function(_this){
            var btnObj = $(_this);
            var cont = $("#win_receipt_msg");
            layer.open({
                type: 1,
                title:['回执信息','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                skin: 'layui-layer-lan',   //样式类名
                area:['410px','280px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    //数据验证
                    var openIndex = index;
                    $.post(ctx+"/sh/isReceipt",{"id":btnObj.data("id"),"isReceipt":1,"receiptMsg":$("#txt_receipt_msg").val()},function(r){
                        if(r != null && r == "success"){
                            btnObj.parent().prev().prev().html("是");
                            btnObj.remove();
                            layer.close(openIndex);
                            $("#txt_receipt_msg").val("已读回执");
                            layer.msg("操作成功",{icon:1,time:1000});
                        }else{
                            layer.msg(r,{icon: 2,time:1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: cont,
                zIndex:100,
                success: function(layero, index){
                },
                end:function(){
                    $("#txt_receipt_msg").val("已读回执");
                }
            });
        },
        isReadClick:function(_this){
            var btnObj = $(_this);
            $.post(ctx+"/sh/isRead",{"id":btnObj.data("id")},function(r){
                if(r != null && r == "success"){
                    btnObj.parent().prev().html("已读");
                    btnObj.parent().parent().children("td:eq(0)").removeClass("blue");
                    btnObj.remove();
                    layer.msg("操作成功",{icon:1,time:1000});
                }else{
                    layer.msg(r,{icon: 2,time:1000});
                }
            });
        },
        btnSendClick:function(){
            if($("#sel_user").val() == ""){
                layer.msg("请选择[发送对象]！",{icon: 2,time: 1000});
                return false;
            }
            if($("#sel_tele").val() == ""){
                layer.msg("请选择[文电]！",{icon: 2,time: 1000});
                return false;
            }
            $("#btn_send").off("click");
            $.ajax({
                type:"POST",
                url:ctx+"/sh/receiveTele",
                data:{"teleId":$("#sel_tele").val(),"teleName":$("#sel_tele option:selected").text(),"userId":$("#sel_user").val(),"userName":$("#sel_user option:selected").text()},
                cache:false,
                success:function(r){
                    $("#btn_send").on("click",a.sh.btnSendClick);
                    if(r != null && r == "success"){
                        layer.msg("发送成功",{icon: 1,time:1000});
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    $("#btn_send").on("click",a.sh.btnSendClick);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });

        }
	},
    a.sm = {
        init: function () {
            $("#btn_search").on("click",a.sm.btnSearchClick);
            $("#btn_create").on("click",a.sm.btnCreateClick);
            $("#btn_delete").on("click",a.sm.btnDeleteClick);
            a.sm.btnSearchClick();
        },
        btnSearchClick:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "tn":trim($("#txt_tn").val())
            };
            $.post(ctx+"/sm/queryTeleCount",param,function(page){
                laypage({
                    cont: $("#div_page_btn_2"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
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
                        $("#div_list_2").load(ctx+"/sm/queryTelePage",param,function(){
                            $("#tbody_list tr td:nth-child(4) a").on("click",function(e){
                                e.stopPropagation();
                                window.location.href = ctx + "/sh/teleSend?ttid=" + $(this).data("id");
                            });
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
    },
    a.shSend = {
        leftTreeObj:{},
        rightTreeObj:{},
        init: function () {
            a.shSend.leftTreeObj = $.fn.zTree.init($("#ztree_left"),setting,zTreeNodes);
            a.shSend.rightTreeObj = $.fn.zTree.init($("#ztree_right"),setting,zTreeNodes);
            $("#btn_send").on("click",a.shSend.btnSendClick);
            $("#btn_left_chk_all").on("click",a.shSend.btnLeftChkAllClick);
            $("#btn_left_clear_all").on("click",a.shSend.btnLeftClearAllClick);
            $("#btn_right_chk_all").on("click",a.shSend.btnRightChkAllClick);
            $("#btn_right_clear_all").on("click",a.shSend.btnRightClearAllClick);

        },
        zTreeOnCheck:function(event,treeId,treeNode){
            var treeObj = null;
            var idContainer = null;
            var nameContainer = null;
            if(treeId == "ztree_left"){
                treeObj = a.shSend.leftTreeObj;
                idContainer = $("#txt_send_to");
                nameContainer = $("#txt_send_to_name");
            }else if(treeId == "ztree_right"){
                treeObj = a.shSend.rightTreeObj;
                idContainer = $("#txt_copy_to");
                nameContainer = $("#txt_copy_to_name");
            }
            var nodes = treeObj.getCheckedNodes(true);
            var idArr = new Array();
            var nameArr = new Array();
            for(var i=0;i<nodes.length;i++){
                if(!nodes[i].isParent&&!isNaN(nodes[i].id)){
                    idArr.push(nodes[i].id);
                    nameArr.push(nodes[i].name);
                }
            }
            idContainer.val(idArr.join(","));
            nameContainer.val(nameArr.join(","));
        },
        btnSendClick:function(){
            if($("#sel_tt").length > 0 && $("#sel_tt").val() == ""){
                layer.msg("请选择文电",{icon:2,time:1000});
                return false;
            }
            if($("#txt_send_to").val() == ""){
                layer.msg("请选择主送人员",{icon:2,time:1000});
                return false;
            }
            var ttid = null;
            if($("#sel_tt").length > 0){
                ttid = $("#sel_tt").val();
            }else{
                ttid = $("#txt_tt_id").val();
            }
            var param = {
                "ttid":ttid,
                "sendTo":$("#txt_send_to").val(),
                "copyTo":$("#txt_copy_to").val()
            };
            $.post(ctx+"/sh/sendTelegram",param,function(r){
                if(r != null && r == "success"){
                    layer.msg("发送成功",{icon: 1,time:1000});
                    window.location.href = ctx + "/sh/main";
                }else{
                    layer.msg(r,{icon: 2,time:1000});
                }
            });
        },
        btnLeftChkAllClick:function(){
            var nodes = a.shSend.leftTreeObj.transformToArray(a.shSend.leftTreeObj.getNodes());
            var idArr = new Array();
            var nameArr = new Array();
            for (var i=0; i < nodes.length; i++) {
                a.shSend.leftTreeObj.checkNode(nodes[i], true, false);
                if(!nodes[i].isParent&&!isNaN(nodes[i].id)){
                    idArr.push(nodes[i].id);
                    nameArr.push(nodes[i].name);
                }
            }
            $("#txt_send_to").val(idArr.join(","));
            $("#txt_send_to_name").val(nameArr.join(","));
        },
        btnLeftClearAllClick:function(){
            var nodes = a.shSend.leftTreeObj.transformToArray(a.shSend.leftTreeObj.getNodes());
            for (var i=0; i < nodes.length; i++) {
                a.shSend.leftTreeObj.checkNode(nodes[i], false, false);
            }
            $("#txt_send_to").val("");
            $("#txt_send_to_name").val("");
        },
        btnRightChkAllClick:function(){
            var nodes = a.shSend.rightTreeObj.transformToArray(a.shSend.rightTreeObj.getNodes());
            var idArr = new Array();
            var nameArr = new Array();
            for (var i=0; i < nodes.length; i++) {
                a.shSend.rightTreeObj.checkNode(nodes[i], true, false);
                if(!nodes[i].isParent&&!isNaN(nodes[i].id)){
                    idArr.push(nodes[i].id);
                    nameArr.push(nodes[i].name);
                }
            }
            $("#txt_copy_to").val(idArr.join(","));
            $("#txt_copy_to_name").val(nameArr.join(","));
        },
        btnRightClearAllClick:function(){
            var nodes = a.shSend.rightTreeObj.transformToArray(a.shSend.rightTreeObj.getNodes());
            for (var i=0; i < nodes.length; i++) {
                a.shSend.rightTreeObj.checkNode(nodes[i], false, false);
            }
            $("#txt_copy_to").val("");
            $("#txt_copy_to_name").val("");
        }
    }
})(WEBGIS);