package com.ydh.model;

/**
 * @description:单位实体 对应EXEC_UNIT表
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class Unit extends BaseModel {
    private static final long serialVersionUID = 5512332223583467471L;

    //单位名称
    private String unitName;
    //父节点id
    private Integer pid;
    //推演id
    private Integer execId;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }
}
