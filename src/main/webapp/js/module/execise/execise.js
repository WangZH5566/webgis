(function(a) {
	a.exec = {
		init:function() {
            $("#btn_search").on("click", a.exec.btnSearchClick);
            $("#btn_modify").on("click", a.exec.btnModifyClick);
            $("#btn_delete").on("click", a.exec.btnDeleteClick);
            $("#btn_print").on("click", a.exec.btnPrintClick);
            $("#btn_exec_user").on("click", a.exec.btnExecUserClick);
            $("#btn_review").on("click", a.exec.btnExecReviewClick);
            $("#btn_search").trigger("click");
		},
        btnSearchClick:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "en":$.trim($("#txt_exec_name").val()),
                "status":$("#txt_exec_status").val()
            };
            $.post(ctx+"/exec/queryExecCount",param,function(page){
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
                        $("#div_list").load(ctx+"/exec/queryExecPage",param,function(){
                            $("#tbody_list tr").on("click",function(){
                                $("#tbody_list tr").removeClass("selected");
                                $(this).addClass("selected");
                            });
                            $("#check_all").on("click",function(){
                                if($(this).is(":checked")){
                                    $("#tbody_list input[type='checkbox']").prop("checked",true);
                                }else{
                                    $("#tbody_list input[type='checkbox']").prop("checked",false);
                                }
                            });
                        });
                    }
                });
            });
        },
        btnModifyClick:function(){
            var execId = $("#tbody_list tr.selected").attr("id");
            var execStatus = $("#tbody_list tr.selected").data("sta");
            if(typeof execId == "undefined"){
                layer.msg("请选择要修改的数据！",{icon: 2,time: 1000});
                return false;
            }
            if(parseInt(execStatus) > 0){
                layer.msg("不允许修改已开始的推演！",{icon: 2,time: 1000});
                return false;
            }
            window.location.href = ctx + "/exec/addStepOne?id=" + execId;
        },
        btnDeleteClick:function(){
            var checkObj = $("#tbody_list input:checked");
            if(typeof checkObj == "undefined" || checkObj.length == 0){
                layer.msg("请勾选要删除的推演！",{icon: 2,time: 1000});
                return false;
            }

            var msg = "";
            var idArr = new Array();
            checkObj.each(function(){
                if(parseInt($(this).data("sta")) > 0){
                    msg = "不允许删除已开始的推演！";
                    return false;
                }
                idArr.push($(this).data("id"));
            });

            if(msg != ""){
                layer.msg(msg,{icon: 2,time: 1000});
                return false;
            }

            layer.confirm("确认要删除勾选的推演么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                $("#btn_delete").off("click");
                $.ajax({
                    type:"POST",
                    url:ctx+"/exec/deleteExec",
                    data:{"ids":idArr.join(",")},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        $("#btn_delete").on("click", a.exec.btnDeleteClick);
                        if(r != null && r == "success"){
                            $("#btn_search").trigger("click");
                        }else{
                            layer.msg(r,{icon: 2,time:1000});
                        }
                    },
                    error:function() {
                        layer.close(index);
                        $("#btn_delete").on("click", a.exec.btnDeleteClick);
                        layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                    }
                });
            },function(index){
            });
        },
        btnPrintClick:function(){
            var execId = $("#tbody_list tr.selected").attr("id");
            if(typeof execId == "undefined"){
                layer.msg("请选择推演！",{icon: 2,time: 1000});
                return false;
            }
            var execName = $("#tbody_list #"+ execId + " td:nth-child(3)").text();
            $("#btn_print").off("click");
            /*var index = layer.load(1, {
             shade: [0.1,'#fff'] //0.1透明度的白色背景
             });*/
            $("#div_print_list").load(ctx+"/exec/printLoad",{"id":execId},function(){
                $("#btn_print").on("click", a.exec.btnPrintClick);
                lodop=getLodop();
                //打印初始化，给打印设置个标题
                lodop.PRINT_INIT("打印角色清单");
                //设置打印页面属性：2：表示横向打印，0：定义纸张宽度，为0表示无效设置，A4：设置纸张为A4
                lodop.SET_PRINT_PAGESIZE (2, 0, 0,"A4");
                //设置打印title
                lodop.SET_PRINT_STYLE("FontSize",14);
                lodop.SET_PRINT_STYLE("Bold",1);
                lodop.SET_PRINT_STYLE("Alignment",2);
                lodop.ADD_PRINT_TEXT(20,'30%','40%',30,execName);
                //ADD_PRINT_HTM(Top,Left,Width,Height,strHtmlContent)
                //Top:打印项在纸张内的上边距，也就是在每张纸的上下起点位置
                //Left:打印项在纸张内的左边距，也就是在每张纸的左右起点位
                //Width:打印区域的宽度
                //Height:打印区域的高度
                lodop.ADD_PRINT_HTM(50,90,940,650,document.getElementById("div_print_list").innerHTML);
                lodop.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);//横向时的正向显示
                lodop.PREVIEW();
                //layer.close(index);
            });
        },
        btnExecUserClick:function(){
            var selectObj=$("#tbody_list tr.selected");
            if(selectObj.size()!=1){
                layer.msg("请选中一条推演",{icon:2,time: 1000})
                return false;
            }
            var execId=selectObj.attr("id");
            window.location.href = ctx + "/exec/execUnitUser?id=" + execId;
        },
        btnExecReviewClick:function(){
            var execId = $("#tbody_list tr.selected").attr("id");
            if(typeof execId == "undefined"){
                layer.msg("请选择推演！",{icon: 2,time: 1000});
                return false;
            }
            var execStatus = $("#tbody_list tr.selected").data("sta");
            if(parseInt(execStatus) != 10){
                layer.msg("该推演还未结束,无法进行回顾！",{icon: 2,time: 1000});
                return false;
            }
            window.open(ctx + "/exec/execReview?id="+execId);
        }
	},
    a.execStepOne = {
		treeObj:{},
        init: function () {
            laydate.skin('danlan');
            a.execStepOne.treeObj = $.fn.zTree.init($("#ztree"),setting,zTreeNodes);
            $("#btn_next").on("click",a.execStepOne.saveStepOne);
            $(".spinnerInput").spinner({});
        },
        zTreeOnCheck: function () {

        },
        saveStepOne:function(){
            var execName = trim($("#txt_exec_name").val());
            var fightTime = trim($("#txt_fight_time").val());
            var loginName = trim($("#txt_login_name").val());
            var userName = trim($("#txt_user_name").val());
            var password = trim($("#txt_password").val());
            var comment = trim($("#txt_comment").val());
            if(execName == ""){
                layer.msg("请输入[推演名称]",{icon: 2,time: 1000});
                return false;
            }
            if(fightTime == ""){
                layer.msg("请输入[作战时间]",{icon: 2,time: 1000});
                return false;
            }
            if(loginName == ""){
                layer.msg("请输入[导演登录名]",{icon: 2,time: 1000});
                return false;
            }
            if(userName == ""){
                layer.msg("请输入[导演用户名]",{icon: 2,time: 1000});
                return false;
            }
            if(password == ""){
                layer.msg("请输入[导演密码]",{icon: 2,time: 1000});
                return false;
            }

            //获取所有树节点
            var treeNodes = a.execStepOne.treeObj.getNodes();
            var treeNodes_array = a.execStepOne.treeObj.transformToArray(treeNodes);
            var idArr = new Array();
            var nameArr = new Array();
            var pidArr = new Array();
            var levelArr = new Array();
            for(var i=0;i<treeNodes_array.length;i++){
                var id = treeNodes_array[i].id;
                var pid = treeNodes_array[i].pId;
                idArr.push(id);
                nameArr.push(treeNodes_array[i].name);
                pidArr.push(pid);
                levelArr.push(treeNodes_array[i].level);
            }

            //组装参数
            var param = {
                "id":$("#txt_id").val(),
                "directorId":$("#txt_director_id").val(),
                "execName":execName,
                "fightTime":fightTime,
                "fthd":$("#txt_fthd").val(),
                "loginName":loginName,
                "userName":userName,
                "password":password,
                "comment":comment,
                "curDate":$("#txt_cur_date").val(),
                "serialNo":$("#txt_serial_no").val(),
                "ids":idArr.join(","),
                "names":nameArr.join(","),
                "pids":pidArr.join(","),
                "levels":levelArr.join(",")
            };
            //解绑按钮事件
            $("#btn_next").off("click");
            $.ajax({
                type:"POST",
                url:ctx+"/exec/saveStepOne",
                data:param,
                cache:false,
                success:function(r){
                    $("#btn_next").on("click",a.execStepOne.saveStepOne);
                    if(r != null && r.indexOf("|") > -1){
                        var eId = r.substring(r.indexOf('|') + 1);
                        window.location.href = ctx + "/exec/addStepTwo?id="+eId;
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    $("#btn_next").on("click",a.execStepOne.saveStepOne);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }
    },
    a.execStepTwo = {
        treeObj:{},
        init: function () {
            a.execStepTwo.treeObj = $.fn.zTree.init($("#ztree"),setting,zTreeNodes);
            $("#chk_de").on("click",a.execStepTwo.chkAll);
            $("#tbody_de_list input:checkbox").on("click",a.execStepTwo.chk);
            $("#btn_next").on("click",a.execStepTwo.saveStepTwo);
            console.log(typeof unitDepartment);
            /*$("#tbody_de_list tr td:nth-child(1) input:checkbox").click(function(e){
                e.stopPropagation();
            });
            $("#tbody_de_list tr").click(function(){
                var chkObj = $(this).find("td:nth-child(1)").find("input:checkbox");
                if(chkObj.is(":checked")){
                    chkObj.prop("checked",false);
                }else{
                    chkObj.prop("checked",true);
                }
            });*/
        },
        zTreeOnClick:function(event, treeId, treeNode) {
            var unitId = treeNode.id.substring(4);
            var arrStr = unitDepartment[unitId];
            $("#tbody_de_list input:checkbox").each(function () {
                $(this).prop("checked",false);
            });
            if(arrStr != null && typeof arrStr != "undefined"){
                var arr = arrStr.split(",");
                for(var i=0;i<arr.length;i++){
                    $("#tbody_de_list input:checkbox").each(function () {
                        if($(this).data("id") == arr[i]){
                            $(this).prop("checked",true);
                        }
                    });
                }
            }
        },
        chkAll:function(){
            var nodes = a.execStepTwo.treeObj.getSelectedNodes();
            if(nodes.length == 0) {
                if ($(this).is(":checked")) {
                    $("#tbody_de_list input:checkbox").prop("checked", true);
                }else{
                    $("#tbody_de_list input:checkbox").prop("checked", false);
                }
                return;
            }
            var node = nodes[0];
            var nodeId = node.id;
            nodeId = nodeId.substring(4);
            if($(this).is(":checked")){
                $("#tbody_de_list input:checkbox").prop("checked",true);
                var deIds = "";
                $("#tbody_de_list input:checkbox").each(function(){
                    deIds+= $(this).data("id")+",";
                });
                if(deIds != ""){
                    deIds = deIds.substring(0,deIds.length-1);
                    unitDepartment[nodeId] = deIds;
                }
            }else{
                $("#tbody_de_list input:checkbox").prop("checked",false);
                delete unitDepartment[nodeId];
            }
        },
        chk:function () {
            var nodes = a.execStepTwo.treeObj.getSelectedNodes();
            if(nodes.length == 0) {
                return;
            }
            var node = nodes[0];
            var nodeId = node.id;
            nodeId = nodeId.substring(4);
            var deIds = "";
            $("#tbody_de_list input:checkbox:checked").each(function(){
                deIds+= $(this).data("id")+",";
            });
            if(deIds != ""){
                deIds = deIds.substring(0,deIds.length-1);
                unitDepartment[nodeId] = deIds;
            }
        },
        saveStepTwo:function () {
            var length = 0;
            for (var item in unitDepartment) {
                length++;
            }
            if(length == 0){
                layer.msg("请为每个单位分配台位",{icon: 2,time:1000});
                return false;
            }
            //组装参数
            var eId
            var param = {
                "id":$("#txt_id").val(),
                "unitDepartment":JSON.stringify(unitDepartment)
            };
            //解绑按钮事件
            $("#btn_next").off("click");
            $.ajax({
                type:"POST",
                url:ctx+"/exec/saveStepTwo",
                data:param,
                cache:false,
                success:function(r){
                    $("#btn_next").on("click",a.execStepTwo.saveStepTwo);
                    if(r != null && r =="success"){
                        window.location.href = ctx + "/exec/addStepThree?id="+$("#txt_id").val();
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function() {
                    $("#btn_next").on("click",a.execStepTwo.saveStepTwo);
                    layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }
    },
    a.execStepThree = {
        init: function () {
            a.execStepThree.loadUsers();
        },
        loadUsers:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "id":$("#txt_id").val()
            };
            $.post(ctx+"/exec/queryExecUserCount",param,function(page){
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
                        $("#div_list").load(ctx+"/exec/queryExecUserPage",param,function(){
                            $("#tbody_list tr td:nth-child(7) a:nth-child(1)").click(a.execStepThree.modifyUserClick);
                            /*$("#tbody_list tr td:nth-child(7) a:nth-child(2)").click(a.execStepThree.deleteUserClick);*/
                        });
                    }
                });
            });
        },
        modifyUserClick:function(){
            var btnObj = $(this);
            var trObj = btnObj.parent().parent();
            layer.open({
                type: 1,
                title:["编辑用户","font-weight:bold;"],
                area:['400px', '330px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var ln = trim($("#txt_login_name").val());
                    if(ln == ""){
                        layer.msg("请输入登录名",{icon:2,time:1000});
                        return false;
                    }
                    var un = trim($("#txt_user_name").val());
                    if(un == ""){
                        layer.msg("请输入用户名",{icon:2,time:1000});
                        return false;
                    }
                    var pa = trim($("#txt_password").val());
                    if(pa == ""){
                        layer.msg("请输入密码",{icon:2,time:1000});
                        return false;
                    }
                    var cu = 0;
                    if($("#chk_cu").is(":checked")){
                        cu = 1;
                    }
                    var param = {
                        "id":btnObj.data("id"),
                        "ln":ln,
                        "un":un,
                        "pa":pa,
                        "cu":cu
                    };
                    $.post(ctx + "/exec/modifyExeciseUser",param,function(msg){
                        if (msg != null && msg == "success") {
                            trObj.find("td:nth-child(3)").text(ln);
                            trObj.find("td:nth-child(4)").text(un);
                            trObj.find("td:nth-child(5)").text(pa);
                            if($("#chk_cu").is(":checked")){
                                trObj.find("td:nth-child(6) input:checkbox").prop("checked",true);
                            }else{
                                trObj.find("td:nth-child(6) input:checkbox").prop("checked",false);
                            }
                            layer.close(index);
                            layer.msg("操作成功", {icon: 1, time: 1000});
                        } else {
                            layer.msg(msg, {icon: 2, time: 1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#div_user_info"),
                zIndex:100,
                success: function(layero, index){
                    //密码文本框只允许输入数字
                    WEBGIS.Helper.onlyNumber($("#txt_password"));
                    $("#txt_login_name").val(trObj.find("td:nth-child(3)").text());
                    $("#txt_user_name").val(trObj.find("td:nth-child(4)").text());
                    $("#txt_password").val(trObj.find("td:nth-child(5)").text());
                    if(trObj.find("td:nth-child(6) input:checked").is(":checked")){
                        $("#chk_cu").prop("checked",true);
                    }
                },
                end:function(){
                    $("#div_user_info input:text").val("");
                    $("#div_user_info input:checkbox").prop("checked",false);
                }
            });
        }
        /*tdModify: function () {
            $(this).unbind("click",a.execStepThree.tdModify);
            //打开输入框
            var tdObj = $(this);
            var inputObj = $("<input type=\"text\" style=\"width:100%;height:100%;border:none;\"/>").clone();
            var val = tdObj.text();
            inputObj.val(val);
            inputObj.attr("id","inputTmp");
            tdObj.text("");
            tdObj.append(inputObj);
            inputObj.focus();
            inputObj.blur(function(){
                a.execStepThree.inputBlur(tdObj);
            });
        },
        inputBlur:function(tdObj){
            var inputObj = tdObj.find("input");
            var val = $.trim(inputObj.val());
            if(trim(val) == ""){
                layer.msg("用户名不能为空！",{icon: 2,time: 1000});
                $("#inputTmp").focus();
            }else{
                inputObj.remove();
                tdObj.text(val);
                var param = {
                    "id":tdObj.data("id"),
                    "un":val
                };
                $.ajax({
                    type:"POST",
                    url:ctx+"/exec/modifyExeciseUser",
                    data:param,
                    cache:false,
                    success:function(r){
                        tdObj.bind("click",a.execStepThree.tdModify);
                    },
                    error:function(){
                        tdObj.bind("click",a.execStepThree.tdModify);
                        layer.msg("保存失败,请联系管理员！",{icon: 2,time:1000});
                    }
                });
            }
        },
        checkboxClick:function(){
            var param = {
                "id":$(this).data("id")
            };
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            if($(this).is(":checked")){
                param[$(this).data("type")] = "1";

            }else{
                param[$(this).data("type")] = "0";
            }
            $.ajax({
                type:"POST",
                url:ctx+"/exec/modifyExeciseUser",
                data:param,
                cache:false,
                success:function(r){
                    layer.close(index);
                },
                error:function(){
                    layer.close(index);
                    layer.msg("保存失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }*/
    },
    a.execStepFour = {
        init: function () {
            $("#tbody_list tr").click(a.execStepFour.trClick);
            $("#btn_save").on("click",a.execStepFour.btnSaveClick);
        },
        trClick:function(){
            $("#tbody_list input:radio").prop("checked",false);
            $(this).find("input:radio").prop("checked",true);
        },
        btnSaveClick:function(){
            if($("#tbody_list input:radio:checked").length == 0){
                layer.msg("请选择海图！",{icon: 2,time:1000});
                return false;
            }
            var param = {
                "id":$("#txt_id").val(),
                "scId":$("#tbody_list input:radio:checked").data("id")
            };
            $("#btn_save").off("click");
            $.ajax({
                type:"POST",
                url:ctx+"/exec/modifyExeciseSeaChart",
                data:param,
                cache:false,
                success:function(r){
                    $("#btn_save").on("click",a.execStepFour.btnSaveClick);
                    if(r != null && r == "success"){
                        window.location.href = ctx + "/exec/addStepFive?id="+$("#txt_id").val();
                    }else{
                        layer.msg(r,{icon: 2,time:1000});
                    }
                },
                error:function(){
                    $("#btn_save").on("click",a.execStepFour.btnSaveClick);
                    layer.msg("保存失败,请联系管理员！",{icon: 2,time:1000});
                }
            });
        }
    },
    a.execStepFive = {
        unit_treeObj:{},
        init: function () {
            $("#btn_troop_import").on("click",a.execStepFive.troopImport);
            $("#sel_option").change(a.execStepFive.chooseIcon);
            a.execStepFive.btnSearchClick();
        },
        btnSearchClick:function(){
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "id":$("#txt_id").val()
            };
            $.post(ctx+"/exec/queryExeciseTroopCount",param,function(page){
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
                        $("#div_list").load(ctx+"/exec/queryExeciseTroopPage",param,function(){
                            $("#tbody_list tr").on("click",function(){
                                $("#tbody_list tr").removeClass("selected");
                                $(this).addClass("selected");
                            });
                            $("#tbody_list tr td:nth-child(10) a:nth-child(2)").on("click",a.execStepFive.editTroop);
                            $("#tbody_list tr td:nth-child(10) a:nth-child(1)").on("click",a.execStepFive.deleteTroop);
                        });
                    }
                });
            });
        },
        troopImport:function(){
            layer.open({
                type: 1,
                title:["选择兵力模板","font-weight:bold;"],
                area:['300px','200px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var id = $("#sel_troop_import").val();
                    if(id == ""){
                        layer.msg("请选择模板",{"icon":2,"time":1000});
                        return false;
                    }
                    $.post(ctx+"/exec/troopImport",{"id":id,"cid":$("#txt_id").val()},function(r){
                        if(r!=null&&r=="success"){
                            layer.msg("操作成功！",{icon:1,time:1000});
                            layer.close(index);
                            WEBGIS.execStepFive.btnSearchClick();
                        }else{
                            layer.msg("保存失败！",{icon:2,time:1000});
                        }
                    });
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#div_troop_import"),
                zIndex:100,
                success: function(layero, index){

                },
                end:function(){
                    $("#sel_troop_import").val("");
                }
            });
        },
        chooseIcon:function(){
            var id = $("#sel_option").val();
            if(id == ""){
                return;
            }
            $("#div_choose_icon").load(ctx+"/exec/chooseIcon",{"id":id},function(){
                layer.open({
                    type: 1,
                    title:["选择图标","font-weight:bold;"],
                    area:['500px','400px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        if($("#div_icon_list > div.selected").length == 0){
                            layer.msg("请选择图标！",{icon: 2,time:1000});
                            return false;
                        }
                        WEBGIS.execStepFiveChooseIcon.iconTmp.iconID = $("#div_icon_list > div.selected").eq(0).data("icon");
                        WEBGIS.execStepFiveChooseIcon.iconTmp.iconPath = $("#div_icon_list > div.selected").eq(0).find("img").attr("src");
                        WEBGIS.execStepFiveChooseIcon.iconTmp.iconData = $("#div_icon_list > div.selected").eq(0).find("img").attr("icon-data");
                        layer.close(index);
                        WEBGIS.execStepFiveChooseIcon.addIcon();
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_choose_icon"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_choose_icon").html("");
                        $("#sel_option").val("");
                    }
                });
            });
        },
        editTroop:function(){
            var troopId = $(this).data("id");
            var execId = $("#txt_id").val();
            if(typeof troopId == "undefined"){
                layer.msg("数据异常！",{icon: 2,time: 1000});
                return false;
            }
            $("#div_choose_icon").load(ctx+"/exec/editTroopWin",{troopId:troopId},function(){
                $("#txt_longitude").val(tmpTroop.longitude==null?"":tmpTroop.longitude);
                $("#txt_latitude").val(tmpTroop.latitude==null?"":tmpTroop.latitude);

                if(tmpTroop.mainType == 0 || tmpTroop.mainType == 1){
                    $("#div_speed input").val();
                    $("#txt_move_speed").val(tmpTroop.speed==null?0:tmpTroop.speed);
                    $("#txt_move_angle").val(tmpTroop.moveAngle==null?"":tmpTroop.moveAngle);
                    $("#lab_speed_unit").html("海里/时");
                    if(tmpTroop.mainType == 1){
                        $("#lab_speed_unit").html("公里/时");
                    }
                    $("#div_speed").show();
                }
                //图标权限
                $("#div_add_icon_right").load(ctx+"/exec/iconRightSetting",{"execId":execId},function(){
                    a.execStepFive.unit_treeObj = $.fn.zTree.init($("#unit_ztree"),unit_setting,unit_zTreeNodes);
                    a.execStepFive.unit_treeObj.expandAll(true);
                    $("#div_add_icon_right").parent().show();
                });
                layer.open({
                    type: 1,
                    title:["修改兵力","font-weight:bold;"],
                    area:['500px','400px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        var param = {
                            troopId:null,
                            speed:null,
                            moveAngle:null,
                            longitude:null,
                            latitude:null,
                            unit:null
                        };
                        param.troopId = tmpTroop.id;
                        var lot = $.trim($("#txt_longitude").val());
                        if(lot == ""){
                            layer.msg("请输入经度",{"icon":2,"time":1000});
                            return false;
                        }
                        var lat = $.trim($("#txt_latitude").val());
                        if(lat == ""){
                            layer.msg("请输入纬度",{"icon":2,"time":1000});
                            return false;
                        }
                        if(!/^\d{1,3},{0,1}\d{0,3},{0,1}\d{0,3}\.{0,1}\d*$/.test(lot)){
                            layer.msg("经度请输入时,分,秒的格式",{"icon":2,"time":1000});
                            return false;
                        }
                        if(!/^\d{1,3},{0,1}\d{0,3},{0,1}\d{0,3}\.{0,1}\d*$/.test(lat)){
                            layer.msg("纬度只允许输入数字或小数",{"icon":2,"time":1000});
                            return false;
                        }
                        param.latitude = lat;
                        param.longitude = lot;

                        /*if(!/^\d+\.{0,1}\d*$/.test(sp)){
                            layer.msg("速度只允许输入数字或小数",{"icon":2,"time":1000});
                            return false;
                        }*/
                        if($("#div_speed").is(":visible")){
                            var moveSpeed = $.trim($("#txt_move_speed").val());
                            if(moveSpeed == ""){
                                layer.msg("请输入速度",{"icon":2,"time":1000});
                                return false;
                            }
                            if(isNaN(moveSpeed)){
                                layer.msg("速度请输入数值！",{icon:2,time:1000});
                                return false;
                            }
                            if(tmpTroop.maxSpeed!=null && tmpTroop.maxSpeed!=""&&(Number(moveSpeed)> tmpTroop.maxSpeed||Number(moveSpeed)<0)){
                                layer.msg("速度最大值为"+ tmpTroop.maxSpeed+",最小值为0,请输入合法速度",{icon:2,time:1000});
                                return false;
                            }
                            var moveAngle =trim($("#txt_move_angle").val());
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
                        //权限数据
                        var nodes = a.execStepFive.unit_treeObj.getNodes();
                        if(nodes!=null&&nodes.length>0){
                            for(var i=0;i<nodes.length;i++){
                                a.execStepFive.unit_treeObj.setChkDisabled(nodes[i], false,true,true);
                            }
                        }
                        var selectedNodes=a.execStepFive.unit_treeObj.getCheckedNodes(true);
                        if(nodes!=null&&nodes.length>0){
                            for(var i=0;i<nodes.length;i++){
                                a.execStepFive.unit_treeObj.setChkDisabled(nodes[i], true,true,true);
                            }
                        }
                        if(selectedNodes!=null&&selectedNodes.length>0){
                            param.unit=selectedNodes[0].id;
                        }
                        if(param.unit == null){
                            layer.msg("请选择权限",{icon:2,time:1000});
                            return;
                        }
                        $.post(ctx+"/exec/editTroop",{jsonString:JSON.stringify(param)},function(r){
                            if(r!=null&&r=="success"){
                                layer.msg("保存成功！",{icon:1,time:1000});
                                layer.close(index);
                                WEBGIS.execStepFive.btnSearchClick();
                            }else{
                                layer.msg("保存失败！",{icon:2,time:1000});
                            }
                        });
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_choose_icon"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_choose_icon").html("");
                    }
                });
            });
        },
        deleteTroop:function(){
            var troopId = $(this).data("id");
            if(typeof troopId == "undefined"){
                layer.msg("数据异常！",{icon: 2,time: 1000});
                return false;
            }
            var obj = $(this);
            layer.confirm("确认要删除此项兵力么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            },function(index, layero){
                //解绑按钮事件
                obj.off("click");
                $.ajax({
                    type:"POST",
                    url:ctx+"/exec/deleteTroop",
                    data:{"troopId":troopId},
                    cache:false,
                    success:function(r){
                        layer.close(index);
                        obj.on("click",a.execStepFive.deleteTroop);
                        if(r != null && r == "success"){
                            a.execStepFive.btnSearchClick();
                        }else{
                            layer.msg(r,{icon: 2,time:1000});
                        }
                    },
                    error:function() {
                        layer.close(index);
                        obj.on("click",a.execStepFive.deleteTroop);
                        layer.msg("操作失败,请联系管理员！",{icon: 2,time:1000});
                    }
                });
            },function(index){
            });
        }
    },
    a.execStepFiveChooseIcon = {
        treeObj:{},
        unit_treeObj:{},
        iconTmp:{
            iconID:null,
            iconPath:null,
            iconData:null,
            baseInfo:null,
            baseInfoName:null,
            mainType:null,
            maxSpeed:null,
            speed:null,
            moveAngle:null,
            colorArray:"[]",
            planeAmmu:{}
        },
        init:function(){
            a.execStepFiveChooseIcon.treeObj = $.fn.zTree.init($("#ul_icon_ztree"),setting,zTreeNodes);
        },
        zTreeOnClick:function(event, treeId, treeNode){
            var idArray = new Array();
            idArray.push(treeNode.id);
            var children = treeNode.children;
            a.execStepFiveChooseIcon.buildChildrenID(idArray,children);
            //开始查询
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "groupArray":idArray==null?"":idArray.join(",")
            };
            $.post(ctx+"/iconManage/queryIconListCountForExec",param,function(page){
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
                        $("#div_icon_list").load(ctx+"/iconManage/queryIconListPageForExec",param,function(){
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
        buildChildrenID:function(idArray,children){
            if(children==null||children.length<=0){
                return;
            }
            for(var i=0;i<children.length;i++){
                var node=children[i];
                idArray.push(node.id);
                var tmpChildren = node.children;
                a.execStepFiveChooseIcon.buildChildrenID(idArray,tmpChildren);
            }
        },
        addIcon:function(){
            $("#div_choose_icon").load(ctx+"/exec/addIcon",{iconId:a.execStepFiveChooseIcon.iconTmp.iconID},function(){
                $("#tbody_bi_list tr td:nth-child(1) input:radio").click(function(e){
                    a.execStepFiveChooseIcon.resetValue();
                    a.execStepFiveChooseIcon.iconTmp.baseInfo = $(this).data("bi");
                    a.execStepFiveChooseIcon.iconTmp.baseInfoName = $(this).data("bin");
                    a.execStepFiveChooseIcon.iconTmp.mainType = $(this).data("mt");
                    a.execStepFiveChooseIcon.iconTmp.maxSpeed = $(this).data("ms");
                    a.execStepFiveChooseIcon.baseInfoClick();
                    e.stopPropagation();
                });
                $("#tbody_bi_list tr").click(function(){
                    var chkObj = $(this).find("td:nth-child(1)").find("input:radio");
                    if(!chkObj.is(":checked")){
                        chkObj.prop("checked",true);
                        a.execStepFiveChooseIcon.resetValue();
                        a.execStepFiveChooseIcon.iconTmp.baseInfo = $(this).data("bi");
                        a.execStepFiveChooseIcon.iconTmp.baseInfoName = $(this).data("bin");
                        a.execStepFiveChooseIcon.iconTmp.mainType = $(this).data("mt");
                        a.execStepFiveChooseIcon.iconTmp.maxSpeed = $(this).data("ms");
                        a.execStepFiveChooseIcon.baseInfoClick();
                    }
                });
                layer.open({
                    type: 1,
                    title:["添加图标","font-weight:bold;"],
                    area:['500px','400px'],
                    closeBtn: 1,                //不显示关闭按钮
                    btn: ['确认', '取消'],
                    yes: function(index, layero){
                        a.execStepFiveChooseIcon.saveIcon(index);
                    },
                    shift: 0,
                    shadeClose: false,           //开启遮罩关闭
                    content: $("#div_choose_icon"),
                    zIndex:100,
                    success: function(layero, index){

                    },
                    end:function(){
                        $("#div_choose_icon").html("");
                        a.execStepFiveChooseIcon.resetValue();
                        a.execStepFiveChooseIcon.iconTmp.iconID = null;
                        a.execStepFiveChooseIcon.iconTmp.iconPath = null;
                        a.execStepFiveChooseIcon.iconTmp.iconData = null;
                    }
                });
            });
        },
        baseInfoClick:function(){
            $("#div_add_icon_attr").hide();
            $("#div_add_icon_ammu").parent().hide();
            $("#div_add_icon_equip").parent().hide();
            $("#div_add_icon_right").parent().hide();
            //图标属性
            $("#txt_icon_name").val(a.execStepFiveChooseIcon.iconTmp.baseInfoName);
            //图标颜色
            $.imgspectrum.init({container:$("#img_canvas"),imgSrc:a.execStepFiveChooseIcon.iconTmp.iconPath});
            $("#div_add_icon_attr").show();
            //图标关联属性
            if(a.execStepFiveChooseIcon.iconTmp.mainType>=0 && a.execStepFiveChooseIcon.iconTmp.mainType<=3){
                //雷弹: 舰船(0)、飞机(1)、岸导(2)、仓储机构(3)
                $("#div_add_icon_ammu").load(ctx+"/exec/iconAmmunitionSetting",{"biId":a.execStepFiveChooseIcon.iconTmp.baseInfo},function(){
                    $("#div_add_icon_ammu").parent().show();
                    $('#table_ammunition_list .spinnerInput').spinner({});
                });
            }
            if(a.execStepFiveChooseIcon.iconTmp.mainType==3 || a.execStepFiveChooseIcon.iconTmp.mainType==4){
                //器材: 仓储机构(3)、修理机构(4)
                $("#div_add_icon_equip").load(ctx+"/exec/iconEquipmentSetting",{"biId":a.execStepFiveChooseIcon.iconTmp.baseInfo},function(){
                    $("#div_add_icon_equip").parent().show();
                    $('#table_equipment_list .spinnerInput').spinner({});
                });
            }
            if(a.execStepFiveChooseIcon.iconTmp.mainType == 6){
                //特殊: 机场(7)
                $("#div_plane").load(ctx+"/exec/iconPlaneSetting",{"iconId":a.execStepFiveChooseIcon.iconTmp.iconID,"execId":$("#txt_id").val()},function(){
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
                        a.execStepFiveChooseIcon.iconTmp.planeAmmu[prevBiId] = ammu;
                        var bi = $(this).data("bi");
                        $("#div_plane_ammu").load(ctx+"/exec/iconAmmunitionSetting",{"biId":bi,"isPl":"1"},function(){
                            $('#tbody_plane_ammu_list .spinnerInput').spinner({});
                            var tmpData = a.execStepFiveChooseIcon.iconTmp.planeAmmu[bi];
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
        },
        saveIcon:function(ind){
            if($("#tbody_bi_list tr td:nth-child(1) input:radio:checked").size() == 0){
                layer.msg("选择图标型号",{"icon":2,"time":1000});
                return false;
            }
            if($.trim($("#txt_icon_name").val()) == ""){
                layer.msg("请输入图标名称",{"icon":2,"time":1000});
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
                planeAmmu:null,
                exec_id:""
            };
            param.exec_id = $("#txt_id").val();
            param.iconID = a.execStepFiveChooseIcon.iconTmp.iconID;
            param.iconPath = a.execStepFiveChooseIcon.iconTmp.iconPath;
            param.iconData = a.execStepFiveChooseIcon.iconTmp.iconData;
            param.baseInfo = a.execStepFiveChooseIcon.iconTmp.baseInfo;
            param.mainType = a.execStepFiveChooseIcon.iconTmp.mainType;
            param.maxSpeed = a.execStepFiveChooseIcon.iconTmp.maxSpeed;

            //图标数据
            var iconName = trim($("#txt_icon_name").val());
            if(iconName == ""){
                layer.msg("请输入图标名称",{"icon":2,"time":1000});
                return false;
            }
            param.name = iconName;
            param.colorArray = a.execStepFiveChooseIcon.iconTmp.colorArray;
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
                /*if($("#tbody_plane_list tr td:nth-child(1) input:checked").size == 0){
                    layer.msg("请选择飞机",{icon:2,time:1000});
                    return false;
                }*/
                var prevBiId = $("#tbody_plane_ammu_list").data("biid");
                var ammu = {};
                $("#tbody_plane_ammu_list tr td:nth-child(3)").each(function(){
                    var inputObj = $(this).find("input:text");
                    if(inputObj.val() != "" && inputObj.val() != "0"){
                        ammu[inputObj.data("id")] = {"na":inputObj.data("name"),"co":inputObj.val()};
                    }
                });
                a.execStepFiveChooseIcon.iconTmp.planeAmmu[prevBiId] = ammu;
                var tmpJson = {};
                $("#tbody_plane_list tr td:nth-child(1) input:checked").each(function(){
                    var tmpId = $(this).data("bi");
                    tmpJson[tmpId] = a.execStepFiveChooseIcon.iconTmp.planeAmmu[tmpId];
                });
                param.planeAmmu = JSON.stringify(tmpJson);

            }
            a.execStepFiveChooseIcon.iconTmp.colorArray=JSON.stringify($.imgspectrum.recalColorJson());
            param.colorArray=a.execStepFiveChooseIcon.iconTmp.colorArray;
            //开始保存
            $.post(ctx+"/exec/saveExecIcon",{jsonString:JSON.stringify(param)},function(r){
                if(r!=null&&r=="success"){
                    layer.msg("保存成功！",{icon:1,time:1000});
                    layer.close(ind);
                    WEBGIS.execStepFive.btnSearchClick();
                }else{
                    layer.msg("保存失败！",{icon:2,time:1000});
                }
            });
        },
        resetValue:function(){
            /*a.execStepFiveChooseIcon.iconTmp.iconID = null;
            a.execStepFiveChooseIcon.iconTmp.iconPath = null;
            a.execStepFiveChooseIcon.iconTmp.iconData = null;*/
            a.execStepFiveChooseIcon.iconTmp.baseInfo = null;
            a.execStepFiveChooseIcon.iconTmp.baseInfoName = null;
            a.execStepFiveChooseIcon.iconTmp.mainType = null;
            a.execStepFiveChooseIcon.iconTmp.maxSpeed = null;
            a.execStepFiveChooseIcon.iconTmp.speed = null;
            a.execStepFiveChooseIcon.iconTmp.moveAngle = null;
            a.execStepFiveChooseIcon.iconTmp.colorArray = "[]";
            a.execStepFiveChooseIcon.iconTmp.planeAmmu = {};
        }
    },
    a.execUnitUser = {
        treeObj:{},
        init:function(){
            a.execUnitUser.treeObj = $.fn.zTree.init($("#ztree"),setting,zTreeNodes);
            a.execUnitUser.loadUsers();
        },
        zTreeOnClick: function (event, treeId, treeNode) {
            var idArray=new Array();
            idArray.push(treeNode.id.substring(4));
            var children=treeNode.children;
            a.execUnitUser.buildChildrenID(idArray,children);
            a.execUnitUser.loadUsers(idArray.join(","));
        },
        buildChildrenID:function(idArray,children){
            if(children==null||children.length<=0){
                return;
            }
            for(var i=0;i<children.length;i++){
                var node=children[i];
                idArray.push(node.id.substring(4));
                tmpChildren=node.children;
                a.execUnitUser.buildChildrenID(idArray,tmpChildren);
            }
        },
        loadUsers:function(unitArray){
            var param = {
                "pageNo":"1",
                "pageSize":"10",
                "id":$("#txt_id").val(),
                "unitArray":unitArray==null?"":unitArray
            };
            $.post(ctx+"/exec/queryExecUnitUserCount",param,function(page){
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
                        $("#div_list").load(ctx+"/exec/queryExecUnitUserPage",param);
                    }
                });
            });
        }
    },
    a.execReview = {
        execftInterval:null,
        init: function(){
            a.execReview.initmap("5",$("#div_fight_date").text(),$("#txt_step_length").text());
            $("#btn_begin_review").on("click",a.execReview.beginReview);
            WEBGIS.Helper.onlyNumber($("#txt_step"));
            $("#btn_save_step_length").on("click", a.execReview.btnStepSaveClick);
            $("#btn_fight_time").on("click", a.execReview.btnFightTimeClick);
        },
        initmap: function(execsta,currentExecft,speedRatio){
            var param = {
                map: {
                    url: geoserver + "/" + "cite/wms",
                    layer: "cite:test1",
                    bound: "0.0,-5.684E-13,115.39583333333371,169.64583333333331",
                    num:1,
                    resolution: null
                },
                target: 'div_map',
                popup: 'div_popup'
            };
            //初始化地图
            WEBGIS.map.execsta = execsta;
            WEBGIS.map.currentExecft = currentExecft;
            WEBGIS.map.speedRatio = speedRatio;
            WEBGIS.map.drag.needDrag = false;
            WEBGIS.map.show(param);
            //初始化测量工具
            WEBGIS.map.initMeasure();
            //初始化运动
            WEBGIS.map.initMoveForReview();
            //加载地图上的指标
            if(iconData.length > 0){
                for(var i=0;i<iconData.length;i++){
                    if(typeof iconData[i].newestCoordinate == "undefined" || iconData[i].newestCoordinate == null){
                        //还在机场内部的飞机
                        continue;
                    }
                    var www = iconData[i].newestCoordinate.split(",");
                    var ccc = new Array();
                    ccc.push(parseFloat(www[0]));
                    ccc.push(parseFloat(www[1]));
                    if(iconData[i].iconType == "0"){
                        var icon = {
                            "id":iconData[i].id,
                            "startPoint":ccc,
                            "marker":visitpath + iconData[i].iconData,
                            "iconId":iconData[i].iconId,
                            "iconName":iconData[i].iconName,
                            "feature_is_icon":true,
                            "colorArray":iconData[i].colorArray,
                            "speed":iconData[i].speed,
                            "speed_unit":iconData[i].speedUnit,
                            "unit_id":iconData[i].unitId,
                            "baseInfo":iconData[i].baseInfoId,
                            "mainType":iconData[i].mainType,
                            "max_speed":iconData[i].maxSpeed,
                            "damage":iconData[i].damage,
                            "damageDetail":iconData[i].damageDetail,
                            "damageTime":iconData[i].damageTime,
                            "damageCont":iconData[i].damageCont,
                            "damageDetailCont":iconData[i].damageDetailCont,
                            "crowdId":iconData[i].crowdId,
                            "crowdName":iconData[i].crowdName,
                            "isMain":iconData[i].isMain,
                            "belongAirport":iconData[i].belongAirport,
                            "crowdDetailCont":iconData[i].crowdDetailCont,
                            "isCrowdShow":"0"
                        };
                        WEBGIS.map.markerByxxx(icon);
                    }else if(iconData[i].iconType == "1"){
                        var json = {
                            "id":iconData[i].id,
                            "startPoint":ccc,
                            "iconText":iconData[i].iconName
                        };
                        WEBGIS.map.markerText(json);
                    }
                }
            }
        },
        /*initmap: function(execsta,currentExecft,speedRatio){
            $.ajax({
                url: ctx + '/smap/execMap',
                type: 'post',
                data: {"execId":$("#txt_exec_id").val()},
                dataType: 'json',
                success: function (val) {
                    if (val.code != 0) {
                        layer.msg("海图装载失败！", {icon: 2, time: 1000});
                        return;
                    }
                    var param = {
                        map: {
                            url: geoserver + "/" + val.result.url,
                            layer: val.result.layer,
                            bound: val.result.bound,
                            num:val.result.layerNum,
                            resolution: val.result.resolution
                        },
                        target: 'div_map',
                        popup: 'div_popup'
                    };
                    //初始化地图
                    WEBGIS.map.execsta = execsta;
                    WEBGIS.map.currentExecft = currentExecft;
                    WEBGIS.map.speedRatio = speedRatio;
                    WEBGIS.map.drag.needDrag = false;
                    WEBGIS.map.show(param);
                    //初始化测量工具
                    WEBGIS.map.initMeasure();
                    //初始化运动
                    WEBGIS.map.initMoveForReview();
                    //加载地图上的指标
                    if(iconData.length > 0){
                        for(var i=0;i<iconData.length;i++){
                            if(typeof iconData[i].newestCoordinate == "undefined" || iconData[i].newestCoordinate == null){
                                //还在机场内部的飞机
                                continue;
                            }
                            var www = iconData[i].newestCoordinate.split(",");
                            var ccc = new Array();
                            ccc.push(parseFloat(www[0]));
                            ccc.push(parseFloat(www[1]));
                            if(iconData[i].iconType == "0"){
                                var icon = {
                                    "id":iconData[i].id,
                                    "startPoint":ccc,
                                    "marker":visitpath + iconData[i].iconData,
                                    "iconId":iconData[i].iconId,
                                    "iconName":iconData[i].iconName,
                                    "feature_is_icon":true,
                                    "colorArray":iconData[i].colorArray,
                                    "speed":iconData[i].speed,
                                    "speed_unit":iconData[i].speedUnit,
                                    "unit_id":iconData[i].unitId,
                                    "baseInfo":iconData[i].baseInfoId,
                                    "mainType":iconData[i].mainType,
                                    "max_speed":iconData[i].maxSpeed,
                                    "damage":iconData[i].damage,
                                    "damageDetail":iconData[i].damageDetail,
                                    "damageTime":iconData[i].damageTime,
                                    "damageCont":iconData[i].damageCont,
                                    "damageDetailCont":iconData[i].damageDetailCont,
                                    "crowdId":iconData[i].crowdId,
                                    "crowdName":iconData[i].crowdName,
                                    "isMain":iconData[i].isMain,
                                    "belongAirport":iconData[i].belongAirport,
                                    "crowdDetailCont":iconData[i].crowdDetailCont,
                                    "isCrowdShow":"0"
                                };
                                WEBGIS.map.markerByxxx(icon);
                            }else if(iconData[i].iconType == "1"){
                                var json = {
                                    "id":iconData[i].id,
                                    "startPoint":ccc,
                                    "iconText":iconData[i].iconName
                                };
                                WEBGIS.map.markerText(json);
                            }
                        }
                    }
                }
            });
        },*/
        beginReview:function(){
            $.ajax({
                url: ctx + '/sa/listOrder',
                data: {"execId":$("#txt_exec_id").val()},
                success: function (val) {
                    if (val.code != 0) {
                        return;
                    }
                    WEBGIS.map.appendOrderForReview(val.result);
                }
            });
            // 定时器开始跑,作战时间没秒增加1s*步长的值
            a.execReview.execftInterval = setInterval(function () {
                var step = $("#txt_step_length").html();
                execft = execft + parseInt(step)*1000;
                var aaa = formatDate(execft);
                $("#div_fight_date").html(aaa);
                a.execReview.addHistory();
                // 添加指令
                if(execft >= execfte){
                    // 超过了结束时间,停止
                    clearInterval(a.execReview.execftInterval);
                    WEBGIS.map.execsta = "10";
                }
            }, 1000);
            $("#btn_begin_review").off("click");
        },
        btnStepSaveClick: function () {
            layer.open({
                type: 1,
                title:["设置步长","font-weight:bold;"],
                area:['410px','250px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var st = trim($("#txt_step").val());
                    if(st == ""){
                        layer.msg("请输入步长", {icon: 2, time: 1000});
                        return false;
                    }
                    $("#txt_step_length").html(st);
                    layer.close(index);
                    layer.msg("修改成功", {icon: 1, time: 1000});
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#form_step"),
                zIndex:100,
                success: function(layero, index){},
                end:function(){
                    $("#txt_step").val("");
                }
            });
        },
        btnFightTimeClick:function(){
            var fbt = $("#div_fight_date").html();
            $("#txt_fight_time").val($("#div_fight_date").html());
            layer.open({
                type: 1,
                title:['设置作战时间','margin: 0;width: 410px;background: #bc9975;border: 1px solid #a58564;border-bottom: none;font-size: 14px;border-radius: 4px 4px 0 0;color:#fff;'],
                skin: 'layui-layer-lan',   //样式类名
                area:['410px','250px'],
                closeBtn: 1,                //不显示关闭按钮
                btn: ['确认', '取消'],
                yes: function(index, layero){
                    var dataStr = trim($("#txt_fight_time").val());
                    if(dataStr == ""){
                        layer.msg("请输入[作战时间]", {icon: 2, time: 1000});
                        return false;
                    }
                    var dataNow = dataStr.replace(/-/g,"/");
                    var dataNowTime = new Date(dataNow).getTime();
                    if(dataNowTime < execfts){
                        layer.msg("[作战时间]不能小于起始时间", {icon: 2, time: 1000});
                        $("#txt_fight_time").val(fbt);
                        return false;
                    }
                    if(dataNowTime > execfte){
                        layer.msg("[作战时间]不能大于结束时间", {icon: 2, time: 1000});
                        $("#txt_fight_time").val(fbt);
                        return false;
                    }
                    WEBGIS.map.execsta = "5";
                    $("#div_fight_date").html(dataStr);
                },
                shift: 0,
                shadeClose: false,           //开启遮罩关闭
                content: $("#form_fight_time"),
                zIndex:100,
                success: function(layero, index){
                },
                end:function(){
                    $("#txt_fight_time").val("");
                }
            });
        },
        addHistory:function(){
            var historyLogTep = "<div class=\"d-zone-record clearfix\"><div class=\"d-zone-record-date\">[aaa]</div>" +
                "<div class=\"d-zone-record-des\"><div class=\"d-round\"></div><div class=\"d-zone-record-des-info\"> <div class=\"d-tit\">操作内容:&nbsp;&nbsp;&nbsp;&nbsp;</div> " +
                "<div class=\"d-cont\">[bbb]</div> </div> </div> </div>";
            if(historyLogDtos.length > 0){
                for(var i=0;i<historyLogDtos.length;i++){
                    var hs = historyLogDtos[i];
                    if(hs.isShow == null  && hs.fightTime <= execft){
                        var tmp = historyLogTep;
                        tmp = tmp.replace("[aaa]",hs.fightTimeView);
                        tmp = tmp.replace("[bbb]",hs.content);
                        $("#div_list").append(tmp);
                        hs.isShow = "1";
                    }

                }
            }
        }
    }
})(WEBGIS);