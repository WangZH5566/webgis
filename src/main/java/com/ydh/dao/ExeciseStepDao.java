package com.ydh.dao;

import com.ydh.dto.ExeciseStepDto;
import com.ydh.model.ExeciseStep;

import java.util.List;

/**
 * 推演步长Dao
 */
public interface ExeciseStepDao {

    /**
     * 根据推演id,查询该推演下所有推演步长
     * @param execId
     * @return
     */
    public List<ExeciseStepDto> findExeciseStepByExecId(Integer execId);

    /**
     * 新增ExeciseStep
     * @param execiseStep
     * @return
     */
    public int addExeciseStep(ExeciseStep execiseStep);

    /**
     * 修改ExeciseStep
     * @param execiseStep
     * @return
     */
    public int modifyExeciseStep(ExeciseStep execiseStep);

}
