/**
 */
package io.bitjob.domain;

import java.io.Serializable;
import java.util.Date;

/**

 * @date
 */
public class ServerInstanceInfo implements Serializable {


    /**
     * 主键id
     */

    private Long id;


    /**
     * 服务实例IP
     */

    private String serverIp;


    /**
     * 服务实例路径
     */

    private String serverPath;


    /**
     * UUID
     */

    private String uuid;


    /**
     * 最后修改时间
     */

    private Date lastUpdate;


    /**
     * 创建时间
     */

    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }


    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}