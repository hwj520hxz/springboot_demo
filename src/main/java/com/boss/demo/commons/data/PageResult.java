package com.boss.demo.commons.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
@ApiModel(description= "分页结果对象")
public class PageResult<T> {
    @ApiModelProperty(value = "结果总数",position = 1)
    private Long total;
    @ApiModelProperty(value = "当前页数据",position = 1)
    private List<T> rows;
    @ApiModelProperty(value = "总页数",position = 1)
    private Integer pages;
    @ApiModelProperty(value = "页码",position = 1)
    private Integer pageNum;
    @ApiModelProperty(value = "页容量",position = 1)
    private Integer pageSize;


    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Long total, List<T> rows, Integer pages, Integer pageSize, Integer pageNum) {
        this.total = total;
        this.rows = rows;
        this.pages = pages;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
