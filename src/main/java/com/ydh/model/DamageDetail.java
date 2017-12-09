package com.ydh.model;

/**
 * @description:受损程度明细实体-对应exec_damage_detail表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class DamageDetail extends BaseModel{

    //受损程度内容
    private String damageContent;
    //父表id
    private Integer pid;

    public String getDamageContent() {
        return damageContent;
    }

    public void setDamageContent(String damageContent) {
        this.damageContent = damageContent;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
