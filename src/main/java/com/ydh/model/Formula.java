package com.ydh.model;

/**
 * @description:台位实体 对应EXEC_DEPARTMENT表
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class Formula extends BaseModel {
    private static final long serialVersionUID = 5512332223583467471L;

    //公式名称
    private String name;
    //公式备注
    private String remark;
    //公式内容
    private String val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
