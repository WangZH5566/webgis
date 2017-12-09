package com.ydh.controller;

import com.alibaba.fastjson.JSONArray;
import com.ydh.dto.FileDto;
import com.ydh.model.DataFile;
import com.ydh.model.IconGroup;
import com.ydh.service.DataFileService;
import com.ydh.util.JSON;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/info")
public class InfoController extends BaseController{
    @Value("${fileserver.resource.visitpath}")
    private String FILESERVER_RESOURCE_VISITPATH;

    @Autowired
    private DataFileService dataFileService;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("info.main");
        mv.addObject("fileVisitPath",this.FILESERVER_RESOURCE_VISITPATH);
        return mv;
    }

    @RequestMapping("/fileDetail")
    public ModelAndView fileDetail(HttpServletRequest request, HttpServletResponse response, DataFile df){
        ModelAndView mv = new ModelAndView("info.fileDetail");
        if(df.getIsDirectory()!=null&&df.getIsDirectory()){
            List<DataFile> list=this.dataFileService.queryDataFile(df.getId());
            mv.addObject("list",list);
            mv.addObject("currentDirectory",df.getId());
        }else{
            mv.setViewName("info.filePreview");
            df=this.dataFileService.findByID(df.getId());
            mv.addObject("file",df);
            mv.addObject("fileVisitPath",this.FILESERVER_RESOURCE_VISITPATH);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value="/nodeList",method = {RequestMethod.POST})
    public JSONArray loadNodeList(Integer  parent){
        List<DataFile> list=this.dataFileService.queryDataFile(parent);
        JSONArray ja=(JSONArray)JSONArray.toJSON(list);
        return ja==null?new JSONArray():ja;
    }

    @ResponseBody
    @RequestMapping(value="/uploadFile",method={RequestMethod.POST})
    public JSON uploadFile(HttpServletRequest request,DataFile dataFile){
        JSON json=null;
        MultipartFile file=null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file=multipartRequest.getFile("file");
        }
        String message="SUCCESS";
        try{
            message=this.dataFileService.addDataFile(dataFile, file);
        }catch(Exception e){
            e.printStackTrace();
            message="数据异常，请刷新后重试！";
        }
        if("SUCCESS".equals(message)){
            json=JSON.SUCCESS;
            json.setResult(dataFile);
        }else{
            json=JSON.FAILURE;
            json.setMsg(message);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/saveDirectory", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON saveDirectory(DataFile df) {
        JSON json=null;
        String msg="SUCCESS";
        if(df.getId()==null){
            msg=this.dataFileService.addDataFile(df);
        }else{
            msg=this.dataFileService.modifyDataFile(df);
        }
        if("SUCCESS".equals(msg)){
            json=JSON.SUCCESS;
            json.setResult(df);
        }else{
            json=JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDataFile", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON deleteDataFile(Integer id) {
        JSON json=null;
        String msg="SUCCESS";
        msg=this.dataFileService.deleteDataFile(id);
        if("SUCCESS".equals(msg)){
            json=JSON.SUCCESS;
        }else{
            json=JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }
}
