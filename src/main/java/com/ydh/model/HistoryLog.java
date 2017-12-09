package com.ydh.model;


import java.util.Date;

/**
 * @description:日志实体 对应表 EXEC_HISTORY_LOG
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class HistoryLog extends BaseModel {
    private static final long serialVersionUID = 5503612555543467471L;

    //推演ID码
    private Integer execId;
    //日志内容
    private String content;
    private Date fightTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Date getFightTime() {
        return fightTime;
    }

    public void setFightTime(Date fightTime) {
        this.fightTime = fightTime;
    }
}
