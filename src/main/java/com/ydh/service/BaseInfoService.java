package com.ydh.service;

import com.ydh.dao.BaseInfoDao;
import com.ydh.dto.*;
import com.ydh.model.*;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:基础资料service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class BaseInfoService {

    @Autowired
    private BaseInfoDao baseInfoDao;

    /**
     * 查询全部BaseInfoTypeDto
     * @return
     */
    public List<BaseInfoTypeDto> queryAllBasicInfoType(){
        return baseInfoDao.queryAllBasicInfoType();
    }


    /**
     * 根据id,查询BaseInfoType
     * @param id
     * @return
     */
    public BaseInfoTypeDto queryBaseInfoTypeById(Integer id){
        return baseInfoDao.queryBaseInfoTypeById(id);
    }

    /**
     * 查询基础资料总数量
     * @param searchDto
     * @return
     */
    public Integer queryBaseInfoCount(BaseInfoDto searchDto){
        return baseInfoDao.queryBaseInfoCount(searchDto);
    }

    /**
     * 分页查询基础资料
     * @param searchDto
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoPage(BaseInfoDto searchDto){
        return baseInfoDao.queryBaseInfoPage(searchDto);
    }

    /**
     * 新增基础资料类别
     * @param basicInfoType      基础资料类别实体
     * @return
     * @throws Exception
     */
    public String addBasicInfoType(BasicInfoType basicInfoType) throws Exception {
        //数据验证
        String msg = validateBasicInfoType(basicInfoType);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        baseInfoDao.addBasicInfoType(basicInfoType);
        basicInfoType.setNodePath(new StringBuffer(basicInfoType.getNodePath()).append(",").append(basicInfoType.getId()).toString());
        BasicInfoType bity = new BasicInfoType();
        bity.setId(basicInfoType.getId());
        bity.setNodePath(basicInfoType.getNodePath());
        baseInfoDao.modifyBasicInfoType(bity);
        return "success";
    }

    /**
     * 修改基础资料类别
     * @param basicInfoType      基础资料类别实体
     * @return
     * @throws Exception
     */
    public String modifyBasicInfoType(BasicInfoType basicInfoType) throws Exception {
        //数据验证
        /*String msg = validateBasicInfoType(basicInfoType);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }*/
        baseInfoDao.modifyBasicInfoType(basicInfoType);
        return "success";
    }

    /**
     * 删除基础资料类别
     * @param ids
     * @throws Exception
     */
    public void deleteBasicInfoType(String ids) throws Exception {
        String[] arr = ids.split(",");
        Integer[] idsArr = new Integer[arr.length];
        for(int i=0;i<arr.length;i++){
            idsArr[i] = Integer.valueOf(arr[i]);
        }
        baseInfoDao.deleteBasicInfoByTypeIds(idsArr);
        baseInfoDao.deleteBasicInfoType(idsArr);
    }

    /**
     * 数据验证
     * @param basicInfoType     基础资料类别实体
     * @return
     * @throws Exception
     */
    private String validateBasicInfoType(BasicInfoType basicInfoType) throws Exception {
        if (basicInfoType == null) {
            return Constant.DATA_ABNORMAL;
        }
        if(basicInfoType.getTypeName()  == null){
            return "[分类名称]不能为空";
        }
        /*List<BasicInfoType> list = baseInfoDao.queryRepeation(basicInfoType);
        if(list != null && list.size() > 0){
            return "[分类名称]不能重复";
        }*/
        return "";
    }

    /**
     * 新增基础资料
     * @param basicInfo      基础资料实体
     * @return
     * @throws Exception
     */
    public String addBasicInfo(BasicInfo basicInfo,BaseInfoDto baseInfoDto) throws Exception {
        baseInfoDao.addBasicInfo(basicInfo);

        /*List<BasicInfoAmmunition> basicInfoAmmunitionList = new ArrayList<BasicInfoAmmunition>();
        List<BasicInfoPerson> basicInfoPersonList = new ArrayList<BasicInfoPerson>();
        List<BasicInfoEuqipment> basicInfoEuqipmentList = new ArrayList<BasicInfoEuqipment>();*/
        //基础资料-雷弹关系
        if(baseInfoDto.getAmmunitionIds() != null && !"".equals(baseInfoDto.getAmmunitionIds())
                && baseInfoDto.getAmmunitionCounts() != null && !"".equals(baseInfoDto.getAmmunitionCounts())){
            String[] amIdArr = baseInfoDto.getAmmunitionIds().split(",");
            String[] amCountArr = baseInfoDto.getAmmunitionCounts().split(",");
            for(int i=0;i<amIdArr.length;i++){
                BasicInfoAmmunition basicInfoAmmunition = new BasicInfoAmmunition();
                basicInfoAmmunition.setBiId(basicInfo.getId());
                basicInfoAmmunition.setAmmunitionId(Integer.valueOf(amIdArr[i]));
                basicInfoAmmunition.setAmmunitionCount(Integer.valueOf(amCountArr[i]));
                //basicInfoAmmunitionList.add(basicInfoAmmunition);
                baseInfoDao.addBaseInfoAmmunition(basicInfoAmmunition);
            }
        }

        //基础资料-器材关系
        if(baseInfoDto.getEquipmentIds() != null && !"".equals(baseInfoDto.getEquipmentIds())){
            String[] eqIdArr = baseInfoDto.getEquipmentIds().split(",");
            for(int i=0;i<eqIdArr.length;i++){
                BasicInfoEuqipment basicInfoEuqipment = new BasicInfoEuqipment();
                basicInfoEuqipment.setBiId(basicInfo.getId());
                basicInfoEuqipment.setEuqipmentId(Integer.valueOf(eqIdArr[i]));
                //basicInfoEuqipmentList.add(basicInfoEuqipment);
                baseInfoDao.addBaseInfoEuqipment(basicInfoEuqipment);
            }
        }
        return "success";
    }

    /**
     * 修改基础资料
     * @param basicInfo      基础资料实体
     * @return
     * @throws Exception
     */
    public String modifyBasicInfo(BasicInfo basicInfo,BaseInfoDto baseInfoDto) throws Exception {
        baseInfoDao.modifyBasicInfo(basicInfo);
        //删除雷弹、人员、器材与基础资料的关系
        baseInfoDao.deleteBaseInfoAmmunition(basicInfo.getId());
        baseInfoDao.deleteBaseInfoEuqipment(basicInfo.getId());
        //基础资料-雷弹关系
        if(baseInfoDto.getAmmunitionIds() != null && !"".equals(baseInfoDto.getAmmunitionIds())
                && baseInfoDto.getAmmunitionCounts() != null && !"".equals(baseInfoDto.getAmmunitionCounts())){
            String[] amIdArr = baseInfoDto.getAmmunitionIds().split(",");
            String[] amCountArr = baseInfoDto.getAmmunitionCounts().split(",");
            for(int i=0;i<amIdArr.length;i++){
                BasicInfoAmmunition basicInfoAmmunition = new BasicInfoAmmunition();
                basicInfoAmmunition.setBiId(basicInfo.getId());
                basicInfoAmmunition.setAmmunitionId(Integer.valueOf(amIdArr[i]));
                basicInfoAmmunition.setAmmunitionCount(Integer.valueOf(amCountArr[i]));
                //basicInfoAmmunitionList.add(basicInfoAmmunition);
                baseInfoDao.addBaseInfoAmmunition(basicInfoAmmunition);
            }
        }

        //基础资料-器材关系
        if(baseInfoDto.getEquipmentIds() != null && !"".equals(baseInfoDto.getEquipmentIds())){
            String[] eqIdArr = baseInfoDto.getEquipmentIds().split(",");
            for(int i=0;i<eqIdArr.length;i++){
                BasicInfoEuqipment basicInfoEuqipment = new BasicInfoEuqipment();
                basicInfoEuqipment.setBiId(basicInfo.getId());
                basicInfoEuqipment.setEuqipmentId(Integer.valueOf(eqIdArr[i]));
                //basicInfoEuqipmentList.add(basicInfoEuqipment);
                baseInfoDao.addBaseInfoEuqipment(basicInfoEuqipment);
            }
        }
        return "success";
    }

    /**
     * 根据id,删除基础资料
     * @param id
     * @throws Exception
     */
    public void deleteBasicInfoById(Integer id) throws Exception {
        //删除雷弹、人员、器材与基础资料的关系
        baseInfoDao.deleteBaseInfoAmmunition(id);
        baseInfoDao.deleteBaseInfoEuqipment(id);
        //删除基础资料
        baseInfoDao.deleteBasicInfoById(id);
    }

    /**
     * 根据id查询基础资料
     * @return
     */
    public BaseInfoDto queryById(Integer id){
        return baseInfoDao.queryById(id);
    }

    /**
     * 查询全部雷弹基础资料
     * @return
     */
    public List<BaseInfoDto> queryAmmunition(Integer mainType){
        return baseInfoDao.queryAmmunition(mainType);
    }

    /**
     * 查询当前基础资料所关联的所有雷弹
     * @param id 基础资料id
     * @return
     */
    public List<BaseInfoAmmunitionDto> queryAmmunitionByBaseInfoId(Integer id){
        return baseInfoDao.queryAmmunitionByBaseInfoId(id);
    }

    /**
     * 查询全部器材基础资料
     * @return
     */
    public List<BaseInfoDto> queryEuqipment(Integer mainType){
        return baseInfoDao.queryEuqipment(mainType);
    }

    /**
     * 查询当前基础资料所关联的所有器材
     * @param id 基础资料id
     * @return
     */
    public List<BaseInfoEuqipmentDto> queryEuqipmentByBaseInfoId(Integer id){
        return baseInfoDao.queryEuqipmentByBaseInfoId(id);
    }

    /**
     * 查询图标关联的baseInfo
     * @param iconID
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoByIcon(Integer iconID){
        return this.baseInfoDao.queryBaseInfoByIcon(iconID);
    }

    /**
     * 查询图标关联的baseInfo  态势图用
     * @param map
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoByIconWithoutIds(Map<String,Object> map){
        return this.baseInfoDao.queryBaseInfoByIconWithoutIds(map);
    }

    /**
     * 查询飞机的baseInfo-创建机场时用
     * @param map
     * @return
     */
    public List<BaseInfoDto> queryBaseInfoWithoutIds(Map<String,Object> map){
        return baseInfoDao.queryBaseInfoWithoutIds(map);
    }
}
