/* ========================================================================
 *             .----.
 *                            _.'__    `.
 *                        .--($)($$)---/&\
 *                      .' @          /&&&\
 *                      :         ,   &&&&&
 *                       `-..__.-' _.-\&&&/
 *                             `;_:    `"'
 *                           .'"""""`.
 *                          /,  野比 ,\
 *                         //  很穷!  \\
 *                         `-._______.-'
 *                         ___`. | .'___
 *                        (______|______)
 * ========================================================================*/

package com.ydh.controller;

import com.ydh.dto.FormulaDto;
import com.ydh.model.Formula;
import com.ydh.model.User;
import com.ydh.service.FormulaService;
import com.ydh.util.Constant;
import org.bluebear.jaf.antlr4.latex.Calculator;
import org.bluebear.jaf.antlr4.latex.VariableParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author thomas.chen (thomaschen750215 AT gmail.com) 2017-03-08
 * @version 0.9.0
 */
@Controller
@RequestMapping("/formula")
public class FormulaController extends BaseController {

    @Autowired
    private FormulaService service;

    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, Integer id) {
        ModelAndView mv = new ModelAndView("formula.main");
        return mv;
    }


    @ResponseBody
    @RequestMapping("/queryCount")
    public String queryCount(HttpServletRequest request) {
        int page = 0;
        try {
            //接收参数
            String na = request.getParameter("na");
            String id = request.getParameter("id");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            FormulaDto formulaDto = new FormulaDto();
            if (na != null) formulaDto.setName(na);
            if (id != null) formulaDto.setId(Integer.valueOf(id));
            //查询数据
            Integer count = service.queryCount(formulaDto);
            page = this.calToltalPage(count, Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(page);
    }

    @RequestMapping("queryPage")
    public ModelAndView queryPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("formula.load");
        try {
            //接收参数
            String na = request.getParameter("na");
            String id = request.getParameter("id");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");
            //组装查询参数
            FormulaDto formulaDto = new FormulaDto();
            if (na != null) formulaDto.setName(na);
            if (id != null) formulaDto.setId(Integer.valueOf(id));
            formulaDto.setPageNo(Integer.valueOf(pageNo));
            formulaDto.setPageSize(Integer.valueOf(pageSize));
            //查询数据
            List<Formula> dtos = service.queryPage(formulaDto);
            mv.addObject("dtos", dtos);
            mv.addObject("pageNo", Integer.valueOf(pageNo));
            mv.addObject("pageSize", Integer.valueOf(pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEdit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("formula.addOrEdit");
        try {
            String id = request.getParameter("id");
            if (id != null && !"".equals(id)) {
                //修改
                List<Formula> dtos = service.queryByIds(new Integer[]{Integer.valueOf(id)});
                mv.addObject("dto", dtos.get(0));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public String save(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            User user = this.getLoginUser(request);
            //接收参数
            String id = request.getParameter("id");
            String na = request.getParameter("na");
            String re = request.getParameter("re");
            String vl = request.getParameter("vl");
            //组装参数
            Formula formula = new Formula();
            formula.setName(na);
            formula.setRemark(re);
            formula.setVal(vl);
            if (id != null && !"".equals(id)) {
                //修改
                formula.setId(Integer.valueOf(id));
                msg = service.update(formula);
            } else {
                //新增
                msg = service.add(formula);
            }
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String msg = "success";
        try {
            //接收参数
            String id = request.getParameter("id");
            service.delete(Integer.valueOf(id));
        } catch (Exception e) {
            msg = Constant.OPERATE_FAILED;
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("/validate")
    public Set<String> validate(@RequestParam("expr") String val) {
        try {
            Map<String, BigDecimal> vairalbes = VariableParser.variables(val);
            return vairalbes.keySet();
        } catch (Exception e) {
            return new HashSet<String>();
        }
    }

    @ResponseBody
    @RequestMapping("/calculate")
    public BigDecimal calculate(@RequestParam("expr") String expr, HttpServletRequest request) {
        try {
            Map<String, List<BigDecimal>> parameters = new HashMap<String, List<BigDecimal>>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String name = paramNames.nextElement();
                if (name.startsWith("param_")) {
                    String value = request.getParameter(name);
                    name = name.replace("param_", "");
                    if (name.endsWith("i")) {
                        String[] splits = value.split(",");
                        List<BigDecimal> vals = new ArrayList<BigDecimal>();
                        for (String split : splits) {
                            vals.add(new BigDecimal(split.trim()));
                        }
                        parameters.put(name, vals);
                    } else {
                        parameters.put(name, ofBigDecial(new BigDecimal(value)));
                    }

                }
            }
            return Calculator.eval(expr, parameters);
        } catch (Exception e) {
            return null;
        }
    }

    private List<BigDecimal> ofBigDecial(BigDecimal... vals) {
        List<BigDecimal> list = new ArrayList<BigDecimal>();
        for (BigDecimal val : vals) {
            list.add(val);
        }
        return list;
    }
}
