package cn.devinkin.product.domain;

import java.util.List;

public class PageBean<T> {
    private List<T> list;       //查询
    private Integer currentPage;    //获取
    private Integer pageSize;       //常量
    private Integer totalPage;      //计算
    private Integer totalRecord;    //查询

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return (int)Math.ceil(totalRecord*1.0 / pageSize);
    }


    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public PageBean(List<T> list, Integer currentPage, Integer pageSize, Integer totalRecord) {
        this.list = list;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
    }
}
