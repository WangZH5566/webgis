package com.ydh.model;

/**
 * @description:基础资料专业实体-对应exec_basic_info_major表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class BasicInfoMajor extends BaseModel{

    //专业名称
    private String majorName;

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
