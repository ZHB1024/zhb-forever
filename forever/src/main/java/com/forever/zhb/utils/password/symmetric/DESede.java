package com.forever.zhb.utils.password.symmetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.lang3.StringUtils;

public class DESede {
	
	
	public static final String KEY_ALGORITHM = "DESede";
	//加密、解密模式/工作模式/填充方式
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	
	
	/**
	 * 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data,String key) throws Exception{
		byte[] datas = data.getBytes();
		byte[] bytes = initKey(key);
		SecretKey sk = toKey(bytes);
		
		/*
		 * 使用PKCS7Padding 填充方式,bouncy castle 支持此方式
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
		*/
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		return cipher.doFinal(datas);
	}
	
	/**
	 * 解密
	 * 
	 * @param datas
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] datas,String key) throws Exception{
		byte[] bytes = initKey(key);
		SecretKey sk = toKey(bytes);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, sk);
		byte[] results = cipher.doFinal(datas);
		return new String(results);
	}
	
	/**
	 * 生成二进制密钥
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] initKey(String key) throws NoSuchAlgorithmException{
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom sr = null;
		if (StringUtils.isBlank(key)) {
			sr = new SecureRandom();
		}else{
			byte[] bytes = key.getBytes();
			sr = new SecureRandom(bytes);
		}
		kg.init(sr);
		SecretKey sk =  kg.generateKey();
		return sk.getEncoded();
	}
	
	/**
	 * 转换为密钥对象
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static SecretKey toKey(byte[] keys) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		if (null == keys) {
			return null;
		}
		
		DESedeKeySpec dks = new DESedeKeySpec(keys);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey sk = skf.generateSecret(dks);
		return sk;
	}
	
	public static void main(String[] args) throws Exception{
		String data = "hello world";
		String key = "ZHB1024";
		System.out.println("加密前：\n data:" + data + "\n key:" + key);
		byte[] bytes = encrypt(data, key);
		System.out.println("加密后：");
		for (byte b : bytes) {
			System.out.print(b);
		}
		System.out.println();
		
		System.out.println("解密后：");
		System.out.println(decrypt(bytes, key));
		
		
	}

}
