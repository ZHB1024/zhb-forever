package com.forever.zhb.utils.password.symmetric;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang3.StringUtils;

public class DESUtil {

	/*
	 * DES：Data Encryption Standard,即数据加密算法。是IBM公司于1975年研究成功并公开发表的。
	 * DES算法的入口参数有三个:Key、Data、Mode。其中Key为8个字节共64位,是DES算法的工作密钥;
	 * Data也为8个字节64位,是要被加密或被解密的数据; Mode为DES的工作方式,有两种:加密或解密。
	 * DES算法把64位的明文输入块变为64位的密文输出块,它所使用的密钥也是64位。
	 * 
	 * 一般将秘钥处理为Base64或十六进制
	 */

	public static final String KEY_ALGORITHM = "DES";
	
	//加密、解密模式/工作模式/填充方式
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, String key) throws Exception {
		// 生成二进制秘钥
		byte[] bytes = initKey(key);
		// 转为秘钥对象
		Key k = toKey(bytes);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);

		byte[] datas = data.getBytes();
		return cipher.doFinal(datas);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		// 生成二进制秘钥
		byte[] bytes = initKey(key);
		// 转为秘钥对象
		Key k = toKey(bytes);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		
		return cipher.doFinal(data);
	}

	/**
	 * 生成密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		return initKey(null);
	}

	/**
	 * 生成二进制密钥
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static byte[] initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (StringUtils.isNotBlank(seed)) {
			secureRandom = new SecureRandom(seed.getBytes());
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return secretKey.getEncoded();
	}

	/**
	 * 转换为密钥对象
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		//实例化DES秘钥材料
		DESKeySpec dks = new DESKeySpec(key);
		
		//私密秘钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		//生成私密秘钥对象
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
		// SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

		return secretKey;
	}

	public static void main(String args[]) throws Exception {
		String data = "zhanghuibin";
		String password = "123456";
		System.out.println("加密前：" + data);
		
		//加密
		byte[] bytes = encrypt(data, password);
		System.out.println("加密后：" );
		for (byte b : bytes) {
			System.out.print(b);
		}
		
		//解密
		byte[] decrypt = decrypt(bytes, password);
		System.out.println();
		String decryptValue = new String(decrypt);
		System.out.println("解密后：" + decryptValue);
	}

}
