package com.forever.zhb.utils.password.message.digest;

import java.security.MessageDigest;

public class SHAUtil {
	
	public static final String ALGORITHM = "SHA";
	public static final String CHAR_SET = "UTF-8";

	/*
	 * SHA(Secure Hash Algorithm，安全散列算法）,包含：SHA SHA-256  SHA-384  SHA-512等
	 * 数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。
	 * SHA与MD5通过碰撞法都被破解了，较之MD5更为安全。
	 */

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(ALGORITHM);
		return sha.digest(data.getBytes());

	}
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param bytes
	 *            字节数组
	 * @return
	 */
	public static String toHex(byte[] bytes) {
		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}

}
