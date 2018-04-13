package com.forever.zhb.utils.password.digital.signature;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.forever.zhb.utils.password.PrintByteArray;

public class DSAUtil extends PrintByteArray{
	
	/**
	 * DSA ，数字签名
	 * 
	 * 基于离散对数问题
	 * 
	 * 签名长度与秘钥长度无关
	 * 
	 * 秘钥长度为：必须是64的倍数，512-1024,默认1024
	 */
	
	public static final String KEY_ALGORITHM = "DSA";// 加密算法
	
	public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";// 签名算法


	public static final int KEY_SIZE = 1024;// 秘钥长度，可以为512-1024 ，64的倍数

	public static final String PUBLIC_KEY = "DSAPublicKey";// 公钥

	public static final String PRIVATE_KEY = "DSAPrivateKey";// 私钥
	
	
	public static byte[] sign(String data,byte[] privateKey) throws Exception{
		byte[] datas = data.getBytes();
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey pk = kf.generatePrivate(pkcs8EncodedKeySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(pk);
		signature.update(datas);
		return signature.sign();
	}
	
	public static boolean verify(String data,byte[] publicKey,byte[] signs) throws Exception{
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pk = kf.generatePublic(x509EncodedKeySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pk);
		signature.update(data.getBytes());
		return signature.verify(signs);
	}
	
	
	public static Map<String, Object> initKeyPair() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		kpg.initialize(KEY_SIZE,new SecureRandom());
		KeyPair kp = kpg.genKeyPair();
		DSAPublicKey publicKey = (DSAPublicKey) kp.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) kp.getPrivate();
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
		//签名
		byte[] secret = sign(data, getPrivateKey(keyMap));
		System.out.print("签名：");
		printByte(secret);
		
		System.out.println("------------------------------------------");
		System.out.print("验证：");
		//验证
		System.out.println(verify(data, getPublicKey(keyMap),secret));
	}

}
