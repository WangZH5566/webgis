package com.ydh.controller;

import com.ydh.dto.DepartmentDto;
import com.ydh.model.Department;
import com.ydh.model.User;
import com.ydh.service.DepartmentService;
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
 * @description:台位管理Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("department.main");
        /*try {
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }*/
        return mv;
    }

    /**
     * 查询台位总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCount")
    public String queryCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String na = request.getParameter("na");
            String co = request.getParameter("co");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            DepartmentDto searchDto = new DepartmentDto();
            searchDto.setDepartmentName(na);
            searchDto.setDepartmentCode(co);
            //查询数据
            Integer count = departmentService.queryCount(searchDto);
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
        ModelAndView mv = new ModelAndView("department.load");
        try {
            //接收参数
            String na = request.getParameter("na");
            String co = request.getParameter("co");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            DepartmentDto searchDto = new DepartmentDto();
            searchDto.setDepartmentName(na);
            searchDto.setDepartmentCode(co);
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<DepartmentDto> dtos = departmentService.queryPage(searchDto);
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
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEdit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("department.addOrEdit");
        try {
            String id = request.getParameter("id");
            if (id != null && !"".equals(id)) {
                //修改
                DepartmentDto dto = departmentService.queryById(Integer.valueOf(id));
                mv.addObject("dto",dto);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/saveDepartment", method = {RequestMethod.POST})
    public String saveDepartment(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String id = request.getParameter("id");
            String na = request.getParameter("na");
            String co = request.getParameter("co");
            String cu = request.getParameter("cu");
            //组装参数
            Department department = new Department();
            department.setDepartmentName(na);
            department.setDepartmentCode(co);
            department.setIsCrossUnit((cu == null || "".equals(cu))?0:Integer.valueOf(cu));
            if (id != null && !"".equals(id)) {
                //修改
                department.setId(Integer.valueOf(id));
                msg = departmentService.modifyDepartment(department);
            } else {
                //新增
                msg = departmentService.addDepartment(department);
            }
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDepartment", method = {RequestMethod.POST})
    public String deleteDepartment(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            departmentService.deleteDepartment(Integer.valueOf(id));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
