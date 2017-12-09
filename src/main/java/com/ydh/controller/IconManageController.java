package com.ydh.controller;

import com.ydh.dto.IconDto;
import com.ydh.model.Icon;
import com.ydh.model.IconExt;
import com.ydh.model.IconGroup;
import com.ydh.service.IconExtService;
import com.ydh.service.IconGroupService;
import com.ydh.service.IconService;
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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @description:图标库Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/iconManage")
public class IconManageController extends BaseController {
    @Autowired
    private IconService iconService;
    @Autowired
    private IconGroupService iconGroupService;
    @Autowired
    private IconExtService iconExtService;

    @Value("${fileserver.icon.path}")
    private String FILESERVER_ICON_PATH;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    /**
     * 主页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("iconManage.main");
        return mv;
    }

    @RequestMapping(value = "/addPage")
    public ModelAndView addPage(IconDto dto) {
        ModelAndView mv = new ModelAndView("iconManage.addIconPage");
        mv.addObject("icon",dto);
        return mv;
    }

    @RequestMapping(value = "/editPage")
    public ModelAndView editPage(IconDto dto) {
        ModelAndView mv = new ModelAndView("iconManage.editIconPage");
        dto=this.iconService.findByID(dto.getId());
        List<IconExt> extList=this.iconExtService.queryIconExt(dto.getId());
        mv.addObject("icon",dto);
        mv.addObject("extList",extList);
        return mv;
    }

    @RequestMapping(value = "/addFieldPage")
    public ModelAndView addFieldPage(IconExt ext) {
        ModelAndView mv = new ModelAndView("iconManage.addFieldPage");
        mv.addObject("ext",ext);
        return mv;
    }

    @RequestMapping(value = "/editFieldPage")
    public ModelAndView editFieldPage(IconExt ext) {
        ModelAndView mv = new ModelAndView("iconManage.addFieldPage");
        ext=this.iconExtService.findByID(ext.getId());
        mv.addObject("ext",ext);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value="/saveIcon",method={RequestMethod.POST})
    public JSON saveIcon(HttpServletRequest request,Icon icon){
        JSON json=null;
        MultipartFile image=null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            image=multipartRequest.getFile("image");
        }
        String message="SUCCESS";
        /*OutputStream os = null;
        InputStream is = null;*/
        try{
            if(icon.getId()==null){
                //新增
                if(image!=null) {
                    String fileName = UUID.randomUUID().toString();
                    String extName = image.getOriginalFilename();
                    int lastIndex = extName.lastIndexOf(".");
                    extName = extName.substring(lastIndex);
                    String fullName = this.FILESERVER_ICON_PATH + fileName + extName;
                    File destFile = new File(fullName);
                    icon.setPath(fileName + extName);
                    FileUtils.copyInputStreamToFile(image.getInputStream(), destFile);
                    File nailFile = new File(this.FILESERVER_ICON_PATH + File.separator + fileName + "-small" + extName);
                    Thumbnails.of(destFile).size(32, 32).toFile(nailFile);
                    icon.setPath(fileName + "-small" + extName);
                    /*String fileName = UUID.randomUUID().toString();
                    String extName = image.getOriginalFilename();
                    int lastIndex = extName.lastIndexOf(".");
                    extName = extName.substring(lastIndex);
                    String fullName = this.FILESERVER_ICON_PATH + fileName + extName;
                    // File destFile = new File(fullName);
                    os = new FileOutputStream(fullName);
                    is = image.getInputStream();
                    byte[] bs = new byte[1024];
                    int len;
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                    }
                    icon.setPath(fileName + extName);*/
                }
                message=this.iconService.addIcon(icon);
            }else{
                //修改
                message=this.iconService.updateIcon(icon);
            }
        }catch(Exception e){
            e.printStackTrace();
            message="数据异常，请刷新后重试！";
        }/*finally {
            try {
                if(os != null){
                    os.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        if("SUCCESS".equals(message)){
            json=JSON.SUCCESS;
            json.setResult(icon);
        }else{
            json=JSON.FAILURE;
            json.setMsg(message);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value="/saveIconField",method={RequestMethod.POST})
    public JSON saveIconField(HttpServletRequest request,IconExt ext){
        JSON json=null;
        String message="SUCCESS";
        try{
            if(ext.getId()==null){
                //新增
                message=this.iconExtService.addIconExt(ext);
            }else{
                //修改
                message=this.iconExtService.modifyIconExt(ext);
            }
        }catch(Exception e){
            e.printStackTrace();
            message="数据异常，请刷新后重试！";
        }
        if("SUCCESS".equals(message)){
            json=JSON.SUCCESS;
            json.setResult(ext);
        }else{
            json=JSON.FAILURE;
            json.setMsg(message);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteIconExt", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON deleteIconExt(Integer id) {
        JSON json=null;
        String msg="SUCCESS";
        try{
            msg=this.iconExtService.deleteByID(id);
        }catch (Exception e){
            msg="删除失败，请刷新后重试";
        }
        if("SUCCESS".equals(msg)){
            json=JSON.SUCCESS;
        }else{
            json=JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteIcon", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON deleteIcon(Integer id) {
        JSON json=null;
        String msg="SUCCESS";
        try{
            msg=this.iconService.deleteIcon(id);
        }catch (Exception e){
            msg="删除失败，请刷新后重试";
        }
        if("SUCCESS".equals(msg)){
            json=JSON.SUCCESS;
        }else{
            json=JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/queryIconListCount", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryIconListCount(IconDto searchDto) {
        Integer count=this.iconService.queryIconListCount(searchDto);
        return this.calToltalPage(count,21)+"";
    }

    @RequestMapping(value = "/queryIconListPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView queryIconListPage(IconDto searchDto) {
        ModelAndView mv = new ModelAndView("iconManage.iconList");
        mv.addObject("FILESERVER_ICON_VISITPATH",FILESERVER_ICON_VISITPATH);
        List<IconDto> list=this.iconService.queryIconListPage(searchDto);
        mv.addObject("list",list);
        mv.addObject("searchDto",searchDto);
        return mv;
    }

    @RequestMapping(value = "/queryIconListPageForBaseInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView queryIconListPageForBaseInfo(IconDto searchDto) {
        ModelAndView mv = new ModelAndView("baseInfo.iconList");
        mv.addObject("FILESERVER_ICON_VISITPATH",FILESERVER_ICON_VISITPATH);
        List<IconDto> list=this.iconService.queryIconListPage(searchDto);
        mv.addObject("list",list);
        mv.addObject("searchDto",searchDto);
        return mv;
    }

    /**
     * 分页查询图标(要图标绘用)
     * @param searchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryIconListCountForExec", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryIconListCountForExec(IconDto searchDto) {
        Integer count=this.iconService.queryIconListCount(searchDto);
        return this.calToltalPage(count,8)+"";
    }

    /**
     * 分页查询图标(要图标绘用)
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/queryIconListPageForExec", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView queryIconListPageForExec(IconDto searchDto) {
        ModelAndView mv = new ModelAndView("iconManage.execIcon");
        mv.addObject("FILESERVER_ICON_VISITPATH",FILESERVER_ICON_VISITPATH);
        List<IconDto> list=this.iconService.queryIconListPage(searchDto);
        mv.addObject("list",list);
        mv.addObject("searchDto",searchDto);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value="/iconGroupList",method = {RequestMethod.POST})
    public List<IconGroup> iconGroupList(Integer  pid){
        List<IconGroup> list=this.iconGroupService.queryIconGroup(pid);
        return list==null?new ArrayList<IconGroup>():list;
    }

    @ResponseBody
    @RequestMapping(value = "/saveIconGroup", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON saveIconGroup(IconGroup ig) {
        JSON json=null;
        String msg="SUCCESS";
        if(ig.getId()==null){
            msg=this.iconGroupService.addIconGroup(ig);
        }else{
            msg=this.iconGroupService.modifyIconGroup(ig);
        }
        if("SUCCESS".equals(msg)){
            json=JSON.SUCCESS;
            json.setResult(ig);
        }else{
            json=JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteIconGroup", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON deleteIconGroup(Integer id) {
        JSON json=null;
        String msg="SUCCESS";
        msg=this.iconGroupService.deleteIconGroup(id);
        if("SUCCESS".equals(msg)){
            json=JSON.SUCCESS;
        }else{
            json=JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }
}
