package com.forever.zhb.utils.password;

import java.util.Base64;

public class Base64Util {
	
	/*按照RFC2045的定义，Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
	 * （The Base64 Content-Transfer-Encoding is designed 
	 * to represent arbitrary sequences of octets in a form that need not be humanly readable.） 
	 * 
	常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。 
	BASE64的加密解密是双向的，可以求反解。 
	*/
	
	
	/** 
	 * BASE64加密 
	 *     BASE加密后产生的字节位数是8的倍数，如果不够位数以=符号填充。
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encrypt(byte[] key) throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
	    return encoder.encodeToString(key);
	}  
	
	
	/** 
	 * BASE64解密 
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] decrypt(String key) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
	    return decoder.decode(key);
	}  
	
	public static void main(String[] args) throws Exception{
		System.out.println(encrypt("A".getBytes()));
	}
	  


}
