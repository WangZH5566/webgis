package com.ydh.service;

import com.ydh.dao.BaseInfoMajorDao;
import com.ydh.dto.BaseInfoDto;
import com.ydh.dto.BaseInfoMajorDto;
import com.ydh.model.BasicInfoMajor;
import com.ydh.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:基础资料专业service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class BaseInfoMajorService {

    @Autowired
    private BaseInfoMajorDao baseInfoMajorDao;

    /**
     * 查询全部专业总数量
     * @return
     */
    public List<BaseInfoMajorDto> queryAll(){
        return baseInfoMajorDao.queryAll();
    }

    /**
     * 查询专业总数量
     * @param searchDto
     * @return
     */
    public Integer queryCount(BaseInfoMajorDto searchDto) {
        return baseInfoMajorDao.queryCount(searchDto);
    }

    /**
     * 分页查询专业
     * @param searchDto
     * @return
     */
    public List<BaseInfoMajorDto> queryPage(BaseInfoMajorDto searchDto) {
        return baseInfoMajorDao.queryPage(searchDto);
    }

    /**
     * 根据id,查询专业
     * @param id 专业id
     * @return
     */
    public BaseInfoMajorDto queryById(Integer id) {
        return baseInfoMajorDao.queryById(id);
    }


    /**
     * 新增专业
     * @param basicInfoMajor      专业实体
     * @return
     * @throws Exception
     */
    public String addBaseInfoMajor(BasicInfoMajor basicInfoMajor) throws Exception {
        //数据验证
        String msg = validateBaseInfoMajor(basicInfoMajor);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        baseInfoMajorDao.addBaseInfoMajor(basicInfoMajor);
        return "success";
    }

    /**
     * 修改专业
     * @param basicInfoMajor      专业实体
     * @return
     * @throws Exception
     */
    public String modifyBaseInfoMajor(BasicInfoMajor basicInfoMajor) throws Exception {
        //数据验证
        String msg = validateBaseInfoMajor(basicInfoMajor);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        baseInfoMajorDao.modifyBaseInfoMajor(basicInfoMajor);
        return "success";
    }

    /**
     * 数据验证
     * @param basicInfoMajor     专业实体
     * @return
     * @throws Exception
     */
    private String validateBaseInfoMajor(BasicInfoMajor basicInfoMajor) throws Exception {
        if (basicInfoMajor == null) {
            return Constant.DATA_ABNORMAL;
        }
        if(basicInfoMajor.getMajorName()  == null){
            return "[专业名称]不能为空";
        }
        List<BasicInfoMajor> list = baseInfoMajorDao.queryRepeation(basicInfoMajor);
        if(list != null && list.size() > 0){
            return "[专业名称]不能重复";
        }
        return "";
    }

    /**
     * 删除专业
     * @param id
     */
    public void deleteBaseInfoMajor(Integer id){
        baseInfoMajorDao.deleteBaseInfoMajor(id);
    }
}
