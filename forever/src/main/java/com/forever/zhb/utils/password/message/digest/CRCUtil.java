package com.forever.zhb.utils.password.message.digest;

import java.util.zip.CRC32;

public class CRCUtil {
	
	/*
	 * CRC32 (Cyclic Redundancy Check 循环冗余校验)
	 * 产生简短固定位数的一种散列函数
	 */

	
	/*
	 * 经过CRC32处理后，返回十六进制字符串
	 */
	public static String CRC32(String data){
		byte[] bytes = data.getBytes();
		CRC32 crc = new CRC32();
		crc.update(bytes);
		return Long.toHexString(crc.getValue());
	}

}
