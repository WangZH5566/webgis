package com.ydh.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExeciseAnnotation extends BaseModel {
    private Integer execId; //演习ID
    private String startPoint; //开始坐标
    private String endPoint;//停止坐标
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//停止时间
    private Integer deleted = 0;//结束标志
    private String createByName;//新增人员姓名
    private String lastUpdateByName;//更新人员姓名
    private Integer action;//动作
    private String subject;//说明
    private String marker; //标记图片

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getLastUpdateByName() {
        return lastUpdateByName;
    }

    public void setLastUpdateByName(String lastUpdateByName) {
        this.lastUpdateByName = lastUpdateByName;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("{ 说明: ").append(subject);
        sb.append(", 动作: ").append(action);
        sb.append(", 标记: ").append(marker);
        sb.append(", 开始坐标: ").append(startPoint);
        sb.append(", 停止坐标: ").append(endPoint);
        sb.append(", 开始时间: ");
        if (startTime != null) {
            sb.append(sdf.format(startTime));
        }
        sb.append(", 停止时间: ");
        if (endTime != null) {
            sb.append(sdf.format(endTime));
        }
        sb.append("}");
        return sb.toString();
    }
}
