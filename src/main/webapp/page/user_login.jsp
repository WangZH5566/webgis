<%--

 登录画面

 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/page/comm/tags.jsp"%>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <meta name='renderer' content='webkit' />
    <meta name='viewport' content='initial-scale=1,minimum-scale=1' />
    <%@ include file="/page/comm/css.jsp"%>
    <title>Login</title>
</head>
<body class='login-page'>
<div class='page'>
    <div class='login-panel'>
        <div class='login-panel-header'>
            <h3>账号登录</h3>
        </div><!-- //end of login_panel_header -->
        <div class='login-panel-body'>
            <form action='' role='form'>
                <div class="form-group">
                    <div class='input-group'>
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input type='text' class='form-control' name='recipient-name' id='recipient-name' placeholder="用户名">
                    </div>
                </div>
                <div class="form-group">
                    <div class='input-group'>
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input type='password' class='form-control' name='recipient-passwd' id='recipient-passwd' placeholder="密码">
                    </div>
                </div>
                <div class='form-group'>
                    <button type="button" class="btn btn-block btn-success" id='btnlogin'>登 录</button>
                </div>
            </form>
        </div><!-- //end of login_panel_body -->
        <!-- //end of login_panel_footer -->
    </div>
</div>
<%@ include file="/page/comm/js.jsp"%>
<script>
    $(document).ready(function() {
        $('#btnlogin').click(function(){
            window.location = 'exercise_index.jsp';
        });
    });
</script>
</body>
</html>

