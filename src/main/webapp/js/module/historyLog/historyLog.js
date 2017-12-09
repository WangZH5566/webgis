(function(a) {
	a.hl = {
		init: function (totalPage) {
			laypage({
				cont: 'div_for_page_btn', //容器。值支持id名、原生dom对象，jquery对象,
				pages: totalPage, //总页数
				groups: 0, //连续分数数0
				prev: false, //不显示上一页
				next: '<font color="black">查看更多</font>',
				skin: 'flow', //设置信息流模式的样式
				jump: function(obj){
					$.post(ctx+"/hl/historyLogList",{execId:$("#txt_execID").val(),pageNo:obj.curr},function(array){
						console.log(array);
						if(array!=null&&array.length>0){
							for(var i=0;i<array.length;i++){
								var json=array[i];
								var tmpTr=$("<tr></tr>").append(
									$("<td></td>").html(json.fightTimeView==null?"&nbsp;":json.fightTimeView)
								)/*.append(
									$("<td></td>").html(json.createUserName==null?"&nbsp;":json.createUserName)
								)*/.append(
									$("<td></td>").html(json.content==null?"&nbsp;":json.content)
								);
								$("#tbody_list").append(tmpTr);
							}
						}
					});
				}
			});
		}
	}
})(WEBGIS);