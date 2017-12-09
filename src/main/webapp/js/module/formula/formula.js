(function (a) {
    a.formula = {
        init: function () {
            $("#btn_modify").on("click", a.formula.btnModifyClick);
            $("#btn_delete").on("click", a.formula.btnDeleteClick);
            a.formula.btnSearchClick();
        },
        btnSearchClick: function () {
            var param = {
                "pageNo": "1",
                "pageSize": "10"
            };
            $.post(ctx + "/formula/queryCount", param, function (page) {
                laypage({
                    cont: $("#div_page_btn"),                       //分页按钮容器。值支持id名、原生dom对象，jquery对象,
                    pages: page,                                    //总页数
                    skip: false,                                   //是否开启跳页
                    skin: '#AF0000',
                    prev: '<',
                    next: '>',
                    first: '<<',
                    last: '>>',
                    groups: 5, //连续显示分页数
                    jump: function (obj, first) {
                        param.pageNo = obj.curr;
                        $("#div_list").load(ctx + "/formula/queryPage", param, function () {
                            $("#tbody_list tr").on("click", function () {
                                $("#tbody_list tr").removeClass("selected");
                                $(this).addClass("selected");
                            });
                        });
                    }
                });
            });
        },
        btnModifyClick: function () {
            var deId = $("#tbody_list tr.selected").attr("id");
            if (typeof deId == "undefined") {
                layer.msg("请选择要修改的数据！", {icon: 2, time: 1000});
                return false;
            }
            window.location.href = ctx + "/formula/addOrEdit?id=" + deId;
        },
        btnDeleteClick: function () {
            var deId = $("#tbody_list tr.selected").attr("id");
            if (typeof deId == "undefined") {
                layer.msg("请选择要删除的数据！", {icon: 2, time: 1000});
                return false;
            }

            layer.confirm("确认要删除选择的公式么?", {
                btn: ['确认', '取消'] //可以无限个按钮
            }, function (index, layero) {
                //解绑按钮事件
                $("#btn_delete").off("click");
                $.ajax({
                    type: "POST",
                    url: ctx + "/formula/delete",
                    data: {"id": deId},
                    cache: false,
                    success: function (r) {
                        layer.close(index);
                        $("#btn_delete").on("click", a.formula.btnDeleteClick);
                        if (r != null && r == "success") {
                            a.formula.btnSearchClick();
                        } else {
                            layer.msg(r, {icon: 2, time: 1000});
                        }
                    },
                    error: function () {
                        layer.close(index);
                        $("#btn_delete").on("click", a.formula.btnDeleteClick);
                        layer.msg("操作失败,请联系管理员！", {icon: 2, time: 1000});
                    }
                });
            }, function (index) {
            });
        }
    },
        a.formulaAddOrEdit = {
            init: function () {
                $("#btn_save").on("click", a.formulaAddOrEdit.btnSaveClick);
                $('#btn_validate').on('click', a.formulaAddOrEdit.validate);
                $('#btn_calculate').on('click', a.formulaAddOrEdit.calculate);
            },
            btnSaveClick: function () {
                var na = trim($("#txt_name").val());
                var vl = trim($("#txt_val").val());
                if (na == "") {
                    layer.msg("请输入[公式名称]", {icon: 2, time: 1000});
                    return false;
                }
                if (vl == "") {
                    layer.msg("请输入[公式代码]", {icon: 2, time: 1000});
                    return false;
                }

                //组装参数
                var param = {
                    "id": $("#txt_id").val(),
                    "na": na,
                    "vl": vl,
                    "re": $("#txt_remark").val()
                };
                //解绑按钮事件
                $("#btn_save").off("click");
                var ind = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    type: "POST",
                    url: ctx + "/formula/save",
                    data: param,
                    cache: false,
                    success: function (r) {
                        $("#btn_save").on("click", a.formulaAddOrEdit.btnSaveClick);
                        layer.close(ind);
                        if (r != null && r == "success") {
                            layer.msg("保存成功", {icon: 1, time: 1000});
                        } else {
                            layer.msg(r, {icon: 2, time: 1000});
                        }
                    },
                    error: function () {
                        $("#btn_save").on("click", a.formulaAddOrEdit.btnSaveClick);
                        layer.close(ind);
                        layer.msg("操作失败,请联系管理员！", {icon: 2, time: 1000});
                    }
                });
            },
            validate: function () {
                var vl = trim($("#txt_val").val());
                if (vl == '') {
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: ctx + "/formula/validate",
                    data: {"expr": vl},
                    cache: false,
                    success: function (r) {
                        var $panel = $('#formula_panel');
                        $panel.empty();
                        for (var i = 0; i < r.length; i++) {
                            var $row = $('<div class="row"></div>');
                            var $label = $('<label class="col-sm-2 control-label form-label2">' + r[i] + ':</label>');
                            var $input = $('<div class="col-sm-8 form-div2"><input type="text" placeholder="" class="form-control" name="param_' + r[i] + '" value="" /></div>');
                            $row.append($label).append($input);
                            $panel.append($row);
                        }
                        var $row = $('<div class="row"></div>');
                        var $label1 = $('<label class="col-sm-2 control-label form-label2">测试结果:</label>');
                        var $label2 = $('<label class="col-sm-8 control-label form-label2" id="calcualtor_result" style="text-align:left;font-weight: 400;"></label>');
                        $row.append($label1).append($label2);
                        $panel.append($row);
                    }
                });
            },
            calculate: function () {
                var vl = trim($("#txt_val").val());
                if (vl == '') {
                    return;
                }
                var $panel = $('#formula_panel');
                var $inputs = $panel.find('input');
                var param = {};
                param.expr = vl;
                $inputs.each(function () {
                    var $input = $(this);
                    var name = $input.attr('name');
                    var value = $input.val();
                    param[name] = value;
                });
                $.ajax({
                    type: "POST",
                    url: ctx + "/formula/calculate",
                    data: param,
                    cache: false,
                    success: function (r) {
                        var $label2 = $('#calcualtor_result');
                        if (r != '' || r==0) {
                            $label2.text(r);
                        } else {
                            $label2.text('公式或参数有不对');
                        }
                    }
                });
            }
        }
})(WEBGIS);