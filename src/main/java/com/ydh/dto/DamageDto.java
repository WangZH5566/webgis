package com.ydh.dto;

/**
 * @description:受损程度Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class DamageDto extends BaseDto{

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
