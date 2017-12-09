package com.ydh.model;

import java.util.Date;

/**
 * @description:电文实体 对应表
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class Telegram extends BaseModel {
    private static final long serialVersionUID = 5503612522333467471L;

    //电文名称
    private String tname;
    //电文源文件路径
    private String tpath;
    //电文html文件路径
    private String thtmlpath;
    //所属推演id
    private Integer execId;
    //状态(0:初始状态,1:格式转换成功,2:格式转换失败)
    private Integer tstatus;

    //word转html提示信息(存储用),同时也是返回信息(页面提示用)
    private String msg;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTpath() {
        return tpath;
    }

    public void setTpath(String tpath) {
        this.tpath = tpath;
    }

    public String getThtmlpath() {
        return thtmlpath;
    }

    public void setThtmlpath(String thtmlpath) {
        this.thtmlpath = thtmlpath;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public Integer getTstatus() {
        return tstatus;
    }

    public void setTstatus(Integer tstatus) {
        this.tstatus = tstatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
