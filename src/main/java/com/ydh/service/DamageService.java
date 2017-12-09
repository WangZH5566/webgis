package com.ydh.service;

import com.ydh.dao.DamageDao;
import com.ydh.dto.BaseInfoMajorDto;
import com.ydh.dto.DamageDetailDto;
import com.ydh.dto.DamageDto;
import com.ydh.model.BasicInfoMajor;
import com.ydh.model.Damage;
import com.ydh.model.DamageDetail;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:受损程度service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class DamageService {

    @Autowired
    private DamageDao damageDao;

    /**
     * 查询受损程度总数量
     * @param searchDto
     * @return
     */
    public Integer queryCount(DamageDto searchDto) {
        return damageDao.queryCount(searchDto);
    }

    /**
     * 分页查询受损程度
     * @param searchDto
     * @return
     */
    public List<DamageDto> queryPage(DamageDto searchDto) {
        return damageDao.queryPage(searchDto);
    }

    /**
     * 根据id,查询受损程度
     * @param id 受损程度id
     * @return
     */
    public DamageDto queryById(Integer id) {
        return damageDao.queryById(id);
    }


    /**
     * 新增受损程度
     * @param damage      受损程度实体
     * @return
     * @throws Exception
     */
    public String addDamage(Damage damage) throws Exception{
        damageDao.addDamage(damage);
        return "success";
    }

    /**
     * 修改受损程度
     * @param damage      受损程度实体
     * @return
     * @throws Exception
     */
    public String modifyDamage(Damage damage) throws Exception {
        damageDao.modifyDamage(damage);
        return "success";
    }

    /**
     * 删除受损程度
     * @param id
     */
    public void deleteDamage(Integer id){
       damageDao.deleteDamageDetailByPid(id);
       damageDao.deleteDamage(id);
    }

    /**
     * 查询受损程度明细总数量
     * @param searchDto
     * @return
     */
    public Integer queryDetailCount(DamageDetailDto searchDto) {
        return damageDao.queryDetailCount(searchDto);
    }

    /**
     * 分页查询受损程度明细
     * @param searchDto
     * @return
     */
    public List<DamageDetailDto> queryDetailPage(DamageDetailDto searchDto) {
        return damageDao.queryDetailPage(searchDto);
    }

    /**
     * 根据id,查询受损程度详情
     * @param id 受损程度详情id
     * @return
     */
    public DamageDetailDto queryDetailById(Integer id) {
        return damageDao.queryDetailById(id);
    }

    /**
     * 新增受损程度详情
     * @param damageDetail      受损程度详情实体
     * @return
     * @throws Exception
     */
    public String addDamageDetail(DamageDetail damageDetail) throws Exception{
        damageDao.addDamageDetail(damageDetail);
        return "success";
    }

    /**
     * 修改受损程度详情
     * @param damageDetail      受损程度详情实体
     * @return
     * @throws Exception
     */
    public String modifyDamageDetail(DamageDetail damageDetail) throws Exception {
        damageDao.modifyDamageDetail(damageDetail);
        return "success";
    }


    /**
     * 删除受损程度详情
     * @param id
     */
    public void deleteDamageDetail(Integer id){
        damageDao.deleteDamageDetail(id);
    }

    /**
     * 查询所有受损程度
     * @return
     */
    public List<DamageDto> queryAllDamage() {
        return damageDao.queryAllDamage();
    }

    /**
     * 查询所有受损程度明细
     * @return
     */
    public List<DamageDetailDto> queryAllDamageDetail() {
        return damageDao.queryAllDamageDetail();
    }
}
