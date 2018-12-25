package io.bitjob.service.check;

/**
 * Created by yangkuan on 15/8/12.
 */
public interface ParaCheck<T> {
    public void check(T t) throws Exception;
}
