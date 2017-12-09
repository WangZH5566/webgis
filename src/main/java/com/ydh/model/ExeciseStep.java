package com.ydh.model;

import java.util.Date;

/**
 * @description:推演步长实体 对应表EXEC_EXECISE_STEP
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseStep extends BaseModel {
    private static final long serialVersionUID = 5503612366712333323L;

    //步长
    private Integer stepLength;
    //开始时间
    private Date beginTime;
    //开始时间
    private Date endTime;
    //所属推演id
    private Integer execId;
    //开始时间(作战时间)
    private Date fightBeginTime;
    //开始时间(作战时间)
    private Date fightEndTime;

    public Integer getStepLength() {
        return stepLength;
    }

    public void setStepLength(Integer stepLength) {
        this.stepLength = stepLength;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Date getFightBeginTime() {
        return fightBeginTime;
    }

    public void setFightBeginTime(Date fightBeginTime) {
        this.fightBeginTime = fightBeginTime;
    }

    public Date getFightEndTime() {
        return fightEndTime;
    }

    public void setFightEndTime(Date fightEndTime) {
        this.fightEndTime = fightEndTime;
    }
}
