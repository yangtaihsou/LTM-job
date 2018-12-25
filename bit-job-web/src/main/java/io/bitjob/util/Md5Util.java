package io.bitjob.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**

 * Date: 14-9-15
 * Time: 下午5:44
 */

public class Md5Util {
    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static String digestByMd5(String data) {
        try {
            return DigestUtils.md5Hex(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("Md5Util exception: data=" + data);

            throw new RuntimeException("Md5Util exception: data=" + data);
        }

    }
}


