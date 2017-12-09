package com.ydh.controller;

import com.alibaba.fastjson.JSONArray;
import com.ydh.dto.HistoryLogDto;
import com.ydh.service.HistoryLogService;
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
 * @description:资料查询Controller
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/hl")
public class HistoryLogController extends BaseController{
    @Autowired
    private HistoryLogService historyLogService;

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, HistoryLogDto searchDto){
        ModelAndView mv = new ModelAndView("hl.main");
        try {
            searchDto.setExecId(this.getLoginUser(request).getExecId());
            Integer count=this.historyLogService.queryHistoryLogCountForPage(searchDto);
            mv.addObject("totalPage",this.calToltalPage(count,10));
            mv.addObject("execID", searchDto.getExecId());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/historyLogList",method = {RequestMethod.POST})
    public List<HistoryLogDto> historyLogList(HttpServletRequest request,HistoryLogDto searchDto){
        searchDto.setExecId(this.getLoginUser(request).getExecId());
        return this.historyLogService.queryHistoryLogPage(searchDto);
    }
}
