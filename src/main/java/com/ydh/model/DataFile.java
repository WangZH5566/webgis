package com.ydh.model;

/**
 * Created by yqb on 2016/12/9 0009.
 */
public class DataFile {
    private Integer id;
    private String name;
    private String filePath;
    private Integer pid;
    private Boolean isDirectory;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Boolean getIsDirectory() {
        return isDirectory;
    }
    public Boolean getIsParent() {
        return isDirectory;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }


}
