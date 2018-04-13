package com.forever.zhb.utils.password.symmetric;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

import com.forever.zhb.utils.password.PrintByteArray;

public class AESUtil extends PrintByteArray{

	/*
	 * AES (Advanced Encryption Standard) 高级加密标准，包括：MARS、RC6、Blowfish、Twofish
	 * 秘钥建立时间短、灵活性好、内存需求低等 是DES的替代者
	 * 
	 * 秘钥长度：128、192、256
	 */

	public static final String KEY_ALGORITHM = "AES";

	// 加密、解密模式/工作模式/填充方式
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	public static byte[] encrypt(String data, byte[] keys) throws Exception {
		byte[] bytes = data.getBytes();
		SecretKey sk = toKey(keys);

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, sk);

		return cipher.doFinal(bytes);
	}

	public static byte[] decrypt(byte[] datas, byte[] keys) throws Exception {
		SecretKey sk = toKey(keys);

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, sk);

		return cipher.doFinal(datas);
	}

	/*
	 * 生成秘钥 一般会经过Base64处理，易读
	 * 
	 * @return byte[] 秘钥长度：128、192、256
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(128);
		SecretKey sk = kg.generateKey();
		return sk.getEncoded();
	}

	public static SecretKey toKey(byte[] keys) {
		SecretKey sk = new SecretKeySpec(keys, KEY_ALGORITHM);
		return sk;
	}

	public static void main(String[] args) throws Exception {
		String data = "zhanghuibin";
		System.out.println("加密前：" + data);
		System.out.println("------------------------------");

		byte[] keys = initKey();// 生成key
		byte[] bytes = encrypt(data, keys);// 加密

		System.out.println("加密后：");
		printByte(bytes);

		System.out.println("------------------------------");

		// 解密
		byte[] decrypt = decrypt(bytes, keys);
		String decryptValue = new String(decrypt);
		System.out.println("解密后：" + decryptValue);

		System.out.println("--------------Base64处理----------------");
		String base64 = Base64.toBase64String(bytes);
		System.out.println(base64);
		byte[] temp = Base64.decode(base64);
		printByte(temp);
	}

}
