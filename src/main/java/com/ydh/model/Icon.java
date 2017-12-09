package com.ydh.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class Icon extends BaseModel{
    //主键
    private Integer id;

    private String path;

    private Integer deleted = 0;

    private String name;
    private Boolean hasSpeed;
    private Boolean hasDamage;
    private BigDecimal speed;
    private String speedUnit;
    private String damageLevel;
    private String damageLevelTime;
    private Integer iconGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getHasSpeedInt(){
        return (this.hasSpeed==null||!this.hasSpeed)?0:1;
    }
    public Integer getHasDamageInt(){
        return (this.hasDamage==null||!this.hasDamage)?0:1;
    }
}
