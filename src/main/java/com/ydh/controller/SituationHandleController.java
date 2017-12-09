package com.ydh.controller;

import com.ydh.dto.ExeciseUserDto;
import com.ydh.dto.TelegramDto;
import com.ydh.dto.TelegramSendDto;
import com.ydh.model.*;
import com.ydh.service.*;
import com.ydh.util.Constant;
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
 * @description:文电收发Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/sh")
public class SituationHandleController extends BaseController{

    @Autowired
    private SituationHandleService situationHandleService;
    @Autowired
    private ExeciseService execiseService;
//    @Autowired
//    private ExeciseDepartmentService execiseDepartmentService;
    @Autowired
    private HistoryLogService historyLogService;

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
        ModelAndView mv = new ModelAndView("sh.main");
        try {
            User user = this.getLoginUser(request);
            //组装查询参数
            //TelegramDto searchDto = new TelegramDto();
            //searchDto.setReceiveBy(user.getId());
            //查询数据
            //List<TelegramDto> msgDtos = situationHandleService.queryReceiveTeleList(searchDto);
            //mv.addObject("msgDtos",msgDtos);
            mv.addObject("tvisitPath",tvisitPath);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 查询当前用户接收的文电总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryTeleReceiveCount")
    public String queryTeleReceiveCount(HttpServletRequest request){
        int page = 0;
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramSendDto searchDto = new TelegramSendDto();
            searchDto.setReceiveBy(user.getId());
            //查询数据
            Integer count = situationHandleService.queryTeleReceiveCount(searchDto);
            page = this.calToltalPage(count,Integer.valueOf(pageSize));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询查询当前用户接收的文电
     * @param request
     * @return
     */
    @RequestMapping("queryTeleReceivePage")
    public ModelAndView queryTeleReceivePage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("sh.receiveList");
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramSendDto searchDto = new TelegramSendDto();
            searchDto.setReceiveBy(user.getId());
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<TelegramSendDto> dtos = situationHandleService.queryTeleReceivePage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo", Integer.valueOf(pageNo));
            mv.addObject("pageSize",Integer.valueOf(pageSize));
            mv.addObject("tvisitPath",tvisitPath);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 查询当前用户发送的文电总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryTeleSendCount")
    public String queryTeleSendCount(HttpServletRequest request){
        int page = 0;
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramSendDto searchDto = new TelegramSendDto();
            searchDto.setSendBy(user.getId());
            //查询数据
            Integer count = situationHandleService.queryTeleSendCount(searchDto);
            page = this.calToltalPage(count,Integer.valueOf(pageSize));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询查询当前用户发送的文电
     * @param request
     * @return
     */
    @RequestMapping("queryTeleSendPage")
    public ModelAndView queryTeleSendPage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("sh.sendList");
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramSendDto searchDto = new TelegramSendDto();
            searchDto.setSendBy(user.getId());
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<TelegramSendDto> dtos = situationHandleService.queryTeleSendPage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo",Integer.valueOf(pageNo));
            mv.addObject("pageSize",Integer.valueOf(pageSize));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 查询当前用户发送的文电详情总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryTeleSendDetailCount")
    public String queryTeleSendDetailCount(HttpServletRequest request){
        int page = 0;
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String ttId = request.getParameter("ttId");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramSendDto searchDto = new TelegramSendDto();
            searchDto.setTelegramId(Integer.valueOf(ttId));
            searchDto.setSendBy(user.getId());
            //查询数据
            Integer count = situationHandleService.queryTeleSendDetailCount(searchDto);
            page = this.calToltalPage(count,Integer.valueOf(pageSize));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询查询当前用户发送的文电详情
     * @param request
     * @return
     */
    @RequestMapping("queryTeleSendDetailPage")
    public ModelAndView queryTeleSendDetailPage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("sh.sendDetailList");
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String ttId = request.getParameter("ttId");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            TelegramSendDto searchDto = new TelegramSendDto();
            searchDto.setTelegramId(Integer.valueOf(ttId));
            searchDto.setSendBy(user.getId());
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<TelegramSendDto> dtos = situationHandleService.queryTeleSendDetailPage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo",Integer.valueOf(pageNo));
            mv.addObject("pageSize",Integer.valueOf(pageSize));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return mv;
    }


    /**
     * 文电发送/转发页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/teleSend")
    public ModelAndView teleSend(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("sh.send");
        try {
            User user = this.getLoginUser(request);
            //转发时的文电id
            String ttid = request.getParameter("ttid");
            //树节点数据
            String zTreeNodes = null;
            if(user.getIsCrossUnit() == 1){
                //拥有跨单位发送文电的人员,查询所有单位
                List<ExeciseUnit> euList = execiseService.queryExeciseUnit(user.getExecId());
                //获取所有台位
                List<ExeciseDepartment> dpList = execiseService.queryExeciseDepartment(user.getExecId());
                //查询当前推演下所有人员
                ExeciseUserDto searchDto = new ExeciseUserDto();
                searchDto.setId(user.getId());
                searchDto.setExecId(user.getExecId());
                List<ExeciseUserDto> userList = execiseService.queryExecUserForSendTelegram(searchDto);
                if(user.getIsDirector() == null || user.getIsDirector().equals(0)){
                    filterDataByUnit(euList, dpList, userList, user.getUnitId());
                }
                zTreeNodes = generateTreeDataForAll(euList,dpList,userList,user.getUnitId(),user.getDepartmentId());
            }else{
                //反之,则只允许在单位内容发送文电
                List<ExeciseUnit> euList = execiseService.queryExeciseUnit(user.getExecId());
                //获取所有台位
                List<ExeciseDepartment> dpList = execiseService.queryExeciseDepartment(user.getExecId());
                //查询当前推演,当前单位下所有人员
                ExeciseUserDto searchDto = new ExeciseUserDto();
                searchDto.setId(user.getId());
                searchDto.setExecId(user.getExecId());
                searchDto.setUnitId(user.getUnitId());
                List<ExeciseUserDto> userList = execiseService.queryExecUserForSendTelegram(searchDto);
                zTreeNodes = generateTreeDataForUnitId(euList,dpList,userList,user.getUnitId(),user.getDepartmentId());
            }
            mv.addObject("zTreeNodes", zTreeNodes);
            //选择文电
            if(ttid != null){
                Telegram tele = situationHandleService.findByID(Integer.valueOf(ttid));
                mv.addObject("ttid",ttid);
                mv.addObject("tele",tele);
            }else{
                List<Telegram> teleList = situationHandleService.queryUserTelegram(user.getId());
                mv.addObject("teleList",teleList);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 发送文电
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendTelegram")
    public String sendTelegram(HttpServletRequest request){
        String msg = "success";
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String ttid = request.getParameter("ttid");                   //文电id
            String sendTo = request.getParameter("sendTo");               //主送人
            String copyTo = request.getParameter("copyTo");               //抄送人
            situationHandleService.sendTelegramBatch(Integer.valueOf(ttid), sendTo, copyTo, user.getId());
        }catch(Exception e){
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    /**
     * 回执文电
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/isReceipt")
    public String isReceipt(HttpServletRequest request,TelegramSend telegramSend){
        String msg = "success";
        try{
            //接收参数
            situationHandleService.modifyIsReadOrIsReccipt(telegramSend);
        }catch(Exception e){
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    /**
     * 已读文电
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/isRead")
    public String isRead(HttpServletRequest request){
        String msg = "success";
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String id = request.getParameter("id");
            TelegramSend telegramSend = new TelegramSend();
            telegramSend.setId(Integer.valueOf(id));
            telegramSend.setIsRead(1);
            situationHandleService.modifyIsReadOrIsReccipt(telegramSend);
        }catch(Exception e){
            msg = Constant.DATA_ABNORMAL;
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    /**
     * 查询所有文电
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryTele",method = {RequestMethod.POST})
    public Telegram queryTele(HttpServletRequest request){
        Telegram dto=null;
        try{
            String id = request.getParameter("id");
            dto=situationHandleService.findByID(Integer.parseInt(id));
//            mv.addObject("pageNo",Integer.valueOf(pageNo));
//            mv.addObject("pageSize",Integer.valueOf(pageSize));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return dto;
    }

    /**
     * 更新文电接收人
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/receiveTele")
    public String receiveTele(HttpServletRequest request){
        String msg = "success";
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String teleId = request.getParameter("teleId");
            String teleName = request.getParameter("teleName");
            String userId = request.getParameter("userId");
            String userName = request.getParameter("userName");
            //修改数据
            TelegramSend telegramSend = new TelegramSend();
            telegramSend.setTelegramId(teleId == null ? null : Integer.valueOf(teleId));
            telegramSend.setReceiveBy(userId == null ? null : Integer.valueOf(userId));
            telegramSend.setReceiveTime(new Date());
            telegramSend.setCreateBy(user.getId());
            telegramSend.setCreateTime(new Date());
            msg = situationHandleService.sendTelegram(telegramSend);
            HistoryLog log = new HistoryLog();
            log.setExecId(user.getExecId());
            log.setCreateBy(user.getId());
            log.setCreateTime(new Date());
            log.setContent("电文发送：电文名称[" + teleName + "]," + "接收人:" + userName);
            historyLogService.addHistoryLog(log);
        }catch(Exception e){
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    /**
     * 转发文电
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/forwardTele")
    public String forwardTele(HttpServletRequest request){
        String msg = "success";
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String telID = request.getParameter("telID");
            String userIDs = request.getParameter("userIDs");

            if(telID==null||userIDs==null||"".equals(telID)||"".equals(userIDs)){
                msg="数据异常";
            }else{
                Integer tID=Integer.parseInt(telID);
                String[] userID=userIDs.split(",");
                Date d=new Date();
                List<TelegramSend> list=new ArrayList<TelegramSend>();
                for(String id:userID){
                    TelegramSend telegramSend = new TelegramSend();
                    telegramSend.setTelegramId(tID);
                    telegramSend.setReceiveBy(id == null ? null : Integer.valueOf(id));
                    telegramSend.setReceiveTime(d);
                    telegramSend.setCreateBy(user.getId());
                    telegramSend.setCreateTime(d);
                    list.add(telegramSend);
                }

                msg = situationHandleService.forwardTelegram(list);
            }
        }catch(Exception e){
            msg = Constant.OPERATE_FAILED;
            logger.error(e.getMessage(),e);
        }
        return msg;
    }

    private void filterDataByUnit(List<ExeciseUnit> euList, List<ExeciseDepartment> dpList, List<ExeciseUserDto> userList, Integer unitId) {
        Map<Integer,ExeciseUnit> euMap=new HashMap<Integer,ExeciseUnit>();
        if(euList==null||euList.size()==0){
            return;
        }
        for(ExeciseUnit eu:euList){
            euMap.put(eu.getId(),eu);
        }
        ExeciseUnit currentUnit=euMap.get(unitId);
        //找出当前单位的根单位
        ExeciseUnit root=getRootParentID(euMap, currentUnit);
        Set<Integer> real_euIDset=new HashSet<Integer>();
        Iterator<ExeciseUnit> it = euList.iterator();
        while(it.hasNext()){
            ExeciseUnit eu=it.next();
            if(!isInRoot(euMap,root,eu)){
                //把不在当前根单位的节点剔除
                it.remove();
            }else{
                real_euIDset.add(eu.getId());
            }
        }
        Iterator<ExeciseUserDto> it_user = userList.iterator();
        while(it_user.hasNext()){
            ExeciseUserDto user=it_user.next();
            if(user.getUnitId()!=null&&!real_euIDset.contains(user.getUnitId())){
                it_user.remove();
            }
        }
    }

    private boolean isInRoot(Map<Integer,ExeciseUnit> map,ExeciseUnit root,ExeciseUnit unit){
        if(map==null||root==null||unit==null){
            return false;
        }
        if(unit.getId()!=null&&unit.getId().equals(root.getId())){
            return true;
        }
        ExeciseUnit parent=map.get(unit.getPid());
        return isInRoot(map,root,parent);
    }

    private ExeciseUnit getRootParentID(Map<Integer,ExeciseUnit> map,ExeciseUnit unit){
        if(map==null||unit==null){
            return unit;
        }
        ExeciseUnit eu=map.get(unit.getPid());
        if(eu==null) {
            return unit;
        }else{
            return getRootParentID(map,eu);
        }
    }


    /**
     * 针对拥有跨单位权限的人员生成树
     * @param euList 当前推演下所有单位
     * @param dpList 所有台位
     * @param userList 当前推演下所有人员(包括导演)
     * @param unitId 当前用户所属单位id
     * @param departmentId 当前用户所属部门id
     * @return
     */
    private String generateTreeDataForAll(List<ExeciseUnit> euList,List<ExeciseDepartment> dpList,List<ExeciseUserDto> userList,Integer unitId,Integer departmentId){
        StringBuffer sb = new StringBuffer("");
        if(euList != null && dpList != null && userList != null){
            for (ExeciseUnit eu : euList) {
                sb.append("{\"id\":\"").append("eu_").append(eu.getId()).append("\",")
                        .append("\"pId\":\"").append((eu.getPid() == null || eu.getPid() == 0) ? 0 : new StringBuffer("eu_").append(eu.getPid()).toString()).append("\",")
                        .append("\"name\":\"").append(eu.getUnitName()).append("\",")
                        .append("\"open\":\"true\",\"checked\":\"false\"},");
                for (ExeciseDepartment dp : dpList) {
                    if(eu.getId().equals(unitId) && dp.getId().equals(departmentId)){
                        continue;
                    }
                    if(!dp.getUnitId().equals(eu.getId())){
                        continue;
                    }
                    sb.append("{\"id\":\"").append(eu.getId()).append("_").append(dp.getId()).append("\",")
                            .append("\"pId\":\"").append("eu_").append(eu.getId()).append("\",")
                            .append("\"name\":\"").append(dp.getDepartmentName()).append("\",")
                            .append("\"open\":\"true\",\"checked\":\"false\"},");
                }
            }
            for (ExeciseUserDto u : userList) {
                if(u.getUnitId() == null || u.getDepartmentId() == null){
                    //导演
                    sb.insert(0, "{\"id\":\"d_1\",\"pId\":\"0\",\"name\":\"导演\",\"open\":\"true\",\"checked\":\"false\"},");
                    sb.append("{\"id\":\"").append(u.getId()).append("\",")
                            .append("\"pId\":\"").append("d_1").append("\",")
                            .append("\"name\":\"").append(u.getUserName()).append("\",")
                            .append("\"open\":\"true\",\"checked\":\"false\"},");
                }else{
                    sb.append("{\"id\":\"").append(u.getId()).append("\",")
                            .append("\"pId\":\"").append(u.getUnitId()).append("_").append(u.getDepartmentId()).append("\",")
                            .append("\"name\":\"").append(u.getUserName()).append("\",")
                            .append("\"open\":\"true\",\"checked\":\"false\"},");
                }

            }
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");
            sb.insert(0,"[");
            return sb.toString();
        }else{
            return "[]";
        }
    }

    /**
     * 针对拥只能在当前单位下发送文电的人员生成树
     * @param euList 当前推演下所有单位
     * @param dpList 所有台位
     * @param userList 当前推演下,当前单位下所有人员
     * @param unitId 当前用户所属单位id
     * @param departmentId 当前用户所属部门id
     * @return
     */
    private String generateTreeDataForUnitId(List<ExeciseUnit> euList,List<ExeciseDepartment> dpList,List<ExeciseUserDto> userList,Integer unitId,Integer departmentId){
        StringBuffer sb = new StringBuffer("");
        if(euList != null && dpList != null && userList != null){
            Map<Integer,ExeciseUnit> euMap = new HashMap<Integer,ExeciseUnit>();
            for(ExeciseUnit eu : euList){
                euMap.put(eu.getId(),eu);
            }
            List<ExeciseUnit> currentEu = new ArrayList<ExeciseUnit>();
            currentEu = recursionExeciseUnit(currentEu,euMap,unitId);

            for (ExeciseUnit eu : currentEu) {
                sb.append("{\"id\":\"").append("eu_").append(eu.getId()).append("\",")
                        .append("\"pId\":\"").append((eu.getPid() == null || eu.getPid() == 0) ? 0 : new StringBuffer("eu_").append(eu.getPid()).toString()).append("\",")
                        .append("\"name\":\"").append(eu.getUnitName()).append("\",")
                        .append("\"open\":\"true\",\"nocheck\":\"true\"},");
                if(eu.getId().equals(unitId)){
                    for (ExeciseDepartment dp : dpList) {
                        if(dp.getId().equals(departmentId) || !dp.getUnitId().equals(unitId)){
                            continue;
                        }
                        sb.append("{\"id\":\"").append(eu.getId()).append("_").append(dp.getId()).append("\",")
                                .append("\"pId\":\"").append("eu_").append(eu.getId()).append("\",")
                                .append("\"name\":\"").append(dp.getDepartmentName()).append("\",")
                                .append("\"open\":\"true\",\"checked\":\"false\"},");
                    }
                }
            }
            for (ExeciseUserDto u : userList) {
                if(u.getUnitId() == null || u.getDepartmentId() == null){
                    //导演
                    sb.insert(0, "{\"id\":\"d_1\",\"pId\":\"0\",\"name\":\"导演\",\"open\":\"true\",\"checked\":\"false\"},");
                    sb.append("{\"id\":\"").append(u.getId()).append("\",")
                            .append("\"pId\":\"").append("d_1").append("\",")
                            .append("\"name\":\"").append(u.getUserName()).append("\",")
                            .append("\"open\":\"true\",\"checked\":\"false\"},");
                }else{
                    sb.append("{\"id\":\"").append(u.getId()).append("\",")
                            .append("\"pId\":\"").append(u.getUnitId()).append("_").append(u.getDepartmentId()).append("\",")
                            .append("\"name\":\"").append(u.getUserName()).append("\",")
                            .append("\"open\":\"true\",\"checked\":\"false\"},");
                }

            }
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");
            sb.insert(0,"[");
            return sb.toString();
        }else{
            return "[]";
        }
    }

    public List<ExeciseUnit> recursionExeciseUnit(List<ExeciseUnit> currentEu,Map<Integer,ExeciseUnit> euMap,Integer unitId){
        if(unitId == null || euMap.get(unitId) == null){
            return currentEu;
        }
        ExeciseUnit eu = euMap.get(unitId);
        if(eu.getPid() != null){
            currentEu.add(eu);
            recursionExeciseUnit(currentEu,euMap,eu.getPid());
        }
        return currentEu;
    }
}
