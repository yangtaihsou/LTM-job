package io.bitjob.biz.service;

import com.google.common.collect.HashBasedTable;
import io.bitjob.dao.ServerInstanceInfoMapper;
import io.bitjob.domain.ServerInstanceInfo;
import io.bitjob.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 定时录入实例信息到数据库，按照实际实例数量，对任务做切片扫描，避免查询到同样的数据。

 * Date: 16-11-22
 * Time: 下午6:37
 */
@Service("serverInfoExplore")
public class ServerInfoExplore {

    private final static Logger log = LoggerFactory.getLogger(ServerInfoExplore.class);
    public Integer instanceCount = 1;
    private HashBasedTable<String, String, ServerInstanceInfo> serverInstanceInfoHbase;
    @Autowired
    private ServerInstanceInfoMapper serverInstanceInfoMapper;
    private Random random = new Random();
    private Integer index = random.nextInt(10);//设置改ip机器上的实例编号，用作切片。
    // （一般一个机器上会有1到3个实例，且index在每次重启时会变化。有一定概率，同一个ip机器的不同实例编号会一样。）

    @PostConstruct
    public void init() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    try {
                        updateServerInstanceInfo();
                        Thread.sleep(10 * 1000);//尽量等各个实例信息都初始化完毕。然后执行fisher抓取
                        fisher();
                    } catch (Exception e) {
                        log.error("加载实例信息报错", e);
                        try {
                            Thread.sleep(40 * 1000);//40秒休眠
                        } catch (InterruptedException e1) {
                            log.error("加载实例信息报错线程休眠失败", e1);
                        }
                    } finally {

                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void updateServerInstanceInfo() {
        try {
            ServerInstanceInfo serverInstanceInfo = new ServerInstanceInfo();
            //catalinaHome=/export/servers/tomcat6.0.33,catalinaBase=/export/Domains/job.com/server1
            //  String catalinaHome = System.getProperty("catalina.home");
            String catalinaBase = System.getProperty("catalina.base");//当前tomcat实例的路径。如果是jetty等容器，请使用别的方法得到
            if (StringUtils.isEmpty(catalinaBase)) {//如果没有取到实例路径，那么随机一个。
                catalinaBase = String.valueOf(index);
        }
        InetAddress addr = InetAddress.getLocalHost();
        String localIp = addr.getHostAddress();//本机ip
            serverInstanceInfo.setServerIp(localIp);
            serverInstanceInfo.setServerPath(catalinaBase);
            serverInstanceInfo.setUuid(localIp + "_" + catalinaBase);
            Query<ServerInstanceInfo> serverInstanceInfoQuery = new Query<ServerInstanceInfo>();
            serverInstanceInfoQuery.setQuery(serverInstanceInfo);
            List<ServerInstanceInfo> serverInstanceInfoList = serverInstanceInfoMapper.queryBySelective(serverInstanceInfoQuery);
            if (serverInstanceInfoList == null || serverInstanceInfoList.size() == 0) {
                serverInstanceInfoMapper.save(serverInstanceInfo);
            } else {
                ServerInstanceInfo exsitServerIInfo = serverInstanceInfoList.get(0);
                exsitServerIInfo.setUuid(null);
                exsitServerIInfo.setServerPath(null);
                exsitServerIInfo.setServerIp(null);
                exsitServerIInfo.setCreateTime(null);
                exsitServerIInfo.setLastUpdate(new Date());
                serverInstanceInfoMapper.updateByPrimaryKeySelective(exsitServerIInfo);
            }
        } catch (Exception e) {
            log.info("实例信息入库，发生错误:" + e.getMessage());
        }
    }

    private void fisher() {
        Query<ServerInstanceInfo> serverInstanceInfoQuery = new Query<ServerInstanceInfo>();
        ServerInstanceInfo serverInstanceInfo1 = new ServerInstanceInfo();
        Date date = DateUtils.addMinutes(new Date(), -5);//取得最近 十分钟的实例信息，防止有些实例已经下线。对用随机数取代实例地址的算法，尤其重要
        serverInstanceInfo1.setLastUpdate(date);
        serverInstanceInfoQuery.setQuery(serverInstanceInfo1);//
        List<ServerInstanceInfo> serverInstanceInfoList = serverInstanceInfoMapper.queryBySelective(serverInstanceInfoQuery);
        if (serverInstanceInfoList != null) {
            HashBasedTable<String, String, ServerInstanceInfo> serverInstanceInfoHbaseTemp = HashBasedTable.create();
            int serverCount = serverInstanceInfoList.size();
            //log.info("得到实例信息的个数:" + serverCount);
            for (int i = 0; i < serverCount; i++) {
                ServerInstanceInfo serverInstanceInfo = serverInstanceInfoList.get(i);
                serverInstanceInfo.setId(new Long(String.valueOf(i)));
                serverInstanceInfoHbaseTemp.put(serverInstanceInfo.getServerIp(), serverInstanceInfo.getServerPath(), serverInstanceInfo);
            }
            serverInstanceInfoHbase = serverInstanceInfoHbaseTemp;

            instanceCount = serverCount;
        }

    }

    public HashBasedTable<String, String, ServerInstanceInfo> getServerInstanceInfoHbase() {
        return serverInstanceInfoHbase;
    }

    public Integer getIndex() {
        return index;
    }
}
