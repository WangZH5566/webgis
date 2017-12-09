package com.ydh.controller;

import com.alibaba.fastjson.JSONArray;
import com.ydh.dto.*;
import com.ydh.enums.BasicInfoMainType;
import com.ydh.model.Execise;
import com.ydh.model.ExeciseFightTime;
import com.ydh.model.IconGroup;
import com.ydh.model.User;
import com.ydh.service.*;
import com.ydh.util.Constant;
import com.ydh.util.DateUtil;
import com.ydh.util.JSON;
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
 * @description:态势图Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/smap")
public class SituationMapController extends BaseController {

    @Autowired
    private SeaChartService seaChartService;
    @Autowired
    private ExeciseService execiseService;
    @Autowired
    private ExeciseIconService execiseIconService;
    @Autowired
    private DamageService damageService;
    @Autowired
    private ExeciseFightTimeService execiseFightTimeService;
    @Autowired
    private SituationAnnotationService situationAnnotationService;
    @Autowired
    private IconGroupService iconGroupService;
    @Autowired
    private ExeciseTroopService execiseTroopService;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    //首页
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("smap.main");
        try {
            User user = this.getLoginUser(request);
            ExeciseDto execiseDto = execiseService.queryById(user.getExecId());


            //查询添加图标所需数据
            List<Integer> mainTypeList = execiseTroopService.queryMainTypeByExecId(user.getExecId());
            Map<Integer,String> mainTypeMap = new HashMap<>();
            for(Integer mt : mainTypeList){
                mainTypeMap.put(mt, BasicInfoMainType.values()[mt].getText());
            }
            mv.addObject("mainTypeMap",mainTypeMap);
            /*List<IconGroup> igAllList=this.iconGroupService.queryIconGroup(null);
            List<IconGroup> igList = new ArrayList<IconGroup>();
            List<Integer> rootIdList = new ArrayList<Integer>();
            for(IconGroup ig : igAllList){
                if(ig.getPid().equals(0)){
                    rootIdList.add(ig.getId());
                }
            }
            for(IconGroup ig : igAllList){
                if(rootIdList.contains(ig.getPid())){
                    igList.add(ig);
                }
            }
            mv.addObject("igList",igList);*/
            //查询已标注的图标
            List<ExeciseIconDto> eiDtos = execiseIconService.findExeciseIconByExecId(user.getExecId());
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


            mv.addObject("eiDtos", JSONArray.toJSON(eiDtos));
            mv.addObject("visitpath", FILESERVER_ICON_VISITPATH);
            mv.addObject("stepLength", execiseDto.getStepLength() == null ? 1 : execiseDto.getStepLength());
            mv.addObject("isPause", execiseDto.getIsPause() == null ? 0 : execiseDto.getIsPause());
            mv.addObject("execsta", execiseDto.getExecStatus());
            mv.addObject("ftHideDigit", execiseDto.getFtHideDigit());
            mv.addObject("curUnit", user.getUnitId()==null?"":user.getUnitId());
            mv.addObject("isDirector", user.getIsDirector()==null?"0":user.getIsDirector());
            //当前作战时间(毫秒)
            long execft = execiseDto.getFightTime().getTime();
            if(!execiseDto.getExecStatus().equals(0)){
                Date etime = new Date();
                if(execiseDto.getEndTime() != null){
                    etime = execiseDto.getEndTime();
                }
                execft = execiseFightTimeService.getNowFightTime(user.getExecId(),etime);
            }
            mv.addObject("execft",execft);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @RequestMapping("/map")
    @ResponseBody
    public JSON map(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constant.LOGIN_USER);
        List<SeaChartDto> list = seaChartService.queryByUserId(user.getId());
        if (list == null || list.size() == 0) {
            return JSON.FAILURE.setResult(null);
        }
        return JSON.SUCCESS.setResult(list.get(0));
    }

    /**
     * 根据推演id查询海图-历史回顾用 create by xxx
     * @param request
     * @return
     */
    @RequestMapping("/execMap")
    @ResponseBody
    public JSON execMap(HttpServletRequest request) {
        try {
            String execId = request.getParameter("execId");
            List<SeaChartDto> list = seaChartService.querySeaChartByExecId(Integer.valueOf(execId));
            if (list == null || list.size() == 0) {
                return JSON.FAILURE.setResult(null);
            }
            return JSON.SUCCESS.setResult(list.get(0));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return JSON.FAILURE.setResult(null);
        }
    }

    /**
     * 获取推演的当前步长
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStepLength", method = {RequestMethod.POST})
    public String getStepLength(HttpServletRequest request, HttpServletResponse response) {
        String step = "1,5";
        try {
            User user = this.getLoginUser(request);
            ExeciseDto execiseDto = execiseService.queryById(user.getExecId());
            step = new StringBuffer(execiseDto.getStepLength().toString()).append(",").append(execiseDto.getExecStatus()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return step;
    }

    /**
     * 暂停推演
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pauseExecise", method = {RequestMethod.POST})
    public String pauseExecise(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            String isPause = request.getParameter("isPause");
            String step = request.getParameter("step");
            User user = this.getLoginUser(request);
            Execise exec = new Execise();
            exec.setId(user.getExecId());
            exec.setStepLength(step == null ? null : Integer.valueOf(step));
            exec.setIsPause(Integer.valueOf(isPause));
            execiseService.modifyExecisePause(exec);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/steps", method = {RequestMethod.POST})
    public List<ExeciseStepDto> steps(HttpServletRequest request, HttpServletResponse response) {
        String step = "1";
        try {
            User user = this.getLoginUser(request);
            return execiseService.findExeciseStepByExecId(user.getExecId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<ExeciseStepDto>();
    }

    /**
     * 作战时间跳转
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fightTime", method = {RequestMethod.POST})
    public String fightTime(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            String fbt = request.getParameter("fbt");
            String fet = request.getParameter("fet");
            ExeciseFightTime execiseFightTime = new ExeciseFightTime();
            execiseFightTime.setExecId(user.getExecId());
            execiseFightTime.setFightBeginTime(DateUtil.parseYYYYMMDDHHMMSS(fbt));
            execiseFightTime.setFightEndTime(DateUtil.parseYYYYMMDDHHMMSS(fet));
            execiseFightTime.setNowTime(new Date());
            execiseFightTimeService.addExeciseFightTime(execiseFightTime);

        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 获取推演的当前步长
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFightTime", method = {RequestMethod.POST})
    public String getFightTime(HttpServletRequest request, HttpServletResponse response) {
        long ft = 0L;
        try {
            User user = this.getLoginUser(request);
            ft = execiseFightTimeService.getNowFightTime(user.getExecId(), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(ft);
    }
}
