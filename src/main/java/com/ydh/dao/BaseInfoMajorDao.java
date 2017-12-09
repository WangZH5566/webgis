package com.ydh.dao;

import com.ydh.dto.BaseInfoDto;
import com.ydh.dto.BaseInfoMajorDto;
import com.ydh.model.BasicInfoMajor;

import java.util.List;

/**
 * @description:基础资料专业Dao
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public interface BaseInfoMajorDao {

    /**
     * 查询全部专业
     * @return
     */
    public List<BaseInfoMajorDto> queryAll();

    /**
     * 查询专业总数量
     * @param searchDto
     * @return
     */
    public Integer queryCount(BaseInfoMajorDto searchDto);

    /**
     * 分页查询专业
     * @param searchDto
     * @return
     */
    public List<BaseInfoMajorDto> queryPage(BaseInfoMajorDto searchDto);

    /**
     * 根据id,查询专业
     * @param id 专业id
     * @return
     */
    public BaseInfoMajorDto queryById(Integer id);

    /**
     * 查询重复名称的专业
     * @param basicInfoMajor
     * @return
     */
    public List<BasicInfoMajor> queryRepeation(BasicInfoMajor basicInfoMajor);

    /**
     * 新增专业
     * @param basicInfoMajor
     * @return
     */
    public int addBaseInfoMajor(BasicInfoMajor basicInfoMajor);

    /**
     * 修改专业
     * @param basicInfoMajor
     * @return
     */
    public int modifyBaseInfoMajor(BasicInfoMajor basicInfoMajor);

    /**
     * 删除专业
     * @param id
     */
    public void deleteBaseInfoMajor(Integer id);
}
