package com.ydh.dto;

public class ExeciseEquipmentDto extends BaseDto{
    private Integer icon;
    private Integer order;
    private Integer equipment;
    private String equipmentName;
    private Integer loadTime;
    private Integer equipmentCount;
    private Integer mainType;
    private Integer execiseTroopId;

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getEquipment() {
        return equipment;
    }

    public void setEquipment(Integer equipment) {
        this.equipment = equipment;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Integer loadTime) {
        this.loadTime = loadTime;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public Integer getExeciseTroopId() {
        return execiseTroopId;
    }

    public void setExeciseTroopId(Integer execiseTroopId) {
        this.execiseTroopId = execiseTroopId;
    }
}
