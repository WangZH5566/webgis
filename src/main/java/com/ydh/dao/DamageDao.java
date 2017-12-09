package com.ydh.dao;

import com.ydh.dto.BaseInfoMajorDto;
import com.ydh.dto.DamageDetailDto;
import com.ydh.dto.DamageDto;
import com.ydh.model.BasicInfoMajor;
import com.ydh.model.Damage;
import com.ydh.model.DamageDetail;

import java.util.List;

/**
 * @description:受损程度Dao
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public interface DamageDao {

    /**
     * 查询受损程度总数量
     * @param searchDto
     * @return
     */
    public Integer queryCount(DamageDto searchDto);

    /**
     * 分页查询受损程度
     * @param searchDto
     * @return
     */
    public List<DamageDto> queryPage(DamageDto searchDto);

    /**
     * 根据id,查询受损程度
     * @param id 受损程度id
     * @return
     */
    public DamageDto queryById(Integer id);

    /**
     * 新增受损程度
     * @param damage      受损程度实体
     * @return
     * @throws Exception
     */
    public int addDamage(Damage damage);

    /**
     * 修改受损程度
     * @param damage      受损程度实体
     * @return
     */
    public int modifyDamage(Damage damage);

    /**
     * 删除受损程度
     * @param id
     */
    public int deleteDamage(Integer id);

    /**
     * 根据受损程度id，删除受损程度下的所有明细
     * @param id
     */
    public int deleteDamageDetailByPid(Integer id);

    /**
     * 查询受损程度明细总数量
     * @param searchDto
     * @return
     */
    public Integer queryDetailCount(DamageDetailDto searchDto);

    /**
     * 分页查询受损程度明细
     * @param searchDto
     * @return
     */
    public List<DamageDetailDto> queryDetailPage(DamageDetailDto searchDto);

    /**
     * 根据id,查询受损程度详情
     * @param id 受损程度详情id
     * @return
     */
    public DamageDetailDto queryDetailById(Integer id);

    /**
     * 新增受损程度详情
     * @param damageDetail      受损程度详情实体
     * @return
     * @throws Exception
     */
    public int addDamageDetail(DamageDetail damageDetail);

    /**
     * 修改受损程度详情
     * @param damageDetail      受损程度详情实体
     * @return
     * @throws Exception
     */
    public int modifyDamageDetail(DamageDetail damageDetail);

    /**
     * 删除受损程度详情
     * @param id
     */
    public void deleteDamageDetail(Integer id);

    /**
     * 查询所有受损程度
     * @return
     */
    public List<DamageDto> queryAllDamage();

    /**
     * 查询所有受损程度明细
     * @return
     */
    public List<DamageDetailDto> queryAllDamageDetail();
}
