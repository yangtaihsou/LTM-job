package io.bitjob.util.dbrouter.bean;

/**
 * 动态数据源实现中KEY的存放工具类
 *
 * @author yangkuan
 * @ClassName: DbContextHolder
 * @Description: 动态数据源实现中KEY的存放工具类：使用treadLocal的方式来保证线程安全
 * @date 2012-3-13 下午01:41:35
 */
public class DbContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    private static final ThreadLocal<String> tableIndexHolder = new ThreadLocal<String>();

    public static String getDbKey() {
        return (String) contextHolder.get();
    }

    public static void setDbKey(String dbKey) {
        contextHolder.set(dbKey);
    }

    public static void clearDbKey() {
        contextHolder.remove();
    }

    public static String getTableIndex() {
        return (String) tableIndexHolder.get();
    }

    public static void setTableIndex(String tableIndex) {
        tableIndexHolder.set(tableIndex);
    }

    public static void clearTableIndex() {
        tableIndexHolder.remove();
    }


}