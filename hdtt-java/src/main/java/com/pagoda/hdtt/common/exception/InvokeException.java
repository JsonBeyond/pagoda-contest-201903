package com.pagoda.hdtt.common.exception;

/**
 * @author Xxx
 * 业务异常信息
 */
public class InvokeException extends RuntimeException {

    private static final long serialVersionUID = 6401507641198338287L;

    /**
     * 异常代码
     */
    protected Integer code;

    /**
     * 异常消息
     */
    protected String message;

    /**
     * 数据信息
     */
    protected Object data;

    /**
     * 通用异常信息
     */
    private Object errorInfo;

    public InvokeException() {
        super();
    }

    public InvokeException(String message) {
        super(message);
        this.message = message;
    }

    public InvokeException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public InvokeException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message + " : " + cause.getMessage();
    }

//    public InvokeException(ErrorCode errorCode) {
//        super(errorCode.getMsg());
//        this.code = errorCode.getCode();
//        this.message = errorCode.getMsg();
//    }
//
//    public InvokeException(ErrorCode errorCode, Throwable cause) {
//        super(errorCode.getMsg(), cause);
//        this.code = errorCode.getCode();
//        this.message = errorCode.getMsg() + " : " + cause.getMessage();
//    }

    public InvokeException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public InvokeException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(Object errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "InvokeException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", errorInfo=" + errorInfo +
                '}';
    }
}
