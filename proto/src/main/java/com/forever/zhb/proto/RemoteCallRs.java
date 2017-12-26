package com.forever.zhb.proto;

import java.io.Serializable;

import com.forever.zhb.proto.exception.ProtoCallException;

public class RemoteCallRs<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -446910768718377067L;
	
    public static final int CALLRESULT_SUCCESS = 0;
    public static final int CALLRESULT_ILLEAGALPARAM = 1;
    public static final int CALLRESULT_NOACCESS = 2;
    public static final int CALLRESULT_DBERROR = 3;
    public static final int CALLRESULT_TOOMANYACCESS = 4;
    public static final int CALLRESULT_SERVICE_UNAVAILABLE = 5;
    public static final int CALLRESULT_SERVICE_IN_MIANTENANCE = 6;
    public static final int CALLRESULT_NETWORK_ERROR = 7;
    public static final int CALLRESULT_PROTOCOL_ERROR = 8;
    public static final int CALLRESULT_SUCESS_EMPTY = 9;
    public static final int NULLCALLRS = 10;
    public static final int CALLRESULT_EXCEPTION = 11;
    public static final int CALLRESULT_OTHERERROR = 99;
    private int callResult;
    private String errorMsg;
    T value;

    public RemoteCallRs() {
        this.callResult = 0;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getCallResult() {
        return this.callResult;
    }

    public void setCallResult(int callResult) {
        this.callResult = callResult;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getSuccRsValue() {
        if ((0 == getCallResult()) || (9 == getCallResult())) {
            return getValue();
        }
        ProtoCallException e = new ProtoCallException(getErrorMsg() + ":" + getCallResult());
        e.setErrorCode(getCallResult());
        throw e;
    }

}
