package com.ydh.controller;

import com.ydh.dto.TelegramDto;
import com.ydh.model.Telegram;
import com.ydh.model.User;
import com.ydh.service.ExeciseService;
import com.ydh.service.SituationMakeService;
import com.ydh.servlet.MyProgressListener;
import com.ydh.util.Constant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * @description:文电拟制Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/sm")
public class SituationMakeController extends BaseController{

    @Autowired
    private SituationMakeService situationMakeService;

    @Value("${fileserver.telegram.path}")
    private String tpath;
    @Value("${fileserver.telegram.visitpath}")
    private String tvisitPath;


    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("sm.main");
        mv.addObject("tvisitPath",tvisitPath);
        /*try {

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }*/
        return mv;
    }

    /**
     * 查询电文总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryTeleCount")
    public String queryTeleCount(HttpServletRequest request){
        int page = 0;
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String tn = request.getParameter("tn");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramDto searchDto = new TelegramDto();
            searchDto.setTname(tn);
            searchDto.setCreateBy(user.getId());
            //查询数据
            Integer count = situationMakeService.queryTeleCount(searchDto);
            page = this.calToltalPage(count,Integer.valueOf(pageSize));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询文电
     * @param request
     * @return
     */
    @RequestMapping("queryTelePage")
    public ModelAndView queryTelePage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("sm.load");
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String tn = request.getParameter("tn");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramDto searchDto = new TelegramDto();
            searchDto.setTname(tn);
            searchDto.setCreateBy(user.getId());
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<TelegramDto> dtos = situationMakeService.queryTelePage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo",Integer.valueOf(pageNo));
            mv.addObject("pageSize",Integer.valueOf(pageSize));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response,@RequestParam("tfile")CommonsMultipartFile tfile){
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置最多只允许在内存中存储的数据,单位:字节(默认10kb)
        factory.setSizeThreshold(2048*1024);
        //初始化进度监听器
        MyProgressListener getBarListener = new MyProgressListener(request);
        ServletFileUpload upload = new ServletFileUpload(factory);
        //将进度监听器加载进去
        upload.setProgressListener(getBarListener);
        //设置允许用户上传文件大小,单位:字节
        //upload.setSizeMax(Integer.valueOf(maxSize)*1024*1024);
        try {
            User user = this.getLoginUser(request);
            String tname = request.getParameter("tname");
            FileItem item = tfile.getFileItem();
            String pathFileName = UUID.randomUUID()+item.getName().substring(item.getName().lastIndexOf("."));
            if(!new File(tpath).isDirectory()){
                //如果目录不存在,则创建此目录
                new File(tpath).mkdirs();
            }
            File file = new File(tpath + pathFileName);
            OutputStream out = item.getOutputStream();
            InputStream in = item.getInputStream();
            request.getSession().setAttribute("outPutStream", out);
            request.getSession().setAttribute("inPutStream", in);
            item.write(file);
            //往数据库中写入记录
            Telegram t = new Telegram();
            t.setTname(tname);
            t.setTpath(pathFileName);
            t.setExecId(user.getExecId());
            t.setTstatus(Constant.TELEGRAM_STATUS_INIT);
            t.setCreateBy(user.getId());
            situationMakeService.addTelegram(t);
        } catch (FileUploadException e2) {
            e2.printStackTrace();
            logger.error(e2.getMessage(),e2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 删除文电
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTele")
    public String deleteTele(HttpServletRequest request){
        String msg = "success";
        try{
            //接收参数
            String id = request.getParameter("id");
            //删除数据
            situationMakeService.deleteTelegram(Integer.valueOf(id));
        }catch(Exception e){
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return msg;
    }
}
