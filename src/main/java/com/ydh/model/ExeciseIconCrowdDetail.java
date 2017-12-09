package com.ydh.model;

/**
 * @description:图标集群关系实体
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class ExeciseIconCrowdDetail extends BaseModel{

    //集群id
    private Integer crowdId;
    //图标id
    private Integer iconId;
    //是否旗舰/长机(0否1是)
    private Integer isMain;

    public Integer getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }
}
