package com.ydh.dto;

/**
 * @description:基础资料-器材关系Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class BaseInfoEuqipmentDto extends BaseDto{
    //基础资料id
    private Integer biId;
    //器材id
    private Integer euqipmentId;
    //器材专业名称
    private String equipmentName;

    public Integer getBiId() {
        return biId;
    }

    public void setBiId(Integer biId) {
        this.biId = biId;
    }

    public Integer getEuqipmentId() {
        return euqipmentId;
    }

    public void setEuqipmentId(Integer euqipmentId) {
        this.euqipmentId = euqipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
