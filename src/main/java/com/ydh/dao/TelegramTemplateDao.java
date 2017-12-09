package com.ydh.dao;


import com.ydh.model.TelegramTemplate;

import java.util.List;

/**
 * @description:电文模板Dao
 * @author: .
 * @createDate: 2016/09/04.
 */
public interface TelegramTemplateDao {

    /**
     * 查询所有文档模板
     * @return
     */
    public List<TelegramTemplate> queryAll();

    /**
     * 根据id查询文档模板
     * @return
     */
    public TelegramTemplate queryById(Integer id);

    /**
     * 查询所有待转换的文档模板
     * @return
     */
    public List<TelegramTemplate> queryForCorn(Integer tstatus);

    /**
     * 新增文电模板
     * @param tt
     * @return
     */
    public int addTt(TelegramTemplate tt);

    /**
     * 修改文电模板
     * @param tt
     * @return
     */
    public int modifyTt(TelegramTemplate tt);

    /**
     * 删除文电模板
     * @param ids
     * @return
     */
    public int deleteTt(Integer[] ids);
}