package io.bitjob.util;

/**

 * Date: 15-3-17
 * Time: 下午5:39
 */

import java.io.Serializable;

public class PageQuery<T> extends Query<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNo;
    private int pageSize;

    public PageQuery() {
    }

    public PageQuery(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
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