package com.ydh.model;

public class SeaChart extends BaseModel {

    private String seaChartName;

    private String url;

    private String layer;

    private String bound;

    private Integer layerNum = 0;

    private String resolution;

    private Integer deleted = 0;

    public String getSeaChartName() {
        return seaChartName;
    }

    public void setSeaChartName(String seaChartName) {
        this.seaChartName = seaChartName;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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
