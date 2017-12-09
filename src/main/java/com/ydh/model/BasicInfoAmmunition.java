package com.ydh.model;

/**
 * @description:基础资料-雷弹关系实体-对应exec_basic_info_ammunition表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class BasicInfoAmmunition extends BaseModel{

    //基础资料id
    private Integer biId;
    //雷弹id
    private Integer ammunitionId;
    //雷弹装载上限
    private Integer ammunitionCount;

    public Integer getBiId() {
        return biId;
    }

    public void setBiId(Integer biId) {
        this.biId = biId;
    }

    public Integer getAmmunitionId() {
        return ammunitionId;
    }

    public void setAmmunitionId(Integer ammunitionId) {
        this.ammunitionId = ammunitionId;
    }

    public Integer getAmmunitionCount() {
        return ammunitionCount;
    }

    public void setAmmunitionCount(Integer ammunitionCount) {
        this.ammunitionCount = ammunitionCount;
    }
}
