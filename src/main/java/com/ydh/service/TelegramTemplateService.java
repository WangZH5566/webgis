package com.ydh.service;

import com.ydh.dao.TelegramTemplateDao;
import com.ydh.model.TelegramTemplate;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description:电文模板service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class TelegramTemplateService {

    @Autowired
    private TelegramTemplateDao telegramTemplateDao;

    /**
     * 查询所有文档模板
     * @return
     */
    public List<TelegramTemplate> queryAll(){
        return telegramTemplateDao.queryAll();
    }

    /**
     * 根据id查询文档模板
     * @return
     */
    public TelegramTemplate queryById(Integer id){
        return telegramTemplateDao.queryById(id);
    }

    /**
     * 查询所有待转换的文档模板
     * @return
     */
    public List<TelegramTemplate> queryForCorn(Integer tstatus){
        return telegramTemplateDao.queryForCorn(tstatus);
    }

    /**
     * 新增文电模板
     * @param tt
     * @return
     */
    public TelegramTemplate addTt(TelegramTemplate tt){
        tt = validate(tt);
        if(tt.getMsg() != null && !"".equals(tt.getMsg())){
            return tt;
        }
        tt.setCreateTime(new Date());
        telegramTemplateDao.addTt(tt);
        tt.setMsg("success");
        return tt;
    }

    /**
     * 修改文电模板
     * @param tt
     * @return
     */
    public String modifyTt(TelegramTemplate tt){
        if(tt.getId() == null){
            return Constant.DATA_ABNORMAL;
        }
        tt.setLastUpdateTime(new Date());
        telegramTemplateDao.modifyTt(tt);
        return "success";
    }

    /**
     * 删除文电模板
     * @param ids 逗号分隔
     * @return
     */
    public void deleteTt(String ids){
        String[] arr = ids.split(",");
        Integer[] idsArr = new Integer[arr.length];
        for(int i=0;i<arr.length;i++){
            idsArr[i] = Integer.valueOf(arr[i]);
        }
        telegramTemplateDao.deleteTt(idsArr);
    }

    /**
     * 数据验证
     * @param tt      文电模板实体
     * @return
     * @throws Exception
     */
    private TelegramTemplate validate(TelegramTemplate tt){
        if(tt.getPid() == null){
            tt.setMsg("[父节点]不能为空");
            return tt;
        }
        if(tt.getTname()  == null){
            tt.setMsg("[节点名称]不能为空");
            return tt;
        }
        return tt;
    }

}
