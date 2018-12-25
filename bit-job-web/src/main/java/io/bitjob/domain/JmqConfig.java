/**
 */
package io.bitjob.domain;

import java.io.Serializable;
import java.util.Date;

/**

 * @date
 */
public class JmqConfig implements Serializable {


    /**
     *
     */

    private Long id;


    /**
     * jmq的app
     */

    private String jmqApp;


    /**
     *
     */

    private String jmqPass;


    /**
     * jmq地址
     */

    private String jmqAddress;


    /**
     * 平台系统编码
     */

    private String systemCode;


    /**
     * 平台系统名字
     */

    private String systemName;


    /**
     * 1有效0无效
     */

    private Integer status;


    /**
     *
     */

    private Date createDate;


    /**
     *
     */

    private Date updateDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getJmqApp() {
        return jmqApp;
    }

    public void setJmqApp(String jmqApp) {
        this.jmqApp = jmqApp;
    }


    public String getJmqPass() {
        return jmqPass;
    }

    public void setJmqPass(String jmqPass) {
        this.jmqPass = jmqPass;
    }


    public String getJmqAddress() {
        return jmqAddress;
    }

    public void setJmqAddress(String jmqAddress) {
        this.jmqAddress = jmqAddress;
    }


    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }


    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


}