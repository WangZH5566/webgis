<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
    .ztree *{font-size:14px;}
    .ztree li{margin-top:8px;}
</style>
<html>
<body>
    <ul id="unit_ztree" class="ztree" style="margin-left: 20px;"></ul>
    <script type="text/javascript">
        var unit_zTreeNodes = ${nodes};
        var unit_setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: 0
                },
                key : {
                    name:"unitName"
                }
            },
            view: {
                showIcon: true,
                selectedMulti: false
            },
            check:{
                enable: true,
                chkStyle : "radio",
                radioType: "all"
            }
        };
    </script>
</body>
</html>