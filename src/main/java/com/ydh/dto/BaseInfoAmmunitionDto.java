package com.ydh.dto;


/**
 * @description:基础资料-雷弹关系Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class BaseInfoAmmunitionDto extends BaseDto{
    //基础资料id
    private Integer biId;
    //雷弹id
    private Integer ammunitionId;
    //雷弹名称
    private String ammunitionName;
    //雷弹装载上限
    private Integer ammunitionCount;

    //装载时间（吊装时间）(小时)
    private Integer loadTime;

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

    public String getAmmunitionName() {
        return ammunitionName;
    }

    public void setAmmunitionName(String ammunitionName) {
        this.ammunitionName = ammunitionName;
    }

    public Integer getAmmunitionCount() {
        return ammunitionCount;
    }

    public void setAmmunitionCount(Integer ammunitionCount) {
        this.ammunitionCount = ammunitionCount;
    }

    public Integer getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Integer loadTime) {
        this.loadTime = loadTime;
    }
}
