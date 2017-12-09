package com.ydh.service;

import com.ydh.dao.ExeciseEquipmentDao;
import com.ydh.dto.ExeciseEquipmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推演图标装载Service
 */
@Service
public class ExeciseEquipmentService {

    @Autowired
    private ExeciseEquipmentDao execiseEquipmentDao;


    /**
     * 根据推演图标id,查询该推演图标下的全部装载数据
     * @param execIconId
     * @return
     */
    public List<ExeciseEquipmentDto> findExeciseEquipmentByExecIconId(Integer execIconId){
        return execiseEquipmentDao.findExeciseEquipmentByExecIconId(execIconId);
    }

}
