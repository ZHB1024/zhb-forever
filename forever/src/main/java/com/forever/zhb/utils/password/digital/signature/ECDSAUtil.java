package com.forever.zhb.utils.password.digital.signature;

import com.forever.zhb.utils.password.PrintByteArray;

public class ECDSAUtil extends PrintByteArray{
	
	/**
	 * ECDSA (Elliptic Curve Digital Signature Algorithm ，椭圆曲线数字签名算法)，数字签名
	 * 
	 * 基于离散对数问题
	 * 
	 * 速度快、强度高、签名短等
	 * 
	 */
	
	public static final String KEY_ALGORITHM = "ECDSA";// 加密算法
	
	public static final String SIGNATURE_ALGORITHM = "SHA512withECDSA";// 签名算法


	public static final String PUBLIC_KEY = "ECDSAPublicKey";// 公钥

	public static final String PRIVATE_KEY = "ECDSAPrivateKey";// 私钥

}
