package com.corelibs.base;

/**
 * Created by Ryan on 2016/1/7.
 */
public abstract class BasePaginationPresenter<T extends BasePaginationView> extends BasePresenter<T> {

    private Page page = new Page(1, 10, 1);

    protected void setPageCount(int count) {
        page.setPageCount(count);
    }

    protected void setPageNo(int pageNo) {
        page.setPageNo(pageNo);
    }

    protected void setPageSize(int pageSize) {
        page.setPageSize(pageSize);
    }

    protected int getPageNo() {
        return page.getPageNo();
    }

    protected int getPageSize() {
        return page.getPageSize();
    }

    protected int getPageCount() {
        return page.getPageCount();
    }

    protected boolean doPagination(boolean reload) {
        if (reload) {
            page.pageNo = 1;
        } else {
            page.pageNo++;
            if(page.pageNo > page.pageCount) {
                view.onAllPageLoaded();
                return false;
            }
        }
        return true;
    }

    class Page {
        private int pageNo;
        private int pageSize;
        private int pageCount;

        public Page(int pageNo, int pageSize, int pageCount) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.pageCount = pageCount;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
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
    }
}
