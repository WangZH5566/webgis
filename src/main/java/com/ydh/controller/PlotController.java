package com.ydh.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydh.dto.*;
import com.ydh.model.*;
import com.ydh.service.*;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/plot")
public class PlotController extends BaseController {

    @Autowired
    private IconService iconService;
    @Autowired
    private IconExtService iconExtService;
    @Autowired
    private IconGroupService iconGroupService;
    @Autowired
    private ExeciseIconService execiseIconService;
    @Autowired
    private ExeciseEquipmentService execiseEquipmentService;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private ExeciseService execiseService;
    @Autowired
    private ExeciseTroopService execiseTroopService;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    /**
     * 态势图上新增图标第一步:选择图标
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/chooseIcon")
    public ModelAndView chooseIcon(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.chooseIcon");
        try {
            String mt = request.getParameter("mt");
            mv.addObject("mt",mt);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 根据mainType从兵力表查询图标数量
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryIconListCountForMainType", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryIconListCountForMainType(HttpServletRequest request, HttpServletResponse response) {
        int page = 0;
        try {
            User user = this.getLoginUser(request);
            String mt = request.getParameter("mt");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseTroopDto searchDto = new ExeciseTroopDto();
            searchDto.setExecId(user.getExecId());
            searchDto.setMainType(Integer.valueOf(mt));
            //查询数据
            Integer count = execiseTroopService.queryExeciseTroopCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return String.valueOf(page);
    }

    /**
     * 根据mainType从兵力表分页查询图标
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryIconListPageForMainType", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView queryIconListPageForMainType(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.queryIcon");
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String mt = request.getParameter("mt");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseTroopDto searchDto = new ExeciseTroopDto();
            searchDto.setExecId(user.getExecId());
            searchDto.setMainType(Integer.valueOf(mt));
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<ExeciseTroopDto> dtos = execiseTroopService.queryExeciseTroopPage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("visitpath", FILESERVER_ICON_VISITPATH);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
        /*mv.addObject("FILESERVER_ICON_VISITPATH",FILESERVER_ICON_VISITPATH);
        List<IconDto> list=this.iconService.queryIconListPage(searchDto);
        mv.addObject("list",list);
        mv.addObject("searchDto",searchDto);
        return mv;*/
    }



    /**
     * 态势图上新增图标第二步:新增图标-查询图标下的基础资料
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping("/addIcon")
    public ModelAndView addIcon(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.addIcon");
        try {
            User user = this.getLoginUser(request);
            String iconId = request.getParameter("iconId");
            //查询已经使用的baseInfo
            List<ExeciseIconDto> dtos = execiseIconService.findExeciseIconByExecIdForUsed(user.getExecId());
            List<Integer> ids = new ArrayList<Integer>();
            for(ExeciseIconDto d : dtos){
                if(d.getMainType() != null){
                    ids.add(d.getBaseInfoId());
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("iconId",Integer.valueOf(iconId));
            if(ids.size() > 0){
                map.put("ids",ids);
            }
            List<BaseInfoDto> list = this.baseInfoService.queryBaseInfoByIconWithoutIds(map);
            mv.addObject("baseInfoList",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }*/

    /**
     * 个人标注上新增图标第二步:新增图标-查询图标下的基础资料
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping("/addIcon_user")
    public ModelAndView addIcon_user(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.addIcon_user");
        try {
            String iconId = request.getParameter("iconId");
            List<BaseInfoDto> list=this.baseInfoService.queryBaseInfoByIcon(Integer.valueOf(iconId));
            mv.addObject("baseInfoList",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }*/

    @RequestMapping("/addIcon_user_new")
    public ModelAndView addIcon_user_new(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.addIcon_user_new");
        /*try {
            String iconId = request.getParameter("iconId");
            List<BaseInfoDto> list=this.baseInfoService.queryBaseInfoByIcon(Integer.valueOf(iconId));
            mv.addObject("baseInfoList",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }*/
        return mv;
    }

    /**
     * 态势图上新增图标第二步:新增图标-查询该基础资料的雷弹数据
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping("/iconAmmunitionSetting")
    public ModelAndView iconAmmunitionSetting(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.iconAmmunitionSetting");
        try {
            String biId = request.getParameter("biId");
            String isPl = request.getParameter("isPl");
            List<BaseInfoAmmunitionDto> list=this.baseInfoService.queryAmmunitionByBaseInfoId(Integer.valueOf(biId));
            mv.addObject("list",list);
            mv.addObject("biId",biId);
            if(isPl != null && !"".equals(isPl)){
                mv.addObject("isPl",isPl);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }*/

    /**
     * 态势图上新增图标第二步:新增图标-查询该基础资料的器材数据
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping("/iconEquipmentSetting")
    public ModelAndView iconEquipmentSetting(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.iconEquipmentSetting");
        try {
            String biId = request.getParameter("biId");
            List<BaseInfoEuqipmentDto> list=this.baseInfoService.queryEuqipmentByBaseInfoId(Integer.valueOf(biId));
            mv.addObject("list",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }*/

    /**
     * 态势图上新增图标第二步:新增图标-查询该基础资料的权限树
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping(value = "/iconRightSetting")
    public ModelAndView iconRightSetting(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.iconRightSetting");
        try {
            User user = this.getLoginUser(request);
            List<ExeciseUnit> euList = execiseService.queryExeciseUnit(user.getExecId());
            JSONArray ja=(JSONArray)JSONArray.toJSON(euList);
            mv.addObject("nodes",ja==null?new JSONArray():ja);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }*/

    /**
     * 态势图上新增图标第二步:新增图标(机场)-查询未使用的飞机
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping(value = "/iconPlaneSetting")
    public ModelAndView iconPlaneSetting(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.iconPlaneSetting");
        try {
            User user = this.getLoginUser(request);
            List<ExeciseIconDto> dtos = execiseIconService.findExeciseIconByExecIdForUsed(user.getExecId());
            List<Integer> ids = new ArrayList<Integer>();
            for(ExeciseIconDto d : dtos){
                if(d.getMainType() != null && d.getMainType().equals(1)){
                    ids.add(d.getBaseInfoId());
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("mt",1);
            if(ids.size() > 0){
                map.put("ids",ids);
            }
            List<BaseInfoDto> list = this.baseInfoService.queryBaseInfoWithoutIds(map);
            mv.addObject("baseInfoList",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }*/

    /**
     * 态势图-保存图标属性
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveExecIcon")
    public JSONObject saveExecIcon(HttpServletRequest request,HttpServletResponse response,String jsonString) {
        String msg = "success";
        JSONObject re_json=new JSONObject();
        re_json.put("msg","success");
        try {
            JSONObject json=JSONObject.parseObject(jsonString);
            User user = this.getLoginUser(request);
            json.put("createUser", user.getId());
            //新增
            ExeciseIcon icon = execiseIconService.addExecIcon(json);
            re_json.put("icon", icon);
            re_json.put("visitpath", FILESERVER_ICON_VISITPATH);
        } catch (Exception e) {
            re_json.put("msg", Constant.OPERATE_FAILED);
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return re_json;
    }


    /**
     * 态势图-保存文字属性
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveExecText")
    public JSONObject saveExecText(HttpServletRequest request,HttpServletResponse response,String jsonString) {
        String msg = "success";
        JSONObject re_json=new JSONObject();
        re_json.put("msg","success");
        try {
            JSONObject json=JSONObject.parseObject(jsonString);
            User user = this.getLoginUser(request);
            json.put("createUser",user.getId());
            json.put("exec_id",user.getExecId());
            json.put("unit_id",user.getUnitId());
            //新增
            ExeciseIcon icon = execiseIconService.addExecText(json);
            re_json.put("icon", icon);
        } catch (Exception e) {
            re_json.put("msg",Constant.OPERATE_FAILED);
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return re_json;
    }

    /**
     * 获取推演图标的原始图标所属分组id
     * @param request
     * @return
     */
   /* @RequestMapping(value="/getIconGroup",method={RequestMethod.POST})
    @ResponseBody
    public String getIconGroup(HttpServletRequest request){
        String groupId = "";
        try{
            String id = request.getParameter("id");
            ExeciseIconDto dto = execiseIconService.findExeciseIconById(Integer.valueOf(id));
            groupId = dto.getIconGroupId().toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return groupId;
    }*/


    /**
     * 要图标绘-修改推演图标最新坐标
     * @param request
     * @return
     */
   /* @ResponseBody
    @RequestMapping("/modifyCoordinate")
    public String modifyCoordinate(HttpServletRequest request,HttpServletResponse response) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            String coordinate = request.getParameter("coordinate");
            ExeciseIcon execiseIcon = new ExeciseIcon();
            execiseIcon.setId((id == null || "".equals(id)) ? null : Integer.valueOf(id));
            execiseIcon.setNewestCoordinate(coordinate);
            execiseIcon.setLabelTime(new Date());
            execiseIconService.modifyExecIconData(execiseIcon);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }*/





    /**
     * 要图标绘-加载图标属性
     * @return
     */
    @RequestMapping(value = "/iconAttribute")
    public ModelAndView iconAttribute(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("plot.iconAttribute");
        String id = request.getParameter("id");
        String iconId = request.getParameter("iconId");
        IconDto dto = this.iconService.findByID(Integer.valueOf(iconId));
        Map<String,String> damageLevelMap = new HashMap<String,String>();
        if(dto.getDamageLevel() != null && !"".equals(dto.getDamageLevel()) && dto.getDamageLevelTime() != null && !"".equals(dto.getDamageLevelTime())){
            String[] dlArr = dto.getDamageLevel().split(",");
            String[] dltArr = dto.getDamageLevelTime().split(",");
            for(int i=0;i<dlArr.length;i++){
                damageLevelMap.put(dlArr[i],dltArr[i]);
            }
        }
        List<IconExt> extList=this.iconExtService.queryIconExt(dto.getId());
        if(id != null && !"".equals(id)){
            ExeciseIconDto eiDto = execiseIconService.findExeciseIconById(Integer.valueOf(id));
            mv.addObject("eiDto",eiDto);
        }
        mv.addObject("icon",dto);
        mv.addObject("damageLevelMap",damageLevelMap);
        mv.addObject("extList",extList);
        return mv;
    }
}
