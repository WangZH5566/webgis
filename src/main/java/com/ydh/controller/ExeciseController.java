package com.ydh.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydh.dto.*;
import com.ydh.model.*;
import com.ydh.service.*;
import com.ydh.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/exec")
public class ExeciseController extends BaseController {

    @Autowired
    private ExeciseService execiseService;
    @Autowired
    private SeaChartService seaChartService;
    @Autowired
    private ExeciseTroopService execiseTroopService;
    @Autowired
    private ExeciseIconService execiseIconService;
    @Autowired
    private IconGroupService iconGroupService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private HistoryLogService historyLogService;
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
        ModelAndView mv = new ModelAndView("exec.main");
        Map<Integer,String> statusMap=new TreeMap<Integer,String>();
        statusMap.put(Constant.EXEC_STATUS_INIT,Constant.getExecStatusText(Constant.EXEC_STATUS_INIT));
        statusMap.put(Constant.EXEC_STATUS__START,Constant.getExecStatusText(Constant.EXEC_STATUS__START));
        statusMap.put(Constant.EXEC_STATUS__END,Constant.getExecStatusText(Constant.EXEC_STATUS__END));
        mv.addObject("statusMap",statusMap);
        /*try {
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }*/
        return mv;
    }

    /**
     * 查询推演总数量
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryExecCount")
    public String queryExecCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String en = request.getParameter("en");
            String execStatus=request.getParameter("status");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseDto searchDto = new ExeciseDto();
            searchDto.setExeciseName(en);
            searchDto.setExecStatus((execStatus == null || "".equals(execStatus.trim())) ? null : Integer.parseInt(execStatus));
            //查询数据
            Integer count = execiseService.queryExecCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 分页查询推演
     *
     * @param request
     * @return
     */
    @RequestMapping("queryExecPage")
    public ModelAndView queryExecPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("exec.execLoad");
        try {
            //接收参数
            String en = request.getParameter("en");
            String execStatus=request.getParameter("status");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseDto searchDto = new ExeciseDto();
            searchDto.setExeciseName(en);
            searchDto.setExecStatus((execStatus==null||"".equals(execStatus.trim()))?null:Integer.parseInt(execStatus));
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<ExeciseDto> dtos = execiseService.queryExecPage(searchDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo", Integer.valueOf(pageNo));
            mv.addObject("pageSize", Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping("/findByLoginUser")
    public JSON findByLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constant.LOGIN_USER);
        ExeciseDto dto = execiseService.findByLoginUser(user.getId());
        return JSON.SUCCESS.setResult(dto);
    }

    /**
     * 删除推演
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteExec")
    public String deleteExec(HttpServletRequest request) {
        String msg = "success";
        try {
            //接收参数
            String ids = request.getParameter("ids");
            //删除数据
            execiseService.deleteExecise(ids);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 打印角色清单:根据推演id，查询推演人员(包括导演)
     * @param request
     * @return
     */
    @RequestMapping(value = "/printLoad", method = {RequestMethod.POST})
    public ModelAndView printLoad(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("exec.userPrint");
        try {
            //接收参数
            String id = request.getParameter("id");
            //组装查询参数
            ExeciseUserDto searchDto = new ExeciseUserDto();
            searchDto.setExecId(Integer.valueOf(id));
            //查询数据
            List<ExeciseUserDto> dtos = execiseService.queryExecUserPrint(searchDto);
            mv.addObject("dtos", dtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 单位人员
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/execUnitUser")
    public ModelAndView execUnitUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.unitUser");
        try {
            String id = request.getParameter("id");
            if (id != null && !"".equals(id)) {
                //修改
                List<ExeciseUnit> euList = execiseService.queryExeciseUnit(Integer.valueOf(id));
                //组装树节点
                String zTreeNodes = generateTree(euList);
                mv.addObject("id", id);
                mv.addObject("zTreeNodes", zTreeNodes);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 单位人员页面-查询推演人员总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryExecUnitUserCount")
    public String queryExecUnitUserCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String pageSize = request.getParameter("pageSize");
            String id = request.getParameter("id");
            String unitArray=request.getParameter("unitArray");
            //组装查询参数
            ExeciseUserDto searchDto = new ExeciseUserDto();
            searchDto.setExecId(Integer.valueOf(id));
            searchDto.setUnitArray(unitArray);
            //查询数据
            Integer count = execiseService.queryExecUserCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 单位人员页面-查询推演人员
     * @param request
     * @return
     */
    @RequestMapping("queryExecUnitUserPage")
    public ModelAndView queryExecUnitUserPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("exec.unitUserList");
        try {
            //接收参数
            String id = request.getParameter("id");
            String unitArray=request.getParameter("unitArray");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseUserDto searchDto = new ExeciseUserDto();
            searchDto.setExecId(Integer.valueOf(id));
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            searchDto.setUnitArray(unitArray);
            //查询数据
            List<ExeciseUserDto> dtos = execiseService.queryExecUserPage(searchDto);
            mv.addObject("dtos", dtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 新增-步骤1
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addStepOne")
    public ModelAndView addStepOne(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.addStepOne");
        try {
            String id = request.getParameter("id");
            //查询所有台位
            List<DepartmentDto> allDeDtos = departmentService.queryAllDepartment();
            mv.addObject("allDeDtos", allDeDtos);
            if (id != null && !"".equals(id)) {
                //修改
                ExeciseDto dto = execiseService.queryById(Integer.valueOf(id));
                List<ExeciseUnit> euList = execiseService.queryExeciseUnit(Integer.valueOf(id));
                List<ExeciseDepartment> edList = execiseService.queryExeciseDepartment(Integer.valueOf(id));
                Map<Integer,ExeciseDepartment> edMap = new HashMap<Integer,ExeciseDepartment>();
                for(ExeciseDepartment ed : edList){
                    edMap.put(ed.getDepartmentId(),ed);
                }
                //组装树节点
                String zTreeNodes = generateTree(euList);
                mv.addObject("dto", dto);
                mv.addObject("zTreeNodes", zTreeNodes);
                mv.addObject("edMap",edMap);
            } else {
                //新增
                //自动生成导演用户
                Date date = new Date();
                //当前日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String curDate = sdf.format(date);
                //当前日期下最大序列号
                Integer maxSerialNo = execiseService.queryMaxSerialNo(curDate);
                if (maxSerialNo == null) {
                    maxSerialNo = 0;
                }
                maxSerialNo++;
                String serialNo = maxSerialNo.toString();
                if (serialNo.length() < 3) {
                    serialNo = String.format("%03d", maxSerialNo);
                }
                //导演登录名
                StringBuffer sb = new StringBuffer("dy").append(curDate).append(serialNo);
                //组装数据
                ExeciseDto dto = new ExeciseDto();
                dto.setFtHideDigit(0);
                dto.setDirectorLoginName(sb.toString());
                dto.setDirectorUserName(sb.toString());
                dto.setDirectorPassword(String.valueOf(RandomUtils.generateRandomInt(100000,999999)));
                dto.setCurDate(curDate);
                dto.setSerialNo(maxSerialNo.toString());
                mv.addObject("dto", dto);
                mv.addObject("zTreeNodes", "[{id:1,pId:0,name:\"单位\"}]");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/startExecise", method = {RequestMethod.POST})
    public String startExecise(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            String step = request.getParameter("step");

            User user = this.getLoginUser(request);
            Integer execId=user.getExecId();

            ExeciseDto old=this.execiseService.queryById(execId);
            if(old==null){
                return "未找到任何推演，请刷新后重试";
            }
            if(old.getExecStatus()!=null&&old.getExecStatus()>Constant.EXEC_STATUS_INIT){
                return "当前推演为["+Constant.getExecStatusText(old.getExecStatus())+"]状态,无法重新开始";
            }

            Date now=new Date();
            Execise exec=new Execise();
            exec.setId(execId);
            exec.setBeginTime(now);
            exec.setFightTime(old.getFightTime());
            exec.setExecStatus(Constant.EXEC_STATUS__START);
            exec.setStepLength(Integer.valueOf(step));
            exec.setLastUpdateTime(now);
            exec.setLastUpdateBy(user.getId());
            this.execiseService.modifyExeciseStatus(exec);
            msg += "|" + now.getTime();
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/endExecise", method = {RequestMethod.POST})
    public String endExecise(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            Integer execId=user.getExecId();
            String endFightTime = request.getParameter("endFightTime");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            ExeciseDto old=this.execiseService.queryById(execId);
            if(old==null){
                return "未找到任何推演，请刷新后重试";
            }
            if(old.getExecStatus()==null||old.getExecStatus()<Constant.EXEC_STATUS__START){
                return "当前推演为["+Constant.getExecStatusText(old.getExecStatus())+"]状态,请先开始推演";
            }
            if(old.getExecStatus()!=null&&old.getExecStatus()>Constant.EXEC_STATUS__START){
                return "当前推演为["+Constant.getExecStatusText(old.getExecStatus())+"]状态,无法重复结束";
            }

            Date now=new Date();
            Execise exec=new Execise();
            exec.setId(execId);
            exec.setEndTime(now);
            exec.setEndFightTime(sdf.parse(endFightTime));
            exec.setExecStatus(Constant.EXEC_STATUS__END);
            exec.setLastUpdateTime(now);
            exec.setLastUpdateBy(user.getId());
            this.execiseService.modifyExeciseStatus(exec);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/stepSave", method = {RequestMethod.POST})
    public String stepSave(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            String step = request.getParameter("step");

            User user = this.getLoginUser(request);
            Integer execId=user.getExecId();

            ExeciseDto old=this.execiseService.queryById(execId);
            if(old==null){
                return "未找到任何推演，请刷新后重试";
            }
            if(old.getExecStatus()==null||old.getExecStatus()<Constant.EXEC_STATUS__START){
                return "当前推演为["+Constant.getExecStatusText(old.getExecStatus())+"]状态,请先开始推演";
            }
            if(old.getExecStatus()!=null&&old.getExecStatus()>Constant.EXEC_STATUS__START){
                return "当前推演为["+Constant.getExecStatusText(old.getExecStatus())+"]状态,无法重复结束";
            }

            Date now=new Date();
            Execise exec=new Execise();
            exec.setId(execId);
            exec.setStepLength(Integer.valueOf(step));
            exec.setLastUpdateTime(now);
            exec.setLastUpdateBy(user.getId());
            this.execiseService.modifyExeciseStatus(exec);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/saveStepOne", method = {RequestMethod.POST})
    public String saveStepOne(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String id = request.getParameter("id");
            String directorId = request.getParameter("directorId");
            String execName = request.getParameter("execName");
            String fightTime = request.getParameter("fightTime");
            String fthd = request.getParameter("fthd");
            String loginName = request.getParameter("loginName");
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String comment = request.getParameter("comment");
            String curDate = request.getParameter("curDate");
            String serialNo = request.getParameter("serialNo");
            String ids = request.getParameter("ids");
            String names = request.getParameter("names");
            String pids = request.getParameter("pids");
            String levels = request.getParameter("levels");
            //组装参数
            Execise execise = new Execise();
            execise.setExeciseName(execName);
            execise.setComment(comment);
            execise.setFightTime((fightTime == null || "".equals(fightTime)) ? null : DateUtil.parseYYYYMMDDHHMM(fightTime));
            execise.setFtHideDigit(Integer.valueOf(fthd));
            ExeciseUser execiseUser = new ExeciseUser();
            execiseUser.setLoginName(loginName);
            execiseUser.setUserName(userName);
            execiseUser.setOldPassword(password);
            execiseUser.setNewPassword(EncodeUtils.MD5Upper(password));
            execiseUser.setIsDirector(1);
            execiseUser.setCurDate(curDate);
            execiseUser.setSerialNo(Integer.valueOf(serialNo));
            if (id != null && !"".equals(id)) {
                //修改
                execise.setId(Integer.valueOf(id));
                execise.setLastUpdateBy(user.getId());
                execiseUser.setExecId(Integer.valueOf(id));
                execiseUser.setId(Integer.valueOf(directorId));
                msg = execiseService.modifyStepOne(execise,execiseUser,ids,names,pids,levels);
            } else {
                //新增
                execise.setCreateBy(user.getId());
                msg = execiseService.addStepOne(execise,execiseUser,ids,names,pids,levels);
            }
            /*if(msg.indexOf('|') > -1){
                request.getSession().setAttribute(Constant.EXEC_ID,msg.substring(msg.indexOf('|') + 1));
                msg = msg.substring(0,msg.indexOf('|'));
            }*/
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @RequestMapping("/addStepTwo")
    public ModelAndView addStepTwo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.addStepTwo");
        try {
            String id = request.getParameter("id");
            //查询所有台位
            List<DepartmentDto> allDeDtos = departmentService.queryAllDepartment();
            mv.addObject("allDeDtos", allDeDtos);
            if (id != null && !"".equals(id)) {
                //修改
                List<ExeciseUnit> euList = execiseService.queryExeciseUnit(Integer.valueOf(id));
                List<ExeciseDepartment> edList = execiseService.queryExeciseDepartment(Integer.valueOf(id));
                Map<Integer,ExeciseDepartment> edMap = new HashMap<Integer,ExeciseDepartment>();
                JSONObject jsonObject = new JSONObject();
                for(ExeciseDepartment ed : edList){
                    edMap.put(ed.getDepartmentId(),ed);
                    StringBuffer sb =  jsonObject.getString(ed.getUnitId().toString())==null?new StringBuffer():new StringBuffer(jsonObject.getString(ed.getUnitId().toString()));
                    if(sb.length() == 0){
                        sb.append(ed.getDepartmentId());
                    }else{
                        sb.append(",").append(ed.getDepartmentId());
                    }
                    jsonObject.put(ed.getUnitId().toString(),sb.toString());
                }
                //组装树节点
                String zTreeNodes = generateTree(euList);
                mv.addObject("zTreeNodes", zTreeNodes);
                mv.addObject("edMap",edMap);
                mv.addObject("unitDepartment",jsonObject.size()==0?"{}":jsonObject.toJSONString());
                mv.addObject("exec_id", id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/saveStepTwo", method = {RequestMethod.POST})
    public String saveStepTwo(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String id = request.getParameter("id");
            String unitDepartment = request.getParameter("unitDepartment");
            //组装参数
            if (id != null && !"".equals(id)) {
                msg = execiseService.saveStepTwo(Integer.valueOf(id),unitDepartment);
            }
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 新增-步骤3
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addStepThree")
    public ModelAndView addStepThree(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.addStepThree");
        try {
            String id = request.getParameter("id");
            mv.addObject("exec_id", id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 步骤3-根据推演id，查询推演人员(导演除外)总数量
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryExecUserCount")
    public String queryExecUserCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String pageSize = request.getParameter("pageSize");
            String id = request.getParameter("id");
            //组装查询参数
            ExeciseUserDto searchDto = new ExeciseUserDto();
            searchDto.setExecId(Integer.valueOf(id));
            searchDto.setIsDirector(0);
            //查询数据
            Integer count = execiseService.queryExecUserCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 步骤3-根据推演id，查询推演人员(导演除外)
     *
     * @param request
     * @return
     */
    @RequestMapping("queryExecUserPage")
    public ModelAndView queryExecUserPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("exec.userLoad");
        try {
            //接收参数
            String id = request.getParameter("id");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseUserDto searchDto = new ExeciseUserDto();
            searchDto.setExecId(Integer.valueOf(id));
            searchDto.setIsDirector(0);
            searchDto.setPageNo(Integer.valueOf(pageNo));
            searchDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<ExeciseUserDto> dtos = execiseService.queryExecUserPage(searchDto);
            mv.addObject("dtos", dtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 步骤3-修改推演用户信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyExeciseUser")
    public String modifyExeciseUser(HttpServletRequest request) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            String ln = request.getParameter("ln");
            String un = request.getParameter("un");
            String pa = request.getParameter("pa");
            String cu = request.getParameter("cu");
            //组装参数
            ExeciseUserDto execiseUserDto = new ExeciseUserDto();
            execiseUserDto.setId(Integer.valueOf(id));
            execiseUserDto.setLoginName(ln);
            execiseUserDto.setUserName(un);
            execiseUserDto.setOldPassword(pa);
            execiseUserDto.setNewPassword(EncodeUtils.MD5Upper(pa));
            execiseUserDto.setIsCrossUnit((cu == null || "".equals(cu)) ? 0 : Integer.valueOf(cu));
            //修改数据
            execiseService.modifyExeciseUser(execiseUserDto);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 新增-步骤4
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addStepFour")
    public ModelAndView addStepFour(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.addStepFour");
        try {
            String id = request.getParameter("id");
            if (id != null && !"".equals(id)) {
                ExeciseDto dto = execiseService.queryById(Integer.valueOf(id));
                mv.addObject("sea_chart_id", dto.getSeaChart());
            }
            mv.addObject("exec_id", id);
            //查询海图
            List<SeaChartDto> seaChartDtos = seaChartService.queryAll();
            mv.addObject("seaChartDtos", seaChartDtos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 步骤4-修改海图
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyExeciseSeaChart")
    public String modifyExeciseSeaChart(HttpServletRequest request) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            String scId = request.getParameter("scId");
            //组装参数
            ExeciseDto execiseDto = new ExeciseDto();
            execiseDto.setId(Integer.valueOf(id));
            execiseDto.setSeaChart(Integer.valueOf(scId));
            //修改数据
            execiseService.modifyExeciseSeaChart(execiseDto);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


    /**
     * 新增-步骤5
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addStepFive")
    public ModelAndView addStepFive(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.addStepFive");
        try {
            String id = request.getParameter("id");
            if (id != null && !"".equals(id)) {
                //查询全部图标树节点,过滤出第二级的
                List<IconGroup> igAllList=this.iconGroupService.queryIconGroup(null);
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
                mv.addObject("igList",igList);
                // 查询除当前推演外的所有推演
                List<ExeciseDto> execiseDtos = execiseService.queryExceptCurrent(Integer.valueOf(id));
                mv.addObject("execiseDtos",execiseDtos);
            }
            mv.addObject("exec_id", id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 步骤5-根据推演id，查询推演兵力总数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryExeciseTroopCount")
    public String queryExeciseTroopCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String pageSize = request.getParameter("pageSize");
            String id = request.getParameter("id");
            //组装查询参数
            ExeciseTroopDto searchDto = new ExeciseTroopDto();
            searchDto.setExecId(Integer.valueOf(id));
            //查询数据
            Integer count = execiseTroopService.queryExeciseTroopCount(searchDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    /**
     * 步骤5-根据推演id，分页查询推演兵力
     * @param request
     * @return
     */
    @RequestMapping("queryExeciseTroopPage")
    public ModelAndView queryExeciseTroopPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("exec.troopLoad");
        try {
            //接收参数
            String id = request.getParameter("id");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            ExeciseTroopDto searchDto = new ExeciseTroopDto();
            searchDto.setExecId(Integer.valueOf(id));
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
    }

    /**
     * 步骤5-:选择图标
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/chooseIcon")
    public ModelAndView chooseIcon(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.chooseIcon");
        try {
            String id = request.getParameter("id");
            List<IconGroup> igList=this.iconGroupService.queryAll();
            List<IconGroup> result = new ArrayList<IconGroup>();
            for(IconGroup ig : igList){
                if(ig.getId().equals(Integer.valueOf(id))){
                    ig.setPid(0);
                    result.add(ig);
                }
            }
            result = recursionIconGroup(igList,result,Integer.valueOf(id));
            StringBuffer sb = new StringBuffer("[");
            if(result.size() > 0){
                for(IconGroup ig : result){
                    sb.append("{\"id\":\"").append(ig.getId()).append("\",")
                            .append("\"pId\":\"").append((ig.getPid() == null || ig.getPid() == 0) ? 0 : ig.getPid()).append("\",")
                            .append("\"name\":\"").append(ig.getName()).append("\",")
                            .append("\"open\":\"true\"},");
                }
                sb.deleteCharAt(sb.length()-1);
            }
            sb.append("]");
            mv.addObject("zTreeNodes",sb.toString());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    private List<IconGroup> recursionIconGroup(List<IconGroup> igList,List<IconGroup> result,Integer pid){
        for(IconGroup ig : igList){
            if(ig.getPid().equals(pid)){
                result.add(ig);
                result = recursionIconGroup(igList,result,ig.getId());
            }
        }
        return result;
    }

    /**
     * 步骤5:新增图标-查询图标下的基础资料
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addIcon")
    public ModelAndView addIcon(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.addIcon");
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
    }

    /**
     * 步骤5:新增图标-查询该基础资料的雷弹数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/iconAmmunitionSetting")
    public ModelAndView iconAmmunitionSetting(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.iconAmmunitionSetting");
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
    }

    /**
     * 步骤5:新增图标-查询该基础资料的器材数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/iconEquipmentSetting")
    public ModelAndView iconEquipmentSetting(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.iconEquipmentSetting");
        try {
            String biId = request.getParameter("biId");
            List<BaseInfoEuqipmentDto> list=this.baseInfoService.queryEuqipmentByBaseInfoId(Integer.valueOf(biId));
            mv.addObject("list",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 步骤5:新增图标-查询该基础资料的权限树
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/iconRightSetting")
    public ModelAndView iconRightSetting(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.iconRightSetting");
        try {
            String execId = request.getParameter("execId");
            List<ExeciseUnit> euList = execiseService.queryExeciseUnit(Integer.valueOf(execId));
            JSONArray ja=(JSONArray)JSONArray.toJSON(euList);
            mv.addObject("nodes",ja==null?new JSONArray():ja);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 步骤5:新增图标(机场)-查询未使用的飞机
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/iconPlaneSetting")
    public ModelAndView iconPlaneSetting(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.iconPlaneSetting");
        try {
            String execId = request.getParameter("execId");
            List<ExeciseIconDto> dtos = execiseIconService.findExeciseIconByExecIdForUsed(Integer.valueOf(execId));
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
    }

    /**
     * 步骤5:新增图标-保存图标属性
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveExecIcon")
    public String saveExecIcon(HttpServletRequest request,HttpServletResponse response,String jsonString) {
        String msg = "success";
        try {
            JSONObject json=JSONObject.parseObject(jsonString);
            //新增
            msg = execiseTroopService.addExeciseTroop(json);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 步骤5:修改兵力
     * @param request
     * @return
     */
    @RequestMapping("/editTroopWin")
    public ModelAndView editTroopWin(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.editTroop");
        try {
            String troopId = request.getParameter("troopId");
            ExeciseTroopDto execiseTroopDto =  execiseTroopService.queryById(Integer.valueOf(troopId));
            mv.addObject("dto", JSONObject.toJSON(execiseTroopDto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 步骤5:修改兵力
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/editTroop")
    public String editTroop(HttpServletRequest request,HttpServletResponse response,String jsonString) {
        String msg = "success";
        try {
            JSONObject json=JSONObject.parseObject(jsonString);
            execiseTroopService.modifyExeciseTroop(json);
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 步骤5:删除兵力
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTroop")
    public String deleteTroop(HttpServletRequest request,HttpServletResponse response) {
        String msg = "success";
        try {
            String troopId = request.getParameter("troopId");
            //删除
            execiseTroopService.deleteExeciseTroopById(Integer.valueOf(troopId));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


    /**
     * 步骤5:兵力导入
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/troopImport")
    public String troopImport(HttpServletRequest request,HttpServletResponse response) {
        String msg = "success";
        try {
            String execId = request.getParameter("id");
            String currentExecId = request.getParameter("cid");
            //删除
            execiseTroopService.troopImport(Integer.valueOf(execId),Integer.valueOf(currentExecId));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


    /**
     * 历史回顾
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/execReview")
    public ModelAndView execReview(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("exec.review");
        try {
            String id = request.getParameter("id");
            ExeciseDto execiseDto = execiseService.queryById(Integer.valueOf(id));
            long execft = execiseDto.getFightTime().getTime();
            long execfte = execiseDto.getEndFightTime().getTime();
            mv.addObject("execft",execft);
            mv.addObject("execfte",execfte);

            //查询已标注的图标
            List<ExeciseIconDto> eiDtos = execiseIconService.findExeciseIconByExecId(Integer.valueOf(id));
            mv.addObject("eiDtos", JSONArray.toJSON(eiDtos));
            mv.addObject("visitpath", FILESERVER_ICON_VISITPATH);
            mv.addObject("execId", id);

            List<HistoryLogDto> historyLogDtos = historyLogService.queryByExecId(Integer.valueOf(id));
            mv.addObject("historyLogDtos", JSONArray.toJSON(historyLogDtos));
                        /*List<ExeciseIconDto> eiDtos = execiseIconService.findExeciseIconByExecId(Integer.valueOf(id));
            Object o = JSONArray.toJSON(eiDtos);
            mv.addObject("eiDtos", JSONArray.toJSON(eiDtos));
            mv.addObject("visitpath", FILESERVER_ICON_VISITPATH);

            mv.addObject("execId", id);*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    /**
     * 生成单位树节点
     *
     * @param euList
     * @return
     */
    private String generateTree(List<ExeciseUnit> euList) {
        StringBuffer sb = new StringBuffer("");
        if (euList == null || euList.size() == 0) {
            return "[]";
        }
        sb.append("[");
        for (ExeciseUnit eu : euList) {
            sb.append("{\"id\":\"").append("old_").append(eu.getId()).append("\",")
                    .append("\"pId\":\"").append((eu.getPid() == null || eu.getPid() == 0) ? 0 : new StringBuffer("old_").append(eu.getPid()).toString()).append("\",")
                    .append("\"name\":\"").append(eu.getUnitName()).append("\",")
                    .append("\"open\":\"true\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
