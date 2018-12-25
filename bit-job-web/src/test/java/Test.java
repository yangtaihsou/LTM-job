import org.apache.commons.codec.digest.DigestUtils;

import java.lang.instrument.Instrumentation;

/**

 * Date: 15-10-23
 * Time: 下午12:09
 */
public class Test {
    // 通过jvm参数加载agent
    public static void premain(String args, Instrumentation inst) {
        System.out.println("premain");
    }

    public static void main(String[] args) {
        String domain = "baina.bainajob";//即每个应用名，如/config/follow.soa/production中的follow.soa
        String key = DigestUtils.shaHex(domain + "===random===");//密码生成
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
