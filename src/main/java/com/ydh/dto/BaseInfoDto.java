package com.ydh.dto;

/**
 * @description:基础资料Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class BaseInfoDto extends BaseDto{
    //名称
    private String infoName;
    //编号/弦号
    private String infoCode;
    //隶属单位
    private String belongUnit;
    //最大航速(节或公里/时)
    private String maxSpeed;
    //续航能力（海里）
    private String endurance;
    //作战半径（公里）
    private String fightRadius;
    //满载排水量（吨）
    private String maxDisplacement;
    //标准排水量（吨）
    private String standardDisplacement;
    //最大起飞重量（吨）
    private String maxTakeOffWeight;
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

    //传参用(雷弹、器材关系)
    private String ammunitionIds;
    private String ammunitionCounts;
    private String equipmentIds;

    //页面显示用
    //所属专业名称
    private String majorName;

    //查询用
    private Integer[] ids;

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

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getEndurance() {
        return endurance;
    }

    public void setEndurance(String endurance) {
        this.endurance = endurance;
    }

    public String getFightRadius() {
        return fightRadius;
    }

    public void setFightRadius(String fightRadius) {
        this.fightRadius = fightRadius;
    }

    public String getMaxDisplacement() {
        return maxDisplacement;
    }

    public void setMaxDisplacement(String maxDisplacement) {
        this.maxDisplacement = maxDisplacement;
    }

    public String getStandardDisplacement() {
        return standardDisplacement;
    }

    public void setStandardDisplacement(String standardDisplacement) {
        this.standardDisplacement = standardDisplacement;
    }

    public String getMaxTakeOffWeight() {
        return maxTakeOffWeight;
    }

    public void setMaxTakeOffWeight(String maxTakeOffWeight) {
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

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getAmmunitionIds() {
        return ammunitionIds;
    }

    public void setAmmunitionIds(String ammunitionIds) {
        this.ammunitionIds = ammunitionIds;
    }

    public String getAmmunitionCounts() {
        return ammunitionCounts;
    }

    public void setAmmunitionCounts(String ammunitionCounts) {
        this.ammunitionCounts = ammunitionCounts;
    }

    public String getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(String equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
