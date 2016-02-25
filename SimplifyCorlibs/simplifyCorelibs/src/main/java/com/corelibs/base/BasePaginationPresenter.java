package com.corelibs.base;

/**
 * 参考{@link BasePresenter}, 有分页需求的页面可使用继承自此类的子类来减少重复操作
 * <BR/>
 * Created by Ryan on 2016/1/7.
 */
public abstract class BasePaginationPresenter<T extends BasePaginationView>
        extends BaseRxPresenter<T> {

    private Page page = new Page(1, 10, 1);

    /**
     * 设置总页数
     */
    protected void setPageCount(int count) {
        page.setPageCount(count);
    }

    /**
     * 设置当前页码
     */
    protected void setPageNo(int pageNo) {
        page.setPageNo(pageNo);
    }

    /**
     * 设置每页数据个数
     */
    protected void setPageSize(int pageSize) {
        page.setPageSize(pageSize);
    }

    /**
     * 获取当前页码
     */
    protected int getPageNo() {
        return page.getPageNo();
    }

    /**
     * 获取每页数据个数
     */
    protected int getPageSize() {
        return page.getPageSize();
    }

    /**
     * 获取总页数
     */
    protected int getPageCount() {
        return page.getPageCount();
    }

    /**
     * 操作Page对象进行分页, 仅对当前页码, 每页数据个数, 总页数做操作, 具体页面效果需自行实现
     * @param reload 是否是刷新操作, true为刷新, false为
     * @return 分页是否成功, 当当前页码超过总页数时会返回false
     */
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
