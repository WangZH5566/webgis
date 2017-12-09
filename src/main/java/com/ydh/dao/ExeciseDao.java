package com.ydh.dao;


import com.ydh.dto.ExeciseDto;
import com.ydh.dto.ExeciseUserDto;
import com.ydh.model.Execise;
import com.ydh.model.ExeciseDepartment;
import com.ydh.model.ExeciseUnit;
import com.ydh.model.ExeciseUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:推演Dao
 * @author: .
 * @createDate: 2016/09/04.
 */
public interface ExeciseDao {

    /**
     * 查询推演总数量
     * @param searchDto
     * @return
     */
    public Integer queryExecCount(ExeciseDto searchDto);

    /**
     * 分页查询推演
     * @param searchDto
     * @return
     */
    public List<ExeciseDto> queryExecPage(ExeciseDto searchDto);

    /**
     * 根据id,查询推演
     * @param id
     * @return
     */
    public ExeciseDto queryById(Integer id);

    /**
     * 根据推演id,查询该推演下的所有单位
     * @param id 推演id
     * @return
     */
    public List<ExeciseUnit> queryExeciseUnit(Integer id);

    /**
     * 根据推演id,查询该推演下的所有台位
     * @param id 推演id
     * @return
     */
    public List<ExeciseDepartment> queryExeciseDepartment(Integer id);

    /**
     * 根据推演id,查询该推演下的所有用户
     * @param id 推演id
     * @return
     */
    public List<ExeciseUser> queryExeciseUser(Integer id);

    /**
     * 根据当前日期查询用户的最大序列号
     * @param curDate
     * @return
     */
    public Integer queryMaxSerialNo(String curDate);

    /**
     * 打印角色清单:根据推演id，查询推演人员(包括导演)
     * @param searchDto
     * @return
     */
    public List<ExeciseUserDto> queryExecUserPrint(ExeciseUserDto searchDto);

    /**
     * 新增推演
     * @param execise
     * @return
     */
    public int addExecise(Execise execise);

    /**
     * 修改推演
     * @param execise
     * @return
     */
    public int modifyExecise(Execise execise);

    /**
     * 删除推演
     * @param idArr 推演id
     * @return
     */
    public int deleteExecise(Integer[] idArr);

    /**
     * 新增推演用户
     * @param execiseUser
     * @return
     */
    public int addExeciseUser(ExeciseUser execiseUser);

    /**
     * 修改推演人员(仅导演)
     * @param execiseUser
     */
    public int modifyExeciseDirector(ExeciseUser execiseUser);

    /**
     * 新增推演单位
     * @param execiseUnit
     * @return
     */
    public int addExeciseUnit(ExeciseUnit execiseUnit);

    /**
     * 修改推演单位
     * @param execiseUnit
     * @return
     */
    public int modifyExeciseUnit(ExeciseUnit execiseUnit);

    /**
     * 新增推演台位
     * @param execiseDepartment
     * @return
     */
    public int addExeciseDepartment(ExeciseDepartment execiseDepartment);

    /**
     * 删除推演用户
     * @param idArr 所属推演id
     * @return
     */
    public int deleteExeciseUserByArr(Integer[] idArr);

    /**
     * 删除推演用户
     * @param map
     * @return
     */
    public int deleteExeciseUserByMap(Map<String,Object> map);

    /**
     * 删除推演台位
     * @param map
     * @return
     */
    public int deleteExeciseDepartmentByMap(Map<String,Object> map);

    /**
     * 删除推演单位
     * @param idArr 所属推演id
     * @return
     */
    public int deleteExeciseUnitByArr(Integer[] idArr);

    /**
     * 删除推演台位
     * @param idArr 所属推演id
     * @return
     */
    public int deleteExeciseDeByArr(Integer[] idArr);

    /**
     * 删除推演台位
     * @param idList 台位id集合
     * @return
     */
    public int deleteExeciseDepartmentByEdId(List<Integer> idList);

    /**
     * 删除推演人员
     * @param idList 台位id集合
     * @return
     */
    public int deleteExeciseUserByEdIdList(List<Integer> idList);

    /**
     * 删除推演单位
     * @param map
     * @return
     */
    public int deleteExeciseUnitByMap(Map<String,Object> map);


    /**
     * 根据推演id，查询推演人员(导演除外)总数量
     * @param searchDto
     * @return
     */
    public Integer queryExecUserCount(ExeciseUserDto searchDto);

    /**
     * 根据推演id，查询推演人员(导演除外)
     * @param searchDto
     * @return
     */
    public List<ExeciseUserDto> queryExecUserPage(ExeciseUserDto searchDto);

    /**
     * 修改推演人员(导演除外)
     * @param execiseUserDto
     */
    public int modifyExeciseUser(ExeciseUserDto execiseUserDto);

    /**
     * 修改海图
     * @param execiseDto
     */
    public int modifyExeciseSeaChart(ExeciseDto execiseDto);

    /**
     * 删除推演人员
     * @param id
     */
    public int deleteExeciseUser(Integer id);

    List<ExeciseDto> queryByLoginUser(@Param("userid") Integer userid);

    /**
     * 修改推演状态(导演开始、结束推演用)
     * @param execise
     */
    public void modifyExeciseStatus(Execise execise);

    /**
     * 修改推演暂停状态(导演用)
     * @param execise
     */
    public void modifyExecisePause(Execise execise);

    /**
     * 查看当前用户可以发送文电的用户:
     * @param searchDto
     * @return
     */
    public List<ExeciseUserDto> queryExecUserForSendTelegram(ExeciseUserDto searchDto);

    /**
     * 查询除当前推演外的所有推演
     * @param id
     * @return
     */
    public List<ExeciseDto> queryExceptCurrent(Integer id);
}