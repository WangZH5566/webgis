package com.ydh.dto;

/**
 * @description: 推演人员数据Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseUserDto extends BaseDto{

    //登录名
    private String loginName;
    //用户名
    private String userName;
    //密码(加密前)
    private String oldPassword;
    //密码(加密后)
    private String newPassword;
    //所属单位id
    private Integer unitId;
    //所属单位名称
    private String unitName;
    //所属台位id
    private Integer departmentId;
    //所属台位名称
    private String departmentName;
    //是否是导演(0否1是)
    private Integer isDirector;
    //当前日期,格式:20161101
    private String curDate;
    //序列号
    private Integer serialNo;
    //所属推演id
    private Integer execId;
    //能否跨单位发送电文(0否1是)
    private Integer isCrossUnit;

    private String unitArray;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getIsDirector() {
        return isDirector;
    }

    public void setIsDirector(Integer isDirector) {
        this.isDirector = isDirector;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public String getUnitArray() {
        return unitArray;
    }

    public void setUnitArray(String unitArray) {
        this.unitArray = unitArray;
    }

    public Integer getIsCrossUnit() {
        return isCrossUnit;
    }

    public void setIsCrossUnit(Integer isCrossUnit) {
        this.isCrossUnit = isCrossUnit;
    }
}
