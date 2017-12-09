package com.ydh.controller;

import com.alibaba.fastjson.JSONArray;
import com.ydh.dto.*;
import com.ydh.model.*;
import com.ydh.service.*;
import com.ydh.util.Constant;
import com.ydh.util.DateUtil;
import com.ydh.util.JSON;
import freemarker.ext.beans.HashAdapter;
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
 * @description:态势标注Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/sa")
public class SituationAnnotationController extends BaseController {

    @Autowired
    private SituationAnnotationService situationAnnotationService;
    @Autowired
    private ExeciseIconService execiseIconService;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private DamageService damageService;
    @Autowired
    private FormulaService formulaService;


    //首页
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("sa.main");
        mv.addObject("FILESERVER_ICON_VISITPATH", FILESERVER_ICON_VISITPATH);
        return mv;
    }

    //装载动作添加装载物品
    @RequestMapping("/equipmentAdd")
    public ModelAndView equipmentAdd(HttpServletRequest request, HttpServletResponse response, Integer iconID) {
        ModelAndView mv = new ModelAndView("sa.equipmentAdd");
        List<ExeciseEquipmentDto> list = this.situationAnnotationService.queryIconEquipments(iconID);
        mv.addObject("list", list);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/execIconList", method = {RequestMethod.GET, RequestMethod.POST})
    public List<ExeciseIconDto> execIconList(HttpServletRequest request) {
        List<ExeciseIconDto> list = null;
        try {
            User u = this.getLoginUser(request);
            list = this.execiseIconService.findExeciseIconByExecId(u.getExecId());
        } catch (Exception e) {

        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/listOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON listOrder(HttpServletRequest request) {
        List<ExeciseOrder> list = null;
        try {
            User u = this.getLoginUser(request);
            String execId = request.getParameter("execId");
            list = this.situationAnnotationService.listOrder(execId == null ? u.getExecId() : Integer.valueOf(execId));
            return JSON.SUCCESS.setResult(list);
        } catch (Exception e) {
        }
        return JSON.FAILURE;
    }


    @ResponseBody
    @RequestMapping(value = "/sendOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON sendOrder(HttpServletRequest request, ExeciseOrder order) {
        JSON json = null;
        String msg = "SUCCESS";
        try {
            User u = this.getLoginUser(request);
            order.setExecId(u.getExecId());
            order.setOrderBy(u.getId());
            order.setBeginTime(new Date());
            this.situationAnnotationService.sendOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "指令发送出错，请刷新后重试";
        }
        if ("SUCCESS".equals(msg)) {
            json = JSON.SUCCESS;
        } else {
            json = JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteIcon", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON deleteIcon(HttpServletRequest request) {
        JSON json=null;
        String msg="SUCCESS";
        try{
            String id = request.getParameter("id");
            String mt = request.getParameter("mt");
            msg=this.execiseIconService.deleteIcon(Integer.valueOf(id),Integer.valueOf(mt));
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

    /**
     * 修改坐标(推演开始前能修改,开始后就不能修改了)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyLocation", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON modifyLocation(HttpServletRequest request) {
        JSON json=null;
        String msg="SUCCESS";
        try{
            String id = request.getParameter("id");
            String lot = request.getParameter("lot");           //经度
            String lat = request.getParameter("lat");           //纬度
            String coordinates = this.execiseIconService.modifyIconLocation(Integer.valueOf(id),lot,lat);
            json=JSON.SUCCESS;
            json.setMsg(msg);
            json.setResult(coordinates);
        }catch (Exception e){
            json=JSON.FAILURE;
            msg="删除失败，请刷新后重试";
            json.setMsg(msg);
        }
        return json;
    }

    /**
     * 查询图标基础资料
     * @param request
     * @return
     */
    @RequestMapping("/queryIconInfo")
    public ModelAndView queryBaseInfoPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.iconInfo");
        try {
            //接收参数
            String mt = request.getParameter("mt");
            String id = request.getParameter("id");
            //组装查询参数
            //查询数据
            BaseInfoDto dto = baseInfoService.queryById(Integer.valueOf(id));
            mv.addObject("mt", mt);
            mv.addObject("dto", dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 查询图标受损程度
     * @param request
     * @return
     */
    @RequestMapping("/queryIconDamage")
    public ModelAndView queryIconDamage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.iconDamage");
        try {
            //接收参数
            String id = request.getParameter("id");
            //组装查询参数
            //查询数据
            ExeciseIconDto dto = execiseIconService.findExeciseIconById(Integer.valueOf(id));
            List<DamageDto> damageDtos = damageService.queryAllDamage();
            List<DamageDetailDto> damageDetailDtos = damageService.queryAllDamageDetail();
            Map<Integer,List<DamageDetailDto>> damageDetailMap = new HashMap<Integer,List<DamageDetailDto>>();
            for(DamageDetailDto dd : damageDetailDtos){
                if(damageDetailMap.containsKey(dd.getPid())){
                    damageDetailMap.get(dd.getPid()).add(dd);
                }else{
                    List<DamageDetailDto> tmp = new ArrayList<DamageDetailDto>();
                    tmp.add(dd);
                    damageDetailMap.put(dd.getPid(),tmp);
                }
            }
            mv.addObject("dto", dto);
            mv.addObject("damageDtos", damageDtos);
            mv.addObject("damageDetailMap", damageDetailMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/saveIconDamage", method = {RequestMethod.POST})
    public String saveIconDamage(HttpServletRequest request) {
        String msg = "success";
        try {
            String id = request.getParameter("id");             //图标id
            String d = request.getParameter("d");               //受损程度id
            String dt = request.getParameter("dt");             //受损所需维修时间
            String dd = request.getParameter("dd");             //受损详情id(用逗号分隔)

            ExeciseIcon execiseIcon = new ExeciseIcon();
            execiseIcon.setId(Integer.valueOf(id));
            execiseIcon.setDamage(Integer.valueOf(d));
            execiseIcon.setDamageTime(Integer.valueOf(dt));
            execiseIcon.setDamageDetail(dd);
            this.execiseIconService.modifyExecIconDamage(execiseIcon);
        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/saveRepairBeginTime", method = {RequestMethod.POST})
    public long saveRepairBeginTime(HttpServletRequest request) {
        long mi = 0L;
        try {
            String id = request.getParameter("id");             //指令id
            String rbt = request.getParameter("rbt");           //维修开始时间(作战时间)(毫秒数)
            ExeciseOrder execiseOrder = new ExeciseOrder();
            execiseOrder.setId(Integer.valueOf(id));
            execiseOrder.setRepairBeginTime(new Date(Long.valueOf(rbt)));
            this.situationAnnotationService.modifyRepairBeginTime(execiseOrder);
            mi = Long.valueOf(rbt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mi;
    }

    @ResponseBody
    @RequestMapping(value = "/repairEnd", method = {RequestMethod.POST})
    public String repairEnd(HttpServletRequest request) {
        String msg = "success";
        try {
            String id = request.getParameter("id");             //图标id
            String oid = request.getParameter("oid");            //指令id
            ExeciseIcon execiseIcon = new ExeciseIcon();
            execiseIcon.setId(Integer.valueOf(id));
            this.execiseIconService.modifyExecIconDamage(execiseIcon);
            this.situationAnnotationService.modifyIsEnd(Integer.valueOf(oid));
        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/saveAddEquipmentBeginTime", method = {RequestMethod.POST})
    public long saveAddEquipmentBeginTime(HttpServletRequest request) {
        long mi = 0L;
        try {
            String id = request.getParameter("id");                 //指令id
            String aebt = request.getParameter("aebt");             //装载开始时间(作战时间)(毫秒数)
            ExeciseOrder execiseOrder = new ExeciseOrder();
            execiseOrder.setId(Integer.valueOf(id));
            execiseOrder.setAddEquipmentBeginTime(new Date(Long.valueOf(aebt)));
            this.situationAnnotationService.modifyAddEquipmentBeginTime(execiseOrder);
            mi = Long.valueOf(aebt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mi;
    }

    @ResponseBody
    @RequestMapping(value = "/addEquipmentEnd", method = {RequestMethod.POST})
    public String addEquipmentEnd(HttpServletRequest request) {
        String msg = "success";
        try {
            String id = request.getParameter("id");             //图标id
            String oid = request.getParameter("oid");            //指令id
            ExeciseIcon execiseIcon = new ExeciseIcon();
            execiseIcon.setId(Integer.valueOf(id));
            this.execiseIconService.modifyExecIconAddEquipmentTime(execiseIcon);
            this.situationAnnotationService.modifyIsEnd(Integer.valueOf(oid));
        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 集群设置
     * @param request
     * @return
     */
    @RequestMapping("/crowdSet")
    public ModelAndView crowdSet(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.crowdSet");
        try {
            User user = this.getLoginUser(request);
            //查询数据
            List<ExeciseIconCrowdDetailDto> iconDtos = situationAnnotationService.findIconForInsertCrowd(user.getExecId());
            mv.addObject("iconDtos", iconDtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 新增集群
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCrowd", method = {RequestMethod.POST})
    public String addCrowd(HttpServletRequest request) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String cn = request.getParameter("cn");             //集群名称
            String iconIds = request.getParameter("iconIds");   //图标id
            String mainId = request.getParameter("mainId");     //旗舰/长机id
            ExeciseIconCrowd execiseIconCrowd = new ExeciseIconCrowd();
            execiseIconCrowd.setCrowdName(cn);
            execiseIconCrowd.setExecId(user.getExecId());
            msg = situationAnnotationService.addCrowd(execiseIconCrowd,iconIds,Integer.valueOf(mainId));
            msg = new StringBuffer(msg).append("|").append(execiseIconCrowd.getId()).toString();
        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 集群查看
     * @param request
     * @return
     */
    @RequestMapping("/crowdView")
    public ModelAndView crowdView(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.crowdView");
        try {
            User user = this.getLoginUser(request);
            //查询数据
            List<ExeciseIconCrowdDto> crowdDtos = situationAnnotationService.findExeciseIconCrowdByExecId(user.getExecId());
            mv.addObject("crowdDtos", crowdDtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 集群图标详情
     * @param request
     * @return
     */
    /*@RequestMapping("/crowdDetail")
    public ModelAndView crowdDetail(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.crowdDetail");
        try {
            //接收参数
            String id = request.getParameter("id");
            //组装查询参数
            //查询数据
            List<ExeciseIconCrowdDetailDto> crowdDetailDtos = situationAnnotationService.findExeciseIconCrowdDetailByCrowdId(Integer.valueOf(id));
            mv.addObject("crowdDetailDtos", crowdDetailDtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }*/

    /**
     * 删除集群
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCrowd", method = {RequestMethod.POST})
    public String deleteCrowd(HttpServletRequest request) {
        StringBuffer msg = new StringBuffer("success|");
        try {
            String id = request.getParameter("id");             //集群id
            List<ExeciseIconCrowdDetailDto> crowdDetailDtos = situationAnnotationService.findExeciseIconCrowdDetailByCrowdId(Integer.valueOf(id));
            this.situationAnnotationService.deleteExeciseIconCrowdById(Integer.valueOf(id));
            for(ExeciseIconCrowdDetailDto d : crowdDetailDtos){
                msg.append(d.getIconId()).append(",");
            }
            msg.deleteCharAt(msg.length() - 1);
        } catch (Exception e) {
            msg = new StringBuffer(Constant.DATA_ABNORMAL);
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg.toString();
    }

    /**
     * 起飞页面
     * @param request
     * @return
     */
    @RequestMapping("/takeOffSet")
    public ModelAndView takeOffSet(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.takeOffSet");
        try {
            User user = this.getLoginUser(request);
            //查询当前机场下飞机数据
            String id = request.getParameter("id");             //机场id
            //查询当前机场下所有飞机图标
            List<ExeciseIconCrowdDetailDto> iconDtos = situationAnnotationService.findIconForInsertPlaneCrowd(Integer.valueOf(id));
            List<ExeciseIconCrowdDetailDto> takeOffDtos = new ArrayList<ExeciseIconCrowdDetailDto>();
            List<ExeciseIconCrowdDetailDto> unTakeOffDtos = new ArrayList<ExeciseIconCrowdDetailDto>();
            for(ExeciseIconCrowdDetailDto dto : iconDtos){
                if(dto.getCrowdId() == null){
                    unTakeOffDtos.add(dto);
                }else{
                    takeOffDtos.add(dto);
                }
            }
            mv.addObject("takeOffDtos", takeOffDtos);
            mv.addObject("unTakeOffDtos", unTakeOffDtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 新增起飞集群
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPlaneCrowd", method = {RequestMethod.POST})
    public String addPlaneCrowd(HttpServletRequest request) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String cn = request.getParameter("cn");             //集群名称
            String cs = request.getParameter("cs");             //航速
            String ca = request.getParameter("ca");             //航向
            String co = request.getParameter("co");             //起始坐标
            String ft = request.getParameter("ft");             //当前作战时间
            String iconIds = request.getParameter("iconIds");   //飞机图标id
            String mainId = request.getParameter("mainId");     //长机id

            ExeciseIconCrowd execiseIconCrowd = new ExeciseIconCrowd();
            execiseIconCrowd.setCrowdName(cn);
            execiseIconCrowd.setExecId(user.getExecId());
            msg = situationAnnotationService.addPlaneCrowd(execiseIconCrowd, iconIds, Integer.valueOf(mainId),cs,ca,co,ft,user.getId());
            if("success".equals(msg)){
                //查询该集群下的图标数据，返回到页面上
                String[] iconIdArr = iconIds.split(",");
                Integer[] ids = new Integer[iconIdArr.length];
                for(int i=0;i<iconIdArr.length;i++){
                    ids[i] = Integer.valueOf(iconIdArr[i]);
                }
                List<ExeciseIconDto> eiDtos = execiseIconService.findExeciseIconByIds(ids);
                //组装受损程度详情内容
                List<DamageDetailDto> ddDtos = damageService.queryAllDamageDetail();
                Map<Integer,DamageDetailDto> ddMap = new HashMap<Integer,DamageDetailDto>();
                for(DamageDetailDto dd : ddDtos){
                    ddMap.put(dd.getId(),dd);
                }
                //组装集群详情
                List<ExeciseIconCrowdDetailDto> icdDtos = situationAnnotationService.findDetailByExecId(user.getExecId());
                Map<Integer,List<ExeciseIconCrowdDetailDto>> icdMap = new HashMap<Integer, List<ExeciseIconCrowdDetailDto>>();
                if(icdDtos != null && icdDtos.size() > 0){
                    for(ExeciseIconCrowdDetailDto icd : icdDtos){
                        //查询结果顺序,按集群id、isMain排序
                        if(icd.getIsMain() != null && icd.getIsMain().equals(1)){
                            List<ExeciseIconCrowdDetailDto> tmp = new ArrayList<ExeciseIconCrowdDetailDto>();
                            tmp.add(icd);
                            icdMap.put(icd.getCrowdId(),tmp);
                        }else{
                            if(icdMap.get(icd.getCrowdId()) != null){
                                icdMap.get(icd.getCrowdId()).add(icd);
                            }
                        }
                    }
                }
                //key: 旗舰id  value: 集群信息
                Map<Integer,String> icdMsgMap = new HashMap<Integer,String>();
                for(Map.Entry<Integer,List<ExeciseIconCrowdDetailDto>> entry : icdMap.entrySet()){
                    List<ExeciseIconCrowdDetailDto> value = entry.getValue();
                    Integer isMainId = null;
                    StringBuffer sb = new StringBuffer();
                    for(ExeciseIconCrowdDetailDto icd : value){
                        if(icd.getIsMain() != null && icd.getIsMain().equals(1)){
                            isMainId = icd.getIconId();
                            sb.append(icd.getIconName()).append("(旗舰/长机)<br>");
                        }else{
                            sb.append(icd.getIconName()).append("<br>");
                        }
                    }
                    if(isMainId != null){
                        icdMsgMap.put(isMainId,sb.toString());
                    }
                }
                for(ExeciseIconDto ei : eiDtos){
                    if(ei.getDamageDetail() != null && !"".equals(ei.getDamageDetail())){
                        String[] tmpArr = ei.getDamageDetail().split(",");
                        StringBuffer sb = new StringBuffer();
                        for(String s : tmpArr){
                            sb.append(ddMap.get(Integer.valueOf(s)).getDamageContent()).append(",");
                        }
                        ei.setDamageDetailCont(sb.toString());
                    }
                    if(icdMsgMap.get(ei.getId())!= null){
                        ei.setCrowdDetailCont(icdMsgMap.get(ei.getId()));
                    }
                }
                msg = new StringBuffer(msg).append("|").append(JSONArray.toJSONString(eiDtos)).toString();
            }
        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


    /**
     * 降落页面
     * @param request
     * @return
     */
    @RequestMapping("/landingSet")
    public ModelAndView landingSet(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.landingSet");
        try {
            User user = this.getLoginUser(request);
            //查询数据
            String id = request.getParameter("id");             //机场id
            List<ExeciseIconCrowdDetailDto> crowdDtos = situationAnnotationService.findExeciseIconCrowdByIconId(Integer.valueOf(id));
            //查询当前用户权限下的所有机场
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("execId",user.getExecId());
            map.put("unitId",user.getUnitId());
            map.put("mt",6);
            List<ExeciseIconDto> airportDtos = execiseIconService.findExeciseIconByUnitIdAndMainType(map);
            mv.addObject("crowdDtos", crowdDtos);
            mv.addObject("airportDtos", airportDtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 集群降落
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addLandingOrder", method = {RequestMethod.POST})
    public String addLandingOrder(HttpServletRequest request) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String cid = request.getParameter("cid");           //集群id(集群降落时用)
            String fid = request.getParameter("fid");           //图标id(单机降落时用)
            String plid = request.getParameter("plid");         //机场id
            String mco = request.getParameter("mco");           //集群旗舰位置
            String aco = request.getParameter("aco");           //机场位置
            String ft = request.getParameter("ft");             //当前作战时间
            if(cid != null){
                msg = situationAnnotationService.addLandingOrder(Integer.valueOf(cid),Integer.valueOf(plid),mco,aco,ft,user.getId(),user.getExecId());
            }else if(fid != null){
                msg = situationAnnotationService.addLandingOrderSignle(Integer.valueOf(fid), Integer.valueOf(plid), mco, aco, ft, user.getId(), user.getExecId());
            }

        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 降落完成
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/landingEnd", method = {RequestMethod.POST})
    public String landingEnd(HttpServletRequest request) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String oid = request.getParameter("oid");             //指令id
            String fid = request.getParameter("fid");             //图标id
            String aid = request.getParameter("aid");             //机场id
            this.situationAnnotationService.modifyWhenLandingEnd(Integer.valueOf(oid),Integer.valueOf(fid),Integer.valueOf(aid));
        } catch (Exception e) {
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


    /**
     * 计算公式页面
     * @param request
     * @return
     */
    @RequestMapping("/formulaView")
    public ModelAndView formulaView(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sa.formulaView");
        try {
            //查询数据
            List<Formula> dtos = formulaService.queryAll();
            mv.addObject("dtos", dtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }
}
