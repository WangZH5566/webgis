package com.ydh.dto;

import java.math.BigDecimal;

/**
 * Created by yqb on 2016/11/25 0025.
 */
public class IconDto extends BaseDto{
    private Integer id;
    private String name;
    private String iconPath;
    private Boolean hasSpeed;
    private Boolean hasDamage;
    private BigDecimal speed;
    private String speedUnit;
    private String damageLevel;
    private String damageLevelTime;
    private Integer iconGroup;

    private String groupArray;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Boolean getHasSpeed() {
        return hasSpeed;
    }

    public void setHasSpeed(Boolean hasSpeed) {
        this.hasSpeed = hasSpeed;
    }

    public Boolean getHasDamage() {
        return hasDamage;
    }

    public void setHasDamage(Boolean hasDamage) {
        this.hasDamage = hasDamage;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public String getSpeedUnit() {
        return speedUnit;
    }

    public void setSpeedUnit(String speedUnit) {
        this.speedUnit = speedUnit;
    }

    public String getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(String damageLevel) {
        this.damageLevel = damageLevel;
    }

    public String getDamageLevelTime() {
        return damageLevelTime;
    }

    public void setDamageLevelTime(String damageLevelTime) {
        this.damageLevelTime = damageLevelTime;
    }

    public Integer getIconGroup() {
        return iconGroup;
    }

    public void setIconGroup(Integer iconGroup) {
        this.iconGroup = iconGroup;
    }

    public String getGroupArray() {
        return groupArray;
    }

    public void setGroupArray(String groupArray) {
        this.groupArray = groupArray;
    }

}
