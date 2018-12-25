package io.bitjob.util;

import java.io.Serializable;

/**

 * Date: 15-3-17
 * Time: 下午5:40
 */
public class Query<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}