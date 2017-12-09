package com.ydh.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yqb on 2016/11/14 0014.
 */
public class HistoryLogDto extends BaseDto{
    private Integer id;
    private Integer execId;
    private String content;
    private Date createTime;
    private String createUserName;
    private Date fightTime;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
    public String getCreateTimeView(){
        if(createTime != null){
            return sdf.format(createTime);
        }
        return "";
    }

    public Date getFightTime() {
        return fightTime;
    }
    public String getFightTimeView(){
        if(fightTime != null){
            return sdf.format(fightTime);
        }
        return "";
    }

    public void setFightTime(Date fightTime) {
        this.fightTime = fightTime;
    }
}
