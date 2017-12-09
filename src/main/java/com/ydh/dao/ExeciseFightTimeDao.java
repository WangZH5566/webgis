package com.ydh.dao;

import com.ydh.dto.ExeciseFightTimeDto;
import com.ydh.dto.ExeciseStepDto;
import com.ydh.model.ExeciseFightTime;
import com.ydh.model.ExeciseStep;

import java.util.List;

/**
 * 推演作战时间跳跃Dao
 */
public interface ExeciseFightTimeDao {

    /**
     * 根据推演id、步长id,查询该推演下所有推演作战时间跳跃
     * @param execId  推演id
     * @param stepId  步长id
     * @return
     */
    public List<ExeciseFightTimeDto> findExeciseFightTimeByExecIdAndStepId(Integer execId,Integer stepId);

    /**
     * 新增ExeciseFightTime
     * @param execiseFightTime
     * @return
     */
    public int addExeciseFightTime(ExeciseFightTime execiseFightTime);

}
