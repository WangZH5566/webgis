package com.ydh.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private static ThreadLocal tl = new ThreadLocal();

	private WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@SuppressWarnings("unchecked")
	static public void setCurrentContext(HttpServletRequest request,
			HttpServletResponse response) {
		WebContext c = getCurrentContext();
		if (c == null) {
			c = new WebContext(request, response);
			tl.set(c);
		} else {
			c.setRequest(request);
			c.setResponse(response);
		}
	}

	/**
	 * 安全用户认证，返回用户对象
	 * 
	 * @return SecuredUser
	 */
	public Object getCurrentUser() {
		Object user = getRequest().getSession().getAttribute(Constant.LOGIN_USER);
		return user;
	}

	static public WebContext getCurrentContext() {
		return (WebContext) tl.get();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
