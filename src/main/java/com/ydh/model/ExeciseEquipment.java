package com.ydh.model;

/**
 * @description:推演图标装载实体 对应表EXEC_EXECISE_EQUIPMENT
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseEquipment extends BaseModel {
    private static final long serialVersionUID = 5503612334444553323L;

    //所属图标id
    private Integer iconId;
    //所属命令id
    private Integer orderId;
    //装载明细id
    private Integer equipmentId;
    //装载设备名称（可带上型号）
    private String equipmentName;
    //(装载设备)每单位装载耗时（分钟）
    private Integer loadTime;
    //装载设备数量
    private Integer equipmentCount;

    private Integer mainType;

    private Integer execiseTroopId;

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
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
