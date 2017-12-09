(function(a){
    a.equipmentManage={
    	init:function(totalPage){
    		laypage({
    		    cont: $('#div_for_pagebtn'), //容器。值支持id名、原生dom对象，jquery对象,
    		    pages: totalPage, //总页数
    		    skip: true, //是否开启跳页
    		    skin: '#AF0000',
    		    groups: 3, //连续显示分页数
    		    jump:function(obj){
    		    	$("#tableList_div").load(ctx+"/equipmentManage/equipmentList",{pageNo:obj.curr});
    		    }
    		});
    	},
    	currentPageRefresh:function(){
    		$("#tableList_div").load(ctx+"/equipmentManage/equipmentList",{pageNo:$("#query_condition_form #current_page_no").val()},function(){

			});
    	},
        btn_add_equipment_onclick:function(){
        	$.post(ctx+'/equipmentManage/addPage', {}, function(str){
        		layer.open({
        		    type: 1,
                    area: ['400px', '250px'],
                    fix: false, //不固定
                    title: ["添加","font-weight:bold;"],
        		    content: str //注意，如果str是object，那么需要字符拼接。
        		  });
        	});
        },
        btn_edit_equipment_onclick:function(id){
        	$.post(ctx+'/equipmentManage/editPage/'+id, {}, function(str){
      		  layer.open({
	      		    type: 1,
	                area: ['400px', '250px'],
	                fix: false, //不固定
	                title: ["修改","font-weight:bold;"],
	      		    content: str //注意，如果str是object，那么需要字符拼接。
      		  		});
        	 });
        },
        btn_delete_equipment_onclick:function(id){
        	layer.confirm('确定要删除选中项？', {
        		  title: '删除提示',
        		  btn: ['确定','取消'] //按钮
        		}, function(){
        		  $.post(ctx+"/equipmentManage/delete",{id:id},function(data){
          			if(data!=""&&data!=null){
        				var index=data.indexOf("MSG|");
        				if(index==0){
        					layer.msg(data.substring(4),{icon:2});
        				}else{
        					layer.msg("删除成功",{icon:1});
        					a.equipmentManage.currentPageRefresh();
        				}
        				
        			}else{
        				layer.msg("删除失败",{icon:2});
        			}
        		  });
        		}, function(){

        		});
        }
    };
    a.equipmentEdit={
        init:function(){
            $("#btn_save").bind("click", a.equipmentEdit.save);
        },
        save:function(){
        	var json={
        		id:$("#id").val(),
        		name:$("#txt_name").val(),
				loadTime:$("#txt_loadTime").val()
        	};
        	var msg=a.equipmentEdit.validate(json);
        	if(msg!=null){
        		layer.msg(msg,{icon:2});
        		return;
        	}
			$("#btn_save").unbind("click");
            $.post(ctx+"/equipmentManage/save",json,function(data){
    			if(data!=""&&data!=null){
    				var index=data.indexOf("MSG|");
    				if(index==0){
    					layer.msg(data.substring(4),{icon:2});
    				}else{
    					layer.msg("保存成功",{icon:1});
						layer.closeAll();
    					a.equipmentManage.currentPageRefresh();
    				}
    				
    			}else{
    				layer.msg("保存失败",{icon:2});
    			}
				$("#btn_save").bind("click", a.equipmentEdit.save);
            });
        },
        validate:function(json){
        	if(json.name==""){
        		return "请输入装备名称"
        	}
        	return null;
        }
    }
})(WEBGIS);