package io.bitjob.export;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by yangkuan on 15/8/13.
 */
public class GetSignParaUtil {
    public static <T> String getParams(T obj) throws Exception {
        if (obj == null) {
            throw new RuntimeException("obj is empty!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> map = new ConcurrentSkipListMap<String, Object>();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                if (!field.getName().equals("hmac")) {
                    map.put(field.getName(), field.get(obj));
                }
            }
        }
        Class superClazz = clazz.getSuperclass();
        Field[] superFields = superClazz.getDeclaredFields();
        for (Field field : superFields) {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                if (!field.getName().equals("hmac")) {
                    map.put(field.getName(), field.get(obj));
                }
            }
        }
        if (map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }
            return stringBuilder.toString();
        } else {
            return null;
        }
    }
}
