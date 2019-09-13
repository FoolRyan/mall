package bean.admin;

import java.util.List;

public class Page<T> {


    private List<T> list;
    private int totalCount;
    private int currentPage;
    private int previousPage;
    private int nextPage;
    private int totalPageCount;
    public final static int PAGE_SIZE = 4;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    @Override
    public String toString() {
        return "Page{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", previousPage=" + previousPage +
                ", nextPage=" + nextPage +
                ", totalPageCount=" + totalPageCount +
                '}';
    }
}
