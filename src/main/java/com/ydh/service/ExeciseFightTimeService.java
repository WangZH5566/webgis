package com.ydh.service;

import com.ydh.dao.ExeciseFightTimeDao;
import com.ydh.dao.ExeciseStepDao;
import com.ydh.dto.ExeciseFightTimeDto;
import com.ydh.dto.ExeciseStepDto;
import com.ydh.model.ExeciseFightTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 推演作战时间跳跃Service
 */
@Service
public class ExeciseFightTimeService {
    @Autowired
    private ExeciseFightTimeDao execiseFightTimeDao;
    @Autowired
    private ExeciseStepDao execiseStepDao;

    /**
     * 新增ExeciseFightTime
     * @param execiseFightTime
     */
    public void addExeciseFightTime(ExeciseFightTime execiseFightTime){
        List<ExeciseStepDto> esDtos = execiseStepDao.findExeciseStepByExecId(execiseFightTime.getExecId());
        execiseFightTime.setStepId(esDtos.get(esDtos.size()-1).getId());
        execiseFightTimeDao.addExeciseFightTime(execiseFightTime);
    }

    /**
     * 获取当前作战时间
     * @param execId
     * @param now  如果推演已结束，则now是推演结束时间;其他情况now就是new Date()
     * @return
     */
    public long getNowFightTime(Integer execId,Date now){
        //所有步长
        List<ExeciseStepDto> esDtos = execiseStepDao.findExeciseStepByExecId(execId);
        //最新步长
        if(esDtos == null || esDtos.size() == 0){
            return 0L;
        }
        ExeciseStepDto esDto = esDtos.get(esDtos.size() - 1);
        //最新步长下的作战时间跳跃
        List<ExeciseFightTimeDto> eftDtos = execiseFightTimeDao.findExeciseFightTimeByExecIdAndStepId(execId, esDto.getId());
        if(esDto.getEndTime() != null){
            //当前正处于暂停中,以新步长的结束时间为当前时间(就算推演已结束,也是用这个时间)
            now = esDto.getEndTime();
        }
        int sl = esDto.getStepLength();
        //作战时间 = ft + (now - 最后一个步长的天文开始时间) * 步长
        long ft = esDto.getFightBeginTime().getTime() + (now.getTime() - esDto.getBeginTime().getTime()) * sl;
        if(eftDtos != null && eftDtos.size() > 0){
            //作战时间加上跳跃的时间段
            for(int i=0;i<eftDtos.size();i++){
                ExeciseFightTimeDto eftDto = eftDtos.get(i);
                ft += (eftDto.getFightEndTime().getTime() - eftDto.getFightBeginTime().getTime());
            }
        }
        return ft;
    }

}
