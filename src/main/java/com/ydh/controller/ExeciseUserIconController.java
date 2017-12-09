package com.ydh.controller;

import com.alibaba.fastjson.JSONObject;
import com.ydh.enums.BasicInfoMainType;
import com.ydh.model.ExeciseUserIcon;
import com.ydh.model.IconGroup;
import com.ydh.model.User;
import com.ydh.service.ExeciseTroopService;
import com.ydh.service.ExeciseUserIconService;
import com.ydh.service.IconGroupService;
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
 * @description:态势标注Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/userIcon")
public class ExeciseUserIconController extends BaseController {

    @Autowired
    private ExeciseUserIconService execiseUserIconService;
    @Autowired
    private ExeciseTroopService execiseTroopService;
    @Value("${fileserver.icon.visitpath}")
    private String FILESERVER_ICON_VISITPATH;

    //首页
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, Integer id) {
        ModelAndView mv = new ModelAndView("userIcon.main");
        User user = this.getLoginUser(request);
        mv.addObject("FILESERVER_ICON_VISITPATH", FILESERVER_ICON_VISITPATH);
        ExeciseUserIcon icon=this.execiseUserIconService.findByID(id);
        mv.addObject("icon",icon==null?"{}": JSONObject.toJSON(icon));
        mv.addObject("uiid",id==null?"":id);

        //查询添加图标所需数据
        List<Integer> mainTypeList = execiseTroopService.queryMainTypeByExecId(user.getExecId());
        Map<Integer,String> mainTypeMap = new HashMap<>();
        for(Integer mt : mainTypeList){
            mainTypeMap.put(mt, BasicInfoMainType.values()[mt].getText());
        }
        mv.addObject("mainTypeMap",mainTypeMap);
        return mv;
    }

    @RequestMapping("/backupList")
    public ModelAndView backupList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("userIcon.backupList");
        User u = this.getLoginUser(request);
        List<ExeciseUserIcon> list=this.execiseUserIconService.queryListByUser(u.getId());
        mv.addObject("list",list);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendOrder(HttpServletRequest request, ExeciseUserIcon execiseUserIcon) {
        String msg = "success";
        try {
            User u = this.getLoginUser(request);
            execiseUserIcon.setExecId(u.getExecId());
            execiseUserIcon.setCreateBy(u.getId());
            execiseUserIcon.setCreateTime(new Date());
            if(execiseUserIcon.getId() != null){
                msg = this.execiseUserIconService.modifyExeciseUserIcon(execiseUserIcon);
            }else {
                msg = this.execiseUserIconService.addExeciseUserIcon(execiseUserIcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "保存出错，请刷新后重试";
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public JSON delete(HttpServletRequest request, Integer id) {
        JSON json = null;
        String msg = "SUCCESS";
        try {
            this.execiseUserIconService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "删除出错，请刷新后重试";
        }
        if ("SUCCESS".equals(msg)) {
            json = JSON.SUCCESS;
        } else {
            json = JSON.FAILURE;
            json.setMsg(msg);
        }
        return json;
    }
}
