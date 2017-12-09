package com.ydh.controller;

import com.ydh.model.User;
import com.ydh.util.Constant;
import com.ydh.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 提供printGridData 方法向客户端打印数据。
 * @author pt
 *
 */
public abstract class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 打印xml数据
	 * @param xml
	 * @param response
	 */
	public void printXmlData(String xml,HttpServletResponse response){
		
		ServletUtils.setXmlAjaxResponseHeader(response);
		
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(xml);   
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	/**
	 * 打印text数据
	 * @param xml
	 * @param response
	 */
	public void printTextData(String text,HttpServletResponse response){
		
		ServletUtils.setTextAjaxResponseHeader(response); 
		
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(text);   
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	/**
	 * 打印json数据
	 * @param xml
	 * @param response
	 */
	public void printJsonData(String json,HttpServletResponse response){
		
		ServletUtils.setJsonAjaxResponseHeader(response); 
		
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}

	public int calToltalPage(Integer totalCount,Integer pageSize){
		totalCount=totalCount==null?0:totalCount;
		pageSize=pageSize==null?10:pageSize;
		int totalPage=totalCount/pageSize;
		if(totalCount%pageSize>0){
			totalPage++;
		}
		return totalPage;
	}
	
	/**
	 * 获取当前用户的session
	 * @param xml
	 * @param response
	 */
	public User getLoginUser(HttpServletRequest request){
		return (User)request.getSession().getAttribute(Constant.LOGIN_USER);
	}
}
