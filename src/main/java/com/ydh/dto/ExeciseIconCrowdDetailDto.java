package com.ydh.dto;

/**
 * @description:图标集群关系Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class ExeciseIconCrowdDetailDto extends BaseDto{

    //集群id
    private Integer crowdId;
    //集群名称
    private String crowdName;
    //图标id
    private Integer iconId;
    //图标名称
    private String iconName;
    //是否旗舰/长机(0否1是)
    private Integer isMain;
    //所属机场
    private Integer belongAirport;

    public Integer getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public Integer getBelongAirport() {
        return belongAirport;
    }

    public void setBelongAirport(Integer belongAirport) {
        this.belongAirport = belongAirport;
    }
}
