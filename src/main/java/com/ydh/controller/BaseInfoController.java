package com.ydh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydh.dto.*;
import com.ydh.enums.BasicInfoMainType;
import com.ydh.model.*;
import com.ydh.service.BaseInfoMajorService;
import com.ydh.service.BaseInfoService;
import com.ydh.util.BaseInfoConstant;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:基础资料Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/baseInfo/baseInfo")
public class BaseInfoController extends BaseController {

    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private BaseInfoMajorService baseInfoMajorService;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/baseInfoMain")
    public ModelAndView baseInfoMain(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("baseInfo.main");
        try {
            List<BaseInfoTypeDto> dtos = baseInfoService.queryAllBasicInfoType();
            String treeNodes = generateTreeNodes(dtos);
            mv.addObject("zTreeNodes",treeNodes);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 查询基础资料总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryIcon")
    public String queryIcon(HttpServletRequest request){
        String iconPath = "";
        try {
            //接收参数
            String id = request.getParameter("id");
            BaseInfoTypeDto baseInfoTypeDto = baseInfoService.queryBaseInfoTypeById(Integer.valueOf(id));
            if(baseInfoTypeDto != null && baseInfoTypeDto.getIconPath() != null){
                iconPath = FILESERVER_ICON_VISITPATH + baseInfoTypeDto.getIconPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return iconPath;
    }

    /**
     * 查询基础资料总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryBaseInfoCount")
    public String queryBaseInfoCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String mt = request.getParameter("mt");
            String ids = request.getParameter("ids");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            BaseInfoDto searchDto = new BaseInfoDto();
            if(ids != null && !"".equals(ids)){
                String[] tmp = ids.split(",");
                Integer[] arr = new Integer[tmp.length];
                for(int i=0;i<tmp.length;i++){
                    arr[i] = Integer.valueOf(tmp[i]);
                }
                searchDto.setIds(arr);
            }
            searchDto.setSearchSql(BaseInfoConstant.selectSQL.get(mt));
            //查询数据
            Integer count = baseInfoService.queryBaseInfoCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询基础资料
     * @param request
     * @return
     */
    @RequestMapping("/queryBaseInfoPage")
    public ModelAndView queryBaseInfoPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("baseInfo.load");
        try {
            //接收参数
            String mt = request.getParameter("mt");
            String ids = request.getParameter("ids");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            BaseInfoDto searchDto = new BaseInfoDto();
            if(ids != null && !"".equals(ids)){
                String[] tmp = ids.split(",");
                Integer[] arr = new Integer[tmp.length];
                for(int i=0;i<tmp.length;i++){
                    arr[i] = Integer.valueOf(tmp[i]);
                }
                searchDto.setIds(arr);
            }
            searchDto.setSearchSql(BaseInfoConstant.selectSQL.get(mt));
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<BaseInfoDto> dtos = baseInfoService.queryBaseInfoPage(searchDto);
            mv.addObject("mt", mt);
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
     * 新增基础资料分类
     * @param request
     * @param response
     */
    @RequestMapping(value = "/addbaseInfoType", method = {RequestMethod.POST})
    public void addbaseInfoType(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pid = request.getParameter("pid");
            String mt = request.getParameter("mt");
            String np = request.getParameter("np");
            BasicInfoType basicInfoType = new BasicInfoType();
            basicInfoType.setTypeName("新增节点");
            basicInfoType.setPid(Integer.valueOf(pid));
            basicInfoType.setMainType(BasicInfoMainType.values()[Integer.valueOf(mt)]);
            basicInfoType.setNodePath(np);
            basicInfoType.setMainTypeValue(basicInfoType.getMainType().ordinal());
            baseInfoService.addBasicInfoType(basicInfoType);
            printJsonData(JSON.toJSONString(basicInfoType), response);
        }catch (Exception e){
            JSONObject json = new JSONObject();
            json.put("msg", Constant.OPERATE_FAILED);
            printJsonData(json.toJSONString(), response);
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 修改基础资料分类
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyBasicInfoType", method = {RequestMethod.POST})
    public String modifyBasicInfoType(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            String id = request.getParameter("id");
            String na = request.getParameter("na");
            String iconId = request.getParameter("iconId");
            BasicInfoType basicInfoType = new BasicInfoType();
            basicInfoType.setId(Integer.valueOf(id));
            basicInfoType.setTypeName(na);
            basicInfoType.setIconId(iconId == null ? null : Integer.valueOf(iconId));
            msg = baseInfoService.modifyBasicInfoType(basicInfoType);
        }catch (Exception e){
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    /**
     * 删除基础资料分类
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBasicInfoType", method = {RequestMethod.POST})
    public String deleteBasicInfoType(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            String ids = request.getParameter("ids");
            baseInfoService.deleteBasicInfoType(ids);
        }catch (Exception e){
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(),e);
        }
        return "success";
    }

    /**
     * 选择图标页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/baseInfoIcon")
    public ModelAndView baseInfoIcon(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("baseInfo.icon");
        try {
            String ti = request.getParameter("ti");
            mv.addObject("ti", ti);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 新增/修改基础资料
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addOrEditBaseInfo")
    public ModelAndView addOrEditBaseInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        try {
            String id = request.getParameter("id");
            String mt = request.getParameter("mt");             //主要分类
            String ti = request.getParameter("ti");             //父节点id
            mv.setViewName("baseInfo.addOrEdit" + mt);
            mv.addObject("id", id);
            mv.addObject("mt", mt);
            mv.addObject("ti", ti);
            if("7".equals(mt)){
                //查询专业分类
                List<BaseInfoMajorDto> baseInfoMajorDtos = baseInfoMajorService.queryAll();
                mv.addObject("baseInfoMajorDtos",baseInfoMajorDtos);
            }
            if(id != null && !"".equals(id)){
                BaseInfoDto dto = baseInfoService.queryById(Integer.valueOf(id));
                mv.addObject("dto",dto);
            }
            if(!"5".equals(mt) && !"6".equals(mt) && !"7".equals(mt)){
                List<BaseInfoDto> ammunitionDtos = baseInfoService.queryAmmunition(BasicInfoMainType.leidan.ordinal());
                List<BaseInfoDto> equipmentDtos = baseInfoService.queryEuqipment(BasicInfoMainType.qicai.ordinal());
                mv.addObject("ammunitionDtos",ammunitionDtos);
                mv.addObject("equipmentDtos",equipmentDtos);
                if(id != null && !"".equals(id)){
                    List<BaseInfoAmmunitionDto> baseInfoAmmunitionDtos = baseInfoService.queryAmmunitionByBaseInfoId(Integer.valueOf(id));
                    List<BaseInfoEuqipmentDto> baseInfoEquipmentDtos = baseInfoService.queryEuqipmentByBaseInfoId(Integer.valueOf(id));
                    Map<Integer,BaseInfoAmmunitionDto> amMap = new HashMap<Integer,BaseInfoAmmunitionDto>();
                    Map<Integer,BaseInfoEuqipmentDto> eqMap = new HashMap<Integer,BaseInfoEuqipmentDto>();
                    for(BaseInfoAmmunitionDto am : baseInfoAmmunitionDtos){
                        amMap.put(am.getAmmunitionId(),am);
                    }
                    for(BaseInfoEuqipmentDto eq : baseInfoEquipmentDtos){
                        eqMap.put(eq.getEuqipmentId(),eq);
                    }
                    mv.addObject("amMap",amMap);
                    mv.addObject("eqMap",eqMap);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }


    /**
     * 修改基础资料分类
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveBasicInfo", method = {RequestMethod.POST})
    public String saveBasicInfo(HttpServletRequest request,BaseInfoDto baseInfoDto,HttpServletResponse response) {
        String msg = "success";
        try {
            BasicInfo basicInfo = new BasicInfo();
            basicInfo.setId(baseInfoDto.getId());
            basicInfo.setMainType(baseInfoDto.getMainType());
            basicInfo.setTypeId(baseInfoDto.getTypeId());
            basicInfo.setInfoCode(baseInfoDto.getInfoCode());
            basicInfo.setInfoName(baseInfoDto.getInfoName());
            basicInfo.setBelongUnit(baseInfoDto.getBelongUnit());
            basicInfo.setMaxSpeed(baseInfoDto.getMaxSpeed() == null ? null : new BigDecimal(baseInfoDto.getMaxSpeed()));
            basicInfo.setEndurance(baseInfoDto.getEndurance() == null ? null : new BigDecimal(baseInfoDto.getEndurance()));
            basicInfo.setFightRadius(baseInfoDto.getFightRadius() == null ? null : new BigDecimal(baseInfoDto.getFightRadius()));
            basicInfo.setMaxDisplacement(baseInfoDto.getMaxDisplacement() == null ? null : new BigDecimal(baseInfoDto.getMaxDisplacement()));
            basicInfo.setStandardDisplacement(baseInfoDto.getStandardDisplacement() == null ? null : new BigDecimal(baseInfoDto.getStandardDisplacement()));
            basicInfo.setMaxTakeOffWeight(baseInfoDto.getMaxTakeOffWeight() == null ? null : new BigDecimal(baseInfoDto.getMaxTakeOffWeight()));
            basicInfo.setDevelopmentUnit(baseInfoDto.getDevelopmentUnit());
            basicInfo.setServiceDate(baseInfoDto.getServiceDate());
            basicInfo.setImageUrl(baseInfoDto.getImageUrl());
            basicInfo.setRepairSituation(baseInfoDto.getRepairSituation());
            basicInfo.setMainWeapons(baseInfoDto.getMainWeapons());
            basicInfo.setAddress(baseInfoDto.getAddress());
            basicInfo.setLongitudeAndLatitude(baseInfoDto.getLongitudeAndLatitude());
            basicInfo.setTechnologySituation(baseInfoDto.getTechnologySituation());
            basicInfo.setPerformance(baseInfoDto.getPerformance());
            basicInfo.setSwitchTime(baseInfoDto.getSwitchTime());
            basicInfo.setLoadTime(baseInfoDto.getLoadTime());
            basicInfo.setMajorId(baseInfoDto.getMajorId());
            basicInfo.setCount(baseInfoDto.getCount());
            basicInfo.setTechnologyLevel(baseInfoDto.getTechnologyLevel());
            if(basicInfo.getId() != null){
                msg = baseInfoService.modifyBasicInfo(basicInfo,baseInfoDto);
            }else{
                msg = baseInfoService.addBasicInfo(basicInfo,baseInfoDto);
            }
        }catch (Exception e){
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    /**
     * 删除基础资料
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBaseInfo", method = {RequestMethod.POST})
    public String deleteBaseInfo(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            String id = request.getParameter("id");
            baseInfoService.deleteBasicInfoById(Integer.valueOf(id));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(), e);
        }
        return "success";
    }

    /**
     * 生成树节点
     * @param dtos
     * @return
     */
    private String generateTreeNodes(List<BaseInfoTypeDto> dtos){
        StringBuffer sb = new StringBuffer("");
        if (dtos == null || dtos.size() == 0) {
            return "[]";
        }
        sb.append("[");
        for (BaseInfoTypeDto dto : dtos) {
            sb.append("{\"id\":\"").append(dto.getId()).append("\",")
                    .append("\"pId\":\"").append((dto.getPid() == null || dto.getPid() == 0) ? 0 : dto.getPid()).append("\",")
                    .append("\"name\":\"").append(dto.getTypeName()).append("\",")
                    .append("\"type\":\"").append(dto.getMainType().ordinal()).append("\",")
                    .append("\"np\":\"").append(dto.getNodePath()).append("\",")
                    .append("\"open\":\"true\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
