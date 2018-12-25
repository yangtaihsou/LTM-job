package io.bitjob.util.dbrouter.bean;


import io.bitjob.util.dbrouter.RouteConfigConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库查询参数使用的MAP
 *
 * @author yangkuan
 */
public class ParamHashMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 5541751367713832209L;

    @SuppressWarnings("unchecked")
    public ParamHashMap() {
        super();
        this.put((K) RouteConfigConstants.ROUTE_TABLE_INDEX_NAME, (V) DbContextHolder.getTableIndex());
    }

    @SuppressWarnings("unchecked")
    public ParamHashMap(Map<? extends K, ? extends V> m) {
        super(m);
        this.put((K) RouteConfigConstants.ROUTE_TABLE_INDEX_NAME, (V) DbContextHolder.getTableIndex());
    }

    @SuppressWarnings("unchecked")
    public ParamHashMap(int initialCapacity) {
        super(initialCapacity);
        this.put((K) RouteConfigConstants.ROUTE_TABLE_INDEX_NAME, (V) DbContextHolder.getTableIndex());
    }

    @SuppressWarnings("unchecked")
    public ParamHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.put((K) RouteConfigConstants.ROUTE_TABLE_INDEX_NAME, (V) DbContextHolder.getTableIndex());
    }

}
