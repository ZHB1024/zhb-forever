package com.forever.zhb.utils.password.asymmetric;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.forever.zhb.utils.password.PrintByteArray;

public class RSAUtil extends PrintByteArray{
	
	/**
	 * RSA (三位人名姓氏开头) ，私钥加密，公钥解密
	 * 
	 * 基于大数因子分解难题
	 * 
	 * 可用于数据加密、数字签名
	 * 
	 * 曾经被破解过
	 * 算法比较慢
	 * 
	 * 秘钥长度为：必须是64的倍数，默认1024，512-65536
	 */
	
	public static final String KEY_ALGORITHM = "RSA";// 加密算法


	public static final int KEY_SIZE = 512;// 秘钥长度，可以为512-1024 ，8的倍数

	public static final String PUBLIC_KEY = "RSAPublicKey";// 公钥

	public static final String PRIVATE_KEY = "RSAPrivateKey";// 私钥
	
	
	public static byte[] encrypt(String data,byte[] key) throws Exception{
		byte[] datas = data.getBytes();
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = kf.generatePrivate(pkcs8EncodedKeySpec);
		
		Cipher cipher = Cipher.getInstance(kf.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(datas);
	}
	
	public static byte[] decrypt(byte[] datas,byte[] key) throws Exception{
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pk = kf.generatePublic(x509EncodedKeySpec);
		
		Cipher cipher = Cipher.getInstance(kf.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pk);
		return cipher.doFinal(datas);
	}
	
	
	public static Map<String, Object> initKeyPair() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		kpg.initialize(KEY_SIZE);
		KeyPair kp = kpg.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
		Map<String, Object> keyPair = new HashMap<String, Object>();
		keyPair.put(PUBLIC_KEY,publicKey );
		keyPair.put(PRIVATE_KEY,privateKey );
		return keyPair;
	}
	
	public static byte[] getPublicKey(Map<String, Object> keyPair){
		Key key = (Key) keyPair.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	public static byte[] getPrivateKey(Map<String, Object> keyPair){
		Key key = (Key) keyPair.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	public static void main(String[] args) throws Exception{
		String data = "hello world";
		//初始化秘钥
		Map<String, Object> keyMap = initKeyPair();
		System.out.print("公钥：");
		printByte(getPublicKey(keyMap));
		System.out.print("秘钥：");
		printByte(getPrivateKey(keyMap));
		
		System.out.println("------------------------------------------");
		
		System.out.println("原文：" + data);
		//加密
		byte[] secret = encrypt(data, getPrivateKey(keyMap));
		System.out.print("密文：");
		printByte(secret);
		
		System.out.println("------------------------------------------");
		System.out.print("解密：");
		//解密
		byte[] decrypt = decrypt(secret, getPublicKey(keyMap));
		printByte(decrypt);
		System.out.println(new String(decrypt));
	}

}
