package com.ydh.model;

/**
 * @description:受损程度实体-对应exec_damage表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class Damage extends BaseModel{

    //受损程度名称
    private String damageName;
    //图标路径
    private String imgPath;

    public String getDamageName() {
        return damageName;
    }

    public void setDamageName(String damageName) {
        this.damageName = damageName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
