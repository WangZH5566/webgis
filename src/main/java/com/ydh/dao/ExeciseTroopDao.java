package com.ydh.dao;

import com.ydh.dto.ExeciseTroopDto;
import com.ydh.model.ExeciseTroop;

import java.util.List;

public interface ExeciseTroopDao {

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public ExeciseTroopDto queryById(Integer id);

    /**
     * 根据推演id查询
     * @param execId
     * @return
     */
    public List<ExeciseTroopDto> queryByExecId(Integer execId);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public List<ExeciseTroopDto> queryByByAirportId(Integer id);

    /**
     * 查询推演兵力总数量
     * @param searchDto
     * @return
     */
    public Integer queryExeciseTroopCount(ExeciseTroopDto searchDto);

    /**
     * 分页查询推演兵力
     * @param searchDto
     * @return
     */
    public List<ExeciseTroopDto> queryExeciseTroopPage(ExeciseTroopDto searchDto);

    /**
     * 新增ExeciseTroop
     * @param execiseTroop
     * @return
     */
    public int addExeciseTroop(ExeciseTroop execiseTroop);

    public void modifyExeciseTroop(ExeciseTroop execiseTroop);

    public void modifyExeciseTroopWhenAirport(ExeciseTroop execiseTroop);

    /**
     * 根据id删除ExeciseTroop
     * @param id
     * @return
     */
    public int deleteExeciseTroopById(Integer id);

    /**
     * 根据机场id删除ExeciseTroop
     * @param id
     * @return
     */
    public int deleteExeciseTroopByAirportId(Integer id);

    /**
     * 根据推演id查询maintype(去重复)
     * @param execId
     * @return
     */
    public List<Integer> queryMainTypeByExecId(Integer execId);
}
