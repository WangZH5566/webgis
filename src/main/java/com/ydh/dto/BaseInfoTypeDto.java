package com.ydh.dto;

import com.ydh.enums.BasicInfoMainType;

/**
 * @description:基础资料分类Dto
 * @author: xxx.
 * @createDate: 2016/9/5.
 */
public class BaseInfoTypeDto extends BaseDto{
    //类型名称
    private String typeName;
    //父节点id
    private Integer pid;
    //所属图标id
    private Integer iconId;
    //所属图标id
    private String iconPath;
    //主要分类
    private BasicInfoMainType mainType;
    //节点路径(从根节点id开始,到当前id,逗号分隔)
    private String nodePath;

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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
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
}
