package com.ydh.dto;

/**
 * Created by yqb on 2016/11/27 0027.
 */
public class EquipmentDto extends BaseDto{
    private Integer id;
    private String name;
    private Integer loadTime;

    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Integer loadTime) {
        this.loadTime = loadTime;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
