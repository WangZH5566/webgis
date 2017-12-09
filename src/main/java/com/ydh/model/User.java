package com.ydh.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:用户实体 对应PGR_USER表
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class User extends BaseModel {
    private static final long serialVersionUID = 5503612483583467471L;


    // 登录名
    private String loginName;
    // 用户名
    private String userName;
    // 登录密码
    private String pwd;
    // 显示顺序
    private Integer serialNo;
    // 所属单位
    private Integer unitId;
    // 所属单位名称
    private String unitName;
    // 所属台位
    private Integer departmentId;
    // 所属台位名称
    private String departmentName;
    // 是否是导演
    private Integer isDirector;
    //所属推演
    private Integer execId;
    //能否跨单位发送电文(0否1是)
    private Integer isCrossUnit;
    //推演状态(0:初始状态,5:推演开始,10:推演结束)
    private Integer execStatus;
    //超级管理员
    private Integer isAdmin;


    // 所有权限集合
    private List<String> rights = new ArrayList<String>();


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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getIsDirector() {
        return isDirector;
    }

    public void setIsDirector(Integer isDirector) {
        this.isDirector = isDirector;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Integer getIsCrossUnit() {
        return isCrossUnit;
    }

    public void setIsCrossUnit(Integer isCrossUnit) {
        this.isCrossUnit = isCrossUnit;
    }

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 框架提供的权限接口
     */
    public List<String> getRights() {
        return this.rights;
    }
    public void setRights(List<String> rights) {
        this.rights = rights;
    }
    public boolean hasAnyRights(String right) {
        if (this.getIsAdmin() != null && this.getIsAdmin().equals(1)) {
            return true;
        }
        return rights.contains(right);
    }
}
