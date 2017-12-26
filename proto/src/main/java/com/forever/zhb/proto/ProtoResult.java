package com.forever.zhb.proto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;

public class ProtoResult  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3761115119777055445L;
	
	protected static Logger logger = LoggerFactory.getLogger(ProtoResult.class);
    private int callResult;
    private String errorMsg;
    private String javaClass;
    private byte[] protoBytes;

    public ProtoResult() {
        this.callResult = 0;
    }

    public static ProtoResult newSuccessProtoResult(String messageClassName, Message message) {
        ProtoResult rs = new ProtoResult();
        rs.setJavaClass(messageClassName);
        if (message == null) {
            rs.setCallResult(9);
        } else {
            rs.setCallResult(0);
            rs.setProtoBytes(message.toByteArray());
        }
        return rs;
    }

    public static ProtoResult newExceptionProtoResult(String messageClassName, Throwable e) {
        ProtoResult rs = new ProtoResult();
        rs.setJavaClass(messageClassName);
        rs.setCallResult(11);
        rs.setProtoBytes(null);
        if (e != null) {
            rs.setErrorMsg(e.getMessage());
        }
        logger.error("远程调用异常", e);
        return rs;
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

    public byte[] getProtoBytes() {
        return this.protoBytes;
    }

    public void setProtoBytes(byte[] protoBytes) {
        this.protoBytes = protoBytes;
    }

    public String getJavaClass() {
        return this.javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }


}
