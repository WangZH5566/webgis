/**
 * 滚动条对象
 */
var PB = PB || {};
/**
 * 滚动条对象方法
 */
PB.Method = {

	/**
	 * 获取进度条的进度
	 * @param startTime 开始时间(Number),通过getTime方法获取
	 * @param div 进度条div,appendProgressDiv方法的返回值,jquery对象
	 * @param botton 按钮,jquery对象
	 */
	getProgressBar:function(startTime,index){
		var timestamp = (new Date()).valueOf();
		var bytesReadToShow = 0;
		var contentLengthToShow = 0;
		var bytesReadGtMB = 0;
		var contentLengthGtMB = 0;
		$.getJSON(ctx+"/getBar.do",{"t":timestamp},function(json){
			var bytesRead = (json.pBytesRead / 1024).toString();//到目前为止读取文件的比特数(单位:kb)
			if (bytesRead > 1024) {
				bytesReadToShow = (bytesRead / 1024).toString();
				bytesReadGtMB = 1;
			}else{
				bytesReadToShow = bytesRead.toString();
			}
			var contentLength = (json.pContentLength / 1024).toString();//文件总大小(单位:kb)
			if (contentLength > 1024) {
				contentLengthToShow = (contentLength / 1024).toString();
				contentLengthGtMB = 1;
			}else{
				contentLengthToShow= contentLength.toString();
			}
			bytesReadToShow = bytesReadToShow.substring(0, bytesReadToShow.lastIndexOf(".") + 3);
			contentLengthToShow = contentLengthToShow.substring(0, contentLengthToShow.lastIndexOf(".") + 3);
			if (contentLengthGtMB == 0) {
				$("#txt_bytes").html(contentLengthToShow+"KB");
			}else{
				$("#txt_bytes").html(contentLengthToShow+"MB");
			}
			//绘制进度条
			if (bytesRead == contentLength) {
				$("#txt_pre").html("100%");
				$("#div_bar").css("width","100%");
				setTimeout(function(){
                    if(typeof index != "undefined" && index != null){
                        layer.close(index);
                    }
					layer.msg("上传成功",{"icon":1,"time":1000});
				},500);
			} else {
				var pastTimeBySec = (new Date().getTime() - startTime) / 1000;
				var sp = (bytesRead / pastTimeBySec).toString();
				var speed = sp.substring(0, sp.lastIndexOf(".") + 3);
				var percent = Math.floor((bytesRead / contentLength) * 100) + "%";
				$("#div_bar").css("width", percent);
                $("#txt_pre").html(percent);
				if (bytesReadGtMB == 0 && contentLengthGtMB == 0) {
					$("#txt_bytes").html("\u4e0a\u4f20\u901f\u5ea6:" + speed + "KB/Sec,\u5df2\u7ecf\u8bfb\u53d6" + bytesReadToShow + "KB,\u5b8c\u6210" + percent);
				} else {
					if (bytesReadGtMB == 0 && contentLengthGtMB == 1) {
						$("#txt_bytes").html("\u4e0a\u4f20\u901f\u5ea6:" + speed + "KB/Sec,\u5df2\u7ecf\u8bfb\u53d6" + bytesReadToShow + "MB,\u5b8c\u6210" + percent);
					} else {
						if (bytesReadGtMB == 1 && contentLengthGtMB == 1) {
							$("#txt_bytes").html("\u4e0a\u4f20\u901f\u5ea6:" + speed + "KB/Sec,\u5df2\u7ecf\u8bfb\u53d6" + bytesReadToShow + "MB,\u5b8c\u6210" + percent);
						}
					}
				}
				window.setTimeout(function(){
					PB.Method.getProgressBar(new Date().getTime(),index);
				},500);
			}
		});
	}
};