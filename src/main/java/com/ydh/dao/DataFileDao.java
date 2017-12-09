package com.ydh.dao;


import com.ydh.model.DataFile;

import java.util.List;

public interface DataFileDao {

    /**
     * 查询所有
     * @return
     */
    public List<DataFile> queryAll();

    /**
     * 新增
     * @param dataFile
     * @return
     */
    public int addDataFile(DataFile dataFile);

    /**
     * 修改
     * @param dataFile
     * @return
     */
    public int modifyDataFile(DataFile dataFile);

    public List<DataFile> queryDataFile(Integer pid);

    public void deleteByID(Integer id);

    DataFile findByID(Integer id);
}
