package com.ydh.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 推演图标数据Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseIconDto extends BaseDto{

    //原始图标id
    private Integer iconId;
    //图标名称
    private String iconName;
    //图标数据
    private String iconData;
    //速度
    private BigDecimal speed;
    private BigDecimal maxSpeed;
    //最新坐标
    private String newestCoordinate;
    //标注人(最新)
    private Integer labelBy;
    //标注时间(最新)
    private Date labelTime;
    //是否删除(0否1是)
    private Integer isDelete;
    //所属推演id
    private Integer execId;
    //图标类别(0:图标,1:文字)
    private Integer iconType;
    //所属机场
    private Integer belongAirport;

    //受损等级
    private Integer damage;
    //受损等级内容
    private String damageCont;
    //受损维修所需时间
    private Integer damageTime;
    //受损程度详情(id用逗号分隔)
    private String damageDetail;
    //受损程度详情内容
    private String damageDetailCont;
    //装载时间(小时)
    private Integer addEquipmentTime;

    //所属集群id
    private Integer crowdId;
    //所属集群名称
    private String crowdName;
    //是否旗舰/长机(0否1是)
    private Integer isMain;

    //集群详情内容
    private String crowdDetailCont;

    //原始图标id
    private Integer iconGroupId;
    private String colorArray;
    private Integer unitId;
    private Integer baseInfoId;
    private Integer mainType;
    private String speedUnit;

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconData() {
        return iconData;
    }

    public void setIconData(String iconData) {
        this.iconData = iconData;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(BigDecimal maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getNewestCoordinate() {
        return newestCoordinate;
    }

    public void setNewestCoordinate(String newestCoordinate) {
        this.newestCoordinate = newestCoordinate;
    }

    public Integer getLabelBy() {
        return labelBy;
    }

    public void setLabelBy(Integer labelBy) {
        this.labelBy = labelBy;
    }

    public Date getLabelTime() {
        return labelTime;
    }

    public void setLabelTime(Date labelTime) {
        this.labelTime = labelTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Integer getIconType() {
        return iconType;
    }

    public void setIconType(Integer iconType) {
        this.iconType = iconType;
    }

    public Integer getIconGroupId() {
        return iconGroupId;
    }

    public void setIconGroupId(Integer iconGroupId) {
        this.iconGroupId = iconGroupId;
    }

    public String getColorArray() {
        return colorArray;
    }

    public void setColorArray(String colorArray) {
        this.colorArray = colorArray;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getBaseInfoId() {
        return baseInfoId;
    }

    public void setBaseInfoId(Integer baseInfoId) {
        this.baseInfoId = baseInfoId;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public String getSpeedUnit() {
        return speedUnit;
    }

    public void setSpeedUnit(String speedUnit) {
        this.speedUnit = speedUnit;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getDamageTime() {
        return damageTime;
    }

    public void setDamageTime(Integer damageTime) {
        this.damageTime = damageTime;
    }

    public String getDamageDetail() {
        return damageDetail;
    }

    public void setDamageDetail(String damageDetail) {
        this.damageDetail = damageDetail;
    }

    public Integer getAddEquipmentTime() {
        return addEquipmentTime;
    }

    public void setAddEquipmentTime(Integer addEquipmentTime) {
        this.addEquipmentTime = addEquipmentTime;
    }

    public String getDamageCont() {
        return damageCont;
    }

    public void setDamageCont(String damageCont) {
        this.damageCont = damageCont;
    }

    public String getDamageDetailCont() {
        return damageDetailCont;
    }

    public void setDamageDetailCont(String damageDetailCont) {
        this.damageDetailCont = damageDetailCont;
    }

    public Integer getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public Integer getBelongAirport() {
        return belongAirport;
    }

    public void setBelongAirport(Integer belongAirport) {
        this.belongAirport = belongAirport;
    }

    public String getCrowdDetailCont() {
        return crowdDetailCont;
    }

    public void setCrowdDetailCont(String crowdDetailCont) {
        this.crowdDetailCont = crowdDetailCont;
    }
}
