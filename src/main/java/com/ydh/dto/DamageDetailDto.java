package com.ydh.dto;

/**
 * @description:受损程度明细Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class DamageDetailDto extends BaseDto{

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
