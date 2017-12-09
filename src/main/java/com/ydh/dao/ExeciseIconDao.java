package com.ydh.dao;

import com.ydh.dto.ExeciseIconDto;
import com.ydh.model.ExeciseIcon;
import com.ydh.model.ExeciseTroop;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 推演图标Dao
 */
public interface ExeciseIconDao {

    /**
     * 根据推演id,查询该推演下所有图标
     * @param execId
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByExecId(Integer execId);

    /**
     * 根据图标id,查询图标
     * @param ids
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByIds(Integer[] ids);

    /**
     * 根据推演id,查询该推演下所有图标(新增机场时用)
     * @param execId
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByExecIdForUsed(Integer execId);

    /**
     * 根据id,查询推演图标
     * @param id
     * @return
     */
    public ExeciseIconDto findExeciseIconById(Integer id);

    public ExeciseIcon findExeciseIcon(Integer id);

    /**
     * 新增ExeciseIcon
     * @param execiseIcon
     * @return
     */
    public int addExeciseIcon(ExeciseIcon execiseIcon);

    /**
     * 修改ExeciseIcon
     * @param execiseIcon
     * @return
     */
    public int modifyExeciseIcon(ExeciseIcon execiseIcon);

    public int modifyExeciseIconCoordinate(ExeciseIcon execiseIcon);

    /**
     * 删除ExeciseIcon
     * @param id
     * @return
     */
    public int deleteExeciseIcon(Integer id);

    /**
     * 删除基础下的ExeciseIcon
     * @param id
     * @return
     */
    public int deleteExeciseIconByAirport(Integer id);

    /**
     * 更新推演图标的数据
     * @param icon
     */
    public void modifyExecIconData(ExeciseIcon icon);

    /**
     * 修改推演图标的受损程度
     * @param icon
     */
    public void modifyExecIconDamage(ExeciseIcon icon);

    /**
     * 修改推演图标的装载时间
     * @param icon
     */
    public void modifyExecIconAddEquipmentTime(ExeciseIcon icon);

    /**
     * 更新推演图标的坐标和速度为null
     * @param id  图标id
     * @param aid 机场id
     */
    public void modifyExecIconNull(Integer id,Integer aid);

    /**
     * 根据推演id,单位id,mainType,查询推演图标
     * @param map
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByUnitIdAndMainType(Map<String,Object> map);

    /**
     * 根据troopId查询
     * @param troopId
     * @return
     */
    public List<ExeciseIcon> findExeciseIconByTroopId(Integer troopId);

    public int modifyByExeciseTroop(@Param("et") ExeciseTroop execiseTroop, @Param("co")String co);
    public int modifyByExeciseTroopWhenAirport(@Param("et") ExeciseTroop execiseTroop, @Param("co")String co);

}
