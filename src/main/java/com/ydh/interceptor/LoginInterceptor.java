package com.ydh.interceptor;

import com.ydh.model.User;
import com.ydh.util.Constant;
import com.ydh.util.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yqb on 2016/2/3 0003.
 */
public class LoginInterceptor implements HandlerInterceptor {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//logger.info("LoginInterceptor Pre-handle");
		WebContext.setCurrentContext(request, response);
		if(request.getServletPath().endsWith("login")
				|| request.getServletPath().endsWith("signon")
				|| request.getServletPath().endsWith("kaptcha")){
			return true;
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.LOGIN_USER);
		if(user == null){   
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {

		//logger.info("Post-handle");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {

		//logger.info("After completion handle");
	}

}