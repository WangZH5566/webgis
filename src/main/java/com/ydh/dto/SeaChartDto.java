package com.ydh.dto;

/**
 * @description: 海图Dto
 * @author: xxx.
 * @createDate: 2016/5/05.
 */
public class SeaChartDto extends BaseDto{

    //海图名称
    private String seaChartName;

    private String url;

    private String bound;

    private String layer;

    private Integer layerNum;

    private Integer deleted = 0;

    private String resolution;

    public String getSeaChartName() {
        return seaChartName;
    }

    public void setSeaChartName(String seaChartName) {
        this.seaChartName = seaChartName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBound() {
        return bound;
    }

    public void setBound(String bound) {
        this.bound = bound;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Integer getLayerNum() {
        return layerNum;
    }

    public void setLayerNum(Integer layerNum) {
        this.layerNum = layerNum;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
