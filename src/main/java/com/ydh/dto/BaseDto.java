package com.ydh.dto;

/**
 * @description:Dto基类
 * @author: xxx.
 * @createDate: 2016/9/10.
 */
public class BaseDto {

    //主键
    private Integer id;
    //当前页号
    protected int pageNo = 1;
    //每页显示数据量
    protected int pageSize = 10;
    //总数据量
    protected int totalCount = -1;
    //查询条件
    protected String searchSql;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getSearchSql() {
        return searchSql;
    }

    public void setSearchSql(String searchSql) {
        this.searchSql = searchSql;
    }

    public String getMysqlQueryPage(){
        StringBuffer query = new StringBuffer(" limit ");
        query.append((pageNo - 1) * pageSize)
                .append(",")
                .append(pageSize);
        return query.toString();
    }

    public String getPostgreSQLQueryPage(){
        StringBuffer query = new StringBuffer(" limit ");
        query.append(pageSize).append(" offset ").append((pageNo - 1) * pageSize);
        return query.toString();
    }

    public int getToltalPage(){
        int totalPage=totalCount/pageSize;
        if(totalCount%pageSize>0){
            totalPage++;
        }
        return totalPage;
    }
}
