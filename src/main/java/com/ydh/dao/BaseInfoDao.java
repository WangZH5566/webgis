package com.ydh.dao;

import com.ydh.dto.*;
import com.ydh.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:基础资料Dao
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public interface BaseInfoDao {

    /**
     * 查询全部BaseInfoTypeDto
     * @return
     */
    public List<BaseInfoTypeDto> queryAllBasicInfoType();

    /**
     * 根据id,查询BaseInfoType
     * @param id
     * @return
     */
    public BaseInfoTypeDto queryBaseInfoTypeById(Integer id);

    /**
     * 查询基础资料总数量
     * @param searchDto
     * @return
     */
    public Integer queryBaseInfoCount(BaseInfoDto searchDto);

    /**
     * 分页查询基础资料
     * @param searchDto
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoPage(BaseInfoDto searchDto);

    /**
     * 根据id查询基础资料
     * @param ids
     * @return
     */
    public List<ExeciseIcon> queryBaseInfoByIds(List<Integer> ids);

    /**
     * 新增基础资料类别
     * @param basicInfoType      基础资料类别实体
     * @return
     */
    public int addBasicInfoType(BasicInfoType basicInfoType);

    /**
     * 修改基础资料类别
     * @param basicInfoType      基础资料类别实体
     * @return
     */
    public int modifyBasicInfoType(BasicInfoType basicInfoType);

    /**
     * 查询重复名称的基础资料类别
     * @param basicInfoType
     * @return
     */
    public List<BasicInfoType> queryRepeation(BasicInfoType basicInfoType);

    /**
     * 删除基础资料类别
     * @param ids
     * @throws Exception
     */
    public void deleteBasicInfoType(Integer[] ids);

    /**
     * 根据基础资料类别id,删除基础资料
     * @param ids
     * @throws Exception
     */
    public void deleteBasicInfoByTypeIds(Integer[] ids);

    /**
     * 新增基础资料
     * @param basicInfo      基础资料实体
     * @return
     */
    public int addBasicInfo(BasicInfo basicInfo);

    /**
     * 修改基础资料
     * @param basicInfo      基础资料实体
     * @return
     */
    public int modifyBasicInfo(BasicInfo basicInfo);

    /**
     * 根据id,删除基础资料
     * @param id
     * @throws Exception
     */
    public void deleteBasicInfoById(Integer id);

    /**
     * 根据id查询基础资料
     * @return
     */
    public BaseInfoDto queryById(Integer id);

    /**
     * 查询全部雷弹基础资料
     * @return
     */
    public List<BaseInfoDto> queryAmmunition(Integer mainType);

    /**
     * 查询全部器材基础资料
     * @return
     */
    public List<BaseInfoDto> queryEuqipment(Integer mainType);

    /**
     * 查询当前基础资料所关联的所有雷弹
     * @param id 基础资料id
     * @return
     */
    public List<BaseInfoAmmunitionDto> queryAmmunitionByBaseInfoId(Integer id);

    /**
     * 查询当前基础资料所关联的所有器材
     * @param id 基础资料id
     * @return
     */
    public List<BaseInfoEuqipmentDto> queryEuqipmentByBaseInfoId(Integer id);

    /**
     * 新增基础资料-雷弹关系
     * @return
     */
    public int addBaseInfoAmmunition(BasicInfoAmmunition basicInfoAmmunition);

    /**
     * 新增基础资料-器材关系
     * @return
     */
    public int addBaseInfoEuqipment(BasicInfoEuqipment basicInfoEuqipment);

    /**
     * 删除基础资料-雷弹关系
     * @return
     */
    public int deleteBaseInfoAmmunition(Integer id);

    /**
     * 删除基础资料-器材关系
     * @return
     */
    public int deleteBaseInfoEuqipment(Integer id);

    /**
     * 查询图标关联的baseInfo
     * @param iconID
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoByIcon(Integer iconID);

    /**
     * 查询图标关联的baseInfo  态势图用
     * @param map
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoByIconWithoutIds(Map<String,Object> map);

    /**
     * 查询图标关联的baseInfo
     * @param map
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoWithoutIds(Map<String,Object> map);
}
