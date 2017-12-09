package com.ydh.dto;

/**
 * @description:基础资料专业Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class BaseInfoMajorDto extends BaseDto{
    //专业名称
    private String majorName;

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
