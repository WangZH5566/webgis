package com.ydh.model;

/**
 * @description:推演台位实体 对应表EXEC_EXECISE_DEPARTMENT
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseDepartment extends BaseModel {
    private static final long serialVersionUID = 5503612223512903471L;

    //台位名称
    private String departmentName;
    //台位代码
    private String departmentCode;
    //能否跨单位发送电文(0否1是)
    private Integer isCrossUnit;
    //所属推演id
    private Integer execId;
    //所属台位id
    private Integer departmentId;
    //所属单位id
    private Integer unitId;

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

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
