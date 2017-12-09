package com.ydh.dao;

import com.ydh.model.IconGroup;

import java.util.List;

public interface IconGroupDao {

    /**
     * 查询所有图标分组
     * @return
     */
    public List<IconGroup> queryAll();

    /**
     * 新增图标分组
     * @param iconGroup
     * @return
     */
    public int addIconGroup(IconGroup iconGroup);

    /**
     * 修改图标分组
     * @param iconGroup
     * @return
     */
    public int modifyIconGroup(IconGroup iconGroup);

    public List<IconGroup> queryIconGroup(Integer pid);

    public void deleteByID(Integer id);
}
