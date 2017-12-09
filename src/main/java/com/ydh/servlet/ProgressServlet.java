package com.ydh.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.alibaba.fastjson.JSONObject;
import com.ydh.model.FileUploadStatus;


public class ProgressServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		
		HttpSession session = request.getSession();
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
		JSONObject json = new JSONObject();
		PrintWriter pw;
		try {
			json.put("pBytesRead", status.getPBytesRead());
			json.put("pContentLength", status.getPContentLength());
			/*System.out.println("{\"pBytesRead\":"
					+status.getPBytesRead()+",\"pContentLength\":"+status.getPContentLength()+"}");*/
			pw = response.getWriter();
			pw.print(json);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		this.doPost(request,response);
		
	}
}
