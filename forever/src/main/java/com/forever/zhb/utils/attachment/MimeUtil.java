package com.forever.zhb.utils.attachment;

import org.apache.commons.lang3.StringUtils;

public class MimeUtil {
	
	/*MIME(Multipurpose Internet Mail Extensions)多用途互联网邮件扩展类型。
	 * 是设定某种扩展名的文件用一种应用程序来打开的方式类型，当该扩展名文件被访问的时候，浏览器会自动使用指定应用程序来打开。
	 * 多用于指定一些客户端自定义的文件名，以及一些媒体文件打开方式。*/
	
	public static String getContentTypeBySuffix(String suffix){
		if (StringUtils.isBlank(suffix)) {
			return "application/octet-stream";
		}
		switch (suffix.toLowerCase()) {
		case "":
			
			break;

		default:
			break;
		}
		
		return "application/octet-stream";
	}

}
