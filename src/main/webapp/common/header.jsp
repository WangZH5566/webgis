<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>

<!-- header start -->
<header class="header">
    <%--<a class="logo" href="#"></a>--%>
    <%--<div style="color: white;">
        <div style="display: block;float:left;height: 50px;line-height: 50px;background: #008fbf;padding:0px 10px;">
            <a href="#" style="font-size:20px;color:#fff;"><i class="fa fa-home"></i></a>
        </div>
        <div style="display: block;float:left;height: 50px;line-height: 55px;font-size: 18px;">
            推演系统
        </div>
    </div>--%>
    <div class="user-nav navbar">
        <ul class="nav">
            <li>
                <a href="javascript:;">
                    <i class="glyphicon glyphicon-user"></i>
                    <span>欢迎,<span style="color:#FFF;">${login_user.userName}<span></span>
                </a>
            </li>
            <c:if test="${not empty login_user.unitName}">
            <li>
                <a href="javascript:;">
                    <span>${login_user.unitName}</span>
                </a>
            </li>
            </c:if>
            <c:if test="${not empty login_user.departmentName}">
            <li>
                <a href="javascript:;">
                    <span>${login_user.departmentName}</span>
                </a>
            </li>
            </c:if>
            <%--<li>--%>
            <%--<a href="#">--%>
            <%--<i class="glyphicon glyphicon-list"></i>--%>
            <%--<span>通知</span>--%>
            <%--</a>--%>
            <%--</li>--%>
            <li>
                <%--<a href="javascript:void(0);" onclick="updatePswWin()">
                    <i class="glyphicon glyphicon-edit"></i>
                    <span>修改密码</span>
                </a>--%>
                <a href="${ctx}/js/doc.pdf" target="_blank">
                    <i class="glyphicon glyphicon-edit"></i>
                    <span>操作手册</span>
                </a>
            </li>
            <li>
                <a href="${ctx}/logout">
                    <i class="glyphicon glyphicon-log-out"></i>
                    <span>注销</span>
                </a>
            </li>
        </ul>
    </div>
</header>
<!-- header end -->

<script type="text/javascript">
    function updatePswWin(){
        $.post(ctx+"/user/updatePsw",null,function(str){
            layer.open({
                type: 1,
                area: ['500px', '285px'],
                fix: false, //不固定
                maxmin: true,
                title: ["修改密码","font-weight:bold;"],
                content: str //注意，如果str是object，那么需要字符拼接。
            });
        });
    }
</script>