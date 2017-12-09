package com.ydh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydh.model.TelegramTemplate;
import com.ydh.model.User;
import com.ydh.service.TelegramTemplateService;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:电文模板Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/tt")
public class TelegramTemplateController extends BaseController{

    @Autowired
    private TelegramTemplateService telegramTemplateService;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("tt.main");
        try {
            List<TelegramTemplate> ttList = telegramTemplateService.queryAll();
            //组装树节点
            String zTreeNodes = generateTree(ttList);
            mv.addObject("zTreeNodes",zTreeNodes);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    /**
     * 根据id查询
     * @param request
     * @return
     */
    @RequestMapping("/queryById")
    public void queryById(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        try{
            //接收参数
            String id = request.getParameter("id");
            //组装参数
            //修改数据
            TelegramTemplate tt = telegramTemplateService.queryById(id == null ? null : Integer.valueOf(id));
            String jsonString = JSON.toJSONString(tt);
            this.printJsonData(jsonString, response);
        }catch(Exception e){
            jsonObject.put("msg",Constant.OPERATE_FAILED);
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            this.printJsonData(jsonObject.toJSONString(), response);
        }
    }

    /**
     * 获取最新的树节点
     * @param request
     * @return
     */
    @RequestMapping("/getTreeNodes")
    public void getTreeNodes(HttpServletRequest request,HttpServletResponse response){
        String zTreeNodes = "[]";
        try{
            List<TelegramTemplate> ttList = telegramTemplateService.queryAll();
            //组装树节点
            zTreeNodes = generateTree(ttList);
            this.printJsonData(zTreeNodes, response);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            this.printJsonData(zTreeNodes, response);
        }
    }

    /**
     * 保存文电模板
     * @param request
     * @return
     */
    @RequestMapping("/saveTt")
    public void saveTt(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String pid = request.getParameter("pid");
            String name = request.getParameter("name");
            //组装参数
            TelegramTemplate tt = new TelegramTemplate();
            tt.setTname(name);
            tt.setPid(Integer.valueOf(pid));
            tt.setCreateBy(user.getId());
            //修改数据
            tt = telegramTemplateService.addTt(tt);
            String jsonString = JSON.toJSONString(tt);
            this.printJsonData(jsonString, response);
        }catch(Exception e){
            jsonObject.put("msg",Constant.OPERATE_FAILED);
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            this.printJsonData(jsonObject.toJSONString(), response);
        }
    }

    /**
     * 保存文电模板
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyTt")
    public String modifyTt(HttpServletRequest request,HttpServletResponse response){
        String msg = "";
        try{
            User user = this.getLoginUser(request);
            //接收参数
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            //组装参数
            TelegramTemplate tt = new TelegramTemplate();
            tt.setId(id == null ? null : Integer.valueOf(id));
            tt.setTname(name);
            tt.setPid(pid == null ? null : Integer.valueOf(pid));
            tt.setLastUpdateBy(user.getId());
            //修改数据
            msg = telegramTemplateService.modifyTt(tt);
        }catch(Exception e){
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 删除文电模板
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTt")
    public String deleteTt(HttpServletRequest request,HttpServletResponse response){
        String msg = "success";
        try{
            String ids = request.getParameter("ids");
            telegramTemplateService.deleteTt(ids);
        } catch(Exception e){
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 生成文电模板树节点
     * @param ttList
     * @return
     */
    private String generateTree(List<TelegramTemplate> ttList){
        StringBuffer sb = new StringBuffer("");
        if(ttList == null || ttList.size() == 0){
            return "[]";
        }
        sb.append("[");
        for(TelegramTemplate tt : ttList){
            sb.append("{\"id\":\"").append(tt.getId()).append("\",")
                .append("\"pId\":\"").append((tt.getPid() == null || tt.getPid() == 0) ? 0 : tt.getPid()).append("\",")
                .append("\"name\":\"").append(tt.getTname()).append("\",")
                .append("\"tpath\":\"").append(tt.getTpath()).append("\",")
                .append("\"thtmlpath\":\"").append(tt.getThtmlpath()).append("\",")
                .append("\"tstatus\":\"").append(tt.getTstatus()).append("\",")
                .append("\"open\":\"true\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
