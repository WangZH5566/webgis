package com.ydh.model;

import com.ydh.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	//主键
	private Integer id;
    //创建时间
    private Date createTime;
    //创建人
    private Integer createBy;
    //最后更新时间
    private Date lastUpdateTime;
    //最后更新人
    private Integer lastUpdateBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTimeView() {
        return DateUtil.getDateDefaultFormateText(this.createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
