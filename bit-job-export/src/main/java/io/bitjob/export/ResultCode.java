package io.bitjob.export;

/**

 * Date: 14-6-20
 * Time: 下午3:37
 * 请求结果码
 */
public enum ResultCode {
    /**
     * 成功
     */
    Success("000000", "成功"),
    /**
     * 参数验证失败
     */
    DataValidateException("000001", "参数验证失败。"),
    /**
     * 该业务单号已存在
     */
    RfidExistException("000002", "该业务单号已存在。"),
    /**
     * 系统异常
     */
    SystemException("000003", "系统异常。");
    private final String code;
    private final String msg;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
