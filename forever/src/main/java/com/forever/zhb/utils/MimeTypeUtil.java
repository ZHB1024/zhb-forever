package com.forever.zhb.utils;

import java.io.File;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

public class MimeTypeUtil {

    /*MIME(Multipurpose Internet Mail Extensions)多用途互联网邮件扩展类型。
     * 描述消息内容类型的因特网标准
     * 消息能包含文本、图像、音频、视频以及其他应用程序专用的数据。
     * 是设定某种扩展名的文件用一种应用程序来打开的方式类型，当该扩展名文件被访问的时候，浏览器会自动使用指定应用程序来打开。
     * 多用于指定一些客户端自定义的文件名，以及一些媒体文件打开方式。*/

    public static String getMimeTypeByFormat(String format){
        if (StringUtil.isBlank(format)) {
            return "application/octet-stream";
        }
        switch (format.toLowerCase()) {

            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";

            case "png":
                return "image/png";
            case "jpg":
                return "image/jpeg";
            case "jpeg":
                return "image/jpeg";
            case "jpe":
                return "image/jpeg";
            case "gif":
                return "image/gif";

            case "mp2":
            case "mp3":
            case "mpeg2":
            case "mpeg3":
                return "audio/mpeg";

            case "mpeg":
            case "mpg":
            case "mpe":
                return "video/mpeg";
            case "mp4":
            case "mpeg4":
            case "mpg4":
                return "video/mp4";
            case "avi":
                return "video/x-msvideo";
            case "rm":
                return "audio/x-pn-realaudio";
            case "rmvb":
                return "audio/x-pn-realaudio";
            case "wmv":
                return "audio/x-ms-wmv";
            case "flv":
                return "flv-application/octet-stream";

            case "zip":
                return "application/zip";

            default:
                return "application/octet-stream";
        }
    }

    public static String getMimeType(String filePath){
        Magic parser = new Magic() ;
        MagicMatch match = null;
        try {
            match = parser.getMagicMatch(new File(filePath),false);
        } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(match.getMimeType()) ;
        return match.getMimeType();

    }



}
