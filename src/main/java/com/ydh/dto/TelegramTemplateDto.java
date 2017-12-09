package com.ydh.dto;

/**
 * @description: 文电模板Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class TelegramTemplateDto extends BaseDto{
    //模板名称
    private String tname;
    //模板源文件路径
    private String tpath;
    //模板html文件路径
    private String thtmlpath;
    //父节点id
    private Integer pid;
    //返回信息(页面提示用)
    private String msg;
    //状态(0:初始状态,1:格式转换成功,2:格式转换失败)
    private Integer tstatus;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTstatus() {
        return tstatus;
    }

    public void setTstatus(Integer tstatus) {
        this.tstatus = tstatus;
    }
}
