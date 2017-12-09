package com.ydh.model;


import java.util.Date;

/**
 * @description:推演实体 对应表
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class Execise extends BaseModel {
    private static final long serialVersionUID = 5503612223583467471L;

    //推演名称
    private String execiseName;
    //海图类型
    private Integer seaChart;
    //开始时间
    private Date beginTime;
    //结束时间
    private Date endTime;
    //备注
    private String comment;
    //状态(0:初始状态,5:推演开始,10:推演结束)
    private Integer execStatus;
    //作战时间
    private Date fightTime;
    //作战结束时间
    private Date endFightTime;
    //步长
    private Integer stepLength;
    //是否暂停(0否1是)
    private Integer isPause;
    //作战时间年份隐藏位数
    private Integer ftHideDigit;

    public String getExeciseName() {
        return execiseName;
    }

    public void setExeciseName(String execiseName) {
        this.execiseName = execiseName;
    }

    public Integer getSeaChart() {
        return seaChart;
    }

    public void setSeaChart(Integer seaChart) {
        this.seaChart = seaChart;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }

    public Date getFightTime() {
        return fightTime;
    }

    public void setFightTime(Date fightTime) {
        this.fightTime = fightTime;
    }

    public Integer getStepLength() {
        return stepLength;
    }

    public void setStepLength(Integer stepLength) {
        this.stepLength = stepLength;
    }

    public Integer getIsPause() {
        return isPause;
    }

    public void setIsPause(Integer isPause) {
        this.isPause = isPause;
    }

    public Integer getFtHideDigit() {
        return ftHideDigit;
    }

    public void setFtHideDigit(Integer ftHideDigit) {
        this.ftHideDigit = ftHideDigit;
    }

    public Date getEndFightTime() {
        return endFightTime;
    }

    public void setEndFightTime(Date endFightTime) {
        this.endFightTime = endFightTime;
    }
}
