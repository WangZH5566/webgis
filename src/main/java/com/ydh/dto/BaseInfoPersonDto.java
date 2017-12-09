package com.ydh.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:基础资料-人员关系Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class BaseInfoPersonDto extends BaseDto{
    //基础资料id
    private Integer biId;
    //人员id
    private Integer personId;
    //人员专业名称
    private String personName;
    //技术等级
    private Integer technologyLevel;

    private static Map<Integer,String> statusMap=new HashMap<Integer,String>();
    static {
        statusMap.put(0,"技工");
        statusMap.put(1,"技师");
        statusMap.put(2,"高级技工");
    }

    public Integer getBiId() {
        return biId;
    }

    public void setBiId(Integer biId) {
        this.biId = biId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getTechnologyLevel() {
        return technologyLevel;
    }

    public String getTechnologyLevelView() {
        String technologyLevelName=statusMap.get(this.getTechnologyLevel());
        return technologyLevelName==null?"":technologyLevelName;
    }

    public void setTechnologyLevel(Integer technologyLevel) {
        this.technologyLevel = technologyLevel;
    }
}
