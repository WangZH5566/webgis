package com.ydh.dto;

import java.util.Date;

/**
 * @description: 推演图标数据Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseStepDto extends BaseDto{

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
