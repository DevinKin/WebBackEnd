package cn.devinkin.case1.domain;

import java.util.List;

public class PageBean<T> {
    private List<T> list;       //当前页内容
    private int currentPage;    //当前页码
    private int pageSize;       //当前页大小
    private int totalCount;     //记录总数
    private int totalPage;      //页码总数

    @Override
    public String toString() {
        return "PageBean{" +
                "list=" + list +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                '}';
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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

    public PageBean(List<T> list, int currentPage, int pageSize, int totalCount) {
        this.list = list;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    /**
     * 获取总页数
     * @return 总页数
     */
    public int getTotalPage() {
        return (int) Math.ceil(totalCount*1.0/pageSize);
    }

}
