(function (a) {
    a.icon = {
        init: function () {
            $('#btn_upLoad').bind('change', a.icon.btnUpload);
            $('#btn_delete').bind('click',a.icon.btnDel);
            a.icon.initload();
        },

        initload: function(){
            $.ajax({
                url: ctx+'/ic/list',
                method:'post',
                data:{},
                dataType:'json',
                success:function(val) {
                    for(var i=0;i<val.length;i++) {
                        a.icon.appendIcon(val[i].path);
                    }
                }
            });
        },

        btnDel: function(){
            var $div  =  $('#div_list').find('div.checked');
            var url= new Array();
            $div.each(function(){
                var path = $(this).children(':first').attr('src');
                path = path.replace(ctx,'').replace('/icon/','');
                url.push(path);
            });
            if(url.length == 0){
                return;
            }
            $.ajax({
                url: ctx+'/ic/delete',
                method:'post',
                data:{urls: url.join(',')},
                dataType:'json',
                success:function(val) {
                    $div.each(function(){
                        $(this).remove();
                    });
                }
            });
        },

        btnUpload: function () {
            var fileName = $("#btn_upLoad").val();
            if (fileName == '') {
                return;
            }
            $("#uploadForm").submit();
        },

        btnUploadResult: function (val) {
            if (val != '') {
                layer.msg('上传成功！');
            } else {
                layer.msg('上传失败！');
            }
            a.icon.appendIcon(val);
        },

        appendIcon:function(val) {
            var $div = $('<div class="icon"><img src="' + ctx + '/icon/' + val + '"/></div>');
            $div.click(a.icon.iconClick);
            $('#div_list').append($div);
        },

        iconClick: function () {
            var $div = $(this);
            if ($div.hasClass('checked')) {
                $div.removeClass('checked');
            } else {
                $div.addClass('checked');
            }
            var large = $div.children(':first').attr('src').replace('-small','');

            var $preview = $('#preview');
            var $img = null;
            if ($preview.children().length == 0) {
                $img = $('<img src=""/>');
                $preview.append($img);
            } else {
                $img = $preview.children(':first');
            }
            $img.attr('src', large);
        }
    }
})(WEBGIS);