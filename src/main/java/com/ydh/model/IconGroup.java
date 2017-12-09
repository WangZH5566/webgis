package com.ydh.model;

/**
 * Created by yqb on 2016/11/24 0024.
 */
public class IconGroup extends BaseModel{
    private String name;
    private Integer pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
