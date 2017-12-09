package com.ydh.model;

import com.ydh.enums.BasicInfoMainType;

/**
 * @description:基础资料类型实体-对应exec_basic_info_type表
 * @author: xxx.
 * @createDate: 2016/12/21.
 */
public class BasicInfoType extends BaseModel{

    //类型名称
    private String typeName;
    //父节点id
    private Integer pid;
    //所属图标id
    private Integer iconId;
    //主要分类
    private BasicInfoMainType mainType;
    //节点路径(从根节点id开始,到当前id,逗号分隔)
    private String nodePath;

    //主要分类
    private Integer mainTypeValue;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public BasicInfoMainType getMainType() {
        return mainType;
    }

    public void setMainType(BasicInfoMainType mainType) {
        this.mainType = mainType;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public Integer getMainTypeValue() {
        return mainTypeValue;
    }

    public void setMainTypeValue(Integer mainTypeValue) {
        this.mainTypeValue = mainTypeValue;
    }
}
