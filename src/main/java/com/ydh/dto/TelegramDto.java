package com.ydh.dto;

import com.ydh.util.DateUtil;

import java.io.File;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @description: 文电Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class TelegramDto extends BaseDto{

    //电文名称
    private String tname;
    //电文源文件路径
    private String tpath;
    //电文html文件路径
    private String thtmlpath;
    //电文接收人id
    private Integer receiveBy;
    //电文接收人名称
    private String receiveByName;
    //电文接收时间
    private Date receiveTime;
    //电文创建人id
    private Integer createBy;
    //电文创建人名称
    private String createByName;
    //电文创建时间
    private Date createTime;
    //所属推演id
    private Integer execId;
    //状态(0:初始状态,1:格式转换成功,2:格式转换失败)
    private Integer tstatus;
    //返回信息(页面提示用)
    private String msg;

    public String getTname() {return tname;}

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

    public Integer getReceiveBy() {
        return receiveBy;
    }

    public void setReceiveBy(Integer receiveBy) {
        this.receiveBy = receiveBy;
    }

    public String getReceiveByName() {
        return receiveByName;
    }

    public void setReceiveByName(String receiveByName) {
        this.receiveByName = receiveByName;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {this.execId = execId;}

    public Integer getTstatus() {
        return tstatus;
    }

    public void setTstatus(Integer tstatus) {
        this.tstatus = tstatus;
    }

    public String getTstatusView() {
        String tstatusView = "";
        if(tstatus != null){
            switch (tstatus){
                case 0 :
                    tstatusView = "文电格式转换中";
                    break;
                case 1 :
                    tstatusView = "文电格式转换成功";
                    break;
                case 2 :
                    tstatusView = "文电格式转换出错";
                    break;
                default:
                    tstatusView = "";
            }
        }
        return tstatusView;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getReceiveTimeMills(){
        return this.receiveTime==null?0:this.receiveTime.getTime();
    }

    public String getReceiveTimeView(){
        if(receiveTime != null){
            return DateUtil.getDateDefaultFormateText(receiveTime.getTime());
        }
        return "";
    }

    public String getCreateTimeView() {
        if(createTime != null){
            return DateUtil.formatYYYYMMDDHHMM(createTime);
        }
        return "";
    }

    public String getDocName(){
        if(this.tpath==null||"".equals(this.tpath)){
            return "";
        }
        File f=new File(this.tpath);
        if(f.exists()){
            return f.getName();
        }
        return "文件路径出错";
    }
}
