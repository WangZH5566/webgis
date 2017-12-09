package com.ydh.controller;

import com.ydh.dto.DamageDetailDto;
import com.ydh.dto.DamageDto;
import com.ydh.model.BasicInfoMajor;
import com.ydh.model.Damage;
import com.ydh.model.DamageDetail;
import com.ydh.service.DamageService;
import com.ydh.util.Constant;
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
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @description:受损程度维护Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/baseInfo/damage")
public class DamageController extends BaseController {

    @Autowired
    private DamageService damageService;
    @Value("${fileserver.icon.path}")
    private String FILESERVER_ICON_PATH;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/damageMain")
    public ModelAndView damageMain(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("damage.main");
        return mv;
    }

    /**
     * 查询受损程度总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCount")
    public String queryCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            DamageDto searchDto = new DamageDto();
            //查询数据
            Integer count = damageService.queryCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询受损程度
     * @param request
     * @return
     */
    @RequestMapping("queryPage")
    public ModelAndView queryPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("damage.load");
        try {
            //接收参数
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            DamageDto searchDto = new DamageDto();
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<DamageDto> dtos = damageService.queryPage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo", Integer.valueOf(pageNo));
            mv.addObject("pageSize", Integer.valueOf(pageSize));
            mv.addObject("fiv",FILESERVER_ICON_VISITPATH);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 新增/修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEdit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("damage.addOrEdit");
        try {
            String id = request.getParameter("id");
            String modifyType = request.getParameter("modifyType");
            mv.addObject("modifyType",modifyType);
            if(id != null && !"".equals(id)){
                DamageDto dto = damageService.queryById(Integer.valueOf(id));
                mv.addObject("dto",dto);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 新增受损程度
     * @param request
     * @param damage
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/addWithImg",method={RequestMethod.POST})
    public String addWithImg(HttpServletRequest request,Damage damage){
        String msg = "success";
        /*MultipartFile image=null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            image=multipartRequest.getFile("image");
        }*/
        try{
            /*if(image!=null) {
                String fileName = UUID.randomUUID().toString();
                String extName = image.getOriginalFilename();
                int lastIndex = extName.lastIndexOf(".");
                extName = extName.substring(lastIndex);
                String fullName = this.FILESERVER_ICON_PATH + File.separator + fileName + extName;
                File destFile = new File(fullName);
                FileUtils.copyInputStreamToFile(image.getInputStream(), destFile);
                File nailFile = new File(this.FILESERVER_ICON_PATH + File.separator + fileName + "-small" + extName);
                Thumbnails.of(destFile).size(32, 32).toFile(nailFile);
                damage.setImgPath(fileName + "-small" + extName);
            }*/
            this.damageService.addDamage(damage);
        }catch(Exception e){
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 修改受损程度(不改图标)
     * @param request
     * @param damage
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/modifyWithoutImg",method={RequestMethod.POST})
    public String modifyWithoutImg(HttpServletRequest request,Damage damage){
        String msg = "success";
        try {
            //接收参数
            damageService.modifyDamage(damage);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 修改受损程度图标
     * @param request
     * @param damage
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyImg", method = {RequestMethod.POST})
    public String modifyImg(HttpServletRequest request,Damage damage){
        String msg = "success";
        MultipartFile image=null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            image=multipartRequest.getFile("image");
        }
        try{
            if(image!=null) {
                String fileName = UUID.randomUUID().toString();
                String extName = image.getOriginalFilename();
                int lastIndex = extName.lastIndexOf(".");
                extName = extName.substring(lastIndex);
                String fullName = this.FILESERVER_ICON_PATH + File.separator + fileName + extName;
                File destFile = new File(fullName);
                FileUtils.copyInputStreamToFile(image.getInputStream(), destFile);
                File nailFile = new File(this.FILESERVER_ICON_PATH + File.separator + fileName + "-small" + extName);
                Thumbnails.of(destFile).size(32, 32).toFile(nailFile);
                damage.setImgPath(fileName + "-small" + extName);
            }
            this.damageService.modifyDamage(damage);
        }catch(Exception e){
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 删除受损程度
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteDamage")
    public String deleteDamage(HttpServletRequest request) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            //删除数据
            damageService.deleteDamage(Integer.valueOf(id));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 查询受损程度明细总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDetailCount")
    public String queryDetailCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String pid = request.getParameter("pid");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            DamageDetailDto searchDto = new DamageDetailDto();
            searchDto.setPid(Integer.valueOf(pid));
            //查询数据
            Integer count = damageService.queryDetailCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询受损程度明细
     * @param request
     * @return
     */
    @RequestMapping("queryDetailPage")
    public ModelAndView queryDetailPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("damageDetail.load");
        try {
            //接收参数
            String pid = request.getParameter("pid");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            DamageDetailDto searchDto = new DamageDetailDto();
            searchDto.setPid(Integer.valueOf(pid));
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<DamageDetailDto> dtos = damageService.queryDetailPage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo", Integer.valueOf(pageNo));
            mv.addObject("pageSize", Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 受损程度详情新增/修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addOrEditDetail")
    public ModelAndView addOrEditDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("damageDetail.addOrEdit");
        try {
            String id = request.getParameter("id");
            String pid = request.getParameter("pid");
            if(id != null && !"".equals(id)){
                DamageDetailDto dto = damageService.queryDetailById(Integer.valueOf(id));
                pid = dto.getPid().toString();
                mv.addObject("dto",dto);
            }
            mv.addObject("pid",pid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/saveDamageDetail", method = {RequestMethod.POST})
    public String saveDamageDetail(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            String pid = request.getParameter("pid");
            String na = request.getParameter("na");
            //组装参数
            DamageDetail damageDetail = new DamageDetail();
            damageDetail.setDamageContent(na);
            damageDetail.setPid(Integer.valueOf(pid));
            if (id != null && !"".equals(id)) {
                //修改
                damageDetail.setId(Integer.valueOf(id));
                msg = damageService.modifyDamageDetail(damageDetail);
            } else {
                //新增
                msg = damageService.addDamageDetail(damageDetail);
            }
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 删除受损程度详情
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteDamageDetail")
    public String deleteDamageDetail(HttpServletRequest request) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            //删除数据
            damageService.deleteDamageDetail(Integer.valueOf(id));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
