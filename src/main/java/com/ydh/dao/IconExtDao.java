package com.ydh.dao;

import com.ydh.model.IconExt;

import java.util.List;

public interface IconExtDao {
    /**
     * 新增图标自定义字段
     * @param iconExt
     * @return
     */
    public int addIconExt(IconExt iconExt);

    /**
     * 修改图标自定义字段
     * @param iconExt
     * @return
     */
    public int modifyIconExt(IconExt iconExt);

    public List<IconExt> queryIconExt(Integer icon);

    public void deleteByID(Integer id);

    IconExt findByID(Integer id);
}
