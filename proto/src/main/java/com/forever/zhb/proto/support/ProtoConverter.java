package com.forever.zhb.proto.support;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.forever.zhb.proto.ProtoResult;
import com.google.protobuf.Message;

public class ProtoConverter {
	
	public Message converFromProto(String className,ProtoResult rs) throws Exception {
		if (StringUtils.isBlank(className)) {
			return null;
		}
		//String callId = UUIDUtil.getRandomUUID();
		Class c = Class.forName(className);
		Method method = c.getMethod("parseFrom", byte[].class);
		if (null == method) {
			return null;
		}
		return (Message)method.invoke(c, rs.getProtoBytes());
	}
	
}
