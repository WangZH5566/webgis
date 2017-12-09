package com.ydh.dao;


import com.ydh.dto.TelegramDto;
import com.ydh.dto.TelegramSendDto;
import com.ydh.model.Telegram;
import com.ydh.model.TelegramSend;

import java.util.List;

/**
 * @description:电文Dao
 * @author: .
 * @createDate: 2016/09/04.
 */
public interface TelegramDao {

    /**
     * 查询文电总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleCount(TelegramDto searchDto);

    /**
     * 分页查询文电
     * @param searchDto
     * @return
     */
    public List<TelegramDto> queryTelePage(TelegramDto searchDto);

    /**
     * 根据id查询文电
     * @return
     */
    public Telegram queryById(Integer id);

    /**
     * 查询所有待转换的文电
     * @return
     */
    public List<Telegram> queryForCorn(Integer tstatus);

    /**
     * 新增文电
     * @param telegram
     * @return
     */
    public int addTelegram(Telegram telegram);

    /**
     * 修改文电
     * @param telegram
     * @return
     */
    public void modifyTelegram(Telegram telegram);

    /**
     * 删除文电
     * @param id
     */
    public void deleteTelegram(Integer id);

    /**
     * 查询当前用户所创建的文电
     * @param userId
     * @return
     */
    public List<Telegram> queryUserTelegram(Integer userId);

    /**
     * 查询当前用户接收的文电总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleReceiveCount(TelegramSendDto searchDto);

    /**
     * 分页查询查询当前用户接收的文电
     * @param searchDto
     * @return
     */
    public List<TelegramSendDto> queryTeleReceivePage(TelegramSendDto searchDto);

    /**
     * 查询当前用户发送的文电总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleSendCount(TelegramSendDto searchDto);

    /**
     * 分页查询查询当前用户发送的文电
     * @param searchDto
     * @return
     */
    public List<TelegramSendDto> queryTeleSendPage(TelegramSendDto searchDto);

    /**
     * 查询当前用户发送的文电详情总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleSendDetailCount(TelegramSendDto searchDto);

    /**
     * 分页查询查询当前用户发送的文电详情
     * @param searchDto
     * @return
     */
    public List<TelegramSendDto> queryTeleSendDetailPage(TelegramSendDto searchDto);

    void sendTelegram(TelegramSend telegramSend);

    void sendTelegramBatch(List<TelegramSend> list);

    /**
     * 修改是否已读/是否回执
     * @param telegramSend
     */
    public void modifyIsReadOrIsReccipt(TelegramSend telegramSend);
}