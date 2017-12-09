package com.ydh.service;

import com.ydh.dao.FormulaDao;
import com.ydh.dto.FormulaDto;
import com.ydh.model.Formula;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FormulaService {

    @Autowired
    private FormulaDao dao;

    public List<Formula> queryAll(){
        return dao.queryAll();
    }

    public Integer queryCount(FormulaDto formulaDto) {
        return dao.queryCount(formulaDto);
    }

    public List<Formula> queryPage(FormulaDto formulaDto) {
        return dao.queryPage(formulaDto);
    }

    public List<Formula> queryByIds(Integer[] ids) {
        return dao.queryByIds(ids);
    }

    public String add(Formula formula) throws Exception {
        //数据验证
        String msg = validateFormula(formula);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        dao.add(formula);
        return "success";
    }

    public String update(Formula formula) throws Exception {
        //数据验证
        String msg = validateFormula(formula);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        dao.update(formula);
        return "success";
    }


    public void delete(Integer id) throws Exception {
        //数据验证
        dao.delete(id);
    }

    private String validateFormula(Formula formula) throws Exception {
        if (formula == null) {
            return Constant.DATA_ABNORMAL;
        }
        if(formula.getName() == null){
            return "[公式名称]不能为空";
        }
        if(formula.getVal()  == null){
            return "[公式代码]不能为空";
        }
        return "";
    }
}
