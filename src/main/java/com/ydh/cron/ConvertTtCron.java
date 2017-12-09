package com.ydh.cron;

import com.ydh.model.TelegramTemplate;
import com.ydh.service.TelegramTemplateService;
import com.ydh.util.AsposeWordsUtils;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @description:文电模板word转html
 * @author: xxx.
 * @createDate: 2016/11/14.
 */
@Component
public class ConvertTtCron {

    @Autowired
    private TelegramTemplateService telegramTemplateService;

    @Value("${fileserver.tt.visitpath}")
    private String visitPath;

    public void poll() {
        List<TelegramTemplate> ttList = telegramTemplateService.queryForCorn(Constant.TELEGRAM_STATUS_INIT);
        if(ttList != null && ttList.size() > 0 && AsposeWordsUtils.getLicense()){
            for (TelegramTemplate tt : ttList){
                String msg = AsposeWordsUtils.convertToHtml(tt.getTpath());
                if(!"success".equals(msg)){
                    //转换失败
                    tt.setMsg(msg);
                    tt.setTstatus(Constant.TELEGRAM_STATUS_FAILD);
                }else{
                    System.out.println(tt.getTpath().lastIndexOf(File.separator) + 1);
                    System.out.println(tt.getTpath().lastIndexOf("."));
                    String hpath = tt.getTpath().substring(tt.getTpath().lastIndexOf("/") + 1,tt.getTpath().lastIndexOf("."));
                    hpath = new StringBuffer(visitPath).append(hpath).append(".html").toString();
                    tt.setThtmlpath(hpath);
                    tt.setTstatus(Constant.TELEGRAM_STATUS_SUCCESS);
                }
                telegramTemplateService.modifyTt(tt);
            }
        }
    }
}
