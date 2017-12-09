package com.ydh.service;

import com.ydh.dao.IconExtDao;
import com.ydh.model.IconExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:推演service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class IconExtService {
    @Autowired
    private IconExtDao iconExtDao;

    /**
     * 新增图标属性
     * @param iconExt
     * @return
     */
    public String addIconExt(IconExt iconExt){
        this.iconExtDao.addIconExt(iconExt);
        return "SUCCESS";
    }

    /**
     * 修改图标属性
     * @param iconExt
     * @return
     */
    public String modifyIconExt(IconExt iconExt){
        this.iconExtDao.modifyIconExt(iconExt);
        return "SUCCESS";
    }

    public List<IconExt> queryIconExt(Integer icon) {
        return this.iconExtDao.queryIconExt(icon);
    }

    public String deleteIconExt(Integer id) {
        this.iconExtDao.deleteByID(id);
        return "SUCCESS";
    }

    public IconExt findByID(Integer id) {
        return this.iconExtDao.findByID(id);
    }

    public String deleteByID(Integer id) {
        this.iconExtDao.deleteByID(id);
        return "SUCCESS";
    }
}
