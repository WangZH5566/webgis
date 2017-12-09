package com.ydh.dto;

/**
 * @description: 台位Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class DepartmentDto extends BaseDto{

    //台位名称
    private String departmentName;
    //台位代码
    private String departmentCode;
    //能否跨单位发送电文(0否1是)
    private Integer isCrossUnit;

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
}
