package com.ydh.service;

import com.ydh.dao.DepartmentDao;
import com.ydh.dto.DepartmentDto;
import com.ydh.model.Department;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description:台位管理service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 查询所有台位
     * @return
     */
    public List<Department> queryAll(){
        return departmentDao.queryAll();
    }

    /**
     * 查询所有台位
     * @return
     */
    public List<DepartmentDto> queryAllDepartment(){
        return departmentDao.queryAllDepartment();
    }

    /**
     * 查询台位总数量
     * @param searchDto
     * @return
     */
    public Integer queryCount(DepartmentDto searchDto) {
        return departmentDao.queryCount(searchDto);
    }

    /**
     * 分页查询台位
     * @param searchDto
     * @return
     */
    public List<DepartmentDto> queryPage(DepartmentDto searchDto) {
        return departmentDao.queryPage(searchDto);
    }

    /**
     * 根据id,查询台位
     * @param id 台位id
     * @return
     */
    public DepartmentDto queryById(Integer id) {
        return departmentDao.queryById(id);
    }


    /**
     * 新增台位
     * @param department      台位实体
     * @return
     * @throws Exception
     */
    public String addDepartment(Department department) throws Exception {
        //数据验证
        String msg = validateDepartment(department);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        departmentDao.addDepartment(department);
        return "success";
    }

    /**
     * 修改台位
     * @param department      台位实体
     * @return
     * @throws Exception
     */
    public String modifyDepartment(Department department) throws Exception {
        //数据验证
        String msg = validateDepartment(department);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        departmentDao.modifyDepartment(department);
        return "success";
    }

    /**
     * 删除台位
     * @param id      台位id
     * @return
     * @throws Exception
     */
    public void deleteDepartment(Integer id) throws Exception {
        //数据验证
        departmentDao.deleteDepartment(id);
    }

    /**
     * 数据验证
     * @param department      台位实体
     * @return
     * @throws Exception
     */
    private String validateDepartment(Department department) throws Exception {
        if (department == null) {
            return Constant.DATA_ABNORMAL;
        }
        if(department.getDepartmentName()  == null){
            return "[台位名称]不能为空";
        }
        if(department.getDepartmentCode()  == null){
            return "[台位代码]不能为空";
        }
        List<Department> list = departmentDao.queryRepeation(department);
        if(list != null && list.size() > 0){
            for(Department d : list){
                if(d.getDepartmentName().equals(department.getDepartmentName())){
                    return "[台位名称]不能重复";
                }
                if(d.getDepartmentCode().equals(department.getDepartmentCode())){
                    return "[台位代码]不能重复";
                }
            }
        }
        return "";
    }
}
