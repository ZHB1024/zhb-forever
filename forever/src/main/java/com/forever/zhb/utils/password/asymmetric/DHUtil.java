package com.forever.zhb.utils.password.asymmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class DHUtil {

	/**
	 * DH (Diffie Hellman，秘钥交换算法) ，非对称加密的鼻祖
	 * 
	 * 基于有限域上的离散对数难题
	 * 
	 * 仅能用于秘钥分配，不能用于加密解密消息。
	 * 
	 * 它相比对称加密，多了密钥对的构建和本地秘钥的构建这两步骤，最后它使用的还是对称加密算法。
	 * 
	 * 秘钥长度为：64的倍数，在512-1024之间
	 */

	/**
	 * 定义使用的算法为:DH算法
	 */
	public static final String KEY_ALGORITHM = "DH";// 加密算法

	public static final String SECRET_ALGORITHM = "AES";// 对称加密算法

	public static final int KEY_SIZE = 512;// 秘钥长度，可以为512-1024 ，8的倍数

	public static final String PUBLIC_KEY = "DHPublicKey";// 公钥

	public static final String PRIVATE_KEY = "DHPrivateKey";// 私钥

	public static byte[] encrypt(String data, byte[] secretKey) throws Exception {
		SecretKey sk = new SecretKeySpec(secretKey, SECRET_ALGORITHM);

		Cipher cipher = Cipher.getInstance(sk.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		return cipher.doFinal(data.getBytes());
	}

	public static byte[] decrypt(byte[] datas, byte[] secretKey) throws Exception {
		SecretKey sk = new SecretKeySpec(secretKey, SECRET_ALGORITHM);

		Cipher cipher = Cipher.getInstance(sk.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, sk);
		return cipher.doFinal(datas);
	}

	public static Map<String, Object> initKeyPair() throws NoSuchAlgorithmException {
		Map<String, Object> keyMap = new HashMap<String, Object>();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		kpg.initialize(KEY_SIZE);
		KeyPair kp = kpg.generateKeyPair();
		DHPublicKey dhPublicKey = (DHPublicKey) kp.getPublic();
		DHPrivateKey dhPrivateKey = (DHPrivateKey) kp.getPrivate();
		keyMap.put(PUBLIC_KEY, dhPublicKey);
		keyMap.put(PRIVATE_KEY, dhPrivateKey);
		return keyMap;
	}

	public static Map<String, Object> initKeyPair(byte[] key)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException {
		X509EncodedKeySpec xedks = new X509EncodedKeySpec(key);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pk = kf.generatePublic(xedks);
		DHParameterSpec dhParameterSpec = ((DHPublicKey) pk).getParams();

		KeyPairGenerator kpg = KeyPairGenerator.getInstance(kf.getAlgorithm());
		kpg.initialize(dhParameterSpec);
		KeyPair kp = kpg.generateKeyPair();
		DHPublicKey dhPublicKey = (DHPublicKey) kp.getPublic();
		DHPrivateKey dhPrivateKey = (DHPrivateKey) kp.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, dhPublicKey);
		keyMap.put(PRIVATE_KEY, dhPrivateKey);
		return keyMap;
	}

	public static byte[] initSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey tempPublickey = kf.generatePublic(x509EncodedKeySpec);

		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
		PrivateKey tempPrivatekey = kf.generatePrivate(pkcs8EncodedKeySpec);

		KeyAgreement ag = KeyAgreement.getInstance(kf.getAlgorithm());
		ag.init(tempPrivatekey);
		ag.doPhase(tempPublickey, true);
		SecretKey sk = ag.generateSecret(SECRET_ALGORITHM);
		return sk.getEncoded();
	}
	
	public static byte[] getPublicKey(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	public static byte[] getPrivateKey(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	private static void printByte(byte[] bytes){
		for (byte b : bytes) {
			System.out.print(b);
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception{
		Map<String, Object> keyMap1 = initKeyPair();
		System.out.print("甲方公钥：");
		printByte(getPublicKey(keyMap1));
		System.out.print("甲方秘钥：");
		printByte(getPrivateKey(keyMap1));
		
		System.out.println("------------------------------------------");
		
		Map<String, Object> keyMap2 = initKeyPair(getPublicKey(keyMap1));
		System.out.print("乙方公钥：");
		printByte(getPublicKey(keyMap2));
		System.out.print("乙方秘钥：");
		printByte(getPrivateKey(keyMap2));
		
		System.out.println("------------------------------------------");
		
		byte[] secretKey1 = initSecretKey(getPublicKey(keyMap2), getPrivateKey(keyMap1));
		byte[] secretKey2 = initSecretKey(getPublicKey(keyMap1), getPrivateKey(keyMap2));
		System.out.print("甲方本地秘钥：");
		printByte(secretKey1);
		System.out.println("甲方本地秘钥：" + Base64.toBase64String(secretKey1));
		System.out.print("乙方本地秘钥：");
		printByte(secretKey2);
		System.out.println("乙方本地秘钥：" + Base64.toBase64String(secretKey2));
		
	}

}
