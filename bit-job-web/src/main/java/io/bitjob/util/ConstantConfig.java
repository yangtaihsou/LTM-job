package io.bitjob.util;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

/**

 * Date: 14-9-17
 * Time: 下午6:03
 */

public class ConstantConfig {
    @Autowired
    public static LinkedHashMap<Integer, String> taskTypeConfig;

    public static LinkedHashMap getTaskTypeConfig() {
        return taskTypeConfig;
    }
}
