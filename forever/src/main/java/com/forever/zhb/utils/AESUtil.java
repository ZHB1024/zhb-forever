package com.forever.zhb.utils;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

	/**
	 * AES encryption and decryption tool.
	 * 
	 * @author zhanghuibin
     * @creation 2018-01-16
	 */
	protected static final Logger log = LoggerFactory.getLogger(AESUtil.class);

	private static byte[] initVector = { 0x32, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38, 0x27, 0x36, 0x35, 0x33, 0x23,
			0x32, 0x31 };

	/**
	 * FIXME For demo only, should rewrite this method in your product
	 * environment!
	 * 
	 * @param appid
	 * @return
	 */
	public static String findKeyById(String appid) {
		// Fake key.
		String key = "8f1c527dd2e244f88f071c8b6f8b152d";
		return key;
	}

	/**
	 * Encrypt the content with a given key using aes algorithm.
	 * 
	 * @param content
	 * @param key
	 *            must contain exactly 32 characters
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String key) throws Exception {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		}
		String encrypted = null;
		byte[] keyBytes = key.getBytes();
		if (keyBytes.length != 32 && keyBytes.length != 24 && keyBytes.length != 16) {
			throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
		}
		byte[] encryptedBytes = null;
		encryptedBytes = encrypt(content.getBytes(), keyBytes, initVector);
		encrypted = new String(Hex.encode(encryptedBytes));
		return encrypted;
	}

	/**
	 * Decrypt the content with a given key using aes algorithm.
	 * 
	 * @param content
	 * @param key
	 *            must contain exactly 32 characters
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content, String key) throws Exception {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		}
		String decrypted = null;
		byte[] encryptedContent = Hex.decode(content);
		byte[] keyBytes = key.getBytes();
		byte[] decryptedBytes = null;
		if (keyBytes.length != 32 && keyBytes.length != 24 && keyBytes.length != 16) {
			throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
		}
		decryptedBytes = decrypt(encryptedContent, keyBytes, initVector);
		decrypted = new String(decryptedBytes);
		return decrypted;
	}

	/**
	 * Encrypt data.
	 * 
	 * @param plain
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
		aes.init(true, ivAndKey);
		return cipherData(aes, plain);
	}

	/**
	 * Decrypt data.
	 * 
	 * @param cipher
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
		aes.init(false, ivAndKey);
		return cipherData(aes, cipher);
	}

	/**
	 * Encrypt or decrypt data.
	 * 
	 * @param cipher
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws Exception {
		int minSize = cipher.getOutputSize(data.length);
		byte[] outBuf = new byte[minSize];
		int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
		int length2 = cipher.doFinal(outBuf, length1);
		int actualLength = length1 + length2;
		byte[] result = new byte[actualLength];
		System.arraycopy(outBuf, 0, result, 0, result.length);
		return result;
	}

	public static void main(String[] args) throws Exception {
		AESUtil aesTool = new AESUtil();
		String appid = "canairport001";
		String key = aesTool.findKeyById(appid);
		String xml = "hello world!";
		String encrypted = aesTool.encrypt(xml, key);
		System.out.println("encrypted: \n" + encrypted);
		System.out.println("encrypted length: \n" + encrypted.length());
		String decrypted = aesTool.decrypt(encrypted, key);
		System.out.println("decrypted: \n" + decrypted);
		System.out.println("decrypted length: \n" + decrypted.length());
		boolean isSuccessful = StringUtils.equals(decrypted, xml);
		System.out.println(isSuccessful);
	}

}
