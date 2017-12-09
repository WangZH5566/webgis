package com.ydh.dao;

import com.ydh.dto.DepartmentDto;
import com.ydh.dto.FormulaDto;
import com.ydh.model.Department;
import com.ydh.model.Formula;

import java.util.List;

public interface FormulaDao {

    public List<Formula> queryAll();

    public List<Formula> queryByIds(Integer[] ids);

    public Integer queryCount(FormulaDto example);

    public List<Formula> queryPage(FormulaDto example);

    public int add(Formula formula);

    public int update(Formula formula);

    public int delete(Integer id);
}
