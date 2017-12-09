(function(a) {
	a.info = {
        treeObj:{},
        visitPath:null,
		init: function () {
            a.info.visitPath=$("#txt_fileVisitPath").val();
            $("#btn_add_root_node").on("click",a.info.addRootNode);
            $("#btn_upload_file").on("click",a.info.uploadFileOnclick);
            $("#btn_upload_save").on("click",a.info.save);
            a.info.refreshList();
            $("#div_file_detail").load(ctx+"/info/fileDetail",{id:0,isDirectory:1},function(){

            });
		},
        refreshList:function(){
            $.post(ctx+"/info/nodeList",{parent:null},function(array){
                a.info.treeObj = $.fn.zTree.init($("#ztree"), setting, array);
            })
        },
        zTreeOnClick: function (event, treeId, treeNode) {
            if(treeNode.isParent){
                $("#div_file_detail").load(ctx+"/info/fileDetail",{id:treeNode.id,isDirectory:1},function(){

                });
            }else{
                $("#div_file_detail").load(ctx+"/info/fileDetail",{id:treeNode.id,isDirectory:0},function(){

                });
            }
        },
        nodeExpand:function(event, treeId, treeNode){
            var children=treeNode.children;
            if(children!=null&&children.length>0){
                return true;
            }
            $.post(ctx+"/info/nodeList",{parent:treeNode.relativePath},function(newNodes){
                a.info.treeObj.addNodes(treeNode, newNodes);
            });
        },
        addRootNode:function(){
            layer.prompt({title: '请输入根节点名称', formType: 0}, function(text, index){
                $.post(ctx+"/info/saveDirectory",{name:text,pid:0,isDirectory:1},function(re){
                    layer.close(index);
                    if(re.msg=="SUCCESS"){
                        var json=re.result;
                        a.info.treeObj.addNodes(null, json);
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
                    var zTree = a.info.treeObj;
                    $.post(ctx+"/info/saveDirectory",{name:"目录",pid:treeNode.id,isDirectory:1},function(re){
                        if(re.msg=="SUCCESS"){
                            var json=re.result;
                            a.info.treeObj.addNodes(treeNode, json);
                        }else{
                            layer.msg(re.msg,{icon:2,time:1000});
                        }
                    });
                    return false;
                });
            }
        },
        beforeRemove:function(treeId, treeNode) {
            var zTree = a.info.treeObj;
            if(treeNode.children!=null&&treeNode.children.length>0){
                layer.msg("请先删除该分组下的子分组",{icon:2,time:1000});
                return false;
            }
            layer.confirm('确认删除目录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                layer.closeAll();
                $.post(ctx+"/info/deleteDataFile",{id:treeNode.id},function(re){
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
            $.post(ctx+"/info/saveDirectory",{id:treeNode.id,name:newName,pid:treeNode.pid},function(re){
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
            $.post(ctx+"/info/saveDirectory",{id:treeNodes[0].id,name:treeNodes[0].name,pid:pid},function(re){
                if(re.msg=="SUCCESS"){
                    return true;
                }else{
                    layer.msg("移动失败",{icon:2,time:1000});
                }
                return false;
            });
        },
        uploadFileOnclick:function(){
            var directory=$("#txt_current_directory").val();
            if(directory==null||directory==""){
                layer.msg("请先选择目录",{icon:2,time:1000});
                return;
            }
            $("#txt_upload_directory").val(directory);
            $("#form_upload_file #txt_name").val("");
            $("#form_upload_file #f_file").val("");
            layer.open({
                type: 1,
                area: ['500px', '250px'],
                fix: false, //不固定
                title: ["上传文件","font-weight:bold;"],
                content: $("#div_upload_form") //注意，如果str是object，那么需要字符拼接。
            });
        },
        save:function(){
            var json={
                name:$("#form_upload_file #txt_name").val(),
                pid:$("#form_upload_file #txt_upload_directory").val(),
                img:$("#form_upload_file #f_file").val()
            }
            var msg=a.info.validate(json);
            if(msg!=null){
                layer.msg(msg,{icon:2});
                return;
            }
            $("#btn_upload_save").unbind("click");
            $("#form_upload_file").ajaxSubmit({
                type: "POST",
                url:ctx+"/info/uploadFile",
                success: function(re){
                    if(re.msg=="SUCCESS"){
                        layer.closeAll();
                        layer.msg("上传成功",{icon:1,time:1000});
                        a.info.refreshList();
                    }else{
                        layer.msg(re.msg,{icon:2,time:1000});
                    }
                    $("#btn_upload_save").bind("click", a.info.save);
                }
            });
        },
        validate:function(json){
            if(json.pid==""){
                return "请选择文件夹后再上传";
            }
            if(json.name==""){
                return "请输入文件名称";
            }
            if(json.img==null||json.img==""){
                return "请选择文件";
            }
            return null;
        }
	}
})(WEBGIS);