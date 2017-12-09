package com.ydh.model;

/**
 * @description:推演单位实体 对应表EXEC_UNIT
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseUnit extends BaseModel {
    private static final long serialVersionUID = 5503612223583223471L;

    //单位名称
    private String unitName;
    //父节点id
    private Integer pid;
    //所属推演id
    private Integer execId;

    //旧id(组装数据用)
    private String oldId;
    //旧pid(组装数据用)
    private String oldPid;

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

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getOldPid() {
        return oldPid;
    }

    public void setOldPid(String oldPid) {
        this.oldPid = oldPid;
    }
}
