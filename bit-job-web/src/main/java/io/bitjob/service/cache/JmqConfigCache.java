/*
package io.bitjob.service.cache;


import JmqConfigMapper;
import Constants;
import JmqConfig;
import AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**

 * Date: 15-5-12
 * Time: 上午11:13
 *//*

@Service("jmqConfigCache")
public class JmqConfigCache implements Cache {
    private final static Logger log = LoggerFactory.getLogger(JmqConfigCache.class);


    @Resource(name = "jmqConfigMapper")
    private JmqConfigMapper jmqConfigMapper;
    public Map<String, MessageProducer> producerOfAppMap = new HashMap<String, MessageProducer>();
    public Map<String, TransportManager> managerOfAppMap = new HashMap<String, TransportManager>();
    public Map<String, JmqConfig> jmqConfigOfAppMap = new HashMap<String, JmqConfig>();

    @PostConstruct
    public void init() {
        List<JmqConfig> timerTaskConfigList = jmqConfigMapper.queryBySelective(null);
        Map<String, JmqConfig> jmqConfigOfAppMapTemp = new HashMap<String, JmqConfig>();
        for (JmqConfig jmqConfig : timerTaskConfigList) {
            initProducer(jmqConfig);
            if (jmqConfig.getStatus() != null && jmqConfig.getStatus() == 1) {
                jmqConfigOfAppMapTemp.put(jmqConfig.getJmqApp(),jmqConfig);
            }
        }
        jmqConfigOfAppMap = jmqConfigOfAppMapTemp;
    }

    @Override
    public void reload() {
        this.init();
    }

    private void initProducer(JmqConfig jmqConfig) {
        String app = jmqConfig.getJmqApp();
        try {
            MessageProducer oldProducer = producerOfAppMap.get(app);
            if (oldProducer != null) {
                oldProducer.stop();
                TransportManager oldManager = managerOfAppMap.get(app);
                if (oldManager != null) {
                    oldManager.stop();
                }
            }
            if (jmqConfig.getStatus() == null || jmqConfig.getStatus() != 1) {       //用户取消了此app的使用

                producerOfAppMap.remove(app);
                return;
            }

            //连接配置
            TransportConfig config = new TransportConfig();
            config.setApp(app);
            //设置broker地址
            config.setAddress(jmqConfig.getJmqAddress());
            //设置用户名
            config.setUser(app);
            //设置密码
            config.setPassword(jmqConfig.getJmqPass());
            //设置发送超时
            config.setSendTimeout(200000);
            config.setConnectionTimeout(20000);
            config.setSoTimeout(20000);
            //设置是否使用epoll模式，windows环境下设置为false，linux环境下设置为true
            config.setEpoll(true);//TODO 判断什么环境

            //创建集群连接管理器
            TransportManager manager = new ClusterTransportManager(config);
            manager.start();

            //创建发送者
            MessageProducer producer = new MessageProducer(manager);
            producer.start();
            producerOfAppMap.put(app, producer);
            managerOfAppMap.put(app, manager);
        } catch (Exception e) {
            String info = app+"初始化jmq的producer报错："+e.getMessage();
            log.info(info);
            AlarmService.alarm(Constants.ALERT_KEY, info);
        }
    }
}
*/
