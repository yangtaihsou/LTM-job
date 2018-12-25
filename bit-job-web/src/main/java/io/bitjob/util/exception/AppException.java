package io.bitjob.util.exception;

/**

 * Date: 14-12-4
 * Time: 下午2:21
 */
public class AppException extends Exception {
    private String code;
    private String message;

    public AppException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
