package com.ydh.service;

import com.ydh.dao.TelegramDao;
import com.ydh.dto.TelegramDto;
import com.ydh.model.Telegram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @description:文电拟制service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class SituationMakeService {

    @Autowired
    private TelegramDao telegramDao;

    /**
     * 查询文电总数量
     * @param searchDto
     * @return
     */
    public Integer queryTeleCount(TelegramDto searchDto){
        return telegramDao.queryTeleCount(searchDto);
    }

    /**
     * 分页查询文电
     * @param searchDto
     * @return
     */
    public List<TelegramDto> queryTelePage(TelegramDto searchDto){
        return telegramDao.queryTelePage(searchDto);
    }

    /**
     * 查询所有待转换的文电
     * @return
     */
    public List<Telegram> queryForCorn(Integer tstatus){
        return telegramDao.queryForCorn(tstatus);
    }


    /**
     * 新增文电
     * @param telegram
     * @return
     */
    public void addTelegram(Telegram telegram){
        telegram.setCreateTime(new Date());
        telegramDao.addTelegram(telegram);
    }

    /**
     * 修改文电
     * @param telegram
     * @return
     */
    public void modifyTelegram(Telegram telegram){
        telegramDao.modifyTelegram(telegram);
    }

    /**
     * 删除文电
     * @param id
     */
    public void deleteTelegram(Integer id) throws Exception{
        Telegram telegram = telegramDao.queryById(id);
        if(telegram != null){
            //删除文件,并删除数据库记录
            File wordFile = new File(telegram.getTpath());
            if(wordFile.exists()){
                //这里删不掉文件，因为文件还在被占用，要想办法
                //wordFile.delete();
            }
            String htmlPath = new StringBuffer(telegram.getTpath().substring(0,telegram.getTpath().lastIndexOf("."))) .append(".html").toString();
            File htmlFile = new File(htmlPath);
            if(htmlFile.exists()){
                //这里删不掉文件，因为文件还在被占用，要想办法
                //htmlFile.delete();
            }
            telegramDao.deleteTelegram(id);
        }
    }

}
