package io.bitjob.export;

/**

 * Date: 14-6-23
 * Time: 下午9:40
 */
public class JobResult {

    public Boolean success;
    /**
     * 返回结果码
     **/
    public String code;
    /**
     * 返回结果信息
     **/
    public String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
