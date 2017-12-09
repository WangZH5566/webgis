package com.ydh.service;

import com.ydh.dao.TelegramDao;
import com.ydh.dto.TelegramDto;
import com.ydh.dto.TelegramSendDto;
import com.ydh.model.Telegram;
import com.ydh.model.TelegramSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:文电拟制service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class SituationHandleService {

    @Autowired
    private TelegramDao telegramDao;

    /**
     * 查询当前用户所创建的待发送的文电
     * @param userId
     * @return
     */
    public List<Telegram> queryUserTelegram(Integer userId){
        return telegramDao.queryUserTelegram(userId);
    }

    /**
     * 查询当前用户接收的文电总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleReceiveCount(TelegramSendDto searchDto){
        return telegramDao.queryTeleReceiveCount(searchDto);
    }

    /**
     * 分页查询查询当前用户接收的文电
     * @param searchDto
     * @return
     */
    public List<TelegramSendDto> queryTeleReceivePage(TelegramSendDto searchDto){
        return telegramDao.queryTeleReceivePage(searchDto);
    }

    /**
     * 查询当前用户发送的文电总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleSendCount(TelegramSendDto searchDto){
        return telegramDao.queryTeleSendCount(searchDto);
    }

    /**
     * 分页查询查询当前用户发送的文电
     * @param searchDto
     * @return
     */
    public List<TelegramSendDto> queryTeleSendPage(TelegramSendDto searchDto){
        return telegramDao.queryTeleSendPage(searchDto);
    }

    /**
     * 查询当前用户发送的文电详情总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleSendDetailCount(TelegramSendDto searchDto){
        return telegramDao.queryTeleSendDetailCount(searchDto);
    }

    /**
     * 分页查询查询当前用户发送的文电详情
     * @param searchDto
     * @return
     */
    public List<TelegramSendDto> queryTeleSendDetailPage(TelegramSendDto searchDto){
        return telegramDao.queryTeleSendDetailPage(searchDto);
    }

    public Telegram findByID(Integer id) {
        return this.telegramDao.queryById(id);
    }

    public String sendTelegram(TelegramSend telegramSend) {
        this.telegramDao.sendTelegram(telegramSend);
        return "success";
    }

    public String forwardTelegram(List<TelegramSend> list) {
        if(list!=null&&list.size()>0){
            this.telegramDao.sendTelegramBatch(list);
        }
        return "success";
    }

    /**
     * 批量发送文电
     * @param ttid   文电id
     * @param sendTo 主送人
     * @param copyTo 抄送人
     * @param userId 发送人
     */
    public void sendTelegramBatch(Integer ttid,String sendTo,String copyTo,Integer userId){
        List<TelegramSend> telegramSendList = new ArrayList<TelegramSend>();
        Date nowDate = new Date();
        if(sendTo != null && sendTo.length() > 0){
            String[] sendToArr = sendTo.split(",");
            for(int i=0;i<sendToArr.length;i++){
                TelegramSend telegramSend = new TelegramSend();
                telegramSend.setTelegramId(ttid);
                telegramSend.setReceiveBy(Integer.valueOf(sendToArr[i]));
                telegramSend.setReceiveTime(nowDate);
                telegramSend.setCreateBy(userId);
                telegramSend.setCreateTime(nowDate);
                telegramSend.setIsRead(0);
                telegramSend.setIsReceipt(0);
                telegramSend.setIsMainSend(1);
                telegramSendList.add(telegramSend);
            }
        }
        if(copyTo != null && copyTo.length() > 0){
            String[] copyToArr = copyTo.split(",");
            for(int i=0;i<copyToArr.length;i++){
                TelegramSend telegramSend = new TelegramSend();
                telegramSend.setTelegramId(ttid);
                telegramSend.setReceiveBy(Integer.valueOf(copyToArr[i]));
                telegramSend.setReceiveTime(nowDate);
                telegramSend.setCreateBy(userId);
                telegramSend.setCreateTime(nowDate);
                telegramSend.setIsRead(0);
                telegramSend.setIsReceipt(0);
                telegramSend.setIsMainSend(0);
                telegramSendList.add(telegramSend);
            }
        }
        for(TelegramSend ts :telegramSendList){
            telegramDao.sendTelegram(ts);
        }
    }

    /**
     * 修改是否已读/是否回执
     * @param telegramSend
     */
    public void modifyIsReadOrIsReccipt(TelegramSend telegramSend){
        telegramDao.modifyIsReadOrIsReccipt(telegramSend);
    }
}
