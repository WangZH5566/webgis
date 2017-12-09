package com.ydh.model;

/**
 * @description:图标集群实体
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class ExeciseIconCrowd extends BaseModel{

    //集群名称
    private String crowdName;
    //所属推演id
    private Integer execId;

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }
}
