package com.forever.zhb.proto.exception;

public class ProtoCallException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
    private int errorCode;

    public ProtoCallException() {
    }

    public ProtoCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtoCallException(String message) {
        super(message);
    }

    public ProtoCallException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
