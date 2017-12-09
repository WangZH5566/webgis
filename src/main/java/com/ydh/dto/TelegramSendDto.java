package com.ydh.dto;

import com.ydh.util.DateUtil;

import java.util.Date;

/**
 * @description: 文电Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class TelegramSendDto extends BaseDto{
    //电文id
    private Integer telegramId;
    //电文名称
    private String telegramName;
    //接收人id
    private Integer receiveBy;
    //接收人名称
    private String receiveByName;
    //接收/发送时间
    private Date receiveTime;
    //发送人id
    private Integer sendBy;
    //发送人名称
    private String sendByName;
    //已读/未读(1:已读,0:未读)
    private Integer isRead;
    //是否回执(0:未回执,1:已回执)
    private Integer isReceipt;
    private String receiptMsg;
    //主送/抄送(1:主送,0:抄送)
    private Integer isMainSend;

    private String docName;

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public String getTelegramName() {
        return telegramName;
    }

    public void setTelegramName(String telegramName) {
        this.telegramName = telegramName;
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

    public Integer getIsMainSend() {
        return isMainSend;
    }

    public void setIsMainSend(Integer isMainSend) {
        this.isMainSend = isMainSend;
    }

    public Integer getSendBy() {
        return sendBy;
    }

    public void setSendBy(Integer sendBy) {
        this.sendBy = sendBy;
    }

    public String getSendByName() {
        return sendByName;
    }

    public void setSendByName(String sendByName) {
        this.sendByName = sendByName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getReceiveTimeView(){
        if(receiveTime != null){
            return DateUtil.getDateDefaultFormateText(receiveTime.getTime());
        }
        return "";
    }

    public String getIsReadView(){
        if(isRead != null){
            if(isRead == 1){
                return "已读";
            }else{
                return "未读";
            }
        }
        return "未读";
    }

    public String getIsReceiptView(){
        if(isReceipt != null){
            if(isReceipt == 1){
                return "是";
            }else{
                return "否";
            }
        }
        return "否";
    }

    public String getReceiptMsg() {
        return receiptMsg;
    }

    public void setReceiptMsg(String receiptMsg) {
        this.receiptMsg = receiptMsg;
    }
}
