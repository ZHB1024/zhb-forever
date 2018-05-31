package com.forever.zhb.search.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	
	public static final transient Page EMPTY_PAGE = new Page(new ArrayList(0), 0, 0, 0, false);
    public static final transient int DEFAULT_COUNT = 10;
    List<T> objects;
    int start;
    int totalCount = 0;

    int pageCount = 0;
    boolean hasNext;

    public Page(List<T> l, int s, int totalCount, int pageCount, boolean hasNext) {
        this.objects = new ArrayList(l);
        this.start = s;
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.hasNext = hasNext;
    }

    public List<T> getList() {
        return this.objects;
    }

    public int getCurPage() {
        if (0 == this.pageCount)
            return 0;
        return (this.start / this.pageCount + 1);
    }

    public int getTotalPage() {
        if (this.pageCount != 0) {
            if (this.totalCount % this.pageCount == 0) {
                return (this.totalCount / this.pageCount);
            }
            return (this.totalCount / this.pageCount + 1);
        }
        return 0;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public boolean isNextPageAvailable() {
        return this.hasNext;
    }

    public boolean isPreviousPageAvailable() {
        return (this.start > 0);
    }

    public int getStartOfNextPage() {
        return (this.start + this.pageCount);
    }

    public int getStartOfPreviousPage() {
        return Math.max(this.start - this.pageCount, 0);
    }

    public int getStartOfLastPage() {
        return (this.pageCount * (getTotalPage() - 1));
    }

    public int getSize() {
        return this.objects.size();
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
