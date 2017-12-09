<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5>公式编辑</h5>
                    </div>
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate" id="form_search">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">公式名称:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_name" value="${dto.name}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">备注:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_remark" value="${dto.remark}" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">公式代码:</label>
                                    <div class="col-sm-8 form-div2">
                                        <textarea class="form-control" id="txt_val" rows="4">${dto.val}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">公式说明:</label>
                                    <div class="col-sm-8 form-div2">
                                        <label style="font-weight: 400;">支持的操作符：+ 加、- 减、* 乘 、 / 除、^ 乘方 、sqrt 开方、（）括号</label><br/>
                                        <label style="font-weight: 400;">支持的三角函数：cos、sin、tan、acos、atan、asin</label><br/>
                                        <label style="font-weight: 400;">求和公式：sum。格式如下：sum([下标值，上标值](表达式)):<%--，表达式有两种形式:<br>1.表达式的分量值命名以i结尾，例如：sum([1,2](ai + bi +i))：表示(a1+b1+1) + (a2 + b2+ 1)<br>2.--%>表达式为含i的表达式如sum([1,n](a^3 + sqrt(b) - i))</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label form-label2">公式验证:</label>
                                    <div class="col-sm-8 form-div2" id="formula_panel">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <div class="col-sm-10 form-div2">
                                        <a href="${ctx}/formula/main" class="btn btn-xs btn-primary buttons" style="float: right;">返回</a>
                                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="float: right;margin-right: 10px;" id="btn_save">保存</a>
                                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="float: right;margin-right: 10px;" id="btn_validate">公式有效性验证</a>
                                        <a href="javascript:;" class="btn btn-xs btn-primary buttons" style="float: right;margin-right: 10px;" id="btn_calculate">公式计算</a>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </form>
                    </div>
                    <input type="hidden" id="txt_id" value="${dto.id}">
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/formula/formula.js?v=20161028024"></script>
    <script type="text/javascript">
        WEBGIS.formulaAddOrEdit.init();
    </script>
</body>
</html>