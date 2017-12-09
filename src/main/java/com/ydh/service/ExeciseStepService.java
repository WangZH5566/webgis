package com.ydh.service;

import com.ydh.dao.ExeciseStepDao;
import com.ydh.dto.ExeciseStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推演图标步长Service
 */
@Service
public class ExeciseStepService {
    @Autowired
    private ExeciseStepDao execiseStepDao;

    /**
     * 根据推演id,查询该推演下所有推演步长
     * @param execId
     * @return
     */
    public List<ExeciseStepDto> findExeciseStepByExecId(Integer execId){
        return execiseStepDao.findExeciseStepByExecId(execId);
    }

}
