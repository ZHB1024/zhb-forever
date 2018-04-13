package com.forever.zhb.utils.password.asymmetric;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.forever.zhb.utils.password.PrintByteArray;

public class ElGamalUtil extends PrintByteArray{
	
	/**
	 * ElGamal (人名) ，公钥加密，私钥解密
	 * 
	 * 基于离散对数问题
	 * 
	 * 可用于数据加密、数字签名
	 * 
	 * 不足：密文会成倍扩张
	 * 
	 * java 不支持该算法，可以采用bouncy castle
	 * 
	 * 秘钥长度为：必须是8的倍数，160-16384,默认1024
	 */
	
	
	public static final String KEY_ALGORITHM = "ElGamal";// 加密算法


	public static final int KEY_SIZE = 256;// 秘钥长度，可以为160-16384 ，8的倍数

	public static final String PUBLIC_KEY = "ElGamalPublicKey";// 公钥

	public static final String PRIVATE_KEY = "ElGamalPrivateKey";// 私钥
	
	
	/*
	 * 使用公钥对data加密
	 * 
	 * 
	 */
	public static byte[] encrypt(String data,byte[] publicKey) throws Exception{
		byte[] datas = data.getBytes();
		
		Security.addProvider(new BouncyCastleProvider());
		
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pk = kf.generatePublic(x509EncodedKeySpec);
		
		Cipher cipher = Cipher.getInstance(kf.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		
		return cipher.doFinal(datas);
	}
	
	/*
	 * 使用私钥对datas解密
	 * 
	 * 
	 */
	public static byte[] decrypt(byte[] datas,byte[] privateKey) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey pk = kf.generatePrivate(pkcs8EncodedKeySpec);
		
		Cipher cipher = Cipher.getInstance(kf.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pk);
		return cipher.doFinal(datas);
	}
	
	
	public static Map<String, Object> initKeyPair() throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		
		AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance(KEY_ALGORITHM);
		apg.init(KEY_SIZE);
		AlgorithmParameters ap = apg.generateParameters();
		DHParameterSpec dhParameterSpec = ap.getParameterSpec(DHParameterSpec.class);
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		kpg.initialize(dhParameterSpec, new SecureRandom());
		KeyPair kp = kpg.genKeyPair();
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();
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
		byte[] secret = encrypt(data, getPublicKey(keyMap));
		System.out.print("密文：");
		printByte(secret);
		
		System.out.println("------------------------------------------");
		System.out.print("解密：");
		//解密
		byte[] decrypt = decrypt(secret, getPrivateKey(keyMap));
		printByte(decrypt);
		System.out.println(new String(decrypt));
	}

}
