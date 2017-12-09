package com.ydh.dao;


import com.ydh.dto.SeaChartDto;
import com.ydh.model.SeaChart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:海图Dao
 * @author: .
 * @createDate: 2016/09/04.
 */
public interface SeaChartDao {

    /**
     * 查询所有海图
     * @return
     */
    List<SeaChartDto> queryAll();

    void addSeaChart(SeaChart s);

    void deleteById(@Param("id") Integer id);

    List<SeaChartDto> queryByUserId(@Param("userId")Integer userId);

    /**
     * 根据推演id查询海图 create by xxx
     * @param execId
     * @return
     */
    public List<SeaChartDto> querySeaChartByExecId(Integer execId);

}