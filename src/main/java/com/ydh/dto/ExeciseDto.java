package com.ydh.dto;

import com.ydh.util.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 推演数据Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class ExeciseDto extends BaseDto{

    //推演名称
    private String execiseName;
    //海图类型
    private Integer seaChart;
    //海图类型名称
    private String seaChartName;
    //创建时间
    private Date createTime;
    //开始时间
    private Date beginTime;
    //结束时间
    private Date endTime;
    //参与人员
    private String participants;
    //备注
    private String comment;
    //状态(0:初始状态,5:推演开始,10:推演结束)
    private Integer execStatus;
    //作战时间
    private Date fightTime;
    //作战结束时间
    private Date endFightTime;
    //导演id
    private String directorId;
    //导演登录名
    private String directorLoginName;
    //导演用户名
    private String directorUserName;
    //导演密码
    private String directorPassword;
    //导演账号的当前日期
    private String curDate;
    //导演账号的序列号
    private String serialNo;
    //步长
    private Integer stepLength;
    //是否暂停(0否1是)
    private Integer isPause;
    //作战时间年份隐藏位数
    private Integer ftHideDigit;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

    public String getSeaChartName() {
        return seaChartName;
    }

    public void setSeaChartName(String seaChartName) {
        this.seaChartName = seaChartName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
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

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public String getDirectorLoginName() {
        return directorLoginName;
    }

    public void setDirectorLoginName(String directorLoginName) {
        this.directorLoginName = directorLoginName;
    }

    public String getDirectorUserName() {
        return directorUserName;
    }

    public void setDirectorUserName(String directorUserName) {
        this.directorUserName = directorUserName;
    }

    public String getDirectorPassword() {
        return directorPassword;
    }

    public void setDirectorPassword(String directorPassword) {
        this.directorPassword = directorPassword;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCreateTimeView(){
        if(createTime != null){
            return sdf.format(createTime);
        }
        return "";
    }

    public String getBeginTimeView(){
        if(beginTime != null){
            return sdf.format(beginTime);
        }
        return "";
    }

    public String getEndTimeView(){
        if(endTime != null){
            return sdf.format(endTime);
        }
        return "";
    }

    public String getFightTimeView(){
        if(fightTime != null){
            return sdf.format(fightTime);
        }
        return "";
    }

    public String getExecStatusView(){
        if(execStatus != null){
            return Constant.getExecStatusText(execStatus);
        }
        return "";
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
