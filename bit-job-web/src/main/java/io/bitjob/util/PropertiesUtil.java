package io.bitjob.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Description: 属性文件工具类
 *

 */
public class PropertiesUtil {

    private static final Logger log = Logger.getLogger(PropertiesUtil.class);

    /**
     * 服务地址配置
     */
    public static ConcurrentMap<String, String> serviceUrlMap = new ConcurrentHashMap<String, String>();
    public static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<String, String>();


    static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: 初始化方法
     *
     * @throws Exception
     */
    private static void init() throws Exception {
        try {
            log.info("Properties init *******************");

            ResourceBundle configRB = ResourceBundle
                    .getBundle("props/config");
            configInit(configRB);
        } catch (Exception e) {
            log.error(e.getStackTrace());
            log.error(e);
        }
    }

    private static void serviceUrlInit(ResourceBundle serviceUrlRB) {
        Enumeration<String> e = serviceUrlRB.getKeys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            serviceUrlMap.put(key, serviceUrlRB.getString(key));
        }
    }

    private static void configInit(ResourceBundle configRB) {
        Enumeration<String> e = configRB.getKeys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            configMap.put(key, configRB.getString(key));
        }
    }


    /**
     * 查询字符串里是否包含该配置
     *
     * @param org
     * @param value
     * @return
     */
    private static boolean isContains(String org, String value) {
        boolean result = false;
        if (StringUtils.isNotEmpty(org)) {
            String[] maps = org.split(",");
            for (String item : maps) {
                if (item.equals(value)) {
                    result = true;
                }
            }
        }
        return result;
    }
}
