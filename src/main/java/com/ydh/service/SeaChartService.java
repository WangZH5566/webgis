package com.ydh.service;

import com.ydh.dao.SeaChartDao;
import com.ydh.dto.SeaChartDto;
import com.ydh.model.SeaChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:海图service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class SeaChartService {

    @Autowired
    private SeaChartDao seaChartDao;

    public List<SeaChartDto> queryAll() {
        return seaChartDao.queryAll();
    }

    public List<SeaChartDto> queryByUserId(Integer userId) {
        return seaChartDao.queryByUserId(userId);
    }

    public void save(SeaChart seaChart) {
        seaChartDao.addSeaChart(seaChart);
    }

    public void delete(Integer id) {
        seaChartDao.deleteById(id);
    }

    /**
     * 根据推演id查询海图 create by xxx
     * @param execId
     * @return
     */
    public List<SeaChartDto> querySeaChartByExecId(Integer execId){
        return seaChartDao.querySeaChartByExecId(execId);
    }
}
