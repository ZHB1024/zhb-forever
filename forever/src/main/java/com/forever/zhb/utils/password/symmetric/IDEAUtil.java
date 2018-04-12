package com.forever.zhb.utils.password.symmetric;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class IDEAUtil {
	
	/*
	 * IDEA (International Data Encryption Algorithm，国际数据加密算法) 
	 * 
	 * 通过BouncyCastle来实现
	 * 
	 * 秘钥长度：128位
	 */
	
	public static final String KEY_ALGORITHM = "IDEA";

	// 加密、解密模式/工作模式/填充方式
	public static final String CIPHER_ALGORITHM = "IDEA/ECB/ISO10126Padding";
	
	public static byte[] encrypt(String data,byte[] key) throws Exception{
		byte[] bytes = data.getBytes();
		
		Security.addProvider(new BouncyCastleProvider());
		
		SecretKey sk = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		return cipher.doFinal(bytes);
	}
	
	public static byte[] decrypt(byte[] datas,byte[] key) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		
		SecretKey sk = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, sk);
		
		return cipher.doFinal(datas);
	}
	
	public static byte[] initKey() throws NoSuchAlgorithmException{
		Security.addProvider(new BouncyCastleProvider());
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(128);
		SecretKey sk = kg.generateKey();
		return sk.getEncoded();
	}
	
	public static SecretKey toKey(byte[] key){
		SecretKey sk = new SecretKeySpec(key,KEY_ALGORITHM);
		return sk;
	}
	
	public static void main(String[] args) throws Exception{
		String data = "zhanghuibin";
		System.out.println("加密前：" + data);
		System.out.println("------------------------------");

		byte[] keys = initKey();// 生成key
		byte[] bytes = encrypt(data, keys);// 加密

		System.out.println("加密后：");
		for (byte b : bytes) {
			System.out.print(b);
		}

		System.out.println();
		System.out.println("------------------------------");

		// 解密
		byte[] decrypt = decrypt(bytes, keys);
		String decryptValue = new String(decrypt);
		System.out.println("解密后：" + decryptValue);

		System.out.println("--------------Base64处理----------------");
		String base64 = Base64.toBase64String(bytes);
		System.out.println(base64);
		byte[] temp = Base64.decode(base64);
		for (byte b : temp) {
			System.out.print(b);
		}
	}


}
