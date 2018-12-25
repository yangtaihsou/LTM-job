package io.bitjob.util;

public class PageContextHolder {
    private static final ThreadLocal<Integer> startRowHolder = new ThreadLocal<Integer>();
    private static final ThreadLocal<Integer> endRowHolder = new ThreadLocal<Integer>();
    private static final ThreadLocal<Integer> pageSizeHolder = new ThreadLocal<Integer>();

    public static Integer getStartRow() {
        return (Integer) startRowHolder.get();
    }

    public static void setStartRow(Integer startRow) {
        startRowHolder.set(startRow);
    }

    public static void clearStartRow() {
        startRowHolder.remove();
    }

    public static Integer getEndRow() {
        return (Integer) endRowHolder.get();
    }

    public static void setEndRow(Integer endRow) {
        endRowHolder.set(endRow);
    }

    public static void clearEndRow() {
        endRowHolder.remove();
    }

    public static Integer getPageSize() {
        return (Integer) pageSizeHolder.get();
    }

    public static void setPageSize(Integer endRow) {
        pageSizeHolder.set(endRow);
    }

    public static void clearPageSize() {
        pageSizeHolder.remove();
    }

}