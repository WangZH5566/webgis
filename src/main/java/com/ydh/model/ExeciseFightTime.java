package com.ydh.model;

import java.util.Date;

/**
 * @description:推演作战时间跳跃实体 对应表EXEC_EXECISE_FIGHT_TIME
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseFightTime extends BaseModel {
    private static final long serialVersionUID = 5583312366711783323L;


    //所属推演id
    private Integer execId;
    //当前步长id
    private Integer stepId;
    //作战时间-跳跃开始时间
    private Date fightBeginTime;
    //作战时间-跳跃结束时间
    private Date fightEndTime;
    //跳跃时刻天文时间
    private Date nowTime;

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
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

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }
}
