package com.ydh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: xxx.
 * @createDate: 2016/10/31.
 */

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{

    //首页
    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("home.main");
        /*try {

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }*/
        return mv;
    }

}
