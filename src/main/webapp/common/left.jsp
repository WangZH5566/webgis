<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<!-- sidebar start -->
<div class="sidebar">
    <ul>
        <li class="submenu">
            <a href="${ctx}/exec/main">
                <i class="fa fa-flag" aria-hidden="true"></i>
                <span>推演管理</span>
            </a>
        </li>
        <li class="submenu">
            <a href="${ctx}/department/main">
                <i class="fa fa-cubes" aria-hidden="true"></i>
                <span>台位管理</span>
            </a>
        </li>
        <li class="submenu">
            <a href="${ctx}/tt/main">
                <i class="fa fa-database" aria-hidden="true"></i>
                <span>文电模板</span>
            </a>
        </li>
        <li class="submenu">
            <a href="javascript:;">
                <i class="fa fa-file-text" aria-hidden="true"></i>
                <span>基础资料管理</span>
            </a>
            <ul style="display:none;">
                <li>
                    <a href="${ctx}/baseInfo/baseInfoMajor/baseInfoMajorMain">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>专业维护</span></a>
                </li>
                <li>
                    <a href="${ctx}/baseInfo/baseInfo/baseInfoMain">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>基础资料维护</span></a>
                </li>
                <li>
                    <a href="${ctx}/baseInfo/damage/damageMain">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>受损程度维护</span></a>
                </li>
            </ul>
        </li>
        <li class="submenu">
            <a href="${ctx}/iconManage/main">
                <i class="fa fa-picture-o" aria-hidden="true"></i>
                <span>图标库管理</span>
            </a>
        </li>
        <li class="submenu">
            <a href="${ctx}/formula/main">
                <i class="fa fa-calculator" aria-hidden="true"></i>
                <span>公式管理</span>
            </a>
        </li>
    </ul>
</div>
<!-- sidebar end -->
<script type="text/javascript" >
    // === Sidebar navigation === //
    $(".sidebar> ul > li.submenu > a").click(function(e){
        var submenu = $(this).siblings("ul");
        var li = $(this).parents("li");
        var submenus = $(".sidebar li.submenu ul");
        var submenus_parents = $(".sidebar li.submenu");
        if(li.hasClass("open")){
            if(($(window).width() > 768) || ($(window).width() < 479)){
                submenu.slideUp(150);
            }else{
                submenu.fadeOut(150);
            }
            li.removeClass("open");
        }else{
            if(($(window).width() > 768) || ($(window).width() < 479)){
                submenus.slideUp(150);
                submenu.slideDown(150);
            }else{
                submenus.fadeOut(150);
                submenu.fadeIn(150);
            }
            submenus_parents.removeClass("open");
            li.addClass("open");
        }
    });
//    $('.sidebar >ul >li > ul >li').click(function(e)
//    {
////        e.preventDefault();
//        $(".sidebar li").removeClass("active");
//        $(this).addClass("active");
//    });
</script>