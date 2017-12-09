package com.ydh.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:推演命令实体 对应表EXEC_EXECISE_ORDER
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseOrder extends BaseModel {
    private static final long serialVersionUID = 5503612366778123323L;

    //指令类型(1:移动,2:维修,3:装载,4:航向移动,5:降落(非航向))
    private Integer orderType;
    //路径坐标
    private String pathCoordinate;
    //图标1id
    private Integer iconOneId;
    //图标2id
    private Integer iconTwoId;
    //开始时间
    private Date beginTime;
    //下达指令人
    private Integer orderBy;
    //所属推演id
    private Integer execId;
    //开始时间(作战时间)
    private String fightBeginTimeView;
    private Date fightBeginTime;

    private BigDecimal moveAngle;
    //装载时间(小时)
    private Integer addEquipmentTime;
    //受损等级
    private Integer damage;
    //受损维修所需时间
    private Integer damageTime;
    //受损程度详情(id用逗号分隔)
    private String damageDetail;
    //维修总人数
    private Integer repairNum;
    //维修开始时间(作战时间)
    private Date repairBeginTime;
    //装载开始时间(作战时间)
    private Date addEquipmentBeginTime;
    //维修开始时间(作战时间)(毫秒数)
    private long repairBeginTimeMills;
    //装载开始时间(作战时间)(毫秒数)
    private long addEquipmentBeginTimeMills;
    //维修/装载/降落是否结束(0否1是)
    private Integer isEnd;
    //写日志时用到
    private String iconName;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getPathCoordinate() {
        return pathCoordinate;
    }

    public void setPathCoordinate(String pathCoordinate) {
        this.pathCoordinate = pathCoordinate;
    }

    public Integer getIconOneId() {
        return iconOneId;
    }

    public void setIconOneId(Integer iconOneId) {
        this.iconOneId = iconOneId;
    }

    public Integer getIconTwoId() {
        return iconTwoId;
    }

    public void setIconTwoId(Integer iconTwoId) {
        this.iconTwoId = iconTwoId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public BigDecimal getMoveAngle() {
        return moveAngle;
    }

    public void setMoveAngle(BigDecimal moveAngle) {
        this.moveAngle = moveAngle;
    }

    public String getFightBeginTimeView() {
        return fightBeginTimeView;
    }

    public void setFightBeginTimeView(String fightBeginTimeView) {
        this.fightBeginTimeView = fightBeginTimeView;
    }

    public Date getFightBeginTime() {
        return fightBeginTime;
    }

    public void setFightBeginTime(Date fightBeginTime) {
        this.fightBeginTime = fightBeginTime;
    }

    /**
     * 保存的时候用到该属性，存储装载列表
     */
    private BigDecimal moveSpeed;
    private String loadListStr;
    private String damageLevel;

    public BigDecimal getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(BigDecimal moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public String getLoadListStr() {
        return loadListStr;
    }
    public void setLoadListStr(String loadListStr) {
        this.loadListStr = loadListStr;
    }
    public String getDamageLevel() {
        return damageLevel;
    }
    public void setDamageLevel(String damageLevel) {
        this.damageLevel = damageLevel;
    }

    public Integer getAddEquipmentTime() {
        return addEquipmentTime;
    }

    public void setAddEquipmentTime(Integer addEquipmentTime) {
        this.addEquipmentTime = addEquipmentTime;
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

    public Integer getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(Integer repairNum) {
        this.repairNum = repairNum;
    }

    public Date getRepairBeginTime() {
        return repairBeginTime;
    }

    public void setRepairBeginTime(Date repairBeginTime) {
        this.repairBeginTime = repairBeginTime;
    }

    public Date getAddEquipmentBeginTime() {
        return addEquipmentBeginTime;
    }

    public void setAddEquipmentBeginTime(Date addEquipmentBeginTime) {
        this.addEquipmentBeginTime = addEquipmentBeginTime;
    }

    public long getRepairBeginTimeMills() {
        if(repairBeginTime == null){
            return 0L;
        }else{
            return repairBeginTime.getTime();
        }
    }

    public void setRepairBeginTimeMills(long repairBeginTimeMills) {
        this.repairBeginTimeMills = repairBeginTimeMills;
    }

    public long getAddEquipmentBeginTimeMills() {
        if(addEquipmentBeginTime == null){
            return 0L;
        }else{
            return addEquipmentBeginTime.getTime();
        }
    }

    public void setAddEquipmentBeginTimeMills(long addEquipmentBeginTimeMills) {
        this.addEquipmentBeginTimeMills = addEquipmentBeginTimeMills;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
