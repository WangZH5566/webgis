package com.ydh.enums;

/**
 * @description:基础资料分类-主要分类枚举类
 * @author: xxx.
 * @createDate: 2016/12/22.
 */
public enum BasicInfoMainType {
    jianchuan("舰船"),
    feiji("飞机"),
    andao("岸导"),
    cangchu("仓储机构"),
    xiuli("修理机构"),
    leidan("雷弹"),
    jichang("机场"),
    qicai("器材");

    //成员变量
    private String text;

    /**
     * 构造方法
     * @param text
     */
    private BasicInfoMainType(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
