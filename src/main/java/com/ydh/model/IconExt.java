package com.ydh.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class IconExt extends BaseModel{
    //主键
    private Integer id;
    private String name;
    private String value;
    private Integer icon;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
