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
                    <div class="widget-content no-padding">
                        <form class="form-horizontal" action="#" method="post" novalidate="novalidate">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label form-label2" style="padding-left: 0;">受损程度详情:</label>
                                    <div class="col-sm-8 form-div2">
                                        <input type="text" placeholder="" class="form-control" id="txt_cont" value="${dto.damageContent}" />
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                            <input type="hidden" id="txt_id" value="${dto.id}">
                            <input type="hidden" id="txt_pid" value="${pid}">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctx}/js/module/damage/damage.js?v=20161028002"></script>
    <script type="text/javascript">
    </script>
</body>
</html>