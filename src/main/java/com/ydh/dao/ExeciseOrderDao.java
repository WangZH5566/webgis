package com.ydh.dao;

import com.ydh.model.ExeciseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExeciseOrderDao {

    void addOrder(ExeciseOrder order);

    List<ExeciseOrder> listOrder(@Param("execId") Integer execId);

    /**
     * 修改当前推演下的所有指令的开始时间
     * @param order
     * @return
     */
    public int modifyBeginTime(ExeciseOrder order);

    /**
     * 修改维修开始时间
     * @param execiseOrder
     */
    public int modifyRepairBeginTime(ExeciseOrder execiseOrder);
    /**
     * 修改装载开始时间
     * @param execiseOrder
     */
    public int modifyAddEquipmentBeginTime(ExeciseOrder execiseOrder);
    /**
     * 修改装载/维修结束标识量
     * @param id 指令id
     */
    public int modifyIsEnd(Integer id);
}
