package com.forever.zhb.utils.password.message.digest;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class MD5Util {
	
	public static final String ALGORITHM = "MD5";
	public static final String CHAR_SET = "UTF-8";

	/*
	 * MD5 -- message-digest algorithm 5 （信息-摘要算法）缩写，包含 MD2 MD4 MD5等
	 * 已不安全，虽然是单向加密，但是利用穷举法却可以找到明文
	 * 常用于数据文件完整性、一致性校验、数字签名。
	 * 下载软件时，链接旁边放着MD5的串。，就是用来验证文件是否一致的。
	 * 
	 * 通常我们不直接使用上述MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串。 
	 */
	
	
	
	/*
	 * 
	 * password经过MD5处理后，变为128位，16个字节，称为数字指纹
	 * 
	 */
	public static String MD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] bytes = md.digest(password.getBytes(CHAR_SET));//bytes长度为16
			//将二进制转为十六进制
			return toHex(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 * inputStream经过MD5处理后
	 * 
	 */
	public static String InputStream2MD5(InputStream is) {
		try {
			DigestInputStream dis = new DigestInputStream(is, MessageDigest.getInstance(ALGORITHM));
			int buf = 1024;
			byte[] buffer = new byte[buf];
			while ((dis.read(buffer, 0, buf)>-1)) {
			}
			dis.close();
			MessageDigest md = dis.getMessageDigest();
			byte[] bytes = md.digest();
			//将二进制转为十六进制
			return toHex(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	public static void main(String[] args) {
		String password = "zhanghuibin";
		System.out.println(password);
		System.out.println(MD5(password));
	}

}
