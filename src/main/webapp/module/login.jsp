<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html lang="zh_CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="推演系统" />
    <meta name="author" content="湖北英迪华科技有限公司" />
    <title>海军推演系统</title>
    <link href="${ctx}/css/login_new.css" rel="stylesheet">
</head>
<body class="login_bg">
    <div class="login_area">
        <input type="text" class="form-control login_user" id="txt_login_name" value="admin">
        <input type="password" class="form-control login_password" id="txt_password" value="111111">
        <input type="text" class="form-control login_checked" id="txt_kaptcha">
        <img id="codeImg" onclick="this.src = (this.backupSrc || (this.backupSrc = this.src)) + '&amp;t=' + new Date().getTime()" title="点击刷新" src="" class="login_img" />
        <button type="button" class="login_btn" id="btn_login">登&nbsp;录</button>
    </div>
    <script src="${ctx}/js/jquery/jquery-1.8.2.min.js"></script>
    <script src="${ctx}/js/global/global.js"></script>
    <script type="text/javascript">
        $(function(){
            WEBGIS.Api.User.captcha($("#codeImg"));
            $("#btn_login").bind("click",login);
            $("#txt_kaptcha").keyup(function (event) {
                if (event.keyCode == 13) {
                    $("#btn_login").trigger("click");
                }
            });
        });
        function login(){
            var un = trim($("#txt_login_name").val());
            if (un == "") {
                alert("请输入登录");
                return false;
            }
            var pwd = trim($("#txt_password").val());
            if (pwd == "") {
                alert("请输入密码");
                return false;
            }
            var kap = trim($("#txt_kaptcha").val());
            if (kap == "") {
                alert("请输入验证码");
                return false;
            }
            var param = {
                "un":un,
                "pwd":pwd,
                "kap":kap
            };
            $("#btn_login").unbind("click");
            $.ajax({
                type:"POST",
                url:ctx+"/signon",
                data:param,
                cache:false,
                success:function(r){
                    $("#btn_login").bind("click",login);
                    if(r != null && r.indexOf("|") > -1){
                        var lt = r.substring(r.indexOf("|") + 1);
                        if(lt == "0"){
                            window.open(ctx + "/smap/main", '_self','toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
                            /*var browserName = navigator.appName;
                            if(browserName == "Microsoft Internet Explorer"){
                                window.opener = null;
                                window.open('','_self','');
                                window.close();
                            }else{
                                window.location.replace("about:blank");
                            }*/
                        }else if(lt == 1){
                            window.location.href = ctx + "/exec/main";
                        }
                    }else{
                        $("#codeImg").trigger("click");
                        alert(r);
                    }
                },
                error : function() {
                    $("#codeImg").trigger("click");
                    $("#btn_login").bind("click",login);
                    alert("操作失败,请联系管理员！");
                }
            });
        }
    </script>
</body>
<%--<body class="body_bg">
    <div class="login-container">
        <div class="login-header">
            <h3>欢迎登陆推演系统</h3>
        </div>
        <div class="login-content">
            <div class="control-group">
                <label class="control-label" for="txt_login_name">登陆名:</label>
                <div class="controls">
                    <input type="text" class="" id="txt_login_name" value="admin">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="txt_password">密码:</label>
                <div class="controls">
                    <input type="password" class="" id="txt_password" value="111111">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="txt_kaptcha">验证码:</label>
                <div class="controls">
                    <input type="text" class="" id="txt_kaptcha" style="width: 60%;">
                    <img id="codeImg" onclick="this.src = (this.backupSrc || (this.backupSrc = this.src)) + '&amp;t=' + new Date().getTime()" title="点击刷新" src="" />
                </div>
            </div>
            <div class="right_div">
                <button type="submit" class="customBtn colourDarkBlue" id="btn_login">登录</button>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</body>--%>
</html>
