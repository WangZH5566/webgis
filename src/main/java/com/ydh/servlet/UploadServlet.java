package com.ydh.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ydh.model.TelegramTemplate;
import com.ydh.service.TelegramTemplateService;
import com.ydh.util.Constant;
import com.ydh.util.PropertiesUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/**
	 * 上传项目只要足够小，就应该保留在内存里。
	 * 较大的项目应该被写在硬盘的临时文件上。
	 * 非常大的上传请求应该避免。
	 * 限制项目在内存中所占的空间，限制最大的上传请求，并且设定临时文件的位置。
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//设置最多只允许在内存中存储的数据,单位:字节(默认10kb)
		factory.setSizeThreshold(2048*1024);
		//初始化进度监听器
		MyProgressListener getBarListener = new MyProgressListener(req);
		ServletFileUpload upload = new ServletFileUpload(factory);
		//将进度监听器加载进去
		upload.setProgressListener(getBarListener);
		//设置允许用户上传文件大小,单位:字节
		//upload.setSizeMax(Integer.valueOf(maxSize)*1024*1024);
		try {
			List<FileItem> formList = upload.parseRequest(req);
			Iterator<FileItem> formItem = formList.iterator();
			String ttId = null;
			String fileName = null;
			String uploadAddress = PropertiesUtil.get("webgis","fileserver.tt.path");
			String pathFileName = null;
			while (formItem.hasNext()) {
				FileItem item = formItem.next();
				if (item.isFormField()) {
					//获取其他不是文件域的所有表单信息
					if(item.getFieldName().equals("ttId")){
                        ttId = item.getString();
					}
				}else{
					pathFileName=UUID.randomUUID() + item.getName().substring(item.getName().lastIndexOf("."));
					if(!new File(uploadAddress).isDirectory()){
						//如果目录不存在,则创建此目录
						new File(uploadAddress).mkdirs();
					}
					File file = new File(uploadAddress + pathFileName);
					OutputStream out = item.getOutputStream();
					InputStream in = item.getInputStream();
					req.getSession().setAttribute("outPutStream", out);
					req.getSession().setAttribute("inPutStream", in);
					item.write(file);
				}
			}
			//往数据库中写入记录
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			TelegramTemplateService telegramTemplateService = (TelegramTemplateService) ac.getBean("telegramTemplateService");
			TelegramTemplate tt = new TelegramTemplate();
            tt.setId(ttId == null ? null : Integer.parseInt(ttId));
            tt.setTpath(uploadAddress + pathFileName);
            tt.setTstatus(Constant.TELEGRAM_STATUS_INIT);
			telegramTemplateService.modifyTt(tt);
		} catch (FileUploadException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

}
