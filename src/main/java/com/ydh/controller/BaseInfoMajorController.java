package com.ydh.controller;

import com.ydh.dto.BaseInfoMajorDto;
import com.ydh.model.BasicInfoMajor;
import com.ydh.service.BaseInfoMajorService;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:基础资料专业Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/baseInfo/baseInfoMajor")
public class BaseInfoMajorController extends BaseController {

    @Autowired
    private BaseInfoMajorService baseInfoMajorService;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/baseInfoMajorMain")
    public ModelAndView baseInfoMajorMain(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("baseInfoMajor.main");
        return mv;
    }

    /**
     * 查询专业总数量
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
            BaseInfoMajorDto searchDto = new BaseInfoMajorDto();
            //查询数据
            Integer count = baseInfoMajorService.queryCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询台位
     * @param request
     * @return
     */
    @RequestMapping("queryPage")
    public ModelAndView queryPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("baseInfoMajor.load");
        try {
            //接收参数
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            BaseInfoMajorDto searchDto = new BaseInfoMajorDto();
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<BaseInfoMajorDto> dtos = baseInfoMajorService.queryPage(searchDto);
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
     * 新增/修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEdit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("baseInfoMajor.addOrEdit");
        try {
            String id = request.getParameter("id");
            if(id != null && !"".equals(id)){
                BaseInfoMajorDto dto = baseInfoMajorService.queryById(Integer.valueOf(id));
                mv.addObject("dto",dto);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/saveBaseInfoMajor", method = {RequestMethod.POST})
    public String saveBaseInfoMajor(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            String na = request.getParameter("na");
            //组装参数
            BasicInfoMajor basicInfoMajor = new BasicInfoMajor();
            basicInfoMajor.setMajorName(na);
            if (id != null && !"".equals(id)) {
                //修改
                basicInfoMajor.setId(Integer.valueOf(id));
                msg = baseInfoMajorService.modifyBaseInfoMajor(basicInfoMajor);
            } else {
                //新增
                msg = baseInfoMajorService.addBaseInfoMajor(basicInfoMajor);
            }
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 删除专业
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteBaseInfoMajor")
    public String deleteBaseInfoMajor(HttpServletRequest request) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            //删除数据
            baseInfoMajorService.deleteBaseInfoMajor(Integer.valueOf(id));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
