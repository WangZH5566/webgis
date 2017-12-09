package com.ydh.model;

import java.math.BigDecimal;

/**
 * @description:基础资料实体-对应exec_basic_info表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class BasicInfo extends BaseModel{

    //名称
    private String infoName;
    //编号/弦号
    private String infoCode;
    //隶属单位
    private String belongUnit;
    //最大航速(节或公里/时)
    private BigDecimal maxSpeed;
    //续航能力（海里）
    private BigDecimal endurance;
    //作战半径（公里）
    private BigDecimal fightRadius;
    //满载排水量（吨）
    private BigDecimal maxDisplacement;
    //标准排水量（吨）
    private BigDecimal standardDisplacement;
    //最大起飞重量（吨）
    private BigDecimal maxTakeOffWeight;
    //生产研制单位
    private String developmentUnit;
    //服役日期
    private String serviceDate;
    //修理情况
    private String repairSituation;
    //图片路径
    private String imageUrl;
    //主要武备
    private String mainWeapons;
    //地址
    private String address;
    //经纬度
    private String longitudeAndLatitude;
    //技术状态（1,2,3级）
    private Integer technologySituation;
    //性能
    private String performance;
    //战备等级转进时间（小时）
    private Integer switchTime;
    //装载时间（吊装时间）(小时)
    private Integer loadTime;
    //所属专业id
    private Integer majorId;
    //数量
    private Integer count;
    //技术等级（0:技工、1:技师、2:高级技工）
    private Integer technologyLevel;
    //所属分类id
    private Integer typeId;
    //主要类别(0:舰船,1:飞机,2:岸导,3:仓储机构,4:修理机构,5:雷弹,6:机场,7:器材)
    private Integer mainType;

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public String getBelongUnit() {
        return belongUnit;
    }

    public void setBelongUnit(String belongUnit) {
        this.belongUnit = belongUnit;
    }

    public BigDecimal getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(BigDecimal maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BigDecimal getEndurance() {
        return endurance;
    }

    public void setEndurance(BigDecimal endurance) {
        this.endurance = endurance;
    }

    public BigDecimal getFightRadius() {
        return fightRadius;
    }

    public void setFightRadius(BigDecimal fightRadius) {
        this.fightRadius = fightRadius;
    }

    public BigDecimal getMaxDisplacement() {
        return maxDisplacement;
    }

    public void setMaxDisplacement(BigDecimal maxDisplacement) {
        this.maxDisplacement = maxDisplacement;
    }

    public BigDecimal getStandardDisplacement() {
        return standardDisplacement;
    }

    public void setStandardDisplacement(BigDecimal standardDisplacement) {
        this.standardDisplacement = standardDisplacement;
    }

    public BigDecimal getMaxTakeOffWeight() {
        return maxTakeOffWeight;
    }

    public void setMaxTakeOffWeight(BigDecimal maxTakeOffWeight) {
        this.maxTakeOffWeight = maxTakeOffWeight;
    }

    public String getDevelopmentUnit() {
        return developmentUnit;
    }

    public void setDevelopmentUnit(String developmentUnit) {
        this.developmentUnit = developmentUnit;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getRepairSituation() {
        return repairSituation;
    }

    public void setRepairSituation(String repairSituation) {
        this.repairSituation = repairSituation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMainWeapons() {
        return mainWeapons;
    }

    public void setMainWeapons(String mainWeapons) {
        this.mainWeapons = mainWeapons;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitudeAndLatitude() {
        return longitudeAndLatitude;
    }

    public void setLongitudeAndLatitude(String longitudeAndLatitude) {
        this.longitudeAndLatitude = longitudeAndLatitude;
    }

    public Integer getTechnologySituation() {
        return technologySituation;
    }

    public void setTechnologySituation(Integer technologySituation) {
        this.technologySituation = technologySituation;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public Integer getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(Integer switchTime) {
        this.switchTime = switchTime;
    }

    public Integer getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Integer loadTime) {
        this.loadTime = loadTime;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTechnologyLevel() {
        return technologyLevel;
    }

    public void setTechnologyLevel(Integer technologyLevel) {
        this.technologyLevel = technologyLevel;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }
}
