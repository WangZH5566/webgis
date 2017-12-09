package com.ydh.dao;

import com.ydh.dto.DepartmentDto;
import com.ydh.model.Department;

import java.util.List;

/**
 * @description:台位管理Dao
 * @author: xxx.
 * @createDate: 2016/11/10.
 */
public interface DepartmentDao {

    /**
     * 查询所有台位
     * @return
     */
    public List<Department> queryAll();

    /**
     * 查询所有台位
     * @return
     */
    public List<DepartmentDto> queryAllDepartment();

    /**
     * 根据id查询台位
     * @param ids
     * @return
     */
    public List<DepartmentDto> queryByIdArr(Integer[] ids);

    /**
     * 根据id查询台位
     * @param ids
     * @return
     */
    public List<DepartmentDto> queryByIdList(List<Integer> ids);

    /**
     * 查询台位总数量
     * @param searchDto
     * @return
     */
    public Integer queryCount(DepartmentDto searchDto);

    /**
     * 分页查询台位
     * @param searchDto
     * @return
     */
    public List<DepartmentDto> queryPage(DepartmentDto searchDto);

    /**
     * 根据id,查询台位
     * @param id 台位id
     * @return
     */
    public DepartmentDto queryById(Integer id);

    /**
     * 查询重复名称/代码的台位
     * @param department
     * @return
     */
    public List<Department> queryRepeation(Department department);

    /**
     * 新增台位
     * @param department
     * @return
     */
    public int addDepartment(Department department);

    /**
     * 修改台位
     * @param department
     * @return
     */
    public int modifyDepartment(Department department);

    /**
     * 删除台位
     * @param id      台位id
     * @return
     */
    public int deleteDepartment(Integer id);
}
