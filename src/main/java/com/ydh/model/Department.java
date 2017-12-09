package com.ydh.model;

/**
 * @description:台位实体 对应EXEC_DEPARTMENT表
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class Department extends BaseModel {
    private static final long serialVersionUID = 5512332223583467471L;

    //台位名称
    private String departmentName;
    //台位代码
    private String departmentCode;
    //能否跨单位发送电文(0否1是)
    private Integer isCrossUnit;
    //能否删除(0否1是)
    private Integer isDelete;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getIsCrossUnit() {
        return isCrossUnit;
    }

    public void setIsCrossUnit(Integer isCrossUnit) {
        this.isCrossUnit = isCrossUnit;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
