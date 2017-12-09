package com.ydh.dto;

import com.ydh.model.BaseModel;

import java.math.BigDecimal;

/**
 * @description: 推演兵力数据Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseTroopDto extends BaseDto {

    //原始图标id
    private Integer iconId;
    //图标名称
    private String iconName;
    //图标数据
    private String iconData;
    //速度
    private BigDecimal speed;
    private BigDecimal maxSpeed;
    //速度单位  (km/kn)
    private String speedUnit;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //所属推演id
    private Integer execId;
    //所属机场
    private Integer belongAirport;
    //颜色变化数组
    private String colorArray;
    //baseInfoID
    private Integer baseInfoId;
    //类别
    private Integer mainType;
    //所属单位
    private Integer unitId;
    //移动角度
    private BigDecimal moveAngle;
    //类别名称
    private String typeName;
    //机场名称
    private String belongAirportName;
    //所属单位名称
    private String unitName;


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

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public String getColorArray() {
        return colorArray;
    }

    public void setColorArray(String colorArray) {
        this.colorArray = colorArray;
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

    public Integer getBelongAirport() {
        return belongAirport;
    }

    public void setBelongAirport(Integer belongAirport) {
        this.belongAirport = belongAirport;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getMoveAngle() {
        return moveAngle;
    }

    public void setMoveAngle(BigDecimal moveAngle) {
        this.moveAngle = moveAngle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBelongAirportName() {
        return belongAirportName;
    }

    public void setBelongAirportName(String belongAirportName) {
        this.belongAirportName = belongAirportName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
