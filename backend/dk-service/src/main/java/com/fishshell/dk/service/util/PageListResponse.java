package com.fishshell.dk.service.util;

/**
 * @author alfred.zhou
 * @since 2019-02-01
 */

public class PageListResponse<T> extends DataResponse<T> {
    private Integer page;
    private Integer pageSize;
    private long totalCount;

    private PageListResponse(Integer code, T data) {
        super(code, data);
    }

    public PageListResponse(Integer code, T data, Integer page, Integer pageSize, Integer totalCount) {
        this(code, data);
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
    }

    private void setPage(Integer page) {
        this.page = page;
    }

    private void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public long getTotalCount() {
        return this.totalCount;
    }
}