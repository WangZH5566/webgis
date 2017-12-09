package com.ydh.cron;

import com.ydh.model.Telegram;
import com.ydh.service.SituationMakeService;
import com.ydh.util.AsposeWordsUtils;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:文电word转html
 * @author: xxx.
 * @createDate: 2016/11/14.
 */
@Component
public class ConvertTelegramCron {

    @Autowired
    private SituationMakeService situationMakeService;

    @Value("${fileserver.telegram.path}")
    private String tpath;
    @Value("${fileserver.telegram.visitpath}")
    private String tvisitPath;

    public void poll() {
        List<Telegram> teleList = situationMakeService.queryForCorn(Constant.TELEGRAM_STATUS_INIT);
        if(teleList != null && teleList.size() > 0 && AsposeWordsUtils.getLicense()){
            for (Telegram t : teleList){
                String msg = AsposeWordsUtils.convertToHtml(tpath + t.getTpath());
                if(!"success".equals(msg)){
                    //转换失败
                    t.setMsg(msg);
                    t.setTstatus(Constant.TELEGRAM_STATUS_FAILD);
                }else{
                    String hpath = t.getTpath().substring(0,t.getTpath().lastIndexOf("."));
                    hpath = new StringBuffer(hpath).append(".html").toString();
                    t.setThtmlpath(hpath);
                    t.setTstatus(Constant.TELEGRAM_STATUS_SUCCESS);
                }
                situationMakeService.modifyTelegram(t);
            }
        }
    }
}
