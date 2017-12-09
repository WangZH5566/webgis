package com.ydh.controller;

import com.ydh.model.User;
import com.ydh.service.UserService;
import com.ydh.util.Constant;
import com.ydh.util.EncodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @description:
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    //首页
    @RequestMapping("/login")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("login.main");
        /*try {

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }*/
        return mv;
    }

    @ResponseBody
    @RequestMapping("/signon")
    public String signon(HttpServletRequest request, HttpServletResponse response) {
        String isAdmin = "";
        try {
            String un = request.getParameter("un");
            String pwd = request.getParameter("pwd");
            String kap = request.getParameter("kap");
            String sessionCaptcha = (String) request.getSession().getAttribute(Constant.CAPTCHA);
            if ((sessionCaptcha == null) || (kap == null) || ("".equals(sessionCaptcha)) || ("".equals(kap)) || (!StringUtils.equalsIgnoreCase(kap, sessionCaptcha))) {
                return "验证码错误";
            }
            User user = userService.queryLoginUser(un);
            if (user == null) {
                return "当前用户不存在";
            }
            pwd = EncodeUtils.MD5Upper(pwd);
            if (!user.getPwd().equals(pwd)) {
                return "密码错误";
            }
            if (user.getExecStatus() != null && user.getExecStatus().equals(Constant.EXEC_STATUS__END)) {
                return "对不起,您所在的推演已结束";
            }
            isAdmin = user.getIsAdmin().toString();
            request.getSession().setAttribute(Constant.LOGIN_USER, user);
            request.getSession().setAttribute("FILESERVER_ICON_VISITPATH", FILESERVER_ICON_VISITPATH);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constant.DATA_ABNORMAL;
        }
        return "success|" + isAdmin;
    }

    @RequestMapping("/logout")
    public ModelAndView signoff(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.LOGIN_USER);
        ModelAndView mv = new ModelAndView("redirect:login");
        return mv;
    }
}
