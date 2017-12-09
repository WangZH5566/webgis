package com.ydh.model;

import java.util.Date;

/**
 * @description:电文发送实体 对应表
 * @author:
 * @createDate:
 */
public class TelegramSend extends BaseModel{
    private Integer telegramId;
    private Integer receiveBy;
    private Date receiveTime;

    //已读/未读(1:已读,0:未读)
    private Integer isRead;
    //是否回执(0:未回执,1:已回执)
    private Integer isReceipt;
    private String receiptMsg;
    //主送/抄送(1:主送,0:抄送)
    private Integer isMainSend;

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public Integer getReceiveBy() {
        return receiveBy;
    }

    public void setReceiveBy(Integer receiveBy) {
        this.receiveBy = receiveBy;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(Integer isReceipt) {
        this.isReceipt = isReceipt;
    }

    public String getReceiptMsg() {
        return receiptMsg;
    }

    public void setReceiptMsg(String receiptMsg) {
        this.receiptMsg = receiptMsg;
    }

    public Integer getIsMainSend() {
        return isMainSend;
    }

    public void setIsMainSend(Integer isMainSend) {
        this.isMainSend = isMainSend;
    }
}
