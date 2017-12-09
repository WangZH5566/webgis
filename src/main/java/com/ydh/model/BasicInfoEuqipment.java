package com.ydh.model;

/**
 * @description:基础资料-人员关系实体-对应exec_basic_info_person表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class BasicInfoEuqipment extends BaseModel{

    //基础资料id
    private Integer biId;
    //器材id
    private Integer euqipmentId;

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
}
