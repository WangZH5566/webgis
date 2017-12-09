"use strict";(function($){
    $.imgspectrum = {
        img:null,
        canvas:null,
        color_json:{},
        color_count_json:{},
        select_color:"",
        init:function(config) {
            $.imgspectrum.img = new Image();
            $.imgspectrum.canvas = null;
            $.imgspectrum.color_json={};
            $.imgspectrum.color_count_json={};
            $.imgspectrum.select_color="";
            config.container.spectrum({
                showInput: true,
                showInitial: true,
                showAlpha: false,
                showPalette: true,
                showSelectionPalette: true,
                cancelText: "取消",
                chooseText: "确定",
                containerClassName: "canvas-container",
                preferredFormat: "hex",
                maxSelectionSize:10,
                palette: [
                    ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)",
                        "rgb(204, 204, 204)", "rgb(217, 217, 217)","rgb(255, 255, 255)"],
                    ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
                        "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"],
                    ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)",
                        "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)",
                        "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)",
                        "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
                        "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)",
                        "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
                        "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
                        "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
                        "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)",
                        "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
                ],
                change:function(color){
                    var mycontext = $.imgspectrum.canvas.getContext("2d");
                    var image = mycontext.getImageData(0, 0, $.imgspectrum.canvas.width, $.imgspectrum.canvas.height);
                    var d = image.data;
                    for (var i = 0; i < d.length; i += 4) {
                        var r = parseInt(d[i]);
                        var g = parseInt(d[i + 1]);
                        var b = parseInt(d[i + 2]);
                        var a = parseInt(d[i + 3]);
                        if(r==$.imgspectrum.select_color[0]&&g==$.imgspectrum.select_color[1]&&b==$.imgspectrum.select_color[2]&&a==$.imgspectrum.select_color[3]){
                            d[i]=parseInt(color._r);
                            d[i + 1]=parseInt(color._g);
                            d[i + 2]=parseInt(color._b);
                            d[i + 3]=parseInt(color._a*255);
                        }
                    }
                    mycontext.putImageData(image, 0, 0);
                    for(var i in $.imgspectrum.color_json){
                        var tmpArray=$.imgspectrum.color_json[i];
                        $.imgspectrum.select_color[0]=parseInt($.imgspectrum.select_color[0]);
                        $.imgspectrum.select_color[1]=parseInt($.imgspectrum.select_color[1]);
                        $.imgspectrum.select_color[2]=parseInt($.imgspectrum.select_color[2]);
                        $.imgspectrum.select_color[3]=parseInt($.imgspectrum.select_color[3]);
                        var aa=new Array();
                        aa.push($.imgspectrum.select_color[0]);
                        aa.push($.imgspectrum.select_color[1]);
                        aa.push($.imgspectrum.select_color[2]);
                        aa.push($.imgspectrum.select_color[3]);
                        if(tmpArray[1].toString()==aa.toString()){
                            tmpArray[1]=[parseInt(color._r),parseInt(color._g),parseInt(color._b),parseInt(color._a*255)];
                            $.imgspectrum.select_color=tmpArray[1];
                        }
                    }
                },
            });


            $.imgspectrum.canvas=config.container[0];
            $.imgspectrum.img.crossOrigin = "anonymous";
            $.imgspectrum.img.src = config.imgSrc+"?_="+new Date();
            $.imgspectrum.img.onload = function(){
                //var sca=150/$.imgspectrum.img.width;
                //$.imgspectrum.img.width=150;
                //$.imgspectrum.img.height=$.imgspectrum.img.height*sca;
                var mycontext = $.imgspectrum.canvas.getContext("2d");
                $.imgspectrum.canvas.width = $.imgspectrum.img.width;
                $.imgspectrum.canvas.height = $.imgspectrum.img.height;
                mycontext.drawImage($.imgspectrum.img, 0, 0, $.imgspectrum.img.width, $.imgspectrum.img.height);
                buildInitColorJson($.imgspectrum.canvas);
            };
            $($.imgspectrum.canvas).on("click",function(e){
                var canvasOffset = config.container.offset();
                var canvasX = Math.floor(e.pageX - canvasOffset.left);
                var canvasY = Math.floor(e.pageY - canvasOffset.top);
                var mycontext = $.imgspectrum.canvas.getContext("2d");
                var pixelData = mycontext.getImageData(canvasX, canvasY, 1, 1);
                var pixel = pixelData.data;
                $.imgspectrum.select_color=pixel;
                if(parseInt(pixel[3])==0){
                    config.container.spectrum("hide");
                    return false;
                }
//                $("#canvas").spectrum("option", "color", "rgb("+pixel[0]+","+pixel[1]+","+pixel[2]+")");
                config.container.spectrum("set", "rgba("+pixel[0]+","+pixel[1]+","+pixel[2]+","+pixel[3]/255+")");
                config.container.spectrum("show");
                return false;
            });

            function buildInitColorJson(cav){
                var mycontext = cav.getContext("2d");
                var image = mycontext.getImageData(0, 0, cav.width, cav.height);
                var d = image.data;
                for (var i = 0; i < d.length; i += 4) {
                    var r = d[i];
                    var g = d[i + 1];
                    var b = d[i + 2];
                    var a = d[i + 3];
                    var array=new Array();
                    array.push(parseInt(r));
                    array.push(parseInt(g));
                    array.push(parseInt(b));
                    array.push(parseInt(a));
                    if(parseInt(a)==0){
                        continue;
                    }
                    var key=array.join(",");
                    if(key in $.imgspectrum.color_count_json){
                        $.imgspectrum.color_count_json[key].count++;
                    }else{
                        $.imgspectrum.color_count_json[key]={
                            color:array,
                            count:1
                        };
                    }
                }
                var list = new Array();
                for(var i in $.imgspectrum.color_count_json){
                    list.push($.imgspectrum.color_count_json[i]);
                }
                list.sort(function(json1,json2){
                    return json2.count-json1.count;
                });
                for(var i=0;i<list.length&&i<list.length;i++){
                    var tempArray=new Array();
                    tempArray.push(list[i].color);
                    tempArray.push(list[i].color);
                    $.imgspectrum.color_json[list[i].color]=tempArray;
                }
            }
        },
        recalColorJson: function(){
            for(var key in $.imgspectrum.color_json){
                if($.imgspectrum.color_json[key][0].toString()==$.imgspectrum.color_json[key][1].toString()){
                    delete $.imgspectrum.color_json[key];
                }
            }
            var colorArray=new Array();
            for(var key in $.imgspectrum.color_json){
                colorArray.push($.imgspectrum.color_json[key]);
            }
            return colorArray;
        },
        reloadxxx:function (width,height) {
            var mycontext = $.imgspectrum.canvas.getContext("2d");
            $.imgspectrum.img.width = width;
            $.imgspectrum.img.height = height;
            $.imgspectrum.canvas.width = width;
            $.imgspectrum.canvas.height = height;
            mycontext.drawImage($.imgspectrum.img, 0, 0, $.imgspectrum.img.width, $.imgspectrum.img.height);
            // this.buildInitColorJson($.imgspectrum.canvas);
        }
    }
})(jQuery);

