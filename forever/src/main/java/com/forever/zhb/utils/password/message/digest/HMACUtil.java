package com.forever.zhb.utils.password.message.digest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.forever.zhb.utils.password.Base64Util;

public class HMACUtil {
	
	public static final String ALGORITHM = "HmacMD5";
	public static final String CHAR_SET = "UTF-8";

	/*
	 * HMAC(Hash Message Authentication Code)，
	 * 结合了MD5和SHA的优势，并加入秘钥的支持
	 * 主要包含：HmacMD2 HmacMD4 HmacMD5  HmacSHA1  HmacSHA224 HmacSHA256 HmacSHA384 HmacSHA512 
	 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
	 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
	 * 
	 * 得到的MAC二进制可以用十六进制表示
	 */

	/**
	 * HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] bytes = secretKey.getEncoded();//这个就是秘钥
		return bytes;
	}

	/**
	 * HMAC加密 
	 *       生成摘要
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(String data) throws Exception {
		//生成秘钥
		byte[] key = initMacKey();
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		byte[] bytes = data.getBytes();
		return mac.doFinal(bytes);

	}

}
