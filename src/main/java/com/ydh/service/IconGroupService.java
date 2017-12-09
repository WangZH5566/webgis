package com.ydh.service;

import com.ydh.dao.IconDao;
import com.ydh.dao.IconGroupDao;
import com.ydh.model.IconGroup;
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
public class IconGroupService {
    @Autowired
    private IconGroupDao iconGroupDao;
    @Autowired
    private IconDao iconDao;

    /**
     * 查询所有图标分组
     * @return
     */
    public List<IconGroup> queryAll(){
        return iconGroupDao.queryAll();
    }

    /**
     * 新增图标分组
     * @param iconGroup      台位实体
     * @return
     */
    public String addIconGroup(IconGroup iconGroup){
        this.iconGroupDao.addIconGroup(iconGroup);
        return "SUCCESS";
    }

    /**
     * 修改图标分组
     * @param iconGroup      台位实体
     * @return
     */
    public String modifyIconGroup(IconGroup iconGroup){
        this.iconGroupDao.modifyIconGroup(iconGroup);
        return "SUCCESS";
    }

    public List<IconGroup> queryIconGroup(Integer pid) {
        return this.iconGroupDao.queryIconGroup(pid);
    }

    public String deleteIconGroup(Integer id) {
        //判断是否有子分组
        List<IconGroup> list=this.iconGroupDao.queryIconGroup(id);
        if(list!=null&&list.size()>0){
            return "请先删除该分组下的子分组";
        }
        this.iconDao.deleteByGroup(id);

        this.iconGroupDao.deleteByID(id);
        return "SUCCESS";
    }
}
