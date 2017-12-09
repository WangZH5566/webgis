package com.ydh.dao;

import com.ydh.dto.ExeciseEquipmentDto;
import com.ydh.model.ExeciseEquipment;

import java.util.List;

public interface ExeciseEquipmentDao {
    public List<ExeciseEquipmentDto> queryIconEquipment(Integer iconID);

    void batchAdd(List<ExeciseEquipment> list);

    /**
     * 根据推演图标id,查询该推演图标下的全部装载数据
     * @param execIconId
     * @return
     */
    public List<ExeciseEquipmentDto> findExeciseEquipmentByExecIconId(Integer execIconId);

    /**
     * 根据推演图标id删除该推演图标下的全部装载数据
     * @return
     */
    public int deleteExeciseEquipmentByExecIconId(Integer execIconId);

    /**
     * 根据ExeciseTroopId删除ExeciseEquipment
     * @param troopId
     * @return
     */
    public int deleteExeciseEquipmentByExeciseTroopIdId(Integer troopId);

    /**
     * 根据机场的ExeciseTroopId删除ExeciseEquipment
     * @param troopId
     * @return
     */
    public int deleteExeciseEquipmentByExeciseTroopIdAirportId(Integer troopId);

    /**
     * 根据ExeciseTroopId和ExeciseIconId去更新
     * @param troopId
     * @param iconId
     * @return
     */
    public int modifyExeciseIconIdByExeciseTroopId(Integer troopId,Integer iconId);

    /**
     * 根据推演id查询
     * @param execId
     * @return
     */
    public List<ExeciseEquipmentDto> findExeciseEquipmentByExecId(Integer execId);

}
